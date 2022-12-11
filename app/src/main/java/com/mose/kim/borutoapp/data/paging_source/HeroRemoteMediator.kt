package com.mose.kim.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mose.kim.borutoapp.data.local.BorutoDatabase
import com.mose.kim.borutoapp.data.remote.BorutoApi
import com.mose.kim.borutoapp.domain.model.Hero
import com.mose.kim.borutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    // api로 데이터 받은 후 직접적으로 database에 저장하기 위함
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
): RemoteMediator<Int, Hero>() { // RemoteMediato<key->id, value->retireve value(hero)>

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    /*
     1. 네트워크에서 어던 데이터를 로드할 지 결정
     2. 네트워크 요청을 트리거
     3. 로드 결과의 따른 작업 수행 (성공 후 마지막 페이지 여부 / 실패 여부 )
     */
    override suspend fun load(
        loadType: LoadType, //  로드 유형 : REFRESH / APPEND / PREPEND https://developer.android.com/reference/kotlin/androidx/paging/LoadType#enum-values_1
        state: PagingState<Int, Hero> // 지금까지 로드된 페이징 시스템 정보를 포함하는 스냅샷
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPositions(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    // PREPEND 일 때 prePage가 없으면 load 함수에서 MediatorResul 반환
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLatItem(state)
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextPage
            }
        }
        return try {
            // request to server
            val response = borutoApi.getAllHeroes(page = page)
            if(response.heroes.isNotEmpty()) {
                borutoDatabase.withTransaction {
                    // loadType의 따른 데이터베이스 설정
                    if(loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKey()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys: List<HeroRemoteKeys> = response.heroes.map {
                        HeroRemoteKeys(
                            id = it.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    heroRemoteKeysDao.addAllRemoteKey(keys)
                    heroDao.addHeroes(heroes = response.heroes)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPositions(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        // anchorPosition: 리스트에서 가장 많이 접근된 인덱스
        return state.anchorPosition?.let { position ->
            // anchorPosition이 null이 아니라면 포지션 찾음
            state.closestItemToPosition(position)?.id?.let { id ->
                // id가 null이 아니면 RemoteKeys를 가져옴
                heroRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    // loadType => PREPEND : First 데이터 가져옴
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Hero>
    ): HeroRemoteKeys? {
        // pages: Loaded pages of data in the list, pages: List<Page<Int, Hero>>,
        // 로드된 pages 중에서 첫 page 가져옴
        return state.pages
            .firstOrNull { // pages의 data가 null 이 아니면 first page 가져옴
                it.data.isNotEmpty() // return page
            }?.data?.firstOrNull() // data(Page<Int, Hero>)의 first item(Hero)을 가져 옴
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(id = hero.id)
            }
    }

    private suspend fun getRemoteKeysForLatItem(
        state: PagingState<Int, Hero>)
    : HeroRemoteKeys? {
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()
            ?.let { hero -> heroRemoteKeysDao.getRemoteKeys(id = hero.id) }
    }
}


























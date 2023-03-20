package com.mose.kim.borutoapp.domain.model

import kotlinx.serialization.Serializable

// JSON Reponse to API Response
@Serializable
data class ApiResponse (
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<Hero> = emptyList(),
    val lastUpdated: Long? = null
)
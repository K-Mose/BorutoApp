package com.mose.kim.borutoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double
) {
    // Svg로 위젯 만들기
    val starString = stringResource(id = R.string.start_path)
    // String to Path
    val starPath = remember {
        PathParser().parsePathString(pathData = starString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    
    FilledStart(startPath = starPath, starPathBounds = starPathBounds)
}

@Composable
fun FilledStart(
    startPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float = 2f
) {
    Canvas(
        modifier = Modifier
            .size(100.dp)
    ) {
        // canvas size를 저장
        val canvasSize = this.size

        // draw의 scale 변경
        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            // 중앙 위치 canvasSize/2 - pathSize/2
            val left = canvasSize.width/2 - pathWidth/2
            val top = canvasSize.height/2 - pathHeight/2


            // canvas 내에서 위치 변경
            translate(
                left = left,
                top = top
            ){
                drawPath(
                    path = startPath,
                    color = StarColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledStartPreview() {
    RatingWidget(modifier = Modifier, rating = 5.0)
}
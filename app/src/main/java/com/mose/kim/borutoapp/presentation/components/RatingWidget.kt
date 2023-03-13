package com.mose.kim.borutoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.ui.theme.LightGray
import com.mose.kim.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2f
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

    FilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = scaleFactor
    )
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        // canvas size를 저장
        val canvasSize = this.size

        // draw의 scale 변경
        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            // 중앙 위치 canvasSize/2 - pathSize/2
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)


            // canvas 내에서 위치 변경
            translate(
                left = left,
                top = top
            ){
                drawPath(
                    path = starPath,
                    color = StarColor,
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        val canvasSize = this.size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)


            // canvas 내에서 위치 변경
            translate(
                left = left,
                top = top
            ){
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f),
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
    ) {
        val canvasSize = this.size

        scale(scale = scaleFactor) {
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)

            translate(
                left = left,
                top = top
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(0.5f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilledStarPreview() {
    RatingWidget(modifier = Modifier, rating = 5.0, scaleFactor = 3.0f)
}

@Preview(showBackground = true)
@Composable
private fun HalfFilledStarPreview() {
    val starString = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    HalfFilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3.0f
    )
}
@Preview(showBackground = true)
@Composable
private fun EmptyStarPreview() {
    val starString = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    EmptyStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3.0f
    )
}
package com.mose.kim.borutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.mose.kim.borutoapp.ui.theme.LightGray
import com.mose.kim.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val result = calculateStars(rating = rating)
    // Svg로 위젯 만들기
    val starString = stringResource(id = R.string.start_path)
    // String to Path
    val starPath = remember {
        PathParser().parsePathString(pathData = starString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it) {
                FilledStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result["halfFilledStars"]?.let {
            repeat(it) {
                HalfFilledStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
        result["emptyStars"]?.let {
            repeat(it) {
                EmptyStar(starPath = starPath, starPathBounds = starPathBounds, scaleFactor = scaleFactor)
            }
        }
    }
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

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableStateOf(5)}
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".")
            .map{ it.toInt()}

        if(firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) halfFilledStars = 1
            if (lastNumber in 6..9) filledStars ++
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d("RatingWidget:: ", "Invalid rating number")
        }

    }
    emptyStars = maxStars - (filledStars + halfFilledStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
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
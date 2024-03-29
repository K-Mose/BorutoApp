package com.mose.kim.borutoapp.presentation.common

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import com.mose.kim.borutoapp.R
import com.mose.kim.borutoapp.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.mose.kim.borutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen (
    error: LoadState.Error
){
    // error message 정의
    val message by remember {
        mutableStateOf(parseErrorMessage(message = error.toString()))
    }
    // error icon 정의
    val icon by remember {
        mutableStateOf(R.drawable.network_error)
    }
    // animation
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }
    EmptyContent(
        alphaAnim = alphaAnim,
        icon = icon,
        message = message
    )
}

@Composable
fun EmptyContent(
    alphaAnim: Float,
    icon: Int,
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(NETWORK_ERROR_ICON_HEIGHT)
                .alpha(alpha = alphaAnim),
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.network_error_icon),
            tint = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
        )
        Text(
            text = message,
            modifier = Modifier
                .padding(all = SMALL_PADDING)
                .alpha(alpha = alphaAnim),
            color = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
    }
}

fun parseErrorMessage(message: String): String = when {
    message.contains("SocketTimeoutException") -> {
        "Server Unavailable."
    }
    message.contains("ConnectException") -> {
        "Internet Unavailable."
    }
    else -> "Unknown Error."
}

@Composable
@Preview(showBackground = true)
fun EmptyScreenPreview1() {
    EmptyContent(1f, R.drawable.network_error, "SocketTimeoutException")
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun EmptyScreenPreview2() {
    EmptyContent(1f, R.drawable.network_error, "ConnectException")
}
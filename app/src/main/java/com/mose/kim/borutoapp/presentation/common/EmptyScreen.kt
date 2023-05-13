package com.mose.kim.borutoapp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(NETWORK_ERROR_ICON_HEIGHT),
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.network_error_icon),
            tint = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
        )
        Text(
            text = message,
            modifier = Modifier.padding(all = SMALL_PADDING) ,
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
    EmptyScreen(error = LoadState.Error(SocketTimeoutException()))
}

@Composable
@Preview(showBackground = true)
fun EmptyScreenPreview2() {
    EmptyScreen(error = LoadState.Error(ConnectException()))
}
package io.getstream.chat.android.twitchclone.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
public fun TwitchLoadingIndicator() {
  Box(
    modifier = Modifier
        .fillMaxSize()
        .background(ChatTheme.colors.appBackground)
  ) {
    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
  }
}

/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.chat.android.twitchclone.livestream

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.twitchclone.messages.ui.messages.TwitchMessage
import io.getstream.video.android.compose.ui.components.livestream.LivestreamPlayer
import kotlinx.coroutines.launch

@Composable
fun LivestreamWatcher(
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  livestreamViewModel: LivestreamViewModel = hiltViewModel()
) {
  val uiState by livestreamViewModel.livestreamUiState.collectAsState()

  val scope = rememberCoroutineScope()
  EnsureVideoCallPermissions {
    scope.launch {
      livestreamViewModel.joinCall(type = "livestream", id = "streamer")
    }
  }
  DisposableEffect(Unit) {
    onDispose {
      val call = (uiState as? LivestreamUiState.Success)?.call
      call?.leave()
    }
  }

  LivestreamWatcherContent(
    listViewModel = listViewModel,
    composerViewModel = composerViewModel,
    uiState = uiState
  )
}

@Composable
private fun LivestreamWatcherContent(
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  uiState: LivestreamUiState
) {
  if (uiState is LivestreamUiState.Success) {
    val call = uiState.call
    DisposableEffect(key1 = call.id) {
      onDispose { call.leave() }
    }
  }

  when (uiState) {
    is LivestreamUiState.Success -> {
      TwitchMessage(
        listViewModel = listViewModel,
        composerViewModel = composerViewModel,
        streamRenderer = {
          LivestreamPlayer(
            modifier = Modifier
              .fillMaxWidth()
              .fillMaxHeight(0.3f),
            call = uiState.call
          )
        }
      )
    }

    LivestreamUiState.Error -> {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(ChatTheme.colors.appBackground)
      ) {
        Text(
          modifier = Modifier.align(Alignment.Center),
          text = stringResource(id = R.string.livestream_joining_failed),
          color = ChatTheme.colors.textHighEmphasis,
          fontSize = 12.sp
        )
      }
    }

    LivestreamUiState.Loading -> {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(ChatTheme.colors.appBackground)
      ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
    }
  }
}

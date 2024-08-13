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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PausePresentation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.twitchclone.messages.ui.messages.TwitchMessage
import io.getstream.chat.android.twitchclone.model.streamerAvatarLink
import io.getstream.chat.android.twitchclone.model.streamerName
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.ui.components.livestream.LivestreamPlayerOverlay
import io.getstream.video.android.compose.ui.components.video.VideoRenderer
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.RealtimeConnection
import kotlinx.coroutines.launch

@Composable
fun LivestreamStreamer(
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

  LivestreamStreamerContent(
    listViewModel = listViewModel,
    composerViewModel = composerViewModel,
    uiState = uiState
  )
}

@Composable
private fun LivestreamStreamerContent(
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
          StreamRenderer(
            modifier = Modifier
              .fillMaxWidth()
              .fillMaxHeight(0.50f),
            call = uiState.call,
            listViewModel = listViewModel,
            composerViewModel = composerViewModel
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

@Composable
private fun StreamRenderer(
  modifier: Modifier,
  call: Call,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel
) {
  LaunchCallPermissions(call = call)

  val context = LocalContext.current
  val connection by call.state.connection.collectAsState()
  val backstage by call.state.backstage.collectAsState()
  val localParticipant by call.state.localParticipant.collectAsState()
  val video = localParticipant?.video?.collectAsState()?.value
  val scope = rememberCoroutineScope()

  Scaffold(
    modifier = modifier
      .background(ChatTheme.colors.appBackground)
      .padding(6.dp),
    contentColor = ChatTheme.colors.appBackground,
    backgroundColor = ChatTheme.colors.appBackground,
    topBar = {
      Box(modifier = Modifier.fillMaxWidth()) {
        StreamerInformation(
          modifier = Modifier.align(Alignment.CenterStart),
          streamerAvatarImage = listViewModel.channel.streamerAvatarLink,
          streamerName = listViewModel.channel.streamerName
        )

        Button(
          modifier = Modifier.align(Alignment.TopEnd),
          colors = ButtonDefaults.buttonColors(
            contentColor = ChatTheme.colors.primaryAccent,
            backgroundColor = ChatTheme.colors.primaryAccent
          ),
          onClick = {
            scope.launch {
              if (backstage) {
                call.goLive()

                val message = composerViewModel.buildNewMessage(
                  message = context.getString(R.string.livestream_live_started)
                )

                composerViewModel.sendMessage(message)
              } else {
                call.stopLive()

                val message = composerViewModel.buildNewMessage(
                  message = context.getString(R.string.livestream_live_end)
                )
                composerViewModel.sendMessage(message)
              }
            }
          }
        ) {
          if (backstage) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              tint = Color.White,
              contentDescription = null
            )
          } else {
            Icon(
              imageVector = Icons.Default.PausePresentation,
              tint = Color.White,
              contentDescription = null
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Text(
            text = if (backstage) {
              stringResource(id = R.string.livestream_go_live)
            } else {
              stringResource(id = R.string.livestream_go_backstage)
            },
            color = ChatTheme.colors.textHighEmphasis
          )
        }
      }
    },
    bottomBar = {
      if (connection == RealtimeConnection.Connected) {
        if (!backstage) {
          Box(
            modifier = Modifier.fillMaxWidth()
          ) {
            LivestreamPlayerOverlay(call = call)
          }
        } else {
          Snackbar {
            Text(
              text = stringResource(id = R.string.livestream_backstage),
              color = ChatTheme.colors.textHighEmphasis
            )
          }
        }
      } else if (connection is RealtimeConnection.Failed) {
        Text(
          text = "Connection failed",
          color = ChatTheme.colors.textHighEmphasis
        )
      }
    }
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      VideoRenderer(
        modifier = Modifier
          .fillMaxSize()
          .padding(
            start = it.calculateStartPadding(
              LayoutDirection.Ltr
            ),
            end = it.calculateEndPadding(LayoutDirection.Ltr)
          )
          .clip(RoundedCornerShape(6.dp)),
        call = call,
        video = video,
        videoFallbackContent = {
          Text(stringResource(id = R.string.livestream_rendering_failed))
        }
      )
    }
  }
}

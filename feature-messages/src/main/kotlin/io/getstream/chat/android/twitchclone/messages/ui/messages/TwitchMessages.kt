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

package io.getstream.chat.android.twitchclone.messages.ui.messages

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.messages.list.MessagesLazyListState
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.util.rememberMessageListState
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.messages.ui.player.TwitchVideoPlayer
import io.getstream.chat.android.ui.common.state.messages.list.MessageItemState
import kotlinx.coroutines.launch

@Composable
fun TwitchMessage(
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  streamRenderer: (@Composable () -> Unit)? = null
) {
  val orientation = LocalConfiguration.current.orientation
  var isChannelDescriptionExpanded by remember { mutableStateOf(false) }

  if (orientation == Configuration.ORIENTATION_PORTRAIT) {
    PortraitTwitchMessage(
      listViewModel = listViewModel,
      composerViewModel = composerViewModel,
      isChannelDescriptionExpanded = isChannelDescriptionExpanded,
      onChannelDescriptionExpanded = { isChannelDescriptionExpanded = it },
      streamRenderer = streamRenderer

    )
  } else {
    LandscapeTwitchMessage(
      listViewModel = listViewModel,
      composerViewModel = composerViewModel,
      isChannelDescriptionExpanded = isChannelDescriptionExpanded,
      onChannelDescriptionExpanded = { isChannelDescriptionExpanded = it },
      streamRenderer = streamRenderer
    )
  }
}

@Composable
private fun PortraitTwitchMessage(
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  isChannelDescriptionExpanded: Boolean,
  onChannelDescriptionExpanded: (Boolean) -> Unit,
  streamRenderer: (@Composable () -> Unit)? = null
) {
  val context = LocalContext.current
  val state = rememberMessageListState()
  val scope = rememberCoroutineScope()

  Scaffold(
    modifier = Modifier.background(color = ChatTheme.colors.appBackground),
    bottomBar = {
      TwitchBottomBarContent(
        listViewModel = listViewModel,
        composerViewModel = composerViewModel
      ) {
        scope.launch { state.lazyListState.animateScrollToItem(0) }
      }
    }
  ) { paddingValues ->

    Column(
      Modifier
        .fillMaxSize()
        .background(ChatTheme.colors.appBackground)
    ) {
      streamRenderer?.invoke() ?: TwitchVideoPlayer(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(0.3f),
        listViewModel = listViewModel
      ) {
        onChannelDescriptionExpanded.invoke(!isChannelDescriptionExpanded)
      }

      if (isChannelDescriptionExpanded) {
        ChannelDescription(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp, max = 120.dp)
            .padding(horizontal = 8.dp)
            .background(ChatTheme.colors.appBackground),
          channel = listViewModel.channel
        ) {
          onChannelDescriptionExpanded.invoke(false)
          Toast.makeText(context, R.string.channel_follow, Toast.LENGTH_SHORT).show()
        }
      }

      StitchMessagesContent(
        modifier = Modifier.padding(
          start = 8.dp,
          end = 8.dp,
          bottom = paddingValues.calculateBottomPadding()
        ),
        state = state,
        listViewModel = listViewModel
      )
    }
  }
}

@Composable
private fun LandscapeTwitchMessage(
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  isChannelDescriptionExpanded: Boolean,
  onChannelDescriptionExpanded: (Boolean) -> Unit,
  streamRenderer: (@Composable () -> Unit)? = null
) {
  val context = LocalContext.current
  val state = rememberMessageListState()
  val scope = rememberCoroutineScope()

  Row(modifier = Modifier.fillMaxSize()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .weight(0.65f)
    ) {
      streamRenderer?.invoke() ?: TwitchVideoPlayer(
        modifier = Modifier.fillMaxSize(),
        listViewModel = listViewModel
      ) {
        onChannelDescriptionExpanded.invoke(!isChannelDescriptionExpanded)
      }

      if (isChannelDescriptionExpanded) {
        ChannelDescription(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(6.dp)
            .width(300.dp)
            .heightIn(min = 54.dp, max = 120.dp)
            .background(ChatTheme.colors.appBackground)
            .padding(horizontal = 16.dp, vertical = 6.dp),
          channel = listViewModel.channel
        ) {
          onChannelDescriptionExpanded.invoke(false)
          Toast.makeText(context, R.string.channel_follow, Toast.LENGTH_SHORT).show()
        }
      }
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .weight(0.35f)
    ) {
      Scaffold(
        bottomBar = {
          TwitchBottomBarContent(
            listViewModel = listViewModel,
            composerViewModel = composerViewModel
          ) {
            scope.launch { state.lazyListState.animateScrollToItem(0) }
          }
        }
      ) {
        StitchMessagesContent(
          modifier = Modifier
            .background(color = ChatTheme.colors.appBackground)
            .weight(0.88f)
            .padding(start = 6.dp, top = 6.dp, end = 6.dp, bottom = it.calculateBottomPadding()),
          state = state,
          listViewModel = listViewModel
        )
      }
    }
  }
}

@Composable
private fun StitchMessagesContent(
  modifier: Modifier = Modifier,
  state: MessagesLazyListState,
  listViewModel: MessageListViewModel
) {
  MessageList(
    modifier = modifier
      .fillMaxSize()
      .background(color = ChatTheme.colors.appBackground),
    viewModel = listViewModel,
    messagesLazyListState = state
  ) {
    if (it is MessageItemState) {
      TwitchMessageItem(messageItemState = it)
    }
  }
}

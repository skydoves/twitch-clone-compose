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

package io.getstream.chat.android.twitchclone.channels

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.twitchclone.channels.grids.GridChannelList
import io.getstream.chat.android.twitchclone.model.createMockChannels
import io.getstream.chat.android.twitchclone.model.createStreamerChannel
import io.getstream.chat.android.twitchclone.navigation.TwitchScreens
import io.getstream.chat.android.twitchclone.navigation.currentComposeNavigator
import kotlinx.coroutines.launch

@Composable
fun TwitchChannels(
  channelListViewModel: ChannelListViewModel
) {
  val navigator = currentComposeNavigator
  val channels = channelListViewModel.channelsState.channelItems
  val isLoading = channelListViewModel.channelsState.isLoading
  val orientation = LocalConfiguration.current.orientation
  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = channels, key2 = isLoading) {
    if (!isLoading && channels.isEmpty()) {
      ChatClient.instance().createMockChannels()
    }
  }

  if (!isLoading && channels.isNotEmpty()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(ChatTheme.colors.appBackground)
    ) {
      Box(
        modifier = Modifier
          .padding(12.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = "Live Channels",
          color = ChatTheme.colors.textHighEmphasis,
          fontWeight = FontWeight.ExtraBold,
          lineHeight = 34.sp,
          fontSize = 28.sp
        )

        Box(
          modifier = Modifier
            .size(32.dp)
            .align(Alignment.CenterEnd)
            .background(color = ChatTheme.colors.primaryAccent, shape = CircleShape)
        ) {
          Icon(
            modifier = Modifier
              .align(Alignment.Center)
              .clickable {
                scope.launch {
                  val channel = ChatClient
                    .instance()
                    .createStreamerChannel() ?: return@launch
                  navigator.navigate(TwitchScreens.LivestreamStreamer.createRoute(channel.cid))
                }
              },
            imageVector = Icons.Default.VideoCall,
            tint = Color.White,
            contentDescription = null
          )
        }
      }
      if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        ChannelList(
          modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground),
          viewModel = channelListViewModel,
          channelContent = { channelItemState ->
            TwitchChannelItem(channelItemState = channelItemState)
          },
          divider = { Spacer(modifier = Modifier.height(6.dp)) }
        )
      } else {
        GridChannelList(
          modifier = Modifier
            .fillMaxSize()
            .background(ChatTheme.colors.appBackground),
          viewModel = channelListViewModel,
          itemContent = { channelItemState ->
            TwitchChannelItem(channelItemState = channelItemState)
          },
          divider = { Spacer(modifier = Modifier.height(6.dp)) }
        )
      }
    }
  } else {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(ChatTheme.colors.appBackground)
    ) {
      CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
  }
}

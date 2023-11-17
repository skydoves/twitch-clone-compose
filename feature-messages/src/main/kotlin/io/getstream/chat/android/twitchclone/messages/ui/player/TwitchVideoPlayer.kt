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

package io.getstream.chat.android.twitchclone.messages.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.model.streamLink

@Composable
fun TwitchVideoPlayer(
  modifier: Modifier,
  listViewModel: MessageListViewModel,
  onChannelInfoClick: () -> Unit
) {
  val videoShape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
  Box(
    modifier = modifier
      .clip(shape = videoShape)
      .background(ChatTheme.colors.appBackground)
      .border(
        width = 4.dp,
        color = ChatTheme.colors.primaryAccent.copy(0.75f),
        shape = videoShape
      )
  ) {
    val streamLink = listViewModel.channel.streamLink

    if (streamLink != null) {
      VideoPlayer(videoUrl = streamLink)
    }

    Surface(
      modifier = Modifier
        .padding(12.dp)
        .align(Alignment.TopEnd)
        .clickable(onClick = onChannelInfoClick),
      color = Color.White,
      shape = CircleShape
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_info),
        contentDescription = null,
        tint = ChatTheme.colors.primaryAccent
      )
    }
  }
}

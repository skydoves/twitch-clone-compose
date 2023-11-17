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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun ChannelInformation(
  streamerAvatarImage: String?,
  streamerName: String?,
  modifier: Modifier = Modifier,
  channelDescription: String? = null,
  channelTags: List<String> = emptyList(),
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top
) {
  Column(
    modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp),
    horizontalAlignment = horizontalAlignment,
    verticalArrangement = verticalArrangement
  ) {
    StreamerInformation(
      streamerAvatarImage = streamerAvatarImage,
      streamerName = streamerName
    )

    if (channelDescription != null) {
      Spacer(modifier = Modifier.height(4.dp))

      Text(
        modifier = Modifier.padding(horizontal = 2.dp),
        text = channelDescription,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = ChatTheme.colors.textHighEmphasis,
        fontSize = 12.sp
      )
    }

    if (channelTags.isNotEmpty()) {
      Spacer(modifier = Modifier.height(4.dp))

      LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        items(items = channelTags) { tag ->
          Text(
            modifier = Modifier
              .background(
                color = ChatTheme.colors.primaryAccent,
                shape = RoundedCornerShape(16.dp)
              )
              .padding(horizontal = 6.dp, vertical = 3.dp),
            text = tag,
            color = Color.White,
            fontSize = 10.sp,
            maxLines = 1
          )
        }
      }
    }
  }
}

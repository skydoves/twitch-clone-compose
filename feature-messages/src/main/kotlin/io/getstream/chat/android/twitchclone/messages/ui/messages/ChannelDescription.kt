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

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.model.description
import io.getstream.chat.android.twitchclone.model.streamerAvatarLink
import io.getstream.chat.android.twitchclone.model.streamerName

@Composable
fun ChannelDescription(
  channel: Channel,
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  onChannelFollowClicked: () -> Unit = {}
) {
  Row(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment
  ) {
    val avatarLink by remember { mutableStateOf(channel.streamerAvatarLink) }

    CoilImage(
      modifier = Modifier
        .size(34.dp)
        .clip(CircleShape),
      imageModel = { avatarLink },
      component = rememberImageComponent {
        +CrossfadePlugin()
      }
    )

    Column(
      modifier = Modifier
        .weight(0.4f)
        .padding(horizontal = 8.dp)
        .verticalScroll(ScrollState(0))
    ) {
      Text(
        text = channel.streamerName.orEmpty(),
        style = ChatTheme.typography.bodyBold,
        fontSize = 13.sp,
        color = ChatTheme.colors.textHighEmphasis
      )

      Text(
        text = channel.description.orEmpty(),
        style = ChatTheme.typography.body,
        fontSize = 11.sp,
        color = ChatTheme.colors.textLowEmphasis
      )
    }

    Button(
      modifier = Modifier.padding(8.dp),
      onClick = onChannelFollowClicked
    ) {
      Icon(
        modifier = Modifier
          .padding(vertical = 2.dp, horizontal = 4.dp)
          .size(16.dp),
        painter = painterResource(id = R.drawable.ic_heart),
        tint = Color.White,
        contentDescription = null
      )
      Text(
        text = stringResource(id = R.string.follow),
        style = ChatTheme.typography.bodyBold,
        color = Color.White
      )
    }
  }
}

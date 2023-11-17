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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun StreamerInformation(
  streamerAvatarImage: String?,
  streamerName: String?,
  modifier: Modifier = Modifier,
  verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.Center
) {
  Row(
    modifier = modifier,
    verticalAlignment = verticalAlignment,
    horizontalArrangement = horizontalArrangement
  ) {
    CoilImage(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape),
      imageModel = { streamerAvatarImage },
      imageOptions = ImageOptions(contentScale = ContentScale.Crop),
      component = rememberImageComponent {
        +CrossfadePlugin()
      }
    )

    Spacer(modifier = Modifier.width(6.dp))

    if (streamerName != null) {
      Text(
        modifier = Modifier.wrapContentSize(),
        text = streamerName,
        style = ChatTheme.typography.bodyBold,
        fontSize = 14.sp,
        color = ChatTheme.colors.textHighEmphasis,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

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

package io.getstream.chat.android.twitchclone.messages.ui.emotes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetUtils

@Composable
fun EmotesContent(
  emotes: Map<String, String>,
  cells: GridCells,
  modifier: Modifier = Modifier,
  onEmoteSelected: (String) -> Unit = {}
) {
  LazyVerticalGrid(modifier = modifier, columns = cells) {
    items(emotes.values.toList(), key = { it }) { emote ->
      Box(modifier = Modifier.fillMaxSize()) {
        CoilImage(
          modifier = Modifier
            .padding(4.dp)
            .size(24.dp)
            .align(Alignment.Center)
            .clickable(
              indication = rememberRipple(bounded = false),
              interactionSource = remember { MutableInteractionSource() }
            ) {
              val emoteName = emote.split(".")[0]
              onEmoteSelected(":$emoteName: ")
            },
          imageModel = { AssetUtils.getEmotePath(emote) }
        )
      }
    }
  }
}

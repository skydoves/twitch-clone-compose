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

package io.getstream.chat.android.twitchclone.messages.extensions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetUtils
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetsProvider

fun String.transformText(): Pair<Map<String, InlineTextContent>, AnnotatedString> {
  val emotes = AssetsProvider.reactions
  val emoteKeys = emotes.keys
  val parts = this.split(" ")
  val addedEmotes = parts.filter { it in emotes }

  val inlineContent = addedEmotes.associateWith { badgeName ->
    InlineTextContent(
      Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
    ) {
      CoilImage(
        modifier = Modifier
          .fillMaxSize()
          .padding(2.dp),
        imageModel = { AssetUtils.getEmotePath(emotes[badgeName]) }
      )
    }
  }

  val annotatedString = buildAnnotatedString {
    for (part in parts) {
      if (part in emoteKeys) {
        appendInlineContent(id = part)
      } else {
        append(part)
      }

      if (parts.indexOf(part) != parts.lastIndex) {
        append(" ")
      }
    }
  }

  return inlineContent to annotatedString
}

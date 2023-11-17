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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetUtils
import io.getstream.chat.android.twitchclone.messages.extensions.badges
import io.getstream.chat.android.twitchclone.messages.extensions.nameColor
import io.getstream.chat.android.twitchclone.messages.extensions.transformText
import io.getstream.chat.android.ui.common.state.messages.list.MessageItemState

@Composable
fun TwitchMessageItem(
  messageItemState: MessageItemState,
  modifier: Modifier = Modifier
) {
  val badges = messageItemState.message.user.badges
  val badgePath = "${AssetUtils.baseUrl}/badges/"

  val badgesContent = badges.associateWith { badgeName ->
    InlineTextContent(
      Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
    ) {
      CoilImage(
        modifier = Modifier
          .fillMaxSize()
          .padding(2.dp),
        imageModel = { "$badgePath/$badgeName" }
      )
    }
  }
  val userSpannable = buildUserSpannableText(messageItemState = messageItemState)
  val (inline, messageText) = messageItemState.message.text.trim().transformText()
  val previewText = userSpannable + messageText

  Text(
    modifier = modifier.padding(vertical = 2.dp),
    text = previewText,
    color = ChatTheme.colors.textHighEmphasis,
    inlineContent = badgesContent + inline
  )
}

private fun buildUserSpannableText(messageItemState: MessageItemState): AnnotatedString {
  val message = messageItemState.message
  val user = messageItemState.message.user
  val badges = user.badges
  val userColor = user.nameColor

  return buildAnnotatedString {
    badges.forEach { badge ->
      appendInlineContent(id = badge)
    }

    if (badges.isNotEmpty()) {
      append(" ")
    }

    val nameStart = this.length

    append(message.user.name)

    val nameEnd = this.length

    addStyle(
      style = SpanStyle(
        color = Color(android.graphics.Color.parseColor(userColor)),
        fontWeight = FontWeight.SemiBold
      ),
      start = nameStart,
      end = nameEnd
    )

    append(": ")
  }
}

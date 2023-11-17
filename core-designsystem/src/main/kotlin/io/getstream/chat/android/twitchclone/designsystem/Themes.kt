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

package io.getstream.chat.android.twitchclone.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamColors
import io.getstream.video.android.compose.theme.VideoTheme

val primaryColor = Color(0xFF6441a5)
val shimmerBase: Color = Color(0xFF25282B)
val shimmerHighlight: Color = Color(0xFFDFDEDE)
val emojiColor: Color = Color(0xFFF4BC04)

@Composable
fun TwitchTheme(
  content: @Composable () -> Unit
) {
  ChatTheme(
    colors = if (isSystemInDarkTheme()) {
      StreamColors.defaultDarkColors().copy(
        primaryAccent = primaryColor
      )
    } else {
      StreamColors.defaultColors().copy(
        primaryAccent = primaryColor
      )
    }
  ) {
    VideoTheme(content = content)
  }
}

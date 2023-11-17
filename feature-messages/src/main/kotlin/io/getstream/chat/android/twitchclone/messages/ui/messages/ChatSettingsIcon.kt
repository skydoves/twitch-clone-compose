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

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.twitchclone.messages.R

@Composable
fun ChatSettingsIcon(onClick: () -> Unit = {}) {
  IconButton(onClick = onClick) {
    Icon(
      painter = painterResource(id = R.drawable.ic_vertical_dots),
      tint = ChatTheme.colors.textLowEmphasis,
      contentDescription = stringResource(id = R.string.accessibility_open_chat_settings)
    )
  }
}

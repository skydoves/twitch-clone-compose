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
import io.getstream.chat.android.compose.R
import io.getstream.chat.android.compose.ui.components.composer.CoolDownIndicator
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.models.Attachment
import io.getstream.chat.android.ui.common.state.messages.composer.ValidationError

@Composable
fun SendButton(
  value: String,
  coolDownTime: Int,
  attachments: List<Attachment>,
  validationErrors: List<ValidationError>,
  onSendMessage: (String, List<Attachment>) -> Unit
) {
  val isInputValid =
    (value.isNotEmpty() || attachments.isNotEmpty()) && validationErrors.isEmpty()

  if (coolDownTime > 0) {
    CoolDownIndicator(coolDownTime = coolDownTime)
  } else {
    IconButton(
      enabled = isInputValid,
      content = {
        Icon(
          painter = painterResource(id = R.drawable.stream_compose_ic_send),
          contentDescription = stringResource(id = R.string.stream_compose_send_message),
          tint = if (isInputValid) {
            ChatTheme.colors.primaryAccent
          } else {
            ChatTheme.colors.textLowEmphasis
          }
        )
      },
      onClick = {
        if (isInputValid) {
          onSendMessage(value, attachments)
        }
      }
    )
  }
}

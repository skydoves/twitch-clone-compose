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

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetsProvider
import io.getstream.chat.android.twitchclone.designsystem.emojiColor
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.messages.ui.emotes.EmotesContent
import io.getstream.chat.android.twitchclone.messages.ui.rewards.RewardsContent
import io.getstream.chat.android.twitchclone.messages.ui.rewards.RewardsIntegration
import io.getstream.chat.android.twitchclone.model.pointsIcon
import io.getstream.chat.android.twitchclone.model.pointsName
import io.getstream.chat.android.twitchclone.model.streamerName
import io.getstream.chat.android.twitchclone.navigation.currentComposeNavigator
import io.getstream.chat.android.ui.common.state.messages.composer.MessageComposerState

@Composable
fun TwitchBottomBarContent(
  modifier: Modifier = Modifier,
  listViewModel: MessageListViewModel,
  composerViewModel: MessageComposerViewModel,
  twitchMessagesViewModel: TwitchMessagesViewModel = hiltViewModel(),
  onMessageSendClicked: () -> Unit
) {
  var isRewardsContentExpanded by rememberSaveable { mutableStateOf(false) }
  var isEmotesContentExpanded by rememberSaveable { mutableStateOf(false) }
  val rewards by twitchMessagesViewModel.rewards.collectAsState()
  val emotes = AssetsProvider.reactions

  Column(modifier = modifier.animateContentSize()) {
    TwitchBottomBar(
      composerViewModel = composerViewModel,
      modifier = Modifier,
      integrations = {
        RewardsIntegration(
          modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(IntrinsicSize.Min)
            .align(Alignment.CenterVertically),
          channel = listViewModel.channel,
          rewardCount = 1,
          onClick = {
            isRewardsContentExpanded = !isRewardsContentExpanded
            isEmotesContentExpanded = false
          }
        )
      },
      onEmotesClick = {
        isRewardsContentExpanded = false
        isEmotesContentExpanded = !isEmotesContentExpanded
      },
      trailingContent = {
        val messageComposerState by composerViewModel.messageComposerState.collectAsState()
        if (messageComposerState.inputValue.isNotBlank()) {
          SendButton(
            value = messageComposerState.inputValue,
            coolDownTime = messageComposerState.coolDownTime,
            attachments = messageComposerState.attachments,
            validationErrors = messageComposerState.validationErrors,
            onSendMessage = { input, attachments ->
              val message = composerViewModel.buildNewMessage(
                message = input,
                attachments = attachments
              )

              composerViewModel.sendMessage(message = message)

              onMessageSendClicked.invoke()
            }
          )
        } else {
          ChatSettingsIcon()
        }
      }
    )

    val channel = listViewModel.channel

    if (isRewardsContentExpanded && rewards is TwitchMessageUiState.Success) {
      val rewardList = rewards as TwitchMessageUiState.Success
      RewardsContent(
        channelName = channel.streamerName.orEmpty(),
        channelPointsIcon = channel.pointsIcon,
        channelPointsName = channel.pointsName ?: "Channel Points",
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        cells = GridCells.Fixed(3),
        rewardList = rewardList.rewards,
        onRewardSelected = {},
        onViewMoreInfoSelected = {}
      )
    }

    if (isEmotesContentExpanded) {
      EmotesContent(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        emotes = emotes,
        cells = GridCells.Fixed(7),
        onEmoteSelected = { emote ->
          val currentInput = composerViewModel.input.value
          composerViewModel.setMessageInput("$currentInput $emote")
        }
      )
    }
  }

  val navigator = currentComposeNavigator
  BackHandler(enabled = true) {
    if (isRewardsContentExpanded) {
      isRewardsContentExpanded = false
      return@BackHandler
    }

    if (isEmotesContentExpanded) {
      isEmotesContentExpanded = false
      return@BackHandler
    }

    navigator.navigateUp()
  }
}

@Composable
fun TwitchBottomBar(
  composerViewModel: MessageComposerViewModel,
  modifier: Modifier = Modifier,
  onEmotesClick: () -> Unit,
  integrations: @Composable RowScope.(MessageComposerState) -> Unit,
  trailingContent: @Composable (MessageComposerState) -> Unit
) {
  val messageComposerState by composerViewModel.messageComposerState.collectAsState()

  Surface(
    modifier = modifier,
    elevation = 4.dp,
    color = ChatTheme.colors.barsBackground
  ) {
    Column(
      modifier = Modifier.padding(vertical = 4.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        integrations(messageComposerState)

        StitchInput(
          modifier = Modifier.weight(1f),
          value = messageComposerState.inputValue,
          onEmotesClick = onEmotesClick,
          onValueChange = {
            composerViewModel.setMessageInput(it)
          }
        )

        trailingContent(messageComposerState)
      }
    }
  }
}

@Composable
private fun StitchInput(
  value: String,
  modifier: Modifier,
  onEmotesClick: () -> Unit,
  onValueChange: (String) -> Unit
) {
  var state by remember { mutableStateOf(TextFieldValue(text = "")) }
  val selection = if (state.isCursorAtTheEnd()) {
    TextRange(value.length)
  } else {
    state.selection
  }

  val textFieldValue = state.copy(
    text = value,
    selection = selection
  )

  state = textFieldValue

  BasicTextField(
    modifier = modifier
      .clip(ChatTheme.shapes.inputField)
      .background(ChatTheme.colors.inputBackground)
      .padding(8.dp),
    value = state,
    onValueChange = {
      if (value != it.text) {
        onValueChange(it.text)
      }
    },
    singleLine = true,
    textStyle = ChatTheme.typography.body.copy(
      color = ChatTheme.colors.textHighEmphasis,
      textDirection = TextDirection.Content
    ),
    cursorBrush = SolidColor(ChatTheme.colors.primaryAccent),
    decorationBox = { innerTextField ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(modifier = Modifier.weight(1f)) {
          innerTextField()

          if (value.isEmpty()) {
            Text(
              text = stringResource(id = R.string.message_label),
              color = ChatTheme.colors.textLowEmphasis
            )
          }
        }

        Icon(
          modifier = Modifier.clickable(onClick = onEmotesClick),
          painter = painterResource(id = R.drawable.ic_emoji),
          contentDescription = stringResource(id = R.string.accessibility_expand_emoji_reactions),
          tint = emojiColor
        )
      }
    }
  )
}

private fun TextFieldValue.isCursorAtTheEnd(): Boolean {
  val textLength = text.length
  val selectionStart = selection.start
  val selectionEnd = selection.end

  return textLength == selectionStart && textLength == selectionEnd
}

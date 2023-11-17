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

package io.getstream.chat.android.twitchclone.messages.ui.rewards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.twitchclone.designsystem.TwitchTheme
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.model.Reward

@Composable
fun RewardItem(
  reward: Reward,
  channelPointsIcon: String?,
  modifier: Modifier = Modifier,
  onRewardSelected: () -> Unit
) {
  Column(modifier = modifier) {
    Card(
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .aspectRatio(0.9f)
        .fillMaxWidth()
        .clickable(
          interactionSource = MutableInteractionSource(),
          indication = null,
          enabled = true,
          onClick = onRewardSelected
        ),
      backgroundColor = reward.background
    ) {
      Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(
          modifier = Modifier.padding(10.dp),
          imageVector = reward.iconVector,
          tint = Color.White,
          contentDescription = stringResource(id = R.string.accessibility_reward_name, reward.name)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
          modifier = Modifier
            .padding(8.dp)
            .height(IntrinsicSize.Min),
          color = ChatTheme.colors.overlay,
          shape = RoundedCornerShape(5.dp)
        ) {
          Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            CoilImage(
              modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically),
              imageModel = { channelPointsIcon }
            )

            Text(
              modifier = Modifier
                .padding(2.dp),
              text = reward.tokenAmount.toString(),
              style = ChatTheme.typography.bodyBold,
              fontSize = 12.sp,
              color = Color.White
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = reward.name,
      fontSize = 12.sp,
      textAlign = TextAlign.Center,
      color = ChatTheme.colors.textHighEmphasis
    )
  }
}

@Preview
@Composable
private fun RewardItemPreview() {
  TwitchTheme {
    RewardItem(
      Reward(
        name = "Unlock a Random Sub Emote",
        color = "#F97B2A",
        icon = "LockOpen",
        tokenAmount = 100
      ),
      channelPointsIcon = null
    ) {}
  }
}

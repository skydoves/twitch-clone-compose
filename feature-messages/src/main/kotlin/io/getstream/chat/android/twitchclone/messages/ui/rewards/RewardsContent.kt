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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil.CoilImage
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.twitchclone.messages.R
import io.getstream.chat.android.twitchclone.model.Reward

@Composable
fun RewardsContent(
  channelName: String,
  channelPointsName: String,
  channelPointsIcon: String?,
  rewardList: List<Reward>,
  cells: GridCells,
  modifier: Modifier = Modifier,
  onRewardSelected: () -> Unit = {},
  onViewMoreInfoSelected: () -> Unit = {}
) {
  Column(modifier = modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        modifier = Modifier
          .padding(8.dp)
          .weight(0.7f),
        text = stringResource(id = R.string.channel_rewards, channelName),
        style = ChatTheme.typography.bodyBold
      )

      Text(
        modifier = Modifier
          .padding(8.dp)
          .background(
            shape = RoundedCornerShape(16.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
          )
          .padding(4.dp),
        fontSize = 12.sp,
        text = "1.2X Multiplier"
      )
    }

    LazyVerticalGrid(columns = cells) {
      items(rewardList, key = { it.name }) { reward ->
        RewardItem(
          modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
          reward = reward,
          onRewardSelected = onRewardSelected,
          channelPointsIcon = channelPointsIcon
        )
      }
    }

    Button(
      onClick = onViewMoreInfoSelected,
      content = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          CoilImage(
            modifier = Modifier.size(12.dp),
            imageModel = { channelPointsIcon }
          )

          Text(
            text = stringResource(
              id = R.string.channel_rewards_earn,
              channelPointsName
            ),
            fontSize = 10.sp
          )
        }
      }
    )
  }
}

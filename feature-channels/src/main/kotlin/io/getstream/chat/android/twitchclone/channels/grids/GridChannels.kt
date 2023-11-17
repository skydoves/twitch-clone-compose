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

package io.getstream.chat.android.twitchclone.channels.grids

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.state.channels.list.ChannelItemState
import io.getstream.chat.android.compose.state.channels.list.ChannelsState
import io.getstream.chat.android.compose.ui.components.LoadingFooter

@Composable
fun GridChannels(
  channelsState: ChannelsState,
  lazyListState: LazyGridState,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(6.dp),
  helperContent: @Composable BoxScope.() -> Unit = {},
  loadingMoreContent: @Composable () -> Unit = { DefaultChannelsLoadingMoreIndicator() },
  itemContent: @Composable (ChannelItemState) -> Unit,
  divider: @Composable () -> Unit
) {
  val (_, isLoadingMore, _, channelItems) = channelsState

  Box(modifier = modifier) {
    LazyVerticalGrid(
      modifier = Modifier
        .fillMaxSize()
        .testTag("Stream_Channels"),
      columns = GridCells.Fixed(2),
      state = lazyListState,
      contentPadding = contentPadding
    ) {
      items(
        items = channelItems,
        key = { it.channel.cid }
      ) { item ->
        itemContent(item)

        divider()
      }

      if (isLoadingMore) {
        item {
          loadingMoreContent()
        }
      }
    }

    helperContent()
  }
}

/**
 * The default loading more indicator.
 */
@Composable
internal fun DefaultChannelsLoadingMoreIndicator() {
  LoadingFooter(modifier = Modifier.fillMaxWidth())
}

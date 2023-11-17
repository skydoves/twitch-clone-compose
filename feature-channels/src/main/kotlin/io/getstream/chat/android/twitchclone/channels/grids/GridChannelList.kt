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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.R
import io.getstream.chat.android.compose.state.channels.list.ChannelItemState
import io.getstream.chat.android.compose.state.channels.list.ChannelsState
import io.getstream.chat.android.compose.ui.channels.list.ChannelItem
import io.getstream.chat.android.compose.ui.components.EmptyContent
import io.getstream.chat.android.compose.ui.components.LoadingIndicator
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.User
import io.getstream.chat.android.models.querysort.QuerySortByField

@Composable
fun GridChannelList(
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(),
  viewModel: ChannelListViewModel = viewModel(
    factory =
    ChannelViewModelFactory(
      ChatClient.instance(),
      QuerySortByField.descByName("last_updated"),
      filters = null
    )
  ),
  lazyListState: LazyGridState = rememberLazyGridState(),
  onChannelClick: (Channel) -> Unit = {},
  onChannelLongClick: (Channel) -> Unit = remember(viewModel) { { viewModel.selectChannel(it) } },
  loadingContent: @Composable () -> Unit = { LoadingIndicator(modifier) },
  emptyContent: @Composable () -> Unit = { DefaultChannelListEmptyContent(modifier) },
  emptySearchContent: @Composable (String) -> Unit = { searchQuery ->
    DefaultChannelSearchEmptyContent(
      searchQuery = searchQuery,
      modifier = modifier
    )
  },
  helperContent: @Composable BoxScope.() -> Unit = {},
  loadingMoreContent: @Composable () -> Unit = { DefaultChannelsLoadingMoreIndicator() },
  itemContent: @Composable (ChannelItemState) -> Unit = { channelItem ->
    val user by viewModel.user.collectAsState()

    DefaultChannelItem(
      channelItem = channelItem,
      currentUser = user,
      onChannelClick = onChannelClick,
      onChannelLongClick = onChannelLongClick
    )
  },
  divider: @Composable () -> Unit = { DefaultChannelItemDivider() }
) {
  val user by viewModel.user.collectAsState()

  ChannelList(
    modifier = modifier,
    contentPadding = contentPadding,
    channelsState = viewModel.channelsState,
    currentUser = user,
    lazyListState = lazyListState,
    onChannelClick = onChannelClick,
    onChannelLongClick = onChannelLongClick,
    loadingContent = loadingContent,
    emptyContent = emptyContent,
    emptySearchContent = emptySearchContent,
    helperContent = helperContent,
    loadingMoreContent = loadingMoreContent,
    itemContent = itemContent,
    divider = divider
  )
}

@Composable
private fun ChannelList(
  channelsState: ChannelsState,
  currentUser: User?,
  modifier: Modifier = Modifier,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  lazyListState: LazyGridState = rememberLazyGridState(),
  onChannelClick: (Channel) -> Unit = {},
  onChannelLongClick: (Channel) -> Unit = {},
  loadingContent: @Composable () -> Unit = { DefaultChannelListLoadingIndicator(modifier) },
  emptyContent: @Composable () -> Unit = { DefaultChannelListEmptyContent(modifier) },
  emptySearchContent: @Composable (String) -> Unit = { searchQuery ->
    DefaultChannelSearchEmptyContent(
      searchQuery = searchQuery,
      modifier = modifier
    )
  },
  helperContent: @Composable BoxScope.() -> Unit = {},
  loadingMoreContent: @Composable () -> Unit = { DefaultChannelsLoadingMoreIndicator() },
  itemContent: @Composable (ChannelItemState) -> Unit = { channelItem ->
    DefaultChannelItem(
      channelItem = channelItem,
      currentUser = currentUser,
      onChannelClick = onChannelClick,
      onChannelLongClick = onChannelLongClick
    )
  },
  divider: @Composable () -> Unit = { DefaultChannelItemDivider() }
) {
  val (isLoading, _, _, channels, searchQuery) = channelsState

  when {
    isLoading -> loadingContent()
    !isLoading && channels.isNotEmpty() -> GridChannels(
      modifier = modifier,
      contentPadding = contentPadding,
      channelsState = channelsState,
      lazyListState = lazyListState,
      helperContent = helperContent,
      loadingMoreContent = loadingMoreContent,
      itemContent = itemContent,
      divider = divider
    )

    searchQuery.isNotEmpty() -> emptySearchContent(searchQuery)
    else -> emptyContent()
  }
}

/**
 * The default channel item.
 *
 * @param channelItem The item to represent.
 * @param currentUser The currently logged in user.
 * @param onChannelClick Handler when the user clicks on an item.
 * @param onChannelLongClick Handler when the user long taps on an item.
 */
@Composable
internal fun DefaultChannelItem(
  channelItem: ChannelItemState,
  currentUser: User?,
  onChannelClick: (Channel) -> Unit,
  onChannelLongClick: (Channel) -> Unit
) {
  ChannelItem(
    channelItem = channelItem,
    currentUser = currentUser,
    onChannelClick = onChannelClick,
    onChannelLongClick = onChannelLongClick
  )
}

/**
 * Default loading indicator.
 *
 * @param modifier Modifier for styling.
 */
@Composable
internal fun DefaultChannelListLoadingIndicator(modifier: Modifier) {
  LoadingIndicator(modifier)
}

/**
 * The default empty placeholder for the case when there are no channels available to the user.
 *
 * @param modifier Modifier for styling.
 */
@Composable
internal fun DefaultChannelListEmptyContent(modifier: Modifier = Modifier) {
  EmptyContent(
    modifier = modifier,
    painter = painterResource(id = R.drawable.stream_compose_empty_channels),
    text = stringResource(R.string.stream_compose_channel_list_empty_channels)
  )
}

/**
 * The default empty placeholder for the case when channel search returns no results.
 *
 * @param searchQuery The search query that returned no results.
 * @param modifier Modifier for styling.
 */
@Composable
internal fun DefaultChannelSearchEmptyContent(
  searchQuery: String,
  modifier: Modifier = Modifier
) {
  EmptyContent(
    modifier = modifier,
    painter = painterResource(id = R.drawable.stream_compose_empty_search_results),
    text = stringResource(R.string.stream_compose_channel_list_empty_search_results, searchQuery)
  )
}

/**
 * Represents the default item divider in channel items.
 */
@Composable
public fun DefaultChannelItemDivider() {
  Spacer(
    modifier = Modifier
      .fillMaxWidth()
      .height(0.5.dp)
      .background(color = ChatTheme.colors.borders)
  )
}

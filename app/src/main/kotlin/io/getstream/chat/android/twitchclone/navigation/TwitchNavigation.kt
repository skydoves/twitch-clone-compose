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

package io.getstream.chat.android.twitchclone.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.InitializationState
import io.getstream.chat.android.models.querysort.QuerySortByField
import io.getstream.chat.android.twitchclone.channels.TwitchChannels
import io.getstream.chat.android.twitchclone.designsystem.TwitchLoadingIndicator
import io.getstream.chat.android.twitchclone.livestream.LivestreamStreamer
import io.getstream.chat.android.twitchclone.livestream.LivestreamWatcher
import io.getstream.chat.android.twitchclone.messages.ui.messages.TwitchMessage

fun NavGraphBuilder.whatsAppHomeNavigation() {
  composable(route = TwitchScreens.Channels.name) {
    val clientInitialisationState
      by ChatClient.instance().clientState.initializationState.collectAsState()

    if (clientInitialisationState == InitializationState.COMPLETE) {
      val factory = remember {
        ChannelViewModelFactory(
          chatClient = ChatClient.instance(),
          querySort = QuerySortByField.descByName("last_updated"),
          filters = Filters.eq("type", "livestream")
        )
      }

      val channelListViewModel: ChannelListViewModel = viewModel(factory = factory)

      TwitchChannels(channelListViewModel = channelListViewModel)
    } else {
      TwitchLoadingIndicator()
    }
  }

  slideComposable(
    route = TwitchScreens.Messages.name,
    arguments = TwitchScreens.Messages.navArguments
  ) {
    val channelId = it.arguments?.getString("channelId") ?: return@slideComposable

    val context = LocalContext.current
    val factory = remember {
      MessagesViewModelFactory(
        context = context,
        channelId = channelId,
        showDateSeparatorInEmptyThread = false,
        showSystemMessages = false
      )
    }

    val listViewModel: MessageListViewModel = viewModel(factory = factory)
    val composerViewModel: MessageComposerViewModel = viewModel(factory = factory)

    TwitchMessage(
      listViewModel = listViewModel,
      composerViewModel = composerViewModel
    )
  }

  slideComposable(
    route = TwitchScreens.LivestreamWatcher.name,
    arguments = TwitchScreens.LivestreamWatcher.navArguments
  ) {
    val channelId = it.arguments?.getString("channelId") ?: return@slideComposable

    val context = LocalContext.current
    val factory = remember {
      MessagesViewModelFactory(
        context = context,
        channelId = channelId,
        showDateSeparatorInEmptyThread = false,
        showSystemMessages = false
      )
    }

    val listViewModel: MessageListViewModel = viewModel(factory = factory)
    val composerViewModel: MessageComposerViewModel = viewModel(factory = factory)

    LivestreamWatcher(
      listViewModel = listViewModel,
      composerViewModel = composerViewModel
    )
  }

  slideComposable(
    route = TwitchScreens.LivestreamStreamer.name,
    arguments = TwitchScreens.LivestreamStreamer.navArguments
  ) {
    val channelId = it.arguments?.getString("channelId") ?: return@slideComposable

    val context = LocalContext.current
    val factory = remember {
      MessagesViewModelFactory(
        context = context,
        channelId = channelId,
        showDateSeparatorInEmptyThread = false,
        showSystemMessages = false
      )
    }

    val listViewModel: MessageListViewModel = viewModel(factory = factory)
    val composerViewModel: MessageComposerViewModel = viewModel(factory = factory)

    LivestreamStreamer(
      listViewModel = listViewModel,
      composerViewModel = composerViewModel
    )
  }
}

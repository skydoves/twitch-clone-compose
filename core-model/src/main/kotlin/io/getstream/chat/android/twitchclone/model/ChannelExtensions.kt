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

package io.getstream.chat.android.twitchclone.model

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_DESCRIPTION
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_POINTS_ICON
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_POINTS_NAME
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_STREAMER_AVATAR_LINK
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_STREAMER_NAME
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_STREAM_LINK
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_STREAM_PREVIEW_LINK
import io.getstream.chat.android.twitchclone.model.ChannelConst.EXTRA_TAGS
import kotlin.random.Random

val Channel.streamerAvatarLink: String?
  inline get() = this.extraData[EXTRA_STREAMER_AVATAR_LINK] as? String

val Channel.streamerName: String?
  inline get() = this.extraData[EXTRA_STREAMER_NAME] as? String

val Channel.streamPreviewLink: String?
  inline get() = this.extraData[EXTRA_STREAM_PREVIEW_LINK] as? String

val Channel.streamLink: String?
  inline get() = this.extraData[EXTRA_STREAM_LINK] as? String

val Channel.description: String?
  inline get() = this.extraData[EXTRA_DESCRIPTION] as? String

val Channel.pointsIcon: String?
  inline get() = this.extraData[EXTRA_POINTS_ICON] as? String

val Channel.pointsName: String?
  inline get() = this.extraData[EXTRA_POINTS_NAME] as? String

@Suppress("UNCHECKED_CAST")
val Channel.tags: List<String>
  inline get() = this.extraData[EXTRA_TAGS] as? List<String> ?: emptyList()

@PublishedApi
internal object ChannelConst {
  const val EXTRA_STREAMER_AVATAR_LINK = "EXTRA_STREAMER_AVATAR_LINK"
  const val EXTRA_STREAMER_NAME = "EXTRA_STREAMER_NAME"
  const val EXTRA_STREAM_PREVIEW_LINK = "EXTRA_STREAM_PREVIEW_LINK"
  const val EXTRA_STREAM_LINK = "EXTRA_STREAM_LINK"
  const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
  const val EXTRA_TAGS = "EXTRA_TAGS"
  const val EXTRA_POINTS_ICON = "EXTRA_POINTS_ICON"
  const val EXTRA_POINTS_NAME = "EXTRA_POINTS_NAME"
}

suspend fun ChatClient.createMockChannels() {
  createChannel(
    channelType = "livestream",
    channelId = "livestream${Random.nextInt(10000)}",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel1Extras
  ).await()

  createChannel(
    channelType = "livestream",
    channelId = "livestream${Random.nextInt(10000)}",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel2Extras
  ).await()
}

suspend fun ChatClient.createStreamerChannel(): Channel? {
  return createChannel(
    channelType = "livestream",
    channelId = "streamer",
    memberIds = listOf(ChatClient.instance().getCurrentUser()?.id.orEmpty()),
    extraData = mockChannel3Extras
  ).await().getOrNull()
}

private val mockChannel1Extras: Map<String, Any>
  inline get() = mapOf(
    EXTRA_STREAMER_AVATAR_LINK to "https://user-images.githubusercontent.com/" +
      "24237865/77314084-eb180600-6cfc-11ea-89d5-65b673f0a391.jpg",
    EXTRA_STREAMER_NAME to "Spiderman",
    EXTRA_STREAM_PREVIEW_LINK to "https://github-production-user-asset-6210df.s3.amazonaws.com/" +
      "24237865/283096747-5aaf1c56-cbc6-4db3-95e0-629f7e6bd951.png",
    EXTRA_STREAM_LINK to "https://commondatastorage.googleapis.com/" +
      "gtv-videos-bucket/sample/BigBuckBunny.mp4",
    EXTRA_DESCRIPTION to "Come to watch that I'm flying above the cities!",
    EXTRA_TAGS to listOf("Flying", "Spiderman", "Marvel", "City Views", "Night Views"),
    EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    EXTRA_POINTS_NAME to "Spider"
  )

private val mockChannel2Extras: Map<String, Any>
  inline get() = mapOf(
    EXTRA_STREAMER_AVATAR_LINK to "https://placekitten.com/200/300",
    EXTRA_STREAMER_NAME to "Big Buck",
    EXTRA_STREAM_PREVIEW_LINK to "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/" +
      "Big_buck_bunny_poster_big.jpg/1024px-Big_buck_bunny_poster_big.jpg",
    EXTRA_STREAM_LINK to "https://commondatastorage.googleapis.com/" +
      "gtv-videos-bucket/CastVideos/dash/ElephantsDreamVideo.mp4",
    EXTRA_DESCRIPTION to "Come watch an awesome cartoon with me!",
    EXTRA_TAGS to listOf("Reality", "Live", "Korean", "K-pop", "Dancing"),
    EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    EXTRA_POINTS_NAME to "Buns"
  )

private val mockChannel3Extras: Map<String, Any>
  inline get() = mapOf(
    EXTRA_STREAMER_AVATAR_LINK to "https://placekitten.com/200/300",
    EXTRA_STREAMER_NAME to "Streamer",
    EXTRA_STREAM_PREVIEW_LINK to "https://github-production-user-asset-6210df.s3.amazonaws.com/" +
      "24237865/283672657-643e4e60-9082-4e75-8a16-e2a37086a2b9.png",
    EXTRA_STREAM_LINK to "https://commondatastorage.googleapis.com/" +
      "gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
    EXTRA_DESCRIPTION to "Come watch an awesome livestreaming!",
    EXTRA_TAGS to listOf("Livestreaming", "Communication", "Talk", "Life", "Games"),
    EXTRA_POINTS_ICON to "https://cdn.betterttv.net/emote/60ee7ce38ed8b373e4222366/1x",
    EXTRA_POINTS_NAME to "Buns"
  )

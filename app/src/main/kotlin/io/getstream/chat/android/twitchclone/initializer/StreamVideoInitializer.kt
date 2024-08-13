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

package io.getstream.chat.android.twitchclone.initializer

import android.content.Context
import androidx.startup.Initializer
import io.getstream.chat.android.twitchclone.BuildConfig
import io.getstream.log.streamLog
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.core.notifications.NotificationConfig
import io.getstream.video.android.model.User

class StreamVideoInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    streamLog { "StreamVideoInitializer is initialized" }

    val userId = "jaewoong"
    StreamVideoBuilder(
      context = context,
      apiKey = BuildConfig.STREAM_API_KEY,
      notificationConfig = NotificationConfig(hideRingingNotificationInForeground = true),
      runForegroundServiceForCalls = false,
      token = StreamVideo.devToken(userId),
      user = User(
        id = userId,
        name = "Jaewoong",
        image = "http://placekitten.com/200/300",
        role = "admin"
      )
    ).build()
  }

  override fun dependencies(): List<Class<out Initializer<*>>> =
    listOf(StreamLogInitializer::class.java)
}

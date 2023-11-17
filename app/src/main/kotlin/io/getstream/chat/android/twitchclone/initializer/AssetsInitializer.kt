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
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetsProvider
import io.getstream.log.streamLog

class AssetsInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    streamLog { "AssetsInitializer is initialized" }

    AssetsProvider.loadBadges(context)
    AssetsProvider.loadReactions(context)
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

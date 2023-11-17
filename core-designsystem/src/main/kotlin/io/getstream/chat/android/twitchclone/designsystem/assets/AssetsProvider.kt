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

package io.getstream.chat.android.twitchclone.designsystem.assets

import android.content.Context

object AssetsProvider {

  private val _userBadges = mutableMapOf<String, String>()
  val userBadges: Map<String, String> = _userBadges

  private val _reactions = mutableMapOf<String, String>()
  val reactions: Map<String, String> = _reactions

  fun loadBadges(context: Context) {
    val assets = context.assets ?: return
    val badges = assets.list("badges") as? Array<String> ?: emptyArray()

    _userBadges += badges.map {
      val parts = it.split(".")

      parts[0] to it
    }
  }

  fun loadReactions(context: Context) {
    val assets = context.assets ?: return

    val gifs = assets.list("gifs") as? Array<String> ?: emptyArray()
    val emotes = assets.list("emotes") as? Array<String> ?: emptyArray()

    _reactions += (gifs + emotes).map {
      val parts = it.split(".")

      ":${parts[0]}:" to it
    }
  }
}

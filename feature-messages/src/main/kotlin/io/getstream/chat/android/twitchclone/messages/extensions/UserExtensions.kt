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

package io.getstream.chat.android.twitchclone.messages.extensions

import io.getstream.chat.android.models.User
import io.getstream.chat.android.twitchclone.designsystem.assets.AssetsProvider

val User.nameColor: String
  inline get() = when {
    "Seth" in name -> "#a9a1b7"
    "John" in name -> "#cbcd3d"
    "iOS" in name -> "#fe5c17"
    "Steven" in name -> "#76d927"
    "Marin" in name -> "#175ad0"
    "Juan" in name -> "#18c090"
    "Jaewoong" in name -> "#005FFF"
    else -> "#808080"
  }

val User.badges: List<String>
  inline get() {
    val badges = AssetsProvider.userBadges.values.toList()

    return when {
      "Seth" in name -> listOf(badges[0], badges[2], badges[3])
      "John" in name -> listOf(badges[0], badges[1])
      "iOS" in name -> listOf(badges[5])
      "Steven" in name -> listOf(badges[1], badges[5])
      "Marin" in name -> listOf(badges[2], badges[4])
      "Juan" in name -> listOf(badges[1], badges[2], badges[3], badges[4])
      "Jaewoong" in name -> listOf(badges[0], badges[1], badges[2], badges[3], badges[4], badges[5])
      else -> listOf(badges[2], badges[5])
    }
  }

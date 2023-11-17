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

package io.getstream.chat.android.twitchclone.messages.ui.player

import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun BoxScope.VideoPlayer(videoUrl: String?) {
  val context = LocalContext.current
  var prepared by remember { mutableStateOf(false) }
  val videoView = remember {
    VideoView(context).apply {
      setOnPreparedListener { prepared = true }
      layoutParams = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      ).apply {
        gravity = Gravity.CENTER
      }
    }
  }

  LaunchedEffect(Unit) {
    videoView.setVideoURI(Uri.parse(videoUrl))
    videoView.start()
  }

  AndroidView(
    modifier = Modifier
      .fillMaxSize()
      .background(ChatTheme.colors.appBackground),
    factory = { videoView }
  )

  if (!prepared) {
    CircularProgressIndicator(
      modifier = Modifier.align(Alignment.Center),
      color = ChatTheme.colors.primaryAccent
    )
  }
}

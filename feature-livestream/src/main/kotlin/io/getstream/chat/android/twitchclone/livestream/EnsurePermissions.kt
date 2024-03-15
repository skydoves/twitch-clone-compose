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

package io.getstream.chat.android.twitchclone.livestream

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EnsureVideoCallPermissions(onPermissionsGranted: () -> Unit) {
  // While the SDK will handle the microphone permission,
  // its not a bad idea to do it prior to entering any call UIs
  val permissionsState = rememberMultiplePermissionsState(
    permissions = buildList {
      // Access to camera & microphone
      add(Manifest.permission.CAMERA)
      add(Manifest.permission.RECORD_AUDIO)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // Allow for foreground service for notification on API 26+
        add(Manifest.permission.FOREGROUND_SERVICE)
      }
    }
  )

  LaunchedEffect(key1 = Unit) {
    permissionsState.launchMultiplePermissionRequest()
  }

  LaunchedEffect(key1 = permissionsState.allPermissionsGranted) {
    if (permissionsState.allPermissionsGranted) {
      onPermissionsGranted()
    }
  }
}

/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import io.getstream.chat.android.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id("getstream.android.application")
  id("getstream.android.application.compose")
  id("getstream.android.hilt")
  id("getstream.spotless")
  id("kotlin-parcelize")
  id(libs.plugins.google.secrets.get().pluginId)
  id(libs.plugins.baseline.profile.get().pluginId)
}

android {
  namespace = "io.getstream.chat.android.twitchclone"
  compileSdk = Configuration.compileSdk

  defaultConfig {
    minSdk = Configuration.minSdk
    targetSdk = Configuration.targetSdk
    versionName = Configuration.versionName
    versionCode = Configuration.versionCode
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    isCoreLibraryDesugaringEnabled = true
  }

  kotlinOptions {
    jvmTarget = libs.versions.jvmTarget.get()
  }

  lint {
    abortOnError = false
  }

  packaging {
    resources {
      excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
  }

  buildTypes {
    create("benchmark") {
      isDebuggable = false
      signingConfig = signingConfigs.getByName("debug")
      matchingFallbacks += listOf("release")
      proguardFiles("benchmark-rules.pro")
    }
  }
}

secrets {
  propertiesFileName = "secrets.properties"
  defaultPropertiesFileName = "secrets.defaults.properties"
  ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
  ignoreList.add("sdk.*") // Ignore all keys matching the regexp "sdk.*"
}

dependencies {
  // core modules
  implementation(project(":core-designsystem"))
  implementation(project(":core-navigation"))
  implementation(project(":core-model"))

  // feature modules
  implementation(project(":feature-channels"))
  implementation(project(":feature-messages"))
  implementation(project(":feature-livestream"))

  // Stream SDK
  implementation(libs.stream.chat.compose)
  implementation(libs.stream.chat.offline)
  implementation(libs.stream.video.mock)

  // Jetpack
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.material)
  implementation(libs.androidx.startup)

  // Compose
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.lifecycle.viewModelCompose)

  implementation(libs.material)
  implementation(libs.accompanist.pager)
  implementation(libs.accompanist.flowlayout)
  implementation(libs.accompanist.permissions)

  implementation(libs.landscapist.coil)
  implementation(libs.landscapist.animation)
  implementation(libs.landscapist.placeholder)

  implementation(libs.stream.log)
  coreLibraryDesugaring(libs.android.desugarJdkLibs)

  baselineProfile(project(":baselineprofile"))
}

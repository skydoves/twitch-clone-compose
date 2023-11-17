import io.getstream.chat.android.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  id(libs.plugins.android.test.get().pluginId)
  id(libs.plugins.kotlin.android.get().pluginId)
  id(libs.plugins.baseline.profile.get().pluginId)
}

android {
  namespace = "io.getstream.chat.android.twitchclone.baselineprofile"
  compileSdk = Configuration.compileSdk

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = libs.versions.jvmTarget.get()
  }

  defaultConfig {
    minSdk = 24
    targetSdk = Configuration.targetSdk
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    create("benchmark") {
      isDebuggable = false
      signingConfig = signingConfigs.getByName("debug")
      matchingFallbacks += listOf("release")
    }
  }

  targetProjectPath = ":app"

  testOptions.managedDevices.devices {
    maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel6api31").apply {
      device = "Pixel 6"
      apiLevel = 31
      systemImageSource = "aosp"
    }
  }
}

// This is the plugin configuration. Everything is optional. Defaults are in the
// comments. In this example, you use the GMD added earlier and disable connected devices.
baselineProfile {

  // This specifies the managed devices to use that you run the tests on. The default
  // is none.
  managedDevices += "pixel6api31"

  // This enables using connected devices to generate profiles. The default is true.
  // When using connected devices, they must be rooted or API 33 and higher.
  useConnectedDevices = false
}

dependencies {
  implementation(libs.androidx.test.runner)
  implementation(libs.androidx.test.uiautomator)
  implementation(libs.androidx.benchmark.macro)
  implementation(libs.androidx.profileinstaller)
}

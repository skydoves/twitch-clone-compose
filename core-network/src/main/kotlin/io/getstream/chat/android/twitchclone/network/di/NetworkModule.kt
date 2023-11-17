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

package io.getstream.chat.android.twitchclone.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.twitchclone.network.service.RewardsService
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @Provides
  @Singleton
  fun providesNetworkJson(): Json = Json {
    ignoreUnknownKeys = true
  }

  @Provides
  @Singleton
  fun provideTwitchRetrofit(networkJson: Json): Retrofit {
    return Retrofit.Builder()
      .baseUrl(
        "https://gist.githubusercontent.com/skydoves/c2da9e382222ba50aba7a01fcace76e1/" +
          "raw/d87a6fdfa4cff479d8dae7b32ac19a0c6d16817a/"
      )
      .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideRewardsService(
    retrofit: Retrofit
  ): RewardsService {
    return retrofit.create()
  }
}

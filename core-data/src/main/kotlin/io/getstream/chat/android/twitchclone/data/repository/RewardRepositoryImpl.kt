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

package io.getstream.chat.android.twitchclone.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import io.getstream.chat.android.twitchclone.data.mapper.toModel
import io.getstream.chat.android.twitchclone.database.dao.RewardDao
import io.getstream.chat.android.twitchclone.database.entity.asEntity
import io.getstream.chat.android.twitchclone.model.Reward
import io.getstream.chat.android.twitchclone.network.Dispatcher
import io.getstream.chat.android.twitchclone.network.TwitchDispatchers
import io.getstream.chat.android.twitchclone.network.service.RewardsService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * This is an implementation of [RewardRepository].
 *
 * Make sure the implementation class must not be exposed to the outside by using 'internal' visibility modifier.
 */
internal class RewardRepositoryImpl @Inject constructor(
  @Dispatcher(TwitchDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
  private val rewardsService: RewardsService,
  private val rewardDao: RewardDao
) : RewardRepository {

  override fun getRewardStream(): Flow<ApiResponse<List<Reward>>> = flow {
    val offlineRewards = rewardDao.getRewards()
    if (offlineRewards.isEmpty()) {
      val apiResponse = rewardsService.fetchRewards()
      updateRewards(apiResponse)
      emit(apiResponse)
    } else {
      emit(ApiResponse.Success(offlineRewards.map { it.toModel() }))
    }
  }.flowOn(ioDispatcher)

  override suspend fun updateRewards(rewards: ApiResponse<List<Reward>>) {
    val entities = rewards.getOrNull()?.map { it.asEntity() } ?: return
    rewardDao.insertRewards(entities)
  }
}

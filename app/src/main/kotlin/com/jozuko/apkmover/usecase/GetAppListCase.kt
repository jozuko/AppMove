package com.jozuko.apkmover.usecase

import com.jozuko.apkmover.domain.ApkInfo
import com.jozuko.apkmover.domain.ApkRepository
import com.jozuko.apkmover.domain.Future
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class GetAppListCase @Inject constructor(
    private val apkRepository: ApkRepository,
) {
    operator fun invoke(): Flow<Future<List<ApkInfo>>> {
        return apkRepository.getAll()
            .map {
                Future.Success(it)
            }
            .catch { cause ->
                Future.Error(cause)
            }
            .flowOn(Dispatchers.IO)
    }
}
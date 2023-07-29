package com.jozuko.apkmover.domain

import kotlinx.coroutines.flow.Flow

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
interface ApkRepository {
    fun getAll(): Flow<List<ApkInfo>>
    suspend fun moveToExternalStorage(apkInfo: ApkInfo)
}
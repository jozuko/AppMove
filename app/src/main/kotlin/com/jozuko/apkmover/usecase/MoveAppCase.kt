package com.jozuko.apkmover.usecase

import com.jozuko.apkmover.domain.ApkInfo
import com.jozuko.apkmover.domain.ApkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class MoveAppCase @Inject constructor(
    private val apkRepository: ApkRepository,
) {
    suspend fun invoke(apkInfo: ApkInfo) = withContext(Dispatchers.IO) {
        apkRepository.moveToExternalStorage(apkInfo)
    }
}
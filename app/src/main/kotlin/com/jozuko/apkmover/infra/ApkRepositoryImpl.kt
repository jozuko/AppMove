package com.jozuko.apkmover.infra

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.jozuko.apkmover.domain.ApkInfo
import com.jozuko.apkmover.domain.ApkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Suppress("DEPRECATION")
class ApkRepositoryImpl(
    private val context: Context,
) : ApkRepository {
    override fun getAll(): Flow<List<ApkInfo>> {
        return flow {
            val packageManager = context.packageManager
            val installedApplications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0L))
            } else {
                packageManager.getInstalledApplications(0)
            }

            val appInfoList = installedApplications.map { nativeAppInfo ->
                ApkInfo(
                    packageName = nativeAppInfo.packageName,
                    label = nativeAppInfo.loadLabel(packageManager).toString(),
                    icon = nativeAppInfo.loadIcon(packageManager),
                    apkPath = nativeAppInfo.sourceDir,
                )
            }.sortedBy { it.label }

            emit(appInfoList)
        }
    }

    override suspend fun moveToExternalStorage(apkInfo: ApkInfo) {
        val apkFile = File(apkInfo.apkPath)
        if (!apkFile.exists()) {
            Log.e("ApkRepositoryImpl", "[${Thread.currentThread().name}] apkFile dose not exist.(${apkInfo.apkPath})")
            return
        }

        val dir = context.getExternalFilesDir("apks")
        if (dir == null) {
            Log.e("ApkRepositoryImpl", "[${Thread.currentThread().name}] cannot get ExternalFilesDir.")
            return
        }

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e("ApkRepositoryImpl", "[${Thread.currentThread().name}] cannot create dir.(${dir.absolutePath})")
                return
            }
        }

        val apkFileName = "${System.currentTimeMillis()}-${apkFile.name}"
        val copiedFile = apkFile.copyTo(
            File(dir, apkFileName),
            overwrite = true,
        )

        Log.d("ApkRepositoryImpl", "adb pull ${copiedFile.absolutePath} $apkFileName")
        Log.d("ApkRepositoryImpl", "apksigner verify --print-certs -v $apkFileName")
    }
}
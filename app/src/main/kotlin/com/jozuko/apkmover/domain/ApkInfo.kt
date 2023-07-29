package com.jozuko.apkmover.domain

import android.graphics.drawable.Drawable

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class ApkInfo(
    val packageName: String,
    val label: String,
    val icon: Drawable,
    val apkPath: String,
)
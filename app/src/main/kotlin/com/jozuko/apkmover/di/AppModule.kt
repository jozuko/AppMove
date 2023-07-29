package com.jozuko.apkmover.di

import android.content.Context
import com.jozuko.apkmover.domain.ApkRepository
import com.jozuko.apkmover.infra.ApkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideApkRepository(
        @ApplicationContext context: Context,
    ): ApkRepository = ApkRepositoryImpl(context)
}
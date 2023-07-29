package com.jozuko.apkmover.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jozuko.apkmover.domain.ApkInfo
import com.jozuko.apkmover.domain.Future
import com.jozuko.apkmover.presentation.common.text.AutoSizeText

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun AppListScreen(
    viewModel: AppListViewModel = hiltViewModel(),
) {
    val appListFuture = viewModel.appFutureStateFlow.collectAsState().value
    when (appListFuture) {
        is Future.Success -> DataScreen(viewModel, appListFuture.value)
        else -> LoadingScreen(modifier = Modifier)
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 10.dp,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DataScreen(viewModel: AppListViewModel, appList: List<ApkInfo>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = appList) { apkInfo ->
            Box(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onLongClick = { viewModel.onLongClick(apkInfo) },
                            onClick = {},
                        ),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    AsyncImage(
                        model = apkInfo.icon, contentDescription = apkInfo.label, modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        AutoSizeText(
                            apkInfo.label,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            autoSizeMinTextSize = 5f,
                            autoSizeMaxTextSize = 20f,
                        )
                        AutoSizeText(
                            apkInfo.packageName,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            autoSizeMinTextSize = 5f,
                            autoSizeMaxTextSize = 16f,
                        )
                    }
                }
            }
        }
    }
}
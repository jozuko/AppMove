package com.jozuko.apkmover.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jozuko.apkmover.presentation.common.snackbar.SnackbarManager
import com.jozuko.apkmover.presentation.theme.ApkMoverTheme
import kotlinx.coroutines.CoroutineScope

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun ApkMoverApp() {
    val appState = rememberAppState()

    ApkMoverTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    AppListScreen()
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): ApkMoverAppState {
    return remember(snackbarHostState, snackbarManager, coroutineScope) {
        ApkMoverAppState(snackbarHostState, snackbarManager, coroutineScope)
    }
}

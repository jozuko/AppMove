package com.jozuko.apkmover.presentation.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import com.jozuko.apkmover.presentation.common.snackbar.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Stable
class ApkMoverAppState(
    val snackbarHostState: SnackbarHostState,
    private val snackbarManager: SnackbarManager,
    private val coroutineScope: CoroutineScope,
) {
    init {
        coroutineScope.launch {
            snackbarManager.messges.filterNotNull().collect { snackbarMessage ->
                snackbarHostState.showSnackbar(snackbarMessage.message)
            }
        }
    }
}

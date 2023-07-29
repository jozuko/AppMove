package com.jozuko.apkmover.presentation.common.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
object SnackbarManager {
    private val _messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val messges: StateFlow<SnackbarMessage?> get() = _messages.asStateFlow()

    fun showMessage(message: String) {
        _messages.value = SnackbarMessage(message)
    }
}
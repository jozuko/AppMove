package com.jozuko.apkmover.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozuko.apkmover.domain.ApkInfo
import com.jozuko.apkmover.domain.Future
import com.jozuko.apkmover.presentation.common.snackbar.SnackbarManager
import com.jozuko.apkmover.usecase.GetAppListCase
import com.jozuko.apkmover.usecase.MoveAppCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppListCase: GetAppListCase,
    private val moveAppCase: MoveAppCase,
) : ViewModel() {
    private val _appListStateFlow: MutableStateFlow<Future<List<ApkInfo>>> = MutableStateFlow(Future.Idle)
    val appFutureStateFlow: StateFlow<Future<List<ApkInfo>>> get() = _appListStateFlow.asStateFlow()

    init {
        refreshAppList()
    }

    private fun refreshAppList() {
        viewModelScope.launch {
            _appListStateFlow.value = Future.Proceeding

            Log.d("AppListViewModel", "getAppList().collect start.")
            getAppListCase().collect {
                Log.d("AppListViewModel", "getAppList().collect done.")
                _appListStateFlow.value = it
            }
        }
    }

    fun onLongClick(apkInfo: ApkInfo) {
        viewModelScope.launch {
            moveAppCase.invoke(apkInfo)
            SnackbarManager.showMessage("DONE!!")
        }
    }
}
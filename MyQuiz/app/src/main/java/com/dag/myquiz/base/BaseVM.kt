package com.dag.myquiz.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseVM<T: BaseVS>(initialValue: T = BaseVS.Default as T) : ViewModel() {

    private var _viewState:MutableStateFlow<T> = MutableStateFlow(initialValue)
    var viewState: StateFlow<T> = _viewState.asStateFlow()

}
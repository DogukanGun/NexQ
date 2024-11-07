package com.dag.nexq_app.presentation.main_activity

import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private var dataPreference: DataPreferencesStore) : BaseVM<MainVS>(
    MainVS.Default
) {
    fun getColor() {
        viewModelScope.launch {
            dataPreference.read(DataPreferencesStore.COLOR).collect{ color ->
                color?.let {
                    _viewState.value = MainVS.ColorChanged(it)
                }
            }
        }
    }
}
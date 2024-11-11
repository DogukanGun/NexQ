package com.dag.nexq_app.presentation.mainActivity

import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private var dataPreference: DataPreferencesStore,
    private var defaultNavigator: DefaultNavigator
    ) : BaseVM<MainVS>(
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

    fun navigate(destination: Destination){
        viewModelScope.launch {
            defaultNavigator.navigate(destination)
        }
    }

    fun isBottomNavActive(currentRoute:String?): Boolean {
        return currentRoute?.let {
            return Destination.NAV_WITHOUT_BOTTOM_NAVBAR
                .map { it.toString() }.contains(currentRoute).not()
        } ?: false
    }

}
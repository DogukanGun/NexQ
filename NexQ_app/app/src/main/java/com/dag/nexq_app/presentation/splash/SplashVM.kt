package com.dag.nexq_app.presentation.splash

import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val navigator: DefaultNavigator,
    private val dataPreferencesStore: DataPreferencesStore
): BaseVM<SplashVS>(SplashVS.Default){
    fun startApplication(){
        viewModelScope.launch {
            dataPreferencesStore.read(DataPreferencesStore.FIRST_LOGIN).collectLatest {
                val destination = if (it == true) {
                    Destination.Onboard
                }else{
                    Destination.LoginScreen
                }
                navigator.navigate(destination) {
                    this.popUpTo(0)
                }
            }
        }
    }
}
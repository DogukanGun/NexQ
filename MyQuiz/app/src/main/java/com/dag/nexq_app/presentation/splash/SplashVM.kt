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
            val firstLoginFlow = dataPreferencesStore.read(DataPreferencesStore.FIRST_LOGIN)
            val tokenFlow = dataPreferencesStore.read(DataPreferencesStore.TOKEN)
            firstLoginFlow.combine(tokenFlow) { firstLogin,token ->
                (firstLogin ?: true) to (token ?: "")
            }.collectLatest {
                val destination = if (it.first) {
                    Destination.Onboard
                }else if (it.second == ""){
                    Destination.LoginScreen
                }else {
                    Destination.HomeGraph
                }
                navigator.navigate(destination) {
                    this.popUpTo(0)
                }
            }
        }
    }
}
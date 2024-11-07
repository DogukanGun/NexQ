package com.dag.nexq_app.presentation.onboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.R
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardVM @Inject constructor(private val preferencesStore: DataPreferencesStore) :
    BaseVM<OnboardVS>(
        initialValue = OnboardVS.OnboardContent(
            R.drawable.image1,
            R.string.onboard_title_1,
            R.string.onboard_subtitle_1,
            Color.Magenta,
            R.string.onboard_next
        )
    ) {

    init {
        viewModelScope.launch {
            preferencesStore.write(DataPreferencesStore.COLOR, Color.Magenta.toArgb())
        }
    }

    val secondContent = OnboardVS.OnboardContent(
        R.drawable.image2,
        R.string.onboard_title_2,
        R.string.onboard_subtitle_2,
        Color.Blue,
        R.string.onboard_next
    )

    val thirdContent = OnboardVS.OnboardContent(
        R.drawable.image3,
        R.string.onboard_title_3,
        R.string.onboard_subtitle_3,
        Color.Red,
        R.string.onboard_next
    )

    var currentStep = mutableStateOf(0)

    fun startNextStep() {
        currentStep.value += 1;
        Log.d("OnboardVM", "Current step: ${currentStep.value}")
        _viewState.value = when (currentStep.value) {
            1 -> {
                Log.d("OnboardVM", "Setting secondContent")
                viewModelScope.launch {
                    preferencesStore.write(DataPreferencesStore.COLOR, secondContent.color.toArgb())
                }
                secondContent
            }

            2 -> {
                Log.d("OnboardVM", "Setting thirdContent")
                viewModelScope.launch {
                    preferencesStore.write(DataPreferencesStore.COLOR, thirdContent.color.toArgb())
                }
                thirdContent
            }

            else -> {
                Log.d("OnboardVM", "Ending onboarding")
                OnboardVS.StartUserOps
            }
        }
        Log.d("OnboardVM", "New viewState: ${_viewState.value}")
    }

    fun finishNavigation(){
        _viewState.value = OnboardVS.Default
    }
}
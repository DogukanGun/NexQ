package com.dag.nexq_app.presentation.onboard

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.R
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardVM @Inject constructor(
    private val preferencesStore: DataPreferencesStore,
    private val defaultNavigator: DefaultNavigator
) :
    BaseVM<OnboardVS>(
        initialValue = OnboardVS.Default
    ) {

    private val firstContent = OnboardVS.OnboardContent(
        R.drawable.image1,
        R.string.onboard_title_1,
        R.string.onboard_subtitle_1,
        Color.Magenta,
        R.string.onboard_next
    )

    private val secondContent = OnboardVS.OnboardContent(
        R.drawable.image2,
        R.string.onboard_title_2,
        R.string.onboard_subtitle_2,
        Color.Blue,
        R.string.onboard_next
    )

    private val thirdContent = OnboardVS.OnboardContent(
        R.drawable.image3,
        R.string.onboard_title_3,
        R.string.onboard_subtitle_3,
        Color.Red,
        R.string.onboard_start
    )

    val contents = listOf(firstContent, secondContent, thirdContent)

    init {
        viewModelScope.launch {
            preferencesStore.write(DataPreferencesStore.COLOR, contents[0].color.toArgb())
        }
    }

    fun changeNavbarColor(contentIndex: Int) {
        viewModelScope.launch {
            preferencesStore.write(
                DataPreferencesStore.COLOR,
                contents[contentIndex].color.toArgb()
            )
        }
    }

    fun goLogin() {
        viewModelScope.launch {
            defaultNavigator.navigate(Destination.LoginScreen) {
                this.popUpTo(0)
            }
        }
    }

}
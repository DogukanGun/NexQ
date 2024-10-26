package com.dag.myquiz.presentation.onboard

import androidx.compose.ui.graphics.Color
import com.dag.myquiz.R
import com.dag.myquiz.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class OnboardVM: BaseVM<OnboardVS>(
    initialValue = OnboardVS.OnboardContent(
        R.drawable.image1,
        R.string.onboard_title_1,
        R.string.onboard_subtitle_1,
        Color.Blue,
        R.string.onboard_next
    )
) {

    val secondContent = OnboardVS.OnboardContent(
        R.drawable.image1,
        R.string.onboard_title_1,
        R.string.onboard_subtitle_1,
        Color.Blue,
        R.string.onboard_next
    )

    val thirdContent = OnboardVS.OnboardContent(
        R.drawable.image1,
        R.string.onboard_title_1,
        R.string.onboard_subtitle_1,
        Color.Blue,
        R.string.onboard_next
    )

    fun startNextStep() {

    }
}
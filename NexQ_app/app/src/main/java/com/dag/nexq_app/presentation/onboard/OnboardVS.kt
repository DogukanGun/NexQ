package com.dag.nexq_app.presentation.onboard

import androidx.compose.ui.graphics.Color
import com.dag.nexq_app.base.BaseVS

sealed class OnboardVS: BaseVS {

    data class OnboardContent(
        val image: Int,
        val title: Int,
        val subtitle: Int,
        val color: Color,
        val buttonText: Int
    ) : OnboardVS()
    data object Default: OnboardVS()
}

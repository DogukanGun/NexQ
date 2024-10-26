package com.dag.myquiz.presentation.onboard

import androidx.compose.ui.graphics.Color
import com.dag.myquiz.base.BaseVS

sealed class OnboardVS:BaseVS{

    data class OnboardContent(val image:Int,val title:Int,val subtitle:Int,val color:Color,val buttonText:Int): OnboardVS()
    data object StartUserOps: OnboardVS()
}

package com.dag.nexq_app

import com.dag.nexq_app.base.BaseVS

sealed class MainVS : BaseVS {
    data class ColorChanged(val color: Int) : MainVS()
    data object Default : MainVS()
}
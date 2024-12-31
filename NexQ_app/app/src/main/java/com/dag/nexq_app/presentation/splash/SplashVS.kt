package com.dag.nexq_app.presentation.splash

import com.dag.nexq_app.base.BaseVS

sealed class SplashVS: BaseVS {
    data object Default: SplashVS()
}

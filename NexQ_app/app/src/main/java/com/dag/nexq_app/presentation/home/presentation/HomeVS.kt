package com.dag.nexq_app.presentation.home.presentation

import com.dag.nexq_app.base.BaseVS


sealed class HomeVS: BaseVS {

    data object Default: HomeVS()
}

package com.dag.nexq_app.presentation.mining

import com.dag.nexq_app.base.BaseVS

sealed class MiningState: BaseVS {
    data object Default: MiningState()
    data class ReturnResult(val address:String): MiningState()
}

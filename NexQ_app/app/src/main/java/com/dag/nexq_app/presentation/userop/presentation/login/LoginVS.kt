package com.dag.nexq_app.presentation.userop.presentation.login

import com.dag.nexq_app.base.BaseVS

sealed class LoginVS: BaseVS {
    data object Default: LoginVS()
}
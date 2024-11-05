package com.dag.nexq_app.presentation.userop.login

import com.dag.nexq_app.base.BaseVS

sealed class LoginVS: BaseVS {
    data object Default: LoginVS()
}
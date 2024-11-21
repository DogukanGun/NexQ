package com.dag.nexq_app.blockchain.data

import kotlinx.serialization.Serializable

@Serializable
data class CheckAssignmentResponse(
    val userWallet: String,
    val searchText: String
)

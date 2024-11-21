package com.dag.nexq_app.blockchain.data

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentAnswer(
    val userContract: String,
    val answer: String
)

@Serializable
data class AnswerAssignmentsRequest(
    val answers: MutableList<AssignmentAnswer>,
    val nodeWalletAddress: String
)

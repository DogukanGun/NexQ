package com.dag.nexq_app.blockchain

import com.dag.nexq_app.blockchain.data.AnswerAssignmentsRequest
import com.dag.nexq_app.blockchain.data.CheckAssignmentResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface BlockService {

    @POST("/check/{wallet_address}")
    suspend fun checkForAssignment(@Path("wallet_address") walletAddress: String): List<CheckAssignmentResponse>

    @POST("/answer")
    suspend fun answerTheSearch(answers: AnswerAssignmentsRequest)
}
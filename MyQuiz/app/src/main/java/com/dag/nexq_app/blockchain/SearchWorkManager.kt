package com.dag.nexq_app.blockchain

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dag.nexq_app.BuildConfig
import com.dag.nexq_app.blockchain.data.AnswerAssignmentsRequest
import com.dag.nexq_app.blockchain.data.AssignmentAnswer
import com.dag.nexq_app.blockchain.data.GoogleSearchResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Named

@HiltWorker
class SearchWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted val workerParams: WorkerParameters,
    @Named("Block") val blockRetrofit: Retrofit,
    @Named("Google") val googleRetrofit: Retrofit
): Worker(appContext, workerParams)  {

    override fun doWork(): Result {
        val service = blockRetrofit.create(BlockService::class.java)
        workerParams.inputData.getString("wallet_key")?.let {
            val scope = CoroutineScope(
                SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
                    println("Caught exception: $exception")
                }
            )
            val answers by mutableStateOf(AnswerAssignmentsRequest(
                emptyList<AssignmentAnswer>() as MutableList<AssignmentAnswer>,
                it
            ))
            scope.launch {
                val assignments = service.checkForAssignment(it)
                assignments.forEach { request ->
                    val result = performGoogleSearch(request.searchText)
                    answers.answers.add(AssignmentAnswer(
                        request.userWallet,
                        if (result.items?.isNotEmpty() == true)
                            result.items[0].title
                        else ""
                    ))
                }
                service.answerTheSearch(answers)
            }

        }
        TODO("Call API to get info, do search, then send back to result")
    }

    private suspend fun performGoogleSearch(query: String): GoogleSearchResponse {
        val googleService = googleRetrofit.create(GoogleSearchService::class.java)
        return googleService.searchGoogle(
            BuildConfig.GOOGLE_SEARCH_KEY,
            "017576662512468239146:omuauf_lfve",
            query
        )
    }

}
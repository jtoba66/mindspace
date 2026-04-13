package com.mindspace.app.data.repository

import com.mindspace.app.data.api.MindSpaceApi
import com.mindspace.app.data.model.PlanResponse
import com.mindspace.app.data.model.ProcessRequest
import retrofit2.Response

class SessionRepository(private val api: MindSpaceApi) {
    suspend fun processBrainDump(
        input: String,
        isPrivate: Boolean,
        energy: String?
    ): Response<PlanResponse> {
        val request = ProcessRequest(
            input = input,
            isPrivate = isPrivate,
            energyOverride = energy
        )
        return api.processBrainDump(request)
    }

    suspend fun getJourney(): Response<List<PlanResponse>> {
        return api.getSessions()
    }
}

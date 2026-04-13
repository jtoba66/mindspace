package com.mindspace.app.data.api

import com.mindspace.app.data.model.PlanResponse
import com.mindspace.app.data.model.ProcessRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MindSpaceApi {
    @POST("api/sessions/process")
    suspend fun processBrainDump(@Body request: ProcessRequest): Response<PlanResponse>

    @GET("api/sessions")
    suspend fun getSessions(): Response<List<PlanResponse>>
}

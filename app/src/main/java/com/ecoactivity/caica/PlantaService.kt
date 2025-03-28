package com.ecoactivity.caica

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantaService {
    @GET("plantas")
    suspend fun getPlantas(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<List<Planta>>
}

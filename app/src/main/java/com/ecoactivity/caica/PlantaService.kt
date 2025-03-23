package com.ecoactivity.caica

import retrofit2.http.GET

interface PlantaService {
    @GET("plantas")
    suspend fun getPlantas(): List<Planta>
}

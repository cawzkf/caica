package com.ecoactivity.caica

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response

/**
 * Interface que define os endpoints da API relacionados a plantas
 * Utiliza Retrofit para fazer chamadas HTTPS assíncronas com suporte a corrotinas
 */
interface PlantaService {

    /**
     * Requisição GET que busca todas as plantas cadastradas
     * Endpoint base deve retornar uma lista de objetos do tipo Planta
     */
    @GET(".")
    suspend fun getPlantasFull(): Response<List<Planta>>

    /**
     * Requisição POST que envia o nome de um arquivo para identificação da planta
     * O backend  processa a imagem enviada e retornar o objeto Planta correspondente
     */
    @POST(".")
    suspend fun identifyPlantas(@Body fileName: Map<String, String>): Response<Planta>
}

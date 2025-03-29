package com.ecoactivity.caica

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // URL dinâmica para alternar entre produção, teste e desenvolvimento
    private const val BASE_URL_DEV = "http://192.168.0.100:3000/"
    private const val BASE_URL_PROD = ""
    private val BASE_URL = BASE_URL_DEV

    // Criando um interceptor para log das requisições (apenas para debugging)
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Configurando OkHttpClient com timeout, logging e retry automático
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Adiciona log das requisições para debugging
            .connectTimeout(15, TimeUnit.SECONDS) // Tempo máximo de conexão reduzido para melhor resposta
            .readTimeout(15, TimeUnit.SECONDS) // Tempo máximo de leitura
            .writeTimeout(15, TimeUnit.SECONDS) // Tempo máximo de escrita
            .retryOnConnectionFailure(true) // Tenta reconectar automaticamente em caso de falha
            .build()
    }

    // Criando uma instância única do Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Usa o cliente configurado
            .addConverterFactory(GsonConverterFactory.create()) // Converte JSON automaticamente
            .build()
    }

    // Criando uma instância única do serviço da API
    val plantaService: PlantaService by lazy {
        retrofit.create(PlantaService::class.java)
    }
}

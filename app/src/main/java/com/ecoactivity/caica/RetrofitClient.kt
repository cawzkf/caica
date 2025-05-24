package com.ecoactivity.caica

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto singleton responsável por configurar e expor o cliente Retrofit
 * Utiliza OkHttp com interceptor de log e timeouts definidos
 */
object RetrofitClient {

    // URL base da API em produção (AWS Lambda)
    private const val BASE_URL_PROD = "https://hapufnlfvskmsslgghhmjva5me0fuerq.lambda-url.us-east-1.on.aws/"
    private const val BASE_URL = BASE_URL_PROD // pode ser trocada por uma URL de teste se necessário

    // Interceptor para exibir logs das requisições no Logcat (útil para debug)
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Cliente HTTP personalizado com log, timeouts e tentativa automática de reconexão
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS) // tempo máximo para abrir conexão
            .readTimeout(15, TimeUnit.SECONDS)    // tempo máximo para leitura de resposta
            .writeTimeout(15, TimeUnit.SECONDS)   // tempo máximo para envio de dados
            .retryOnConnectionFailure(true)       // tenta novamente em caso de falha de rede
            .build()
    }

    // Instância Retrofit configurada com client e conversor Gson
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // converte JSON para objetos Kotlin
            .build()
    }

    // Serviço Retrofit que implementa a interface PlantaService
    val plantaService: PlantaService by lazy {
        retrofit.create(PlantaService::class.java)
    }
}

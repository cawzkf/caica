package com.ecoactivity.caica

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.HttpException

class CatalogoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantaAdapter: PlantaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        // Inicializa RecyclerView
        recyclerView = findViewById(R.id.recycler_plantas)

        // Será exibido em um GRID com 3 colunas
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Inicializa o adapter vazio antes de carregar os dados
        plantaAdapter = PlantaAdapter(emptyList())
        recyclerView.adapter = plantaAdapter

        // Carrega os dados da API
        carregarPlantas()
    }

    private fun carregarPlantas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.plantaService.getPlantas() // Obtém a resposta da API

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) { // Verifica se a resposta foi bem-sucedida (código 200)
                        val plantas = response.body() // Obtém os dados da resposta

                        if (!plantas.isNullOrEmpty()) { //  verifica se a lista não está vazia
                            plantaAdapter = PlantaAdapter(plantas)
                            recyclerView.adapter = plantaAdapter
                            Log.d("API SUCCESS", "Plantas carregadas: ${plantas.size}")
                        } else {
                            Log.w("API WARNING", "Nenhuma planta foi carregada.")
                        }
                    } else {
                        Log.e("API ERROR", "Erro na resposta da API: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("API ERROR", "Erro ao carregar plantas: ${e.message}")
            }
        }
    }

}

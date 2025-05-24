package com.ecoactivity.caica

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.launch

/**
 * Activity responsável por exibir a lista de plantas em um layout de grade
 * Inclui funcionalidade de busca por nome popular ou científico
 */
class CatalogoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView // RecyclerView que exibe os cards de plantas
    private lateinit var plantaAdapter: PlantaAdapter // Adapter da RecyclerView
    private lateinit var barraPesquisa: EditText // Campo de busca
    private var listaPlantas: List<Planta> = emptyList() // Lista completa recebida da API
    private lateinit var progressBar: ProgressBar // Barra de carregamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo) // Define o layout da tela

        // Aplica o preenchimento para evitar que elementos fiquem sob status bar ou barra de navegação
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.catalogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar) // Inicializa barra de carregamento

        recyclerView = findViewById(R.id.recycler_plantas) // Inicializa RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 3) // Usa layout de grade com 3 colunas
        plantaAdapter = PlantaAdapter(emptyList()) // Cria o adapter vazio
        recyclerView.adapter = plantaAdapter // Define o adapter na RecyclerView

        barraPesquisa = findViewById(R.id.barraPesquisa) // Inicializa campo de pesquisa
        carregarPlantasDaApi() // Faz a requisição para buscar as plantas

        // Adiciona escuta no campo de texto para filtrar conforme o usuário digita
        barraPesquisa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarPlantas(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * Faz chamada assíncrona à API para buscar todas as plantas e atualizar a lista
     */
    private fun carregarPlantasDaApi() {
        progressBar.visibility = View.VISIBLE // Mostra o carregamento

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.plantaService.getPlantasFull()

                if (response.isSuccessful) {
                    val plantas = response.body() ?: emptyList()
                    listaPlantas = plantas // Salva lista original
                    plantaAdapter.atualizarLista(plantas) // Atualiza adapter
                    Log.d("CatalogoActivity", "Carregado: ${plantas.size} plantas")
                } else {
                    Log.e("CatalogoActivity", "Erro na resposta: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("CatalogoActivity", "Erro ao buscar plantas: ${e.message}", e)
            } finally {
                progressBar.visibility = View.GONE // Esconde carregamento
            }
        }
    }

    /**
     * Filtra a lista de plantas com base no texto digitado pelo usuário
     */
    private fun filtrarPlantas(query: String) {
        val listaFiltrada = listaPlantas.filter { planta ->
            planta.NOME_POPULAR.contains(query, ignoreCase = true) ||
                    planta.NOME_CIENTIFICO.contains(query, ignoreCase = true)
        }

        plantaAdapter.atualizarLista(listaFiltrada) // Atualiza o adapter com a lista filtrada
    }
}

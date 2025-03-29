package com.ecoactivity.caica

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantaAdapter: PlantaAdapter
    private lateinit var barraPesquisa: EditText
    private var listaPlantas: List<Planta> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.catalogo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recycler_plantas)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        plantaAdapter = PlantaAdapter(emptyList())
        recyclerView.adapter = plantaAdapter

        barraPesquisa = findViewById(R.id.barraPesquisa)
        carregarPlantasFake()

        barraPesquisa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarPlantas(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun carregarPlantasFake() {
            val dadosFakes = listOf(
                Planta(
                    ID = 1,
                    NOME_POPULAR = "Guaraná",
                    NOME_CIENTIFICO = "Paullinia cupana",
                    DESCRICAO_BOTANICA = "Arbusto trepador originário da Amazônia.",
                    HABITAT = "Floresta Amazônica, preferindo áreas sombreadas e úmidas.",
                    PROPRIEDADES_MEDICINAIS = "Estimulante, antioxidante, melhora a concentração.",
                    USO_TRADICIONAL = "Usado em bebidas energéticas por povos indígenas.",
                    METODO_PREPARO = "Sementes torradas e moídas para infusão ou bebidas.",
                    INDICACOES_TERAPEUTICAS = "Fadiga, dores de cabeça, melhora da memória.",
                    COMPOSTOS_ATIVOS = "Cafeína, taninos e saponinas.",
                    ESTUDOS_CIENTIFICOS = "Pesquisas mostram efeitos no desempenho mental e físico.",
                    SUSTENTABILIDADE = "Cultivado de forma sustentável em agroflorestas.",
                    IMPACTO_SOCIAL = "Fonte de renda para comunidades ribeirinhas.",
                    DATA_DESCOBERTA = "Conhecido e utilizado há séculos por indígenas.",
                    OBSERVACOES = "Altas doses podem causar insônia.",
                    USO_MEDICINAL = "Sim",
                    IMAGEM = null
                ),
                Planta(
                    ID = 2,
                    NOME_POPULAR = "Jambu",
                    NOME_CIENTIFICO = "Acmella oleracea",
                    DESCRICAO_BOTANICA = "Erva rasteira com flores amarelas vibrantes.",
                    HABITAT = "Amazônia, encontrada em áreas úmidas e ribeirinhas.",
                    PROPRIEDADES_MEDICINAIS = "Analgésico, anestésico natural, anti-inflamatório.",
                    USO_TRADICIONAL = "Mastigado para aliviar dor de dente e como afrodisíaco.",
                    METODO_PREPARO = "Infusão ou mascado diretamente.",
                    INDICACOES_TERAPEUTICAS = "Dor de dente, inflamações na boca.",
                    COMPOSTOS_ATIVOS = "Espilantol (responsável pelo efeito anestésico).",
                    ESTUDOS_CIENTIFICOS = "Evidências de eficácia contra inflamações bucais.",
                    SUSTENTABILIDADE = "Cultivo sustentável em hortas locais.",
                    IMPACTO_SOCIAL = "Utilizado na gastronomia paraense.",
                    DATA_DESCOBERTA = "Amplamente usado pelos indígenas amazônicos.",
                    OBSERVACOES = "Sensação de dormência temporária ao consumir.",
                    USO_MEDICINAL = "Sim",
                    IMAGEM = null
                ),
                Planta(
                    ID = 3,
                    NOME_POPULAR = "Açaí",
                    NOME_CIENTIFICO = "Euterpe oleracea",
                    DESCRICAO_BOTANICA = "Palmeira alta, com cachos de pequenos frutos roxos.",
                    HABITAT = "Floresta Amazônica, em áreas alagadas.",
                    PROPRIEDADES_MEDICINAIS = "Antioxidante, energético, anti-inflamatório.",
                    USO_TRADICIONAL = "Base da alimentação indígena, consumido em forma de suco.",
                    METODO_PREPARO = "Polpa extraída dos frutos e batida com água.",
                    INDICACOES_TERAPEUTICAS = "Fortalecimento do sistema imunológico.",
                    COMPOSTOS_ATIVOS = "Antocianinas, flavonoides e ácidos graxos essenciais.",
                    ESTUDOS_CIENTIFICOS = "Reduz riscos cardiovasculares.",
                    SUSTENTABILIDADE = "Exploração sustentável e manejo comunitário.",
                    IMPACTO_SOCIAL = "Fonte de renda essencial para comunidades amazônicas.",
                    DATA_DESCOBERTA = "Usado há séculos como alimento energético.",
                    OBSERVACOES = "Deve ser consumido fresco para melhor aproveitamento nutricional.",
                    USO_MEDICINAL = "Sim",
                    IMAGEM = null
                ),
                Planta(
                    ID = 4,
                    NOME_POPULAR = "Copaíba",
                    NOME_CIENTIFICO = "Copaifera langsdorffii",
                    DESCRICAO_BOTANICA = "Árvore grande que produz óleo medicinal.",
                    HABITAT = "Floresta Amazônica, em solos bem drenados.",
                    PROPRIEDADES_MEDICINAIS = "Antisséptico, anti-inflamatório, cicatrizante.",
                    USO_TRADICIONAL = "Óleo usado para tratar feridas e inflamações.",
                    METODO_PREPARO = "Óleo extraído do tronco da árvore.",
                    INDICACOES_TERAPEUTICAS = "Feridas, infecções e problemas respiratórios.",
                    COMPOSTOS_ATIVOS = "Beta-cariofileno e ácidos diterpênicos.",
                    ESTUDOS_CIENTIFICOS = "Evidências sobre eficácia anti-inflamatória.",
                    SUSTENTABILIDADE = "Extração sustentável preserva árvores.",
                    IMPACTO_SOCIAL = "Usado na medicina popular brasileira.",
                    DATA_DESCOBERTA = "Amplamente usado por tribos indígenas.",
                    OBSERVACOES = "O uso excessivo pode causar irritação estomacal.",
                    USO_MEDICINAL = "Sim",
                    IMAGEM = null
                ),
                Planta(
                    ID = 5,
                    NOME_POPULAR = "Murici",
                    NOME_CIENTIFICO = "Byrsonima crassifolia",
                    DESCRICAO_BOTANICA = "Árvore frutífera de pequeno porte.",
                    HABITAT = "Regiões de cerrado e floresta tropical.",
                    PROPRIEDADES_MEDICINAIS = "Expectorante, antioxidante, digestivo.",
                    USO_TRADICIONAL = "Frutos usados para fortalecer a imunidade.",
                    METODO_PREPARO = "Consumido in natura ou em sucos.",
                    INDICACOES_TERAPEUTICAS = "Problemas respiratórios, digestão lenta.",
                    COMPOSTOS_ATIVOS = "Taninos, flavonoides, vitamina C.",
                    ESTUDOS_CIENTIFICOS = "Pesquisa aponta propriedades anti-inflamatórias.",
                    SUSTENTABILIDADE = "Cultivo em áreas nativas mantém biodiversidade.",
                    IMPACTO_SOCIAL = "Fruto amplamente consumido e comercializado.",
                    DATA_DESCOBERTA = "Amplamente conhecido pelos indígenas.",
                    OBSERVACOES = "Sabor forte, pode ser ácido ao paladar.",
                    USO_MEDICINAL = "Sim",
                    IMAGEM = null
                ),
                Planta(
                    ID = 6, NOME_POPULAR = "Tucumã",
                    NOME_CIENTIFICO = "Astrocaryum aculeatum",
                    IMAGEM = null
                ),
                Planta(
                    ID = 7,
                    NOME_POPULAR = "Breu-branco",
                    NOME_CIENTIFICO = "Protium heptaphyllum",
                    IMAGEM = null),
                Planta(
                    ID = 8,
                    NOME_POPULAR = "Unha-de-gato",
                    NOME_CIENTIFICO = "Uncaria tomentosa",
                    IMAGEM = null),
                Planta(
                    ID = 9,
                    NOME_POPULAR = "Pariri",
                    NOME_CIENTIFICO = "Arrabidaea chica",
                    IMAGEM = null),
                Planta(
                    ID = 10,
                    NOME_POPULAR = "Tayuya",
                    NOME_CIENTIFICO = "Cayaponia tayuya",
                    IMAGEM = null),
                Planta(
                    ID = 11,
                    NOME_POPULAR = "Amapá",
                    NOME_CIENTIFICO = "Brosimum parinarioides",
                    IMAGEM = null),
                Planta(
                    ID = 12,
                    NOME_POPULAR = "Uxi-amarelo",
                    NOME_CIENTIFICO = "Endopleura uchi",
                    IMAGEM = null),
                Planta(
                    ID = 13,
                    NOME_POPULAR = "Ipê-roxo",
                    NOME_CIENTIFICO = "Handroanthus impetiginosus",
                    IMAGEM = null),
                Planta(
                    ID = 14,
                    NOME_POPULAR = "Cumaru",
                    NOME_CIENTIFICO = "Dipteryx odorata",
                    IMAGEM = null),
                Planta(
                    ID = 15,
                    NOME_POPULAR = "Pata-de-vaca",
                    NOME_CIENTIFICO = "Bauhinia forficata",
                    IMAGEM = null)
            )

            listaPlantas = dadosFakes
            plantaAdapter.atualizarLista(listaPlantas)


        listaPlantas = dadosFakes
        plantaAdapter.atualizarLista(listaPlantas)
    }


    private fun filtrarPlantas(query: String) {
        val listaFiltrada = listaPlantas.filter { planta ->
            planta.NOME_POPULAR.contains(query, ignoreCase = true) ||
                    planta.NOME_CIENTIFICO.contains(query, ignoreCase = true)
        }
        plantaAdapter.atualizarLista(listaFiltrada)
    }
}
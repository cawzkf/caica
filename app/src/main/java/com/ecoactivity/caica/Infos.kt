package com.ecoactivity.caica

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

/**
 * Activity responsável por exibir as informações detalhadas de uma planta
 * Os dados são recebidos via Intent e exibidos em campos de texto e imagem
 */
class Infos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos) // Define o layout da tela

        // Recupera o objeto Planta da Intent de forma compatível com Android 13+ e versões anteriores
        val planta: Planta? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("planta", Planta::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("planta")
        }

        // Se a planta for nula, encerra a tela
        if (planta == null) {
            finish()
            return
        }

        // Referências para os componentes visuais do layout
        val imagemPlanta = findViewById<ImageView>(R.id.imagem_planta)
        val nomePlanta = findViewById<TextView>(R.id.nome_planta)
        val nomeCientifico = findViewById<TextView>(R.id.nome_cientifico)
        val origemNome = findViewById<TextView>(R.id.origem_nome)
        val descricao = findViewById<TextView>(R.id.descricao)
        val habitat = findViewById<TextView>(R.id.habitat)
        val propriedadesMedicinais = findViewById<TextView>(R.id.propriedades_medicinais)
        val usoTradicional = findViewById<TextView>(R.id.uso_tradicional)
        val parteUtilizada = findViewById<TextView>(R.id.parte_utilizada)
        val modoPreparo = findViewById<TextView>(R.id.modo_preparo)
        val indicacoesTerapeuticas = findViewById<TextView>(R.id.indicacoes_terapeuticas)
        val contraIndicacoes = findViewById<TextView>(R.id.contra_indicacoes)
        val tags = findViewById<TextView>(R.id.tags)

        // Define o conteúdo dos campos de texto, com fallback para mensagens padrão
        nomePlanta.text = planta.NOME_POPULAR.ifEmpty { "Nome desconhecido" }
        nomeCientifico.text = planta.NOME_CIENTIFICO.ifEmpty { "Nome científico desconhecido" }
        origemNome.text = planta.ORIGEM_NOME.orEmpty().ifEmpty { "Origem não informada" }
        descricao.text = planta.DESCRICAO.orEmpty().ifEmpty { "Sem descrição disponível" }
        habitat.text = planta.HABITAT.orEmpty().ifEmpty { "Habitat não informado" }
        propriedadesMedicinais.text = planta.PROPRIEDADES_MEDICINAIS.orEmpty().ifEmpty { "Propriedades desconhecidas" }
        usoTradicional.text = planta.USOS_TRADICIONAIS.orEmpty().ifEmpty { "Uso tradicional não informado" }
        parteUtilizada.text = planta.PARTE_UTILIZADA.orEmpty().ifEmpty { "Parte utilizada não informada" }
        modoPreparo.text = planta.MODO_PREPARO.orEmpty().ifEmpty { "Modo de preparo não informado" }
        indicacoesTerapeuticas.text = planta.INDICACOES_TERAPEUTICAS.orEmpty().ifEmpty { "Sem indicações registradas" }
        contraIndicacoes.text = planta.CONTRA_INDICACOES.orEmpty().ifEmpty { "Contraindicações não informadas" }
        tags.text = planta.TAGS.orEmpty().ifEmpty { "Nenhuma tag registrada" }

        // Carrega a imagem da planta com Glide, ou exibe um placeholder se a URL estiver vazia
        val urlImagem = planta.IMAGEM_URL ?: ""
        if (urlImagem.isNotEmpty()) {
            Glide.with(this)
                .load(urlImagem)
                .placeholder(R.drawable.icon_carregamento) // Imagem temporária durante o carregamento
                .error(R.drawable.icon_image) // Imagem de erro se falhar
                .into(imagemPlanta)
        } else {
            imagemPlanta.setImageResource(R.drawable.placeholder) // Imagem padrão caso não haja URL
        }
    }
}

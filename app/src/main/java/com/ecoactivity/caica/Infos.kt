package com.ecoactivity.caica

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class Infos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos)

        val planta: Planta? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("planta", Planta::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("planta")
        }

        if (planta == null) {
            finish()
            return
        }

        // Referências da UI
        val nomePlanta = findViewById<TextView>(R.id.nome_planta)
        val nomeCientifico = findViewById<TextView>(R.id.nome_cientifico)
        val descricao = findViewById<TextView>(R.id.descricao)
        val habitat = findViewById<TextView>(R.id.habitat)
        val propriedadesMedicinais = findViewById<TextView>(R.id.propriedades_medicinais)
        val usoTradicional = findViewById<TextView>(R.id.uso_tradicional)
        val indicacoesTerapeuticas = findViewById<TextView>(R.id.indicacoes_terapeuticas)
        val imagemPlanta = findViewById<ImageView>(R.id.imagem_planta)

        // Atribui os valores aos elementos da interface
        nomePlanta.text = planta.NOME_POPULAR.ifEmpty { "Nome desconhecido" }
        nomeCientifico.text = planta.NOME_CIENTIFICO.ifEmpty { "Nome científico desconhecido" }
        descricao.text = planta.DESCRICAO_BOTANICA.orEmpty().ifEmpty { "Sem descrição disponível" }
        habitat.text = planta.HABITAT.orEmpty().ifEmpty { "Habitat não informado" }
        propriedadesMedicinais.text = planta.PROPRIEDADES_MEDICINAIS.orEmpty().ifEmpty { "Propriedades desconhecidas" }
        usoTradicional.text = planta.USO_TRADICIONAL.orEmpty().ifEmpty { "Uso tradicional não informado" }
        indicacoesTerapeuticas.text = planta.INDICACOES_TERAPEUTICAS.orEmpty().ifEmpty { "Sem indicações registradas" }

        val urlImagem = planta.IMAGEM ?: ""
        if (urlImagem.isNotEmpty()) {
            Glide.with(this)
                .load(urlImagem)
                .placeholder(R.drawable.icon_carregamento)
                .error(R.drawable.icon_image)
                .into(imagemPlanta)
        } else {
            imagemPlanta.setImageResource(R.drawable.placeholder)
        }

    }
}

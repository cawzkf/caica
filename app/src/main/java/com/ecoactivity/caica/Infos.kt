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

        // Referências dos componentes da UI
        val nomePlanta = findViewById<TextView>(R.id.nome_planta)
        val nomeCientifico = findViewById<TextView>(R.id.nome_cientifico)
        val descricao = findViewById<TextView>(R.id.descricao)
        val imagemPlanta = findViewById<ImageView>(R.id.imagem_planta)

        // Atribui os valores à UI
        nomePlanta.text = planta.NOME_POPULAR ?: "Nome desconhecido"
        nomeCientifico.text = planta.NOME_CIENTIFICO ?: "Nome científico desconhecido"
        descricao.text = planta.DESCRICAO_BOTANICA ?: "Sem descrição disponível"

        // Carrega a imagem com Glide
        Glide.with(this)
            .load(planta.IMAGEM)
            .placeholder(R.drawable.placeholder) // Imagem padrão
            .error(R.drawable.error_image) // Imagem de erro
            .into(imagemPlanta)
    }
}

package com.ecoactivity.caica

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetalhesPlantaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)/*
        setContentView(R.layout.activity_infos)

        // Recupera a planta do Intent, garantindo que não seja nulo
        val planta = intent.getParcelableExtra<Planta>("planta")

        // Referências dos componentes da UI
        val nomePlanta = findViewById<TextView>(R.id.nome_planta)
        val nomeCientifico = findViewById<TextView>(R.id.nome_cientifico)
        val descricao = findViewById<TextView>(R.id.descricao)
        val imagemPlanta = findViewById<ImageView>(R.id.imagem_planta)

        // Verifica se a planta não é nula antes de atribuir os valores
        planta?.let {
            nomePlanta.text = it.nome_popular ?: "Nome desconhecido"
            nomeCientifico.text = it.nome_cientifico ?: "Nome científico desconhecido"
            descricao.text = it.descricao_botanica ?: "Sem descrição disponível"

            // Carrega a imagem com Glide, adicionando um placeholder caso falhe
            Glide.with(this)
                .load(it.imagem)
                .placeholder(R.drawable.placeholder) // Imagem padrão caso falhe
                .error(R.drawable.error_image) // Imagem de erro se não carregar
                .into(imagemPlanta)
        } ?: run {
            // Se a planta for nula, fecha a tela para evitar exibição incorreta
            finish()
        }
    */}
}

package com.ecoactivity.caica

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Adapter personalizado para exibir plantas em uma RecyclerView
 * Cada item utiliza o layout item_planta.xml e carrega a imagem via Glide
 */
class PlantaAdapter(private var lista: List<Planta>) :
    RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

    /**
     * ViewHolder que representa cada item da lista
     */
    inner class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomePopular: TextView = itemView.findViewById(R.id.nome_planta) // Texto do nome popular
        val nomeCientifico: TextView = itemView.findViewById(R.id.nome_cientifico) // Texto do nome científico
        val imagemView: ImageView = itemView.findViewById(R.id.imagem_planta) // Imagem da planta

        /**
         * Associa os dados da planta ao layout do item
         */
        fun bind(planta: Planta) {
            nomePopular.text = planta.NOME_POPULAR
            nomeCientifico.text = planta.NOME_CIENTIFICO

            // Carrega a imagem da planta usando Glide com placeholder e fallback
            Glide.with(itemView.context)
                .load(planta.IMAGEM_URL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(imagemView)

            // Ao clicar no card, abre a tela Infos passando a planta como extra
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Infos::class.java)
                intent.putExtra("planta", planta)
                Log.d("PlantaAdapter", "Enviando para Infos: ${planta.NOME_POPULAR} - ${planta.IMAGEM_URL ?: "Sem imagem"}")
                itemView.context.startActivity(intent)
            }
        }
    }

    /**
     * Infla o layout do item e retorna o ViewHolder correspondente
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planta, parent, false)
        return PlantaViewHolder(view)
    }

    /**
     * Associa cada item da lista ao seu ViewHolder
     */
    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    /**
     * Retorna o tamanho da lista atual
     */
    override fun getItemCount(): Int = lista.size

    /**
     * Atualiza a lista de plantas exibida e força a atualização da RecyclerView
     */
    fun atualizarLista(novaLista: List<Planta>) {
        Log.d("PlantaAdapter", "Lista recebida no adapter: ${novaLista.size} plantas")
        lista = novaLista
        notifyDataSetChanged()
    }
}

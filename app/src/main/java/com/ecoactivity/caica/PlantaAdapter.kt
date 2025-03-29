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

class PlantaAdapter(private var lista: List<Planta>) :
    RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

    inner class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomePopular: TextView = itemView.findViewById(R.id.nome_planta)
        val nomeCientifico: TextView = itemView.findViewById(R.id.nome_cientifico)
        val imagemView: ImageView = itemView.findViewById(R.id.imagem_planta)

        fun bind(planta: Planta) {
            nomePopular.text = planta.NOME_POPULAR ?: "Nome desconhecido"
            nomeCientifico.text = planta.NOME_CIENTIFICO ?: "Nome científico desconhecido"

            Glide.with(itemView.context)
                .load(planta.IMAGEM)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(imagemView)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Infos::class.java)
                intent.putExtra("planta", planta)
                Log.d("PlantaAdapter", "Enviando para Infos: ${planta.NOME_POPULAR} - ${planta.IMAGEM ?: "Sem imagem"}")
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planta, parent, false)
        return PlantaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size

    fun atualizarLista(novaLista: List<Planta>) {
        lista = novaLista
        notifyDataSetChanged()
    }

}


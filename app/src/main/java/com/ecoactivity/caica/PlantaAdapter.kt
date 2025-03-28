package com.ecoactivity.caica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PlantaAdapter(private val lista: List<Planta>) :
    RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder>() {

    inner class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nome_planta)
        val nomeCientifico: TextView = itemView.findViewById(R.id.nome_cientifico)
        val imagemView: ImageView = itemView.findViewById(R.id.imagem_planta)

        fun bind(planta: Planta) {
            nome.text = planta.NOME_POPULAR ?: "Nome desconhecido"
            nomeCientifico.text = planta.NOME_CIENTIFICO ?: "Nome científico desconhecido"

            // Carrega a imagem se existir
            planta.IMAGEM?.replace("localhost", "192.168.0.100")?.let { url ->
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_image)
                    .into(imagemView)
            } ?: run {
                imagemView.setImageResource(R.drawable.error_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planta, parent, false)
        return PlantaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int = lista.size
}


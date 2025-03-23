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

    class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.nome_planta)
        val nomeCientifico: TextView = itemView.findViewById(R.id.nome_cientifico)
        val imagem: ImageView = itemView.findViewById(R.id.imagem_planta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planta, parent, false)
        return PlantaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        val planta = lista[position]
        holder.nome.text = planta.nome_popular
        holder.nomeCientifico.text = planta.nome_cientifico

        Glide.with(holder.itemView.context)
            .load(planta.imagem)
           // .placeholder(R.drawable.placeholder)
            .into(holder.imagem)
    }

    override fun getItemCount(): Int = lista.size
}

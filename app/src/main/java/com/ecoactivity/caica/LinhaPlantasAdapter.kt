package com.ecoactivity.caica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class LinhaDePlantas(val titulo: String, val listaPlantas: List<Planta>)

    class LinhaPlantasAdapter(private val linhas: List<LinhaDePlantas>) :
    RecyclerView.Adapter<LinhaPlantasAdapter.LinhaViewHolder>() {

    class LinhaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerHorizontal: RecyclerView = itemView.findViewById(R.id.recycler_horizontal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_linha, parent, false)
        return LinhaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        val linha = linhas[position]
        val titulo: TextView = holder.itemView.findViewById(R.id.titulo_linha)
        titulo.text = linha.titulo

        holder.recyclerHorizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlantaAdapter(linha.listaPlantas)
        }
    }

    override fun getItemCount(): Int = linhas.size
}

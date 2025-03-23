package com.ecoactivity.caica

import android.os.Bundle
import com.ecoactivity.caica.RetrofitClient
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LinhaPlantasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        recyclerView = findViewById(R.id.recycler_plantas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val plantasFake = listOf(
            Planta(1, "Mentha piperita", "Hortelã", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(2, "Matricaria chamomilla", "Camomila", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(3, "Rosmarinus officinalis", "Alecrim", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(4, "Ocimum basilicum", "Manjericão", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(5, "Melissa officinalis", "Erva-cidreira", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(6, "Foeniculum vulgare", "Erva-doce", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150"),
            Planta(7, "Zingiber officinale", "Gengibre", null, null, null, null, null, null, null, null, null, null, null, null, "S", "https://via.placeholder.com/150")
        )

        val linha = LinhaDePlantas("Plantas Medicinais", plantasFake)
        adapter = LinhaPlantasAdapter(listOf(linha))
        recyclerView.adapter = adapter


        /*  val service = RetrofitClient.plantaService


        service.getPlantas().enqueue(object : Callback<List<Planta>> {
            override fun onResponse(call: Call<List<Planta>>, response: Response<List<Planta>>) {
                if (response.isSuccessful && response.body() != null) {
                    val plantas = response.body()!!
                    val linha = LinhaDePlantas("Plantas Medicinais", plantas)
                    adapter = LinhaPlantasAdapter(listOf(linha))
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Planta>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }*/
    }
}

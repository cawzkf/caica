package com.ecoactivity.caica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class initActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_init)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val abrirCatalago = findViewById<Button>(R.id.botao_catalogo)

        val abrirScan = findViewById<Button>(R.id.botao_escanear)

        abrirScan.setOnClickListener {
            try {
                val intent = Intent(this, scanActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        abrirCatalago.setOnClickListener {
            val intent = Intent(this,CatalogoActivity::class.java)
            startActivity(intent)
        }
    }
}



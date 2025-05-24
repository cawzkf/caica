package com.ecoactivity.caica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Tela inicial do app
 * Exibe dois botões: um para abrir o catálogo e outro para iniciar o escaneamento
 */
class initActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita layout desenhado até as bordas (status bar, navigation bar)
        enableEdgeToEdge()

        // Define o layout da tela
        setContentView(R.layout.activity_init)

        // Aplica o preenchimento para evitar que elementos fiquem atrás das barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referência para o botão de abrir o catálogo
        val abrirCatalago = findViewById<Button>(R.id.botao_catalogo)

        // Referência para o botão de abrir a tela de escaneamento
        val abrirScan = findViewById<Button>(R.id.botao_escanear)

        // Ao clicar no botão de escanear, inicia a scanActivity
        abrirScan.setOnClickListener {
            try {
                val intent = Intent(this, scanActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Ao clicar no botão de catálogo, inicia a CatalogoActivity
        abrirCatalago.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }
    }
}

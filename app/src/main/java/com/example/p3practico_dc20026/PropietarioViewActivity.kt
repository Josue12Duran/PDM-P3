package com.example.p3practico_dc20026

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropietarioViewActivity : AppCompatActivity() {
    val urlBase = "https://apidc20026.000webhostapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_propietario_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbarViewPropietario)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Ver Propietarios"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ViewApiPropietario::class.java)
            lifecycleScope.launch {
                val response = service.getPropietario(1)

                runOnUiThread {
                    val tvholavp = findViewById<TextView>(R.id.tvholavp)
                    tvholavp.text = "${response.body()?.nombres} = ${response.body()?.apellidos}"
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_ver_propietarios -> {
                startActivity(android.content.Intent(this, PropietarioViewActivity::class.java))
                return true
            }
            R.id.action_agregar_propietarios -> {
                startActivity(android.content.Intent(this, PropietarioInsertActivity::class.java))
                return true
            }
            R.id.action_ver_inmuebles -> {
                startActivity(android.content.Intent(this, InmuebleViewActivity::class.java))
                return true
            }
            R.id.action_agregar_inmuebles -> {
                startActivity(android.content.Intent(this, InmuebleInsertActivity::class.java))
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
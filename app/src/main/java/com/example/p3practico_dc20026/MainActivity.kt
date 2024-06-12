package com.example.p3practico_dc20026

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
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

class MainActivity : AppCompatActivity() {
    val urlBase = "https://apidc20026.000webhostapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ViewApiInmueble::class.java)
            lifecycleScope.launch {
                val response = service.getInmueble()
                response.forEach {
                    println(it)
                }

                runOnUiThread {
                    val tvhola = findViewById<TextView>(R.id.tvhola)
                    tvhola.text = response.first().calle
                }
            }
        } catch (e: Exception) {
            println(e)
        }



        val toolbar : Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Casa Top"

        val btnVerPropietarios : Button = findViewById(R.id.btnVerPropietarios)
        btnVerPropietarios.setOnClickListener {
            startActivity(Intent(this, PropietarioViewActivity::class.java))
        }
        val btnAgregarPropietarios : Button = findViewById(R.id.btnAgregarPropietarios)
        btnAgregarPropietarios.setOnClickListener {
            startActivity(Intent(this, PropietarioInsertActivity::class.java))
        }
        val btnVerInmuebles : Button = findViewById(R.id.btnVerInmuebles)
        btnVerInmuebles.setOnClickListener {
            startActivity(Intent(this, InmuebleViewActivity::class.java))
        }
        val btnAgregarInmuebles : Button = findViewById(R.id.btnAgregarInmuebles)
        btnAgregarInmuebles.setOnClickListener {
            startActivity(Intent(this, InmuebleInsertActivity::class.java))
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
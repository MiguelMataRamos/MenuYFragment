package com.example.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.menu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind : ActivityMainBinding
    var nombre: String? = null
    var apellido: String? = null
    var edad: String? = null
    var posicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        var fotos = arrayOf(R.drawable.hq2, R.drawable.putin, R.drawable.torrente)

        nombre = intent.getStringExtra("nombre")
        apellido = intent.getStringExtra("apellido")
        edad = intent.getStringExtra("edad")
        posicion = intent.getIntExtra("posicion",0)

        if (nombre == null) {
            nombre = "Miguelillooo"
        }
        if (apellido == null) {
            apellido = "El Makina"
        }
        if (edad == null) {
            edad = "66"
        }

        bind.imagen.setImageResource(fotos[posicion])
        bind.textInputEditTextNombre.setText(nombre)
        bind.textInputEditTextApellido.setText(apellido)
        bind.textInputEditTextEdad.setText(edad)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action) {
            var intent = Intent(this, Editar::class.java)
            intent.putExtra("nombre",bind.textInputEditTextNombre.text.toString())
            intent.putExtra("apellido",bind.textInputEditTextApellido.text.toString())
            intent.putExtra("edad",bind.textInputEditTextEdad.text.toString())
            intent.putExtra("posicion",posicion)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
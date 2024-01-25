package com.example.menu

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.menu.databinding.ActivityEditarBinding

class Editar : AppCompatActivity() {
    private lateinit var binding: ActivityEditarBinding
    var nombre: String? = null
    var apellido: String? = null
    var edad: String? = null
    var posicion: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fotos = arrayOf(R.drawable.hq2, R.drawable.putin, R.drawable.torrente)

        nombre = intent.getStringExtra("nombre")
        apellido = intent.getStringExtra("apellido")
        edad = intent.getStringExtra("edad")
        posicion = intent.getIntExtra("posicion",0)


        binding.imagen.setImageResource(fotos[posicion])
        binding.textInputEditTextNombre.setText(nombre)
        binding.textInputEditTextApellido.setText(apellido)
        binding.textInputEditTextEdad.setText(edad)

        binding.next.setOnClickListener {
            animateImageView(binding.imagen)
            animateButton(binding.next)
            posicion++
            if (posicion >= fotos.size) {
                posicion = 0
            }
            binding.imagen.setImageResource(fotos[posicion])

        }

        binding.back.setOnClickListener {
            animateImageView(binding.imagen)
            animateButton(binding.back)
            posicion--
            if (posicion < 0) {
                posicion = fotos.size - 1 // Vuelve al final si se ha llegado al inicio
            }
            binding.imagen.setImageResource(fotos[posicion])
        }

        binding.imagen.setOnClickListener {

        }



    }

    fun animateImageView(imageView: ImageView) {
        val animator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f)
        animator.duration = 500// Duración de la animación en milisegundos
        animator.interpolator = LinearInterpolator() // Interpolador para la animación
        animator.repeatCount = ObjectAnimator.RESTART // Repetir la animación indefinidamente
        animator.start()
    }

    fun animateButton(button: ImageView) {
        // Animación para ampliar el botón en el eje X
        val animatorX = ObjectAnimator.ofFloat(button, "scaleX", 0.7f, 1f)
        animatorX.duration = 200 // Duración de la animación en milisegundos

        // Animación para ampliar el botón en el eje Y
        val animatorY = ObjectAnimator.ofFloat(button, "scaleY", 0.7f, 1f)
        animatorY.duration = 200 // Duración de la animación en milisegundos
        // Iniciar las animaciones
        animatorX.start()
        animatorY.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionEdit) {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nombre",binding.textInputEditTextNombre.text.toString())
            intent.putExtra("apellido",binding.textInputEditTextApellido.text.toString())
            intent.putExtra("edad",binding.textInputEditTextEdad.text.toString())
            intent.putExtra("posicion",posicion)

            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
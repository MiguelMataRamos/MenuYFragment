package com.example.menu

import android.animation.ObjectAnimator
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import com.example.menu.databinding.ActivityEditarBinding

class Editar : AppCompatActivity() {
    private lateinit var binding: ActivityEditarBinding
    var nombre: String? = null
    var apellido: String? = null
    var edad: String? = null
    var posicion: Int = 0
    var foto: Drawable? = null
    var uriString: String? = null


    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fotos = mutableListOf(R.drawable.hq2, R.drawable.putin, R.drawable.torrente)


        nombre = intent.getStringExtra("nombre")
        apellido = intent.getStringExtra("apellido")
        edad = intent.getStringExtra("edad")
        posicion = intent.getIntExtra("posicion", 0)
        uriString = intent.getStringExtra("foto")


        binding.imagen.setImageResource(fotos[posicion])
        binding.textInputEditTextNombre.setText(nombre)
        binding.textInputEditTextApellido.setText(apellido)
        binding.textInputEditTextEdad.setText(edad)

        if (uriString == null) {
            binding.imagen.setImageResource(fotos[posicion])
        } else {
            binding.imagen.setImageURI(Uri.parse(uriString))
        }


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

        //Crear un intent para hacer poder recoger una foto de la galería o de la cámara y ponerla en un
        //imageView para que se vea, luego guardar esa foto en drawable para que sea la foto de perfil del
        //usuario.
        binding.imagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)

        }

//        binding.titulo.setOnClickListener {
//            dispatchTakePictureIntent()
//        }


    }

//    val REQUEST_IMAGE_CAPTURE = 1
//
//    private fun dispatchTakePictureIntent() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        } catch (e: ActivityNotFoundException) {
//            // display error state to the user
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Comprobar que el código de respuesta es el que esperamos
        if (requestCode == REQUEST_CODE) {
            // Comprobar que la respuesta es correcta
            if (resultCode == RESULT_OK) {
                // Recoger el valor del extra resultante
                var uri = data?.data
                uriString = uri.toString()
                binding.imagen.setImageURI(uri)
                foto = binding.imagen.drawable
            }
        }

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            var uri = data?.data
//            uriString = uri.toString()
//            binding.imagen.setImageURI(uri)
//        }
    }

    private fun launchIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "NOOOOO!!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun animateImageView(imageView: ImageView) {
        val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
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
            intent.putExtra("nombre", binding.textInputEditTextNombre.text.toString())
            intent.putExtra("apellido", binding.textInputEditTextApellido.text.toString())
            intent.putExtra("edad", binding.textInputEditTextEdad.text.toString())
            intent.putExtra("posicion", posicion)
            intent.putExtra("foto", uriString)

            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
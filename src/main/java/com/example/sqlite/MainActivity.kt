package com.example.sqlite

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var amigosDBHelper: MiSQLHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amigosDBHelper = MiSQLHelper(this)

        binding.btGuardar.setOnClickListener {
            amigosDBHelper.anyadirDato(binding.etNombre.text.toString(), binding.etEmail.text.toString())
            cerrarTeclado()
            mostrarDatos()
        }

        binding.btConsultar.setOnClickListener {
            mostrarDatos()
        }

        binding.btEditar.setOnClickListener {
            amigosDBHelper.editarDatos(
                binding.etEditar.text.toString(),
                binding.etEmail.text.toString(),
                binding.etNombre.text.toString()
            )
            cerrarTeclado()
            mostrarDatos()
        }

        binding.btBorrar.setOnClickListener {
            amigosDBHelper.borrarDatos(binding.etBorrar.text.toString())
            cerrarTeclado()
            mostrarDatos()
        }
    }

    fun mostrarDatos() {
        binding.tvResultados.text = ""
        val cursor = amigosDBHelper.mostrarDatos()
        if (cursor!!.moveToFirst()) {
            do {
                binding.tvResultados.append(
                    cursor.getInt(0).toString() + ": ")
                binding.tvResultados.append(
                    cursor.getString(1).toString()+ ", ")
                binding.tvResultados.append(
                    cursor.getString(2).toString() + "\n")
            } while (cursor.moveToNext())
        }
    }

    fun cerrarTeclado() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.tvResultados.windowToken, 0)
    }
}
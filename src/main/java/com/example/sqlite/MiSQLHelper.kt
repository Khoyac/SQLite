package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MiSQLHelper(context: Context):SQLiteOpenHelper(
    context, "amibos.db", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE amigos " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",nombre TEXT" +
                ",email TEXT)"
        db!!.execSQL(ordenCreacion)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun anyadirDato(nombre: String, email: String) {
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email", email)
        val db = this.writableDatabase /*Abrimos la base de datos en modo escritura*/
        db.insert("amigos", null, datos)
        db.close()
    }

    fun mostrarDatos(): Cursor? {
        val db : SQLiteDatabase = this.readableDatabase /*Abrimos la base de datos en modo lectura*/
        val cursor = db.rawQuery(
            "SELECT * FROM amigos",
            null)
        return cursor
    }

    fun borrarDatos(id: String): Int {
        val db = this.writableDatabase
        val borrados = db.delete("amigos", "id = ?", arrayOf(id))
        db.close()
        return borrados
    }

    fun editarDatos(id: String, nombre: String, email: String) {
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email", email)
        val db = this.writableDatabase
        db.update("amigos", datos, "id = ?", arrayOf(id))
    }

}
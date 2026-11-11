package com.example.rolin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotasAc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val dbHelper = DatabaseHelper(this)
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        val sessionCode = intent.getStringExtra("SESSION_CODE") ?: ""
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val cursor = dbHelper.readableDatabase.query(
            UserContract.CharacterEntry.TABLE_NAME,
            arrayOf(UserContract.CharacterEntry.COLUMN_NOTAS),
            "${UserContract.CharacterEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.CharacterEntry.COLUMN_SESSION_CODE} = ?",
            arrayOf(userEmail, sessionCode), null, null, null
        )
        if (cursor.moveToFirst()) {
            etNote.setText(cursor.getString(0) ?: "")
        }
        cursor.close()

        btnSave.setOnClickListener {
            val notaTexto = etNote.text.toString()
            val values = android.content.ContentValues().apply {
                put(UserContract.CharacterEntry.COLUMN_NOTAS, notaTexto)
            }
            dbHelper.writableDatabase.update(
                UserContract.CharacterEntry.TABLE_NAME, values,
                "${UserContract.CharacterEntry.COLUMN_USER_EMAIL} = ? AND ${UserContract.CharacterEntry.COLUMN_SESSION_CODE} = ?",
                arrayOf(userEmail, sessionCode)
            )
            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
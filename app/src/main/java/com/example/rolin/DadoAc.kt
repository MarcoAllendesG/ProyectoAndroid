package com.example.rolin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.view.View
import kotlin.random.Random


class DadoAc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dado)

        val btnRoll = findViewById<Button>(R.id.btnRoll)
        val txtDiceResult = findViewById<TextView>(R.id.txtDiceResult)
        val diceBackground = findViewById<View>(R.id.diceBackground)

        btnRoll.setOnClickListener {
            val randomNumber = Random.nextInt(1, 21)

            diceBackground.animate()
                .rotationBy(360f)
                .setDuration(400)
                .withEndAction {
                    txtDiceResult.text = randomNumber.toString()

                    when (randomNumber) {
                        20 -> txtDiceResult.setTextColor(android.graphics.Color.GREEN)
                        1 -> txtDiceResult.setTextColor(android.graphics.Color.RED)
                        else -> txtDiceResult.setTextColor(android.graphics.Color.WHITE)
                    }
                }
                .start()
        }
    }
}
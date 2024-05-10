package com.example.mathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Results : AppCompatActivity() {
    private var score = 0
    private lateinit var type: String

    private lateinit var finalscoretv: TextView
    private var currentQuestionNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        score = intent.getIntExtra("Score", 0)

        type = intent.getStringExtra("type_of_scalar") ?: ""

        finalscoretv = findViewById(R.id.final_score)

        endGame()

        val exitButton : Button = findViewById(R.id.exitbutton)

        val exitClickListener = View.OnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Exit")
                .setMessage("Are you sure you want to exit the app?")

            builder.setPositiveButton("Yes") { dialog, which ->
                finishAffinity()
                onDestroy()
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        exitButton.setOnClickListener(exitClickListener)

        val gotohome : Button = findViewById<Button>(R.id.gotohome)

        gotohome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            onDestroy()
        }

        val playagain =  findViewById<Button>(R.id.play_again)

        playagain.setOnClickListener {
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", type)
            startActivity(intent)
            onDestroy()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun endGame() {

        Toast.makeText(this, "Game Over! Your score: $score", Toast.LENGTH_SHORT).show()
        finalscoretv.setText("Score $score")

        currentQuestionNumber = 1
    }
}
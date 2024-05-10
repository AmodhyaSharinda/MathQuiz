package com.example.mathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import java.util.jar.Attributes.Name

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer?.cancel()

        val addition: LinearLayout = findViewById(R.id.addition_layout)
        val substraction: LinearLayout = findViewById(R.id.subtract_button)
        val multiplication: LinearLayout = findViewById(R.id.multiply_button)
        val division: LinearLayout = findViewById(R.id.division_button)
        val random: LinearLayout = findViewById(R.id.random_button)
        val exit: LinearLayout = findViewById(R.id.exit)

        //dialog for exit
        val exitClickListener = View.OnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Exit")
                .setMessage("Are you sure you want to exit the app?")

            builder.setPositiveButton("Yes") { dialog, which ->
                finishAffinity()
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        exit.setOnClickListener(exitClickListener)

        //goes to next activity with type selected
        addition.setOnClickListener {
            val name = "addition"
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", name)
            startActivity(intent)
            finish()
            //startActivity(Intent(this, gameView::class.java))
        }

        //goes to next activity with type selected
        substraction.setOnClickListener {
            val name = "substraction"
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", name)
            startActivity(intent)
            finish()
            //startActivity(Intent(this, gameView::class.java))
        }

        //goes to next activity with type selected
        multiplication.setOnClickListener {
            val name = "multiplication"
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", name)
            startActivity(intent)
            finish()
            //startActivity(Intent(this, gameView::class.java))
        }

        //goes to next activity with type selected
        division.setOnClickListener {
            val name = "division"
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", name)
            startActivity(intent)
            finish()
            //startActivity(Intent(this, gameView::class.java))
        }

        //goes to next activity with type selected
        random.setOnClickListener {
            val name = "random"
            val intent = Intent(this, gameView::class.java)
            intent.putExtra("type_of_scalar", name)
            startActivity(intent)
            finish()
            //startActivity(Intent(this, gameView::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

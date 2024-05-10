package com.example.mathquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.random.Random

class gameView : AppCompatActivity() {
    private lateinit var Type: String
    private val random = Random

    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button
    private lateinit var questionNumberTextView: Button
    private lateinit var timerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var currentQuestionNumber: Int = 1

    private var score = 0
    private var currentQuestionIndex = 0
    private lateinit var currentQuestion: Question
    private var timer: CountDownTimer? = null

    private lateinit var scoreTextView: TextView
    private val correctColor: Int by lazy { ContextCompat.getColor(this, R.color.correctColor) }
    private val incorrectColor: Int by lazy { ContextCompat.getColor(this, R.color.incorrectColor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_view)

        Type = intent.getStringExtra("type_of_scalar") ?: ""


        questionTextView = findViewById(R.id.questionTextView)
        option1Button = findViewById(R.id.option1Button)
        option2Button = findViewById(R.id.option2Button)
        option3Button = findViewById(R.id.option3Button)
        option4Button = findViewById(R.id.option4Button)
        scoreTextView = findViewById(R.id.scoreTextView)
        questionNumberTextView = findViewById(R.id.questionNumberTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        progressBar = findViewById(R.id.progressBar)
        timerTextView = findViewById(R.id.timerTextView)


        startGame(Type)

        option1Button.setOnClickListener { onOptionSelected(it) }
        option2Button.setOnClickListener { onOptionSelected(it) }
        option3Button.setOnClickListener { onOptionSelected(it) }
        option4Button.setOnClickListener { onOptionSelected(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startGame(type: String) {

        option1Button.setBackgroundResource(R.drawable.rounded_button_background)
        option2Button.setBackgroundResource(R.drawable.rounded_button_background)
        option3Button.setBackgroundResource(R.drawable.rounded_button_background)
        option4Button.setBackgroundResource(R.drawable.rounded_button_background)

        score = 0
        currentQuestionIndex = 0
        showNextQuestion(type)
        updateScoreDisplay()
    }

    private fun showNextQuestion(type: String) {
        currentQuestion = generateRandomQuestion(type)
        updateQuestionUI()
        startTimer()
        questionNumberTextView.text = "    Question $currentQuestionNumber/50    "
        currentQuestionNumber++
    }

    private fun startTimer() {

        timer?.cancel()
        var timeLeft = 10000L
        progressBar.progress = 100
        val duration = 10000L // Total duration of the timer in milliseconds
        val interval = 50L // Update interval in milliseconds

        timer = object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((duration - millisUntilFinished) * 100 / duration).toInt()
                progressBar.progress = progress
                timeLeft = millisUntilFinished
                timerTextView.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                progressBar.progress = 100
                timerTextView.text = "Time's up!"
                //endGame()
                val intent = Intent(this@gameView, Results::class.java)
                intent.putExtra("Score", score)
                intent.putExtra("Type", Type)
                startActivity(intent)
            }
        }.start()



    }

    private fun generateRandomQuestion(type: String): Question {
        val num1 = random.nextInt(100)
        val num2 = //random.nextInt(100)
            when (type) {
                "addition", "substraction", "multiplication" -> random.nextInt(100) + 1 // Adjust the range as needed
                "division" -> generateRandomDivisor(num1)
        else -> random.nextInt(100) + 1 // Adjust the range as needed for other cases
    }

        val question: String
        val correctAnswer: Int

        when (type) {
            "addition" -> {
                question = "$num1 + $num2?"
                correctAnswer = num1 + num2
            }
            "substraction" -> {
                question = "$num1 - $num2?"
                correctAnswer = num1 - num2
            }
            "multiplication" -> {
                question = "$num1 * $num2?"
                correctAnswer = num1 * num2
            }
            "division" -> {
                question = "$num1 / $num2?"
                correctAnswer = num1 / num2
            }
            "random" ->{
                val randomOperation = random.nextInt(4)
                val opperation:String

                if(randomOperation == 0){
                     opperation = "addition"
                }else if (randomOperation == 1){
                     opperation = "substraction"
                }else if (randomOperation == 2){
                     opperation = "multiplication"
                }else{
                     opperation = "division"
                }
                return generateRandomQuestion(opperation)
            }

            else -> {
                question = "Default question"
                correctAnswer = 0
            }
        }

        val options = generateOptions(correctAnswer)

        return Question(question, options, options.indexOf(correctAnswer.toString()))
    }

    private fun generateRandomDivisor(dividend: Int): Int {
        // Generate a random divisor that evenly divides the dividend
        val possibleDivisors = (1..dividend).filter { dividend % it == 0 }
        return possibleDivisors.random()
    }


    private fun generateOptions(correctAnswer: Int): List<String> {
        val options = mutableListOf<String>()
        options.add(correctAnswer.toString())

        while (options.size < 4) {
            val wrongAnswer = correctAnswer + random.nextInt(20) - 10
            if (wrongAnswer != correctAnswer && !options.contains(wrongAnswer.toString())) {
                options.add(wrongAnswer.toString())
            }
        }

        return options.shuffled()
    }



    private fun updateQuestionUI() {
        questionTextView.text = currentQuestion.question
        val options = currentQuestion.options
        option1Button.text = options[0]
        option2Button.text = options[1]
        option3Button.text = options[2]
        option4Button.text = options[3]
    }

    fun onOptionSelected(view: View) {
        val selectedOption = when (view.id) {
            R.id.option1Button -> 0
            R.id.option2Button -> 1
            R.id.option3Button -> 2
            R.id.option4Button -> 3
            else -> -1
        }

        if (selectedOption == currentQuestion.correctOption) {
            score += 10  // Increase the score by 10
            updateScoreDisplay()
            view.setBackgroundColor(correctColor)
        } else {
            view.setBackgroundColor(incorrectColor)
            Toast.makeText(this, "Wrong! Game Over.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Results::class.java)
            intent.putExtra("Score", score)
            intent.putExtra("Type", Type)
            startActivity(intent)
            finish()
        }

        
        Handler(Looper.getMainLooper()).postDelayed({
            view.setBackgroundResource(R.drawable.rounded_button_background)
            currentQuestionIndex++
            showNextQuestion(Type)  // <-- Pass selectedOperation here
        }, 1000)

    }

    private fun updateScoreDisplay() {
        scoreTextView.text = "Score: $score"
    }
}

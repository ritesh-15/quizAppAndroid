package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userNameView:TextView = findViewById(R.id.userName)

        userNameView.text = intent.getStringExtra(Constants.USER_NAME)

        val scoreView:TextView = findViewById(R.id.scoreView)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        val totalCorrectQuestion = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)

        scoreView.text = "You score $totalCorrectQuestion out of $totalQuestions"

        val btnFinish:Button = findViewById(R.id.btnFinish)

        btnFinish.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}
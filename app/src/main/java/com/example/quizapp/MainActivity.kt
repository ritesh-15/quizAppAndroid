package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart:Button = findViewById(R.id.btnStart)

        val name:EditText = findViewById(R.id.etName)

        btnStart.setOnClickListener {

            if(name.text.isEmpty()){
                Toast.makeText(this,"Please fill the name!",Toast.LENGTH_LONG)
                    .show()
            }else{

                // create an intent
                    // move from this activity to quiz questions activity
                val intent = Intent(this,QuizQuestionsActivity::class.java)

                // pass the user name in the next activity
                intent.putExtra(Constants.USER_NAME, name.text.toString())

                startActivity(intent)
                finish() // to close this activity completly
            }
        }

    }
}
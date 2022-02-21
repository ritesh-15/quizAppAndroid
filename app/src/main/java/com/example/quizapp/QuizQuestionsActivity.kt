package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var questionsList:ArrayList<Question>? = null

    private var selectedOptionPosition:Int = 0

    private var progressBar:ProgressBar? = null
    private var progressText:TextView? = null
    private var questionText:TextView? = null
    private  var ivImage:ImageView? = null

    private var optionOne:TextView? = null
    private var optionTwo:TextView? = null
    private var optionThree:TextView? = null
    private var optionFour:TextView? = null

    // user name
    private  var userName:String? = null

    // submit button
    private var btnSubmit:Button? = null

    private var currentPosition:Int = 1

    // correct answers
    private var correctAnswers:Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        progressBar = findViewById(R.id.progressBar)
        progressText = findViewById(R.id.progressText)
        questionText = findViewById(R.id.question)
        ivImage = findViewById(R.id.ivImage)

        optionOne = findViewById(R.id.optionOne)
        optionTwo = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)

        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit?.setOnClickListener(this)

        questionsList = Constants.getQuestions()

        setQuestion()


        // retirive the user name in the intent
        userName = intent.getStringExtra(Constants.USER_NAME)

    }

    // set the question
    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = questionsList!![currentPosition - 1]

        // setting the progress bar and text
        progressBar?.progress = currentPosition
        progressText?.text = "${currentPosition}/${progressBar?.max}"

        // question
        questionText?.text = question.question

        // image
        ivImage?.setImageResource(question.image)

        // options
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        if(currentPosition == questionsList!!.size){
            btnSubmit?.text = getString(R.string.btn_finish_text)
        }else{
            btnSubmit?.text = getString(R.string.btn_submit_text)
        }
    }

    // set the default options styles
    private fun defaultOptionsView(){

         val options = ArrayList<TextView>()

        optionOne?.let {
            options.add(0,it)
        }

        optionTwo?.let {
            options.add(1,it)
        }

        optionThree?.let {
            options.add(2,it)
        }

        optionFour?.let {
            options.add(3,it)
        }

        for(option in options){
            // setting default text color
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT

            // setting up default border
            option.background = ContextCompat.getDrawable(
                this,R.drawable.default_options_border_bg
            )

        }

    }

    // on selecting single option
    private fun selectedOptionView(view:TextView,selectedOptionNumber:Int){
        defaultOptionsView()

        selectedOptionPosition = selectedOptionNumber

        view.setTextColor(Color.parseColor("#363a43"))

        // setting the font bold
        view.setTypeface(view.typeface,Typeface.BOLD)

        view.background = ContextCompat.getDrawable(
            this,R.drawable.selected_option_border_bg
        )
    }

    // listen all clicks in the view
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> {
                optionOne?.let{
                    selectedOptionView(it,1)
                }
            }
            R.id.optionTwo -> {
                optionTwo?.let{
                    selectedOptionView(it,2)
                }
            }
            R.id.optionThree -> {
                optionThree?.let{
                    selectedOptionView(it,3)
                }
            }
            R.id.optionFour -> {
                optionFour?.let{
                    selectedOptionView(it,4)
                }
            }
            R.id.btnSubmit -> {

                if(selectedOptionPosition == 0)
                {
                    currentPosition++

                    when{
                        currentPosition <= questionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,userName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,questionsList?.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS,correctAnswers)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = questionsList?.get(currentPosition - 1)

                    if(question!!.correctAnswer != selectedOptionPosition){
                        answerView(selectedOptionPosition,R.drawable.wrong_option_bg)
                    }else{
                        correctAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correect_option_bg)


                    if(currentPosition == questionsList!!.size){
                        btnSubmit?.text = getString(R.string.btn_finish_text)
                    }else{
                        btnSubmit?.text = getString(R.string.go_to_next_question)
                    }


                    selectedOptionPosition = 0

                }

            }
        }
    }

    // set the answer color either right or wrong
    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1 -> {
                optionOne?.background = ContextCompat.getDrawable(this,
                    drawableView
                    )
            }
            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(this,
                    drawableView
                    )
            }
            3 -> {
                optionThree?.background = ContextCompat.getDrawable(this,
                    drawableView
                    )
            }
            4 -> {
                optionFour?.background = ContextCompat.getDrawable(this,
                    drawableView
                    )
            }
        }
    }

}
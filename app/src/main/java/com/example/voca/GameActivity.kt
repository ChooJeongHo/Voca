package com.example.voca

import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class GameActivity : AppCompatActivity(), View.OnClickListener {
    var idx: Int = 0
    var score: Int = 0
    var isEngMode: Boolean = true
    var question: String = ""
    var answer: String = ""
    lateinit var scoreTv: TextView
    lateinit var ansEt: EditText
    lateinit var queTv: TextView
    lateinit var btn: Button
    lateinit var swapBtn: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        queTv = findViewById(R.id.queTv)
        btn = findViewById(R.id.btn)
        scoreTv = findViewById(R.id.scoreTv)
        ansEt = findViewById(R.id.ansEt)
        swapBtn = findViewById(R.id.swapBtn)
        progressBar = findViewById(R.id.progressBar)

        isEngMode = loadPref()
        progressBar.max = MainActivity.oriVocaArr.size

        setQuestion()

        btn.setOnClickListener {
            var myAns: String = ansEt.text.toString()
            ansEt.setText("")
            if (myAns == answer) {
                score += 8000
            }
            scoreTv.text = score.toString() + "점"
            idx++
            setQuestion()
        }
        swapBtn.setOnClickListener {
            var ab: AlertDialog.Builder = AlertDialog.Builder(this)
            ab.setIcon(R.mipmap.ic_launcher)
            ab.setTitle("제목이 들어갑니다")
            ab.setMessage("내용이 들어갑니다")
            ab.setNegativeButton("영어") { dialog, which ->
                isEngMode = true
                idx = 0
                score = 0
                scoreTv.text = score.toString() + "점"
                savePref()
                setQuestion()
            }
            ab.setPositiveButton("한글", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    isEngMode = false
                    idx = 0
                    score = 0
                    scoreTv.text = score.toString() + "점"
                    savePref()
                    setQuestion()
                }
            })
            ab.setCancelable(false)
            ab.show()
        }
    }

    fun savePref(){
        var pref : SharedPreferences = getSharedPreferences("wordPref", MODE_PRIVATE)
        var edit : SharedPreferences.Editor = pref.edit()
        edit.putBoolean("isEngMode",isEngMode)
        edit.commit()
    }

    fun loadPref() : Boolean{
        var pref : SharedPreferences = getSharedPreferences("wordPref", MODE_PRIVATE)
        return pref.getBoolean("isEngMode",true)!!
    }

    fun setQuestion() {
        if (MainActivity.oriVocaArr.size == 0) {
            queTv.text = "단어 없다"
        } else if (MainActivity.oriVocaArr.size <= idx) {
            queTv.text = "다 풀었다"
        } else if (MainActivity.oriVocaArr.size > 0) {
            if (isEngMode) {
                question = MainActivity.oriVocaArr.get(idx).eng
                answer = MainActivity.oriVocaArr.get(idx).kor
            } else {
                question = MainActivity.oriVocaArr.get(idx).kor
                answer = MainActivity.oriVocaArr.get(idx).eng
            }
            progressBar.progress = idx+1
            queTv.text = question
        }
    }

    override fun onClick(p0: View?) {
        idx++
        setQuestion()
    }
}
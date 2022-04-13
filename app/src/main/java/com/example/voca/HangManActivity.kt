package com.example.voca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class HangManActivity : AppCompatActivity() {
    lateinit var queTv: TextView
    lateinit var ansEt: EditText
    lateinit var submitBt: Button
    var answer: String = "apple"
    var oriArr: ArrayList<Boolean> = arrayListOf()

    var idx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hang_man)
        queTv = findViewById(R.id.queTv)
        ansEt = findViewById(R.id.ansEt)
        submitBt = findViewById(R.id.submitBt)

        oriArr.clear()
        for (i in 0 until answer.length step 1) {
            oriArr.add(false)
        }

        showQuestion()
        submitBt.setOnClickListener {
            var myAnswer = ansEt.text.toString()
            ansEt.setText("")
            if (myAnswer.trim().length == 1) {
                var isFind = false
                for (i in 0 until oriArr.size step 1){
                    if(myAnswer == answer.substring(i,i+1)){
                        oriArr.set(i, true)
                        isFind = true
                    }
                }
                if(!isFind){
                    //못 찾았다
                    Log.d("aabb","철자 틀림")
                }
            } else if (myAnswer.trim().length == 0) {
                Toast.makeText(this,"입력을 하고 눌러라 좀!!", Toast.LENGTH_SHORT).show()
            } else {
                if(myAnswer.trim() == answer){
                    //정답 성공!
                    Log.d("aabb","정답")
                }else{
                    //정답 시도 실패!
                    Log.d("aabb","정답 시도 실패")
                }
            }
            showQuestion()
        }
    }

    fun showQuestion() {
        var str: String = ""
        for (i in 0 until oriArr.size step 1) {
            if (oriArr.get(i)) {
                str = str + answer.substring(i, i + 1) + " "
            } else {
                str = str + "* "
            }
        }
        queTv.setText(str)
    }
}
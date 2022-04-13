package com.example.voca

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*


class MenuActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var manageBt: Button
    lateinit var gameBt: Button
    lateinit var quitBt: Button
    lateinit var startForResult: ActivityResultLauncher<Intent>

    fun loadDb(){
        var db: SQLiteDatabase = openOrCreateDatabase("sql_test.db", Context.MODE_PRIVATE, null)
        var sql =
            "CREATE TABLE IF NOT EXISTS voca(" +
                    "idx INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "eng TEXT, kor TEXT)"
        db.execSQL(sql)

        val c: Cursor = db.rawQuery("SELECT * FROM voca", null)

        c.moveToFirst()
        MainActivity.oriVocaArr.clear()
        while (c.isAfterLast() === false) {
            MainActivity.oriVocaArr.add(Voca(c.getString(1), c.getString(2)))
            c.moveToNext()
        }
        c.close()

        db.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        manageBt = findViewById(R.id.manageBt)
        gameBt = findViewById(R.id.gameBt)
        quitBt = findViewById(R.id.quitBt)

        loadDb()

        manageBt.setOnClickListener {
            var intent: Intent = Intent(this, com.example.voca.MainActivity::class.java)
            startActivity(intent)

        }

        gameBt.setOnClickListener(this)

        quitBt.setOnClickListener {
//            finish()
//            var arr   = arrayOf("류재민","추정호","박정재","최용호","권승찬","김승재","이도훈","김건호","조성철","유재형")
//            Log.d("aabb","size: "+arr.size)
//            manageBt.setText(arr.get(Random().nextInt(arr.size)))
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.gameBt -> {
                var intent = Intent(this, com.example.voca.GameActivity::class.java)
                startActivity(intent)
            }
        }


    }
}
package com.example.voca

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() , View.OnClickListener {
    companion object{
        var oriVocaArr : ArrayList<Voca> = arrayListOf()
    }

    var editIdx : Int = -1

    //    var vocaArr : ArrayList<String> = arrayListOf()
//    var engArr : ArrayList<String> = arrayListOf()
//    var korArr : ArrayList<String> = arrayListOf()
    lateinit var btn : Button
    lateinit var engEt : EditText
    lateinit var korEt : EditText
    lateinit var rv : RecyclerView
    lateinit var adapter : MainRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)
        engEt = findViewById(R.id.engEt)
        korEt = findViewById(R.id.korEt)
        rv = findViewById(R.id.listV)
        adapter = MainRvAdapter(this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btn.setOnClickListener(this)

    }


    override fun onClick(p0: View?) {
        var eng: String = engEt.text.toString()
        var kor: String = korEt.text.toString()
        engEt.setText("")
        korEt.setText("")
        if(editIdx != -1){
            var voca :Voca = oriVocaArr.get(editIdx);
            voca.eng = eng
            voca.kor =kor
            editIdx = -1;
            btn.setText("클릭")
        } else {
            oriVocaArr.add(Voca(eng,kor))
        }

        adapter.notifyDataSetChanged()
        saveDb()
    }

    fun saveDb(){
        var db: SQLiteDatabase = openOrCreateDatabase("sql_test.db", Context.MODE_PRIVATE, null)
        db.execSQL("DELETE FROM voca")
        for (i in 0 until oriVocaArr.size step 1) {
            var str = "INSERT INTO voca (eng,kor) VALUES ('" + oriVocaArr.get(i).eng + "','" + oriVocaArr.get(i).kor + "')"
            db.execSQL(str)
        }
        db.close()
    }

    inner class MainRvAdapter(val context: Context) :
        RecyclerView.Adapter<MainRvAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
            return Holder(view)
        }

        override fun getItemCount(): Int {
            return oriVocaArr.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.tv1.setText(oriVocaArr.get(position).eng)
            holder.tv2.setText(oriVocaArr.get(position).kor)

            holder.itemView.setOnClickListener {
                editIdx = position
                engEt.setText(oriVocaArr.get(position).eng)
                korEt.setText(oriVocaArr.get(position).kor)
                btn.setText("수정")
            }

            val pos = position
            holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(p0: View?): Boolean {
                    oriVocaArr.removeAt(pos)
                    saveDb()
                    notifyDataSetChanged()
                    return false
                }
            })

        }

        inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            var tv1: TextView = itemView!!.findViewById(R.id.engTv)
            val tv2: TextView = itemView!!.findViewById(R.id.korTv)
        }
    }


}
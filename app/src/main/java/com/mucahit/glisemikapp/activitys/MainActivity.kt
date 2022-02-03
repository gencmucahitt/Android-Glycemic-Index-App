package com.mucahit.glisemikapp.activitys

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mucahit.glisemikapp.databinding.ActivityMainBinding
import com.mucahit.glisemikapp.models.DB

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding
    lateinit var db: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.hide()
        db = DB(this)
        db.allUrun()
        object : CountDownTimer(3000, 3000) {
            override fun onFinish() {
                val intent=Intent(this@MainActivity, CategoryList::class.java)
                startActivity(intent)
                finish()
            }
            override fun onTick(p0: Long) {
                Log.d("SplashActivity", p0.toString())
            }
        }.start()
    }

}
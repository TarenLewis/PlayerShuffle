package com.example.playershuffle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun showTestActivity(view: View){

        val testIntent = Intent(this, TestActivity::class.java)

        startActivity(testIntent)

    }

}

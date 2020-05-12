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

    fun showPlayGameActivity(view: View){
        val playGameIntent = Intent(this, PlayGameActivity::class.java)
        startActivity(playGameIntent);
    }

    fun showEditTeamActivity(view: View){
        val editTeamIntent = Intent(this, EditTeamActivity::class.java)
        startActivity(editTeamIntent);
    }


}

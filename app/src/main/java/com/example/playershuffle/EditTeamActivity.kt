package com.example.playershuffle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit_team.*

class EditTeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_team)

        // Receive selected team
        val selectedTeam = intent.getIntExtra("selectedTeam", -1)
        // if it's -1, there was an error. Handle it

        teamEditorText.text = "$selectedTeam"

    }
}

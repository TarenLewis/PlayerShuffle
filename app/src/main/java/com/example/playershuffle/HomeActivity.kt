package com.example.playershuffle

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class HomeActivity : AppCompatActivity() {

    // just for global declaration, I think
    var teamList : ArrayList<Team> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //https://developer.android.com/training/data-storage

        // Teams = Arraylist of type Team
        // Access Teams list from data
        // Default selection as item 0

        // check for existence of teams list
        // if it exists, load it
        if (false){
            // reassign teamList
        }

        // populate the team_list with the arraylist

    }

    fun showPlayGameActivity(view: View){
        val playGameIntent = Intent(this, PlayGameActivity::class.java)

        // Pass selected team as value
        playGameIntent.putExtra("selectedTeam", 0)

        startActivity(playGameIntent);
    }

    fun showEditTeamActivity(view: View){
        val editTeamIntent = Intent(this, EditTeamActivity::class.java)

        // Pass selected team as value
        editTeamIntent.putExtra("selectedTeam", 0)

        startActivity(editTeamIntent);
    }

    fun addTeamPrompt(view : View){

        val li = LayoutInflater.from(this)
        val promptsView = li.inflate(R.layout.add_team_prompt, null)

        val userInput = promptsView.findViewById<EditText>(R.id.editTextDialogUserInput)

        val builder = AlertDialog.Builder(this)
            builder.setView(promptsView)
            builder.setTitle("Add New Team")

            builder.setPositiveButton("Add Team", fun (dialog: DialogInterface, id : Int) {
                // get user input, send to create new team
                // need to validate input

                // Create new team with provided name, add to list
                teamList.add(Team(userInput.text.toString()))

                // send to new activity
                val editTeamIntent = Intent(this, EditTeamActivity::class.java)

                // Pass newest team # as arg
                editTeamIntent.putExtra("selectedTeam", teamList.size-1)

                startActivity(editTeamIntent);

            })

            builder.setNegativeButton("Cancel", fun (dialog: DialogInterface, id : Int) {
                dialog.cancel()
            })

        val alertDialog = builder.create()

        alertDialog.show()

    }


}

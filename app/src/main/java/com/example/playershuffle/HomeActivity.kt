package com.example.playershuffle

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_team_prompt.view.*
import java.io.File

class HomeActivity : AppCompatActivity() {

    // Declaration
    private lateinit var teamList : ArrayList<Team>

    // These two lines need to be in all activities which access the shared prefs
    private lateinit var prefs: SharedPreferences // initialize only in "onCreate" below
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        // Check for a built team array and load it (or create new arraylist)
        loadArray()

        // populate the team_list with the arraylist

        // Default selection as item 0

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

        // Create prompt itself (maybe?)
        val li = LayoutInflater.from(this)
        val promptsView = li.inflate(R.layout.add_team_prompt, null)

        // Access the user input on the prompt to grab text later
        val userInput = promptsView.editTextDialogUserInput
        //val userInput = promptsView.findViewById<EditText>(R.id.editTextDialogUserInput)

        // Build the alert
        val builder = AlertDialog.Builder(this)
            builder.setView(promptsView)
            builder.setTitle("Add New Team")

            // Submit button
            builder.setPositiveButton("Add Team", fun (dialog: DialogInterface, id : Int) {
                // get user input, send to create new team
                // need to validate input

                // Create new team with provided name, add to list
                teamList.add(Team(userInput.text.toString()))

                // Save to shared pref
                saveArray()

                // send to new activity
                val editTeamIntent = Intent(this, EditTeamActivity::class.java)

                // Pass newest team # as arg
                editTeamIntent.putExtra("selectedTeam", teamList.size-1)

                // Start team edit activity
                startActivity(editTeamIntent);

            })

            // Cancel button
            builder.setNegativeButton("Cancel", fun (dialog: DialogInterface, id : Int) {
                dialog.cancel()
            })

        // Create and show alert
        val alertDialog = builder.create()
        alertDialog.show()

    }

    fun saveArray(){

        // Open the prefs editor
        val prefsEditor = prefs.edit()
        // Convert arraylist to JSON for storage
        val json = gson.toJson(teamList)
        // Store our JSON to our key
        prefsEditor.putString("teams", json)
        // Keeping commit here since we will load it next activity
        prefsEditor.commit()

    }

    fun loadArray() {

        // grab the JSON that is stored as our key
        val json = prefs.getString("teams", "")

        // if that key is empty, return empty array. Else load and assign our arraylist
        teamList = when {
            json.isNullOrEmpty() -> ArrayList()
            else -> gson.fromJson(json, object : TypeToken<List<Team>>() {}.type)
        }

    }

    fun spTest(){

        /* A test to clear the list,
            Add a few teams
            Save the teamlist to sharedPrefs
            Clear our program's list again
            Load the saved list from sharedPrefs
            Display the last team added somewhere on the page
         */

        teamList.clear()

        teamList.add(Team("doggos"))
        teamList.add(Team("kittycats"))
        teamList.add(Team("littleponies"))

        saveArray()

        teamList.clear()

        loadArray()

        button3.text = teamList[teamList.size - 1].tName

    }

}

/*  References

        // Shared Pref
        https://www.journaldev.com/234/android-sharedpreferences-using-kotlin

        // lateinit
        https://stackoverflow.com/questions/33849811/property-must-be-initialized-or-be-abstract

        // Unused for now but may come up later:
        https://developer.android.com/training/data-storage



 */

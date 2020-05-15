package com.example.playershuffle

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_edit_team.*

class EditTeamActivity : AppCompatActivity() {

    // Declaration
    private var teamList : ArrayList<Team> = arrayListOf()
    private lateinit var team : Team

    // These two lines need to be in all activities which access the shared prefs
    private lateinit var prefs: SharedPreferences // initialize only in "onCreate" below
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_team)

        prefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        loadTeamArray()

        // Receive selected team
        val selectedTeam = intent.getIntExtra("selectedTeam", -1)

        // If it reverts to default of -1, there was an error
        // Or if we are trying to select an index outside of the array
        if (selectedTeam == -1 || teamList.size < selectedTeam){

            // error, abort abort abort

        } else {

            // Set our team to show
            team = teamList[selectedTeam]

            // Just a test which displays team name
            teamName.text = team.tName

        }

    }

    fun toggleActiveClick(view : View){

        // toggle highlighted user as active / not active

    }

    fun addPlayerClick(view : View){

        // launch new player activity
        // may need forResult here, not sure yet. Will test later

        // For testing for now, just add a prompt to add a name (like new team prompt)

    }

    fun deletePlayerClick(view : View){

        // prompt user to see if they want to delete highlighted player

        // if user confirmed
            // delete player

        // if user declined
            //do nothing


    }


    fun deleteTeamClick(view : View){

        // prompt user to see if they really want to delete the team

        // if user confirmed
            // delete team from arraylist
            // save team array
            // return user to home activity
                setResult(RESULT_OK, null)
                finish()

        // else user declined
            // do nothing

    }

    private fun saveTeamArray(){

        // Open the prefs editor
        val prefsEditor = prefs.edit()
        // Convert arraylist to JSON for storage
        val json = gson.toJson(teamList)
        // Store our JSON to our key
        prefsEditor.putString("teams", json)
        // Keeping commit here since we will load it next activity
        prefsEditor.commit()

    }

    private fun loadTeamArray() {

        // grab the JSON that is stored as our key
        val json = prefs.getString("teams", "")

        // if that key is empty, return empty array. Else load and assign our arraylist
        teamList = when {
            json.isNullOrEmpty() -> ArrayList()
            else -> gson.fromJson(json, object : TypeToken<List<Team>>() {}.type)
        }

    }

}

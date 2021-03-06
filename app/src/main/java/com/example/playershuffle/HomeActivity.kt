package com.example.playershuffle

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_team_prompt.view.*


// Gson usage (disregard serializable in question)
// https://stackoverflow.com/questions/14981233/android-arraylist-of-custom-objects-save-to-sharedpreferences-serializable

// Shared Pref
// https://www.journaldev.com/234/android-sharedpreferences-using-kotlin

// lateinit
// https://stackoverflow.com/questions/33849811/property-must-be-initialized-or-be-abstract

// Unused for now but may come up later (like when loading pictures from storage)
// https://developer.android.com/training/data-storage

// RecyclcerView
// https://guides.codepath.com/android/using-the-recyclerview#binding-the-adapter-to-the-recyclerview

// startActivityForResult (going to edit Team and back to Home)
// https://stackoverflow.com/questions/8240934/android-refresh-activity-when-returns-to-it


open class HomeActivity : AppCompatActivity() {

    // Declaration
    private lateinit var teamList : ArrayList<Team>

    lateinit var tList: ArrayList<Team>

    // These two lines need to be in all activities which access the shared prefs
    private lateinit var prefs: SharedPreferences // initialize only in "onCreate" below
    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        prefs = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        // Check for a built team array and load it (or create new arraylist)
        loadTeamArray()

        // ...
        // Lookup the recyclerview in activity layout
        val rvTeams = findViewById<View>(R.id.rvTeams) as RecyclerView





        // Create adapter passing in the sample user data
        val adapter = TeamAdapter(teamList)
        // Attach the adapter to the recyclerview to populate items
        rvTeams.adapter = adapter
        // Set layout manager to position the items
        rvTeams.layoutManager = LinearLayoutManager(this)

        // populate the team_list with the arraylist

        // Default selection as item 0

    }

    class TeamAdapter (private val listTeams: ArrayList<Team>) : RecyclerView.Adapter<TeamAdapter.ViewHolder>()
    {
        // This variable keeps track of the previous position of the team selected by the user.
        // It is used to store last position selected, to set the color white again when the user
        //selects a different team in the list. This lets the user know which team is currently selected.
        companion object {
            var last_position = 0
        }

        //https://guides.codepath.com/android/using-the-recyclerview#binding-the-adapter-to-the-recyclerview

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
            // Your holder should contain and initialize a member variable
            // for any view that will be set as you render a row
            val nameTextView = itemView.findViewById<TextView>(R.id.team_name)
        }

        // ... constructor and member variables
        // Usually involves inflating a layout from XML and returning the holder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.ViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            // Inflate the custom layout
            val teamView = inflater.inflate(R.layout.team_layout_home, parent, false)
            // Return a new holder instance
            return ViewHolder(teamView)
        }

        // Involves populating data into the item through holder
        override fun onBindViewHolder(viewHolder: TeamAdapter.ViewHolder, position: Int) {
            // Get the data model based on position
            val team: Team = listTeams[position]
            // Set item views based on your views and data model
            val textView = viewHolder.nameTextView
            textView.text = team.tName

            val textViewContext = textView.context


            //This listens for user click on View, changes to color gray when selected
            // But it needs to be fixed because it only works for one selection, then stays gray after that
            //Should be moved outside onBindViewHolder at some point
            textView.setOnClickListener{
                //https://stackoverflow.com/questions/40692214/changing-background-color-of-selected-item-in-recyclerview
                textView.setBackgroundColor(Color.parseColor("#bababa"))
                notifyItemChanged(last_position)
                last_position = position

                //trying to test the position but this breaks it, dont uncomment this its just here for testing
                //Toast.makeText(textViewContext, position,Toast.LENGTH_SHORT).show()
            }

            //resets previous click to default white
            textView.setBackgroundColor(Color.parseColor("#fafafa"))

        }

        // Returns the total count of items in the list
        override fun getItemCount(): Int {
            return listTeams.size
        }
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

        // "forResult" here triggers this activity to expect a response from new activity
        startActivityForResult(editTeamIntent, 1);
    }

    fun addTeamPrompt(view : View){

        // https://mkyong.com/android/android-prompt-user-input-dialog-example/

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
                saveTeamArray()

                // send to new activity
                val editTeamIntent = Intent(this, EditTeamActivity::class.java)

                // Pass newest team # as arg
                editTeamIntent.putExtra("selectedTeam", teamList.size-1)

                // Start team edit activity
                // "forResult" here triggers this activity to expect a response from new activity
                startActivityForResult(editTeamIntent, 1);

            })

            // Cancel button
            builder.setNegativeButton("Cancel", fun (dialog: DialogInterface, id : Int) {
                dialog.cancel()
            })

        // Create and show alert
        val alertDialog = builder.create()
        alertDialog.show()

    }

    // interpret response from calling activity
    // Simply refreshes the current activity (which should refresh the team list)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            val refresh = Intent(this, HomeActivity::class.java)
            startActivity(refresh)
            this.finish()
        }
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

        // reading from GSON
        // https://stackoverflow.com/questions/22984696/storing-array-list-object-in-sharedpreferences

    }

    private fun spTest(){

        /* A test to clear the list,
            Add a few teams
            Save the teamlist to sharedPrefs
            Clear our program's list again
            Load the saved list from sharedPrefs
            Display the last team added somewhere on the page
         */

        teamList = ArrayList()

        teamList.clear()

        teamList.add(Team("doggos"))
        teamList.add(Team("kittycats"))
        teamList.add(Team("littleponies"))

        saveTeamArray()

        teamList.clear()

        loadTeamArray()

        button3.text = teamList[teamList.size - 1].tName

    }

}

/*  References









 */

package com.example.playershuffle

class Team constructor(teamName : String) {

    val teamMembers = arrayListOf<Player>()
    var tName : String = ""

    init {
        tName = teamName
    }

    fun addPlayer(newPlayer : Player){

        teamMembers.add(newPlayer)

    }

//This function / companion object is unnecessary, it was being used for testing purposes.
    //https://guides.codepath.com/android/using-the-recyclerview#binding-the-adapter-to-the-recyclerview
    companion object {
        private var lastContactId = 0
        fun createTeamsList(numContacts: Int): ArrayList<Team> {
            val team = ArrayList<Team>()
            for (i in 1..numContacts) {
                team.add(Team("Person " + ++lastContactId))
            }
            return team
        }
    }
}
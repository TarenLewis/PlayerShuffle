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

}
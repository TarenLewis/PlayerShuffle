package com.example.playershuffle

class Team constructor(teamName : String) {

    val teamMembers = arrayListOf<Player>()

    fun addPlayer(newPlayer : Player){

        teamMembers.add(newPlayer)

    }

}
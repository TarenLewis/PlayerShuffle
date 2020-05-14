package com.example.playershuffle

class Player constructor(playerName : String) {

    var active : Boolean = true;

    fun inactivate(){ active = false; }

    fun activate() { active = true; }

}
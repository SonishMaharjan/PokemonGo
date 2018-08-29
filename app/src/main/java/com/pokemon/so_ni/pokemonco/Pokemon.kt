package com.pokemon.so_ni.pokemonco

import android.content.Context
import android.location.Location
import android.widget.Toast

/**
 * Created by so_ni on 8/27/2018.
 */
class Pokemon {

    var name:String? = null
    var des:String? = null
    var image:Int? = null
    var power:Int? = null
   // var lat:Double? = null
   // var log:Double? = null
    var location: Location? = null
    var isCatch:Boolean? = false

    constructor(name:String,image:Int,power:Int,des:String,lat:Double,log:Double)
    {
        this.name = name
        this.image = image
        this.des= des
        this.power= power
        this.location = Location(name)
        this.location!!.latitude = lat
        this.location!!.longitude = log


    }

    fun catchPokemon(applicationContext: Context,playerPower:Int)
    {
        this.isCatch = true
        Toast.makeText(applicationContext,"you catch the "+ this.name+ "Your new power is"+ playerPower,Toast.LENGTH_LONG).show()
    }


}
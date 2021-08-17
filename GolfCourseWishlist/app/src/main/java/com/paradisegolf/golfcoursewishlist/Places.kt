package com.paradisegolf.golfcoursewishlist

import java.util.*
import kotlin.collections.ArrayList

class Places {
    // like "static" in other OOP languages
    companion object {
        // hard code a few places
        var placeNameArray = arrayOf(
            "Black Mountain",
            "Chambers Bay",
            "Clear Water",
            "Harbour Town",
            "Muirfield",
            "Old Course",
            "Pebble Beach",
            "Spy Class"
        )

        // return places
        fun placeList(): ArrayList<Place> {
            val list = ArrayList<Place>()
            for (i in placeNameArray.indices) {
                val place = Place()
                place.name = placeNameArray[i]
                // image name is same as place name without space's
                place.image =
                    placeNameArray[i].replace("\\s+".toRegex(), "").lowercase(Locale.getDefault())
                list.add(place)
            }
            return list
        }
    }
}
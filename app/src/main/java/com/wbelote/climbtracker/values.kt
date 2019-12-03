package com.wbelote.climbtracker


val areaIndex = arrayListOf<Area>()

data class Gym(
    val id: Int,
    val areaNames: ArrayList<String>
) {
    val areas = arrayListOf<Area>()
    val name = NAMES[id]
    init {
        var a: Area
        for (i in areaNames.indices) {
            a = Area(areaNames[i], areaIndex.size)
            areaIndex.add(a)
            this.areas.add(a)
        }
    }

    companion object {
        const val TRC_MORRISVILLE = 0
        const val TRC_DURHAM = 1
        val NAMES = arrayOf("TRC Morrisville", "TRC Durham")
    }
}

data class Area(
    val name: String,
    val gymID: Int
)

val gyms = arrayListOf(
    Gym(
        Gym.TRC_MORRISVILLE,
        arrayListOf(
            "The Donut",
            "Desk Overhang",
            "Summit Overhang",
            "The Vert",
            "The Cave",
            "Expansion"
        )
    ),
    Gym(
        Gym.TRC_DURHAM,
        arrayListOf(
            "The Bull",
            "Left Horn",
            "Right Horn",
            "The Kraken",
            "The Bow",
            "Starboard Wave",
            "The Stern",
            "Port Side",
            "The Chalice",
            "Aisle Four"
        )
    )
)

// TODO: add classes for grades, colors?
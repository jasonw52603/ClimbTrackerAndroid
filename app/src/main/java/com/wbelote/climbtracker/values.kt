package com.wbelote.climbtracker


object GymInfo {

    init {
        Gym.new(
            "TRC Morrisville", listOf(
                "The Donut"
                , "Desk Overhang"
                , "Summit Overhang"
                , "The Vert"
                , "The Cave"
                , "Expansion"
            )
        )
        Gym.new(
            "TRC Durham", listOf(
                "The Bull"
                , "Left Horn"
                , "Right Horn"
                , "The Kraken"
                , "The Bow"
                , "Starboard Wave"
                , "The Stern"
                , "Port Side"
                , "The Chalice"
                , "Aisle Four"
            )
        )
    }

    val gymCount = Gym.all.size
    val gymNames = Gym.all.map { it.name }

    private var currentGymIndex = 0
    var currentGym = Gym.all[0]
        get() = Gym.all[currentGymIndex]
        private set

    fun chooseGym(pos: Int) {
        currentGymIndex = pos
    }


    val areaCount
        get() = currentGym.areas.size
    val areaNames
        get() = currentGym.areas.map { Area.all[it].name }

    private var currentAreaIndex = 0
    var currentArea = Area.all[0]
        get() = Area.all[currentAreaIndex]
        private set

    fun chooseArea(pos: Int) {
        currentAreaIndex = currentGym.areas[pos]
    }

    class Gym private constructor(val id: Int, val name: String, val areas: List<Int>) {

        companion object {

            private var nextID = 0
            val all = arrayListOf<Gym>()

            fun new(name: String, areaNames: List<String>) {
                all.add(Gym(nextID, name, Area.add(areaNames, nextID++)))
            }

        }

    }

    class Area private constructor(val id: Int, val name: String, val gym: Int) {

        companion object {

            private var nextID = 0
            val all = arrayListOf<Area>()

            fun add(names: List<String>, gym: Int): List<Int> {
                val new = names.map { Area(nextID++, it, gym) }
                all.addAll(new)
                return new.map { it.id }
            }

        }
    }


// TODO: add classes for grades, colors?
}
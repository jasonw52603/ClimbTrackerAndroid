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

    private var currentIndex = 0

    var currentGym = Gym.all[0]
        get() = Gym.all[currentIndex]
        private set

    val gymCount = Gym.all.size

    val gymNames = Gym.all.map { it.name }

    fun chooseGym(pos: Int) {
        currentIndex = pos
    }

    class Gym private constructor(val id: Int, val name: String, val areas: List<Int>) {

        companion object {

            private var nextID = 0
            val all = arrayListOf<Gym>()

            fun new(name: String, areaNames: List<String>) {
                all.add(Gym(nextID, name, Area.add(areaNames, nextID++)))
            }

            fun getFor(g: Gym): Gym? = all.filter { g.id == it.id }.getOrNull(0)

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
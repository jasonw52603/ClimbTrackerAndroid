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


    class Color private constructor(val id: Int, val name: String, val hex: Int) {

        val r = hex / (256*256)
        val g = (hex / 256) % 256
        val b = hex % 256

        companion object {
            val all = arrayListOf(
                Color(0, "Red", 0xE53636)
                , Color(1, "Orange", 0xFB8C00)
                , Color(2, "Yellow", 0xFDD835)
                , Color(3, "Green", 0x7CB342)
                , Color(4, "Blue", 0x1E88E5)
                , Color(5, "Purple", 0x784CDB)
                , Color(6, "Pink", 0xF053BE)
                , Color(7, "White", 0xA0A0A0)
            )
        }
    }

    fun colorID(name: String) = Color.all.firstOrNull { it.name == name }?.id ?: 7

    fun color(id: Int) = Color.all[id]
}
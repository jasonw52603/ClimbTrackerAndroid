package com.wbelote.climbtracker


data class Gym(
    val name: String,
    val areas: ArrayList<Area> = arrayListOf()
)

data class Area(
    val name: String
)

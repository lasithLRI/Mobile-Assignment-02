package com.example.cw_02_v2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Film(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    val title: String?,
    val year: String?,
    val rate:String?,
    val released:String?,
    val runtime:String?,
    val genre:String?,
    val director:String?,
    val writer:String?,
    val actors:String?,
    val plot:String?,
)

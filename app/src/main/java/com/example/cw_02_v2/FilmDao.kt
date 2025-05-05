package com.example.cw_02_v2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {
    @Query("select * from film")
    suspend fun getAll(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg film: Film)

    @Insert
    suspend fun insertFilm(film: Film)

    @Delete
    suspend fun deleteFilm(film: Film)

    @Query("SELECT * FROM film WHERE title LIKE :title")
    suspend fun findByTitle(title:String):Film

    @Query("SELECT * FROM film WHERE actors LIKE :actors")
    suspend fun findByActor(actors:String):Film

    @Query("DELETE from film")
    suspend fun deleteAll()
}
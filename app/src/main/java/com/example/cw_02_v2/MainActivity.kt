package com.example.cw_02_v2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.cw_02_v2.ui.theme.CW_02_V2Theme
import kotlinx.coroutines.launch


lateinit var db: AppDatabase
lateinit var filmDao: FilmDao

class MainActivity : ComponentActivity() {

    private var title by mutableStateOf("")
    private var year by mutableStateOf("1800")
    private var rate by mutableStateOf("0")
    private var released by mutableIntStateOf(0)
    private var runtime by mutableStateOf("0")
    private var genre by mutableStateOf("")
    private var director by mutableStateOf("")
    private var writer by mutableStateOf("")
    private var actor by mutableStateOf("")
    private var plot by mutableStateOf("")


    override fun onCreate(savedInstanceState: Bundle?) {

        db = Room.databaseBuilder(this,AppDatabase::class.java, "filmdatabase").build()
        filmDao = db.filmDao()

        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title","")
            year = savedInstanceState.getString("year","1800")
            rate = savedInstanceState.getString("rate","0")
            released = savedInstanceState.getInt("release",1800-12-31)
            runtime = savedInstanceState.getString("runtimes","0")
            genre = savedInstanceState.getString("genre","")
            director = savedInstanceState.getString("director","")
            writer = savedInstanceState.getString("writer","")
            actor = savedInstanceState.getString("actor","")
            plot = savedInstanceState.getString("plot","")
        }

        setContent {
            GUI()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("title", title)
        outState.putString("year", year)
        outState.putString("rate", rate)
        outState.putInt("release", released)
        outState.putString("runtime", runtime)
        outState.putString("genre", genre)
        outState.putString("director", director)
        outState.putString("writer", writer)
        outState.putString("actor", actor)
        outState.putString("plot", plot)
    }


    @Composable
    fun GUI(){

        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = {
                          scope.launch {

                              filmDao.insertFilm(
                                  Film(
                                      title = title,
                                      year = year,
                                      rate = rate,
                                      released = released.toString(),
                                      runtime = runtime,
                                      genre = genre,
                                      director = director,
                                      writer = writer,
                                      actors = actor,
                                      plot = plot
                                  )
                              )
                          }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RectangleShape,

                    ) {
                    Text("Add movies to DB", textAlign = TextAlign.Center)
                }

                Button(
                    onClick = {
                        val i = Intent(context,SearchMovies::class.java)
                        context.startActivity(i)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RectangleShape
                ) {

                    Text("Add movies to DB", textAlign = TextAlign.Center)

                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RectangleShape
                ) {

                    Text("Add movies to DB", textAlign = TextAlign.Center)
                }
            }

            FilmInformation()

        }
    }


    @Composable
    fun FilmInformation(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(value = title, onValueChange ={
                title =it
            }, label = { Text("Title")} )
            TextField(value = year, onValueChange ={

                year = it
            } )
            TextField(value = rate, onValueChange ={

                rate = it
            },)
            TextField(value = released.toString(), onValueChange ={
                val releaseText = it
                released = releaseText.toIntOrNull()?:0
            } )
            TextField(value = runtime, onValueChange ={
                runtime = it
            } )
            TextField(value = genre, onValueChange ={
                genre = it
            } )
            TextField(value = director, onValueChange ={
                director = it
            } )
            TextField(value = writer, onValueChange ={
                writer = it
            } )
            TextField(value = actor, onValueChange ={
                actor = it
            } )
            TextField(value = plot, onValueChange ={
                plot = it
            } )
        }
    }


}




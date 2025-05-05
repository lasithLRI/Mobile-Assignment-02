package com.example.cw_02_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cw_02_v2.ui.theme.CW_02_V2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovies : ComponentActivity() {

    private var search by mutableStateOf("")
    private var movie by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            search = savedInstanceState.getString("search","")
            movie = savedInstanceState.getString("movie","")
        }

        setContent {
            SearchUI()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("search", search)
        outState.putString("movie", movie)
    }

    @Composable
    fun SearchUI(){

        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            TextField(value = search, onValueChange ={
                search = it
            }, label = { Text("Search Movie")} )

            Spacer(modifier = Modifier.height(4.dp))
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
                            movie = findMovie(search)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RectangleShape
                ) {

                    Text("Retrieve Movie", textAlign = TextAlign.Center)
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    shape = RectangleShape
                ) {

                    Text("Save movie to Database", textAlign = TextAlign.Center)
                }
            }

            Text(movie)
        }
    }
}

suspend fun findMovie(searchAText:String):String{
    val url_string = "https://www.omdbapi.com/?t=$searchAText&apikey=ef494778"

    val url = URL(url_string)

    val con: HttpURLConnection = url.openConnection() as HttpURLConnection

    var stb = StringBuilder()

    withContext(Dispatchers.IO){
        var bf = BufferedReader(InputStreamReader(con.inputStream))
        var line: String? = bf.readLine()

        while (line != null){
            stb.append(line + "\n")
            line = bf.readLine()
        }

    }

    val movie = parseJSON(stb)

    return movie
}

fun parseJSON(stb:StringBuilder):String{

    val json = JSONObject(stb.toString())

    val resultMovie = StringBuilder()

    val title = json.optString("Title", "N/A")
    val year = json.optString("Year", "N/A")
    val released = json.optString("Released", "N/A")
    val runtime = json.optString("Runtime", "N/A")
    val genre = json.optString("Genre", "N/A")
    val director = json.optString("Director", "N/A")
    val writer = json.optString("Writer", "N/A")
    val actors = json.optString("Actors", "N/A")
    val plot = json.optString("Plot", "N/A")

    resultMovie.append("Title: $title\n")
    resultMovie.append("Year: $year\n")
    resultMovie.append("Released: $released\n")
    resultMovie.append("Runtime: $runtime\n")
    resultMovie.append("Genre: $genre\n")
    resultMovie.append("Director: $director\n")
    resultMovie.append("Writer: $writer\n")
    resultMovie.append("Actors: $actors\n")
    resultMovie.append("Plot: $plot\n")

    return resultMovie.toString()


}


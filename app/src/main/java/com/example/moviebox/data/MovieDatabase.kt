package com.example.moviebox.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getIntOrNull
import com.example.moviebox.R

object MovieDatabase {
    lateinit var db: SQLiteDatabase
    private val movies = HashMap<String, Movie>()

    private fun execQuery(sql: String): ArrayList<Movie> {
        val movieList = ArrayList<Movie>()

        val cursor = db.rawQuery(sql, null)

        val idIdx = cursor.getColumnIndex("id")
        val nameIdx = cursor.getColumnIndex("name")
        val descriptionIdx = cursor.getColumnIndex("description")
        val imageUrlIdx = cursor.getColumnIndex("imageUrl")
        val directorIdx = cursor.getColumnIndex("director")
        val yearIdx = cursor.getColumnIndex("year")
        val ratingIdx = cursor.getColumnIndex("rating")
        val inWatchListIdx = cursor.getColumnIndex("inWatchList")

        while(cursor.moveToNext()) {
            val id = cursor.getString(idIdx)
            val name = cursor.getString(nameIdx)
            val description = cursor.getString(descriptionIdx)
            val imageUrl = cursor.getInt(imageUrlIdx)
            val director = cursor.getString(directorIdx)
            val year = cursor.getInt(yearIdx)
            val rating = cursor.getIntOrNull(ratingIdx)
            val inWatchList = cursor.getInt(inWatchListIdx) == 1

            val movie = Movie(
                id,
                name,
                description,
                imageUrl,
                director,
                year,
                rating,
                inWatchList
            )

            movieList.add(movie)
        }

        cursor.close()
        return movieList
    }

    private fun getMoviesFromIMDB(): ArrayList<Movie> {
        val movieList = ArrayList<Movie>();

        movieList.add(Movie(
            "1",
            "The Shawshank Redemption",
            "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
            R.drawable.shawshank_redemption,
            "Frank Darabont",
            1994,
            null,
            false
        ))

        movieList.add(Movie(
            "2",
            "The Godfather",
            "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.",
            R.drawable.godfather,
            "Francis Ford Coppola",
            1972,
            null,
            false
        ))

        movieList.add(Movie(
            "3",
            "The Dark Knight",
            "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            R.drawable.dark_knight,
            "Christopher Nolan",
            2008,
            null,
            false
        ))

        movieList.add(Movie(
            "4",
            "12 Angry Men",
            "The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.",
            R.drawable.twelve_angry_men,
            "Sidney Lumet",
            1957,
            null,
            false
        ))

        movieList.add(Movie(
            "5",
            "Schindlers List",
            "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
            R.drawable.schindlers_list,
            "Steven Spielberg",
            1993,
            null,
            false
        ))

        movieList.add(Movie(
            "6",
            "Pulp Fiction",
            "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
            R.drawable.pulp_fiction,
            "Quentin Tarantino",
            1994,
            null,
            false
        ))

        movieList.add(Movie(
            "7",
            "Forrest Gump",
            "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.",
            R.drawable.forrest_gump,
            "Robert Zemeckis",
            1994,
            null,
            false
        ))

        movieList.add(Movie(
            "8",
            "Fight Club",
            "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
            R.drawable.fight_club,
            "David Fincher",
            1999,
            null,
            false
        ))

        movieList.add(Movie(
            "9",
            "Inception",
            "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            R.drawable.inception,
            "Christopher Nolan",
            2010,
            null,
            false
        ))

        movieList.add(Movie(
            "10",
            "The Matrix",
            "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.",
            R.drawable.matrix,
            "Lana Wachowski & Lilly Wachowski",
            1999,
            null,
            false
        ))

        movieList.add(Movie(
            "11",
            "Se7en",
            "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.",
            R.drawable.se7en,
            "David Fincher",
            1995,
            null,
            false
        ))

        movieList.add(Movie(
            "12",
            "City of God",
            "In the slums of Rio, two kids paths diverge as one struggles to become a photographer and the other a kingpin.",
            R.drawable.city_of_god,
            "Fernando Meirelles",
            2002,
            null,
            false
        ))

        movieList.add(Movie(
            "13",
            "Interstellar",
            "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.",
            R.drawable.interstellar,
            "Christopher Nolan",
            2014,
            null,
            false
        ))

        movieList.add(Movie(
            "14",
            "The Green Mile",
            "A tale set on death row in a Southern jail, where gentle giant John possesses the mysterious power to heal peoples ailments",
            R.drawable.green_mile,
            "Frank Darabont",
            1999,
            null,
            false
        ))

        movieList.add(Movie(
            "15",
            "Parasite",
            "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
            R.drawable.parasite,
            "Bong Joon Ho",
            2019,
            null,
            false
        ))

        return movieList
    }

    fun initDB(mainActivity: AppCompatActivity, moviesAddedBefore: Boolean) {
        try {
            // Create DB
            db = mainActivity.openOrCreateDatabase("Movies", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS movies (id INTEGER PRIMARY KEY, name VARCHAR, description VARCHAR, imageUrl INTEGER, director VARCHAR, year INTEGER, rating INTEGER, inWatchList INTEGER)")

            val moviesFromImdb = getMoviesFromIMDB()
            moviesFromImdb.forEach {
                println("Movie:" + it.name)
            }

            if (!moviesAddedBefore) {
                // Add movies
                moviesFromImdb.forEach {
                    println("Insert Movie:" + it.name)
                    db.execSQL("INSERT INTO movies (name, description, imageUrl, director, year, rating, inWatchList) VALUES (" +
                            "'${it.name}'," +
                            "'${it.description}'," +
                            it.imageUrl+ "," +
                            "'${it.director}'," +
                            "${it.year}," +
                            "null," +
                            "0)")
                }
            }

            val movieList = execQuery("SELECT * FROM movies")
            movieList.forEach {
                println("Inserted movie" + it.id + " Name:" + it.name)
                movies[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getMovie(movieId: String): Movie {
        val movieList = execQuery("SELECT * FROM movies WHERE id = $movieId")
        return movieList[0]
    }

    fun getMovieList(): ArrayList<Movie> {
        return execQuery("SELECT * FROM movies")
    }

    fun updateMovieRating(movie: Movie, rating: Int?) {
        movie.rating = rating
        db.execSQL("UPDATE movies SET rating = $rating WHERE id = ${movie.id}")
    }

    fun getMoviesWithRating(): ArrayList<Movie> {
        return execQuery("SELECT * FROM movies WHERE rating IS NOT NULL")
    }

    fun addToWatchList(movie: Movie) {
        movie.inWatchList = true
        db.execSQL("UPDATE movies SET inWatchList = 1 WHERE id = ${movie.id}")
    }

    fun removeFromWatchList(movie: Movie) {
        movie.inWatchList = true
        db.execSQL("UPDATE movies SET inWatchList = 0 WHERE id = ${movie.id}")
    }

    fun isInWatchList(movieId: String): Boolean {
        val movie = getMovie(movieId)
        return movie.inWatchList
    }

    fun getWatchList(): ArrayList<Movie> {
        return execQuery("SELECT * FROM movies WHERE inWatchList <> 0")
    }
}

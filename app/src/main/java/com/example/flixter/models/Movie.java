package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

// represents a movie retrieved from endpoint
@Parcel
public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;

    // empty constructor needed by the Parceler library
    public Movie() {}

    // constructor
    // note: this may throw an exception, JSONException
    // then, if any of getString() fail, whoever calls the constructor is responsible for handling
    //      the exception
    public Movie(JSONObject jsonObject) throws JSONException {
        // get the value at key (name:) in the JSON object passed to us
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
    }

    // iterates through JSON array and constructs a Movie for each element of the array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            // create a new Movie for the item at this position in the JSON array and add it to our
            // List of Movies
            // notice: we use the Movie constructor, passing in a JSON Object which we get by
            //      calling JSONArray.getJSONObject() on an element in the JSON array
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    /* getters */

    // since posterPath from endpoint is only a relative URL, we need to append it to a base URL
    // (TODO: hardcode base URL for now, but find a way to fetch all the available sizes w[number],
    //      append to base URL that we get back, then append THAT to the relative path)
    public String getPosterPath() {
        // in String.format, %s = the place where you want to insert the 2nd argument formatted as
        // String
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }
}

package com.example.flixter.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

// base RecyclerView.Adapter is an abstract class -- we need to write the body of some of its
//      methods ourselves
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    /* member variables */

    // where the adapter is constructed from
    Context context;
    // data to put in adapter
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    // cheaper than onCreateViewHolder, since onCreateViewHolder inflates layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH-- populate elements of view holder with data from movie
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // ViewHolder represents a row in the RecyclerView
    // Holds references to each element in one row of RecyclerView: image, title, overview
    public class ViewHolder extends RecyclerView.ViewHolder {

        // member variables
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        // constructor
        // View itemView = one row
        public ViewHolder(@NonNull View itemView) {
            // invoke parent class constructor, RecyclerView.ViewHolder(itemView)
            super(itemView);

            // connect member variables to the elements in our XML layout
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        // populate each view with data from a particular movie
        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // url for poster or backdrop, depending on screen orientation
            String imageUrl;

            // if phone is in landscape, then imageUrl = backdrop image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackdropPath();
            } else {
                // else, imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            // use Glide to import movie poster image and load it into view
            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.film_reel)
                    .error(R.drawable.film_reel))
                    .into(ivPoster);

            // 1. Register click listener on the whole row
            // add an onclick listener to the row in RecyclerView so we can do something when a
            //      movie row is clicked
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    // "You can think of [intents] as the messengers that request an action from
                    //      other components"-- binds components to each other at runtime
                    Intent i = new Intent(context, DetailActivity.class);
                    // pass data in key=val pairs to the new activity
                    i.putExtra("movie", Parcels.wrap(movie));

                    // shared element transition
                    Pair<View, String> p1 = Pair.create((View)tvTitle, "title");
                    Pair<View, String> p2 = Pair.create((View)tvOverview, "overview");
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity)context, p1, p2);
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}

package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    // get references to elements in activity_detail.xml
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // define member variables, setting them equal to XML elements
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);

        // receive the key=value pair data from MovieAdapter.java
        // (which, BTW, occurs when a movie row is clicked)
        // then use that to set title text on screen to what we received
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
    }
}

package com.example.moviecatalogue.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.MovieModel;

public class DetailActivity extends AppCompatActivity {
    private TextView mTitle;
    private TextView mDate;
    private TextView mRate;
    private TextView mOverview;
    private ImageView mPoster;
    private ImageView mBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);

        MovieModel movieModel = getIntent().getParcelableExtra("movieData");

        mTitle = findViewById(R.id.detail_title);
        mDate = findViewById(R.id.detail_release_date);
        mRate = findViewById(R.id.detail_rate);
        mOverview = findViewById(R.id.detail_overview);
        mPoster = findViewById(R.id.detail_poster);
        mBackdrop = findViewById(R.id.detail_backdrop);

        mTitle.setText(movieModel.getTitle());
        mDate.setText(movieModel.getRealeseDate());
        mRate.setText(Double.toString(movieModel.getRate()));
        mOverview.setText(movieModel.getOverview());

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(movieModel.getPoster())
                .into(mPoster);

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(movieModel.getBackdrop())
                .into(mBackdrop);
    }
}

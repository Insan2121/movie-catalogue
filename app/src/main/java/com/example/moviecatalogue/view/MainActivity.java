package com.example.moviecatalogue.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.MovieAdapter;
import com.example.moviecatalogue.model.MovieModel;
import com.example.moviecatalogue.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import io.isfaaghyth.rak.Rak;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    ListView listView;

    private MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());
        Rak.initialize(getApplicationContext());

        listView = findViewById(R.id.listView);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();

        mainActivityViewModel.getMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                movieAdapter.notifyDataSetChanged();
            }
        });

        prepare();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = mainActivityViewModel.getMovie().getValue().get(position).getTitle();
                String date = mainActivityViewModel.getMovie().getValue().get(position).getRealeseDate();
                double rate = mainActivityViewModel.getMovie().getValue().get(position).getRate();
                String overview = mainActivityViewModel.getMovie().getValue().get(position).getOverview();
                String poster = mainActivityViewModel.getMovie().getValue().get(position).getPoster();
                String backdrop =  mainActivityViewModel.getMovie().getValue().get(position).getBackdrop();

                MovieModel movieModel = new MovieModel(title, date, rate, overview,poster, backdrop);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("movieData", movieModel);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(view.findViewById(R.id.poster), "poster");
                pairs[1] = new Pair<View, String>(view.findViewById(R.id.title), "title");
                pairs[2] = new Pair<View, String>(view.findViewById(R.id.release_date), "date");
                pairs[3] = new Pair<View, String>(view.findViewById(R.id.rate), "rate");
                pairs[4] = new Pair<View, String>(view.findViewById(R.id.overview), "overview");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());
            }
        });
    }

    void prepare(){
        movieAdapter = new MovieAdapter(this, (ArrayList<MovieModel>) mainActivityViewModel.getMovie().getValue());
        listView.setAdapter(movieAdapter);
    }

}

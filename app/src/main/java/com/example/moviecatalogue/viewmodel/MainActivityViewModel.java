package com.example.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue.model.MovieModel;
import com.example.moviecatalogue.repositories.MovieRepository;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<MovieModel>> mMovie;
    private MovieRepository movieRepository;

    public void init(){
        if (mMovie != null){
            return;
        }
        movieRepository = MovieRepository.getInstance();
        mMovie = movieRepository.getMovie();
    }

    public LiveData<List<MovieModel>> getMovie(){
        return mMovie;
    }

}

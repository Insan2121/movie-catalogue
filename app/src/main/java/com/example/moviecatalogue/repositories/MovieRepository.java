package com.example.moviecatalogue.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviecatalogue.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.isfaaghyth.rak.Rak;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MovieRepository {
    public static MovieRepository instance;
    public static ArrayList<MovieModel> dataSet = new ArrayList<>();

    private String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=1b5fe03cf255f2175c9c6b28cd9d4209";
    private String baseImage = "https://image.tmdb.org/t/p/w500/";

    public static synchronized MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<MovieModel>> getMovie(){
        setMovie();
        MutableLiveData<List<MovieModel>> data =  new MutableLiveData<>();
        data.setValue(getMovieData());
        return data;
    }

    private void setMovie(){
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: "+response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String date = jsonObject.getString("release_date");
                                double rating = jsonObject.getDouble("vote_average");
                                String overview = jsonObject.getString("overview");
                                String poster = baseImage + jsonObject.getString("poster_path");
                                String backdrop = baseImage + jsonObject.getString("backdrop_path");

                                dataSet.add(new MovieModel(title, date, rating, overview, poster, backdrop));
                                saveDataTemp(dataSet);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("errorerror", "onError: "+anError.getErrorBody());
                    }
                });

    }

    public void saveDataTemp(ArrayList<MovieModel> dataSet){
        if (Rak.grab("data") != null){
            Rak.remove("data");
            Rak.entry("data", dataSet);
        }else {
            Rak.entry("data", dataSet);
        }
    }

    public ArrayList<MovieModel> getMovieData(){
        ArrayList<MovieModel> dataModel  = new ArrayList<>();;
        if (Rak.grab("data") != null){
            dataModel = Rak.grab("data");
            Log.i(TAG, "getMovieData: "+dataModel.size());
            return dataModel;
        }
        return null;
    }


}

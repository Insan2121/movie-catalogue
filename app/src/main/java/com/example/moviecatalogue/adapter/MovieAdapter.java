package com.example.moviecatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.MovieModel;
import com.example.moviecatalogue.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private List<MovieModel> movieModels ;

    public MovieAdapter(Context context, ArrayList<MovieModel> movieModels) {
        this.context = context;
        this.movieModels = movieModels;
    }

    @Override
    public int getCount() {
        return movieModels.size();
    }

    @Override
    public Object getItem(int position) {
        return movieModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(itemView);

        MovieModel movie = (MovieModel) getItem(position);
        viewHolder.bind(movie);
        return itemView;
    }

    private class ViewHolder{
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvRating;
        private TextView tvOverview;
        private ImageView ivPoster;

        ViewHolder(View view) {
            tvTitle = view.findViewById(R.id.title);
            tvDate = view.findViewById(R.id.release_date);
            tvRating = view.findViewById(R.id.rate);
            tvOverview = view.findViewById(R.id.overview);
            ivPoster = view.findViewById(R.id.poster);
        }

        void bind (MovieModel movieModel){
            tvTitle.setText(movieModel.getTitle());
            tvDate.setText(movieModel.getRealeseDate());
            tvRating.setText(Double.toString(movieModel.getRate()));
            tvOverview.setText(movieModel.getOverview());

            Glide.with(context)
                    .asBitmap()
                    .load(movieModel.getPoster())
                    .into(ivPoster);

        }

    }
}

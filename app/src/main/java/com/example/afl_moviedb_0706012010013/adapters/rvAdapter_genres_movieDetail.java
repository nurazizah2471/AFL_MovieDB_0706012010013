package com.example.afl_moviedb_0706012010013.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.models.Movies;

import java.util.List;

public class rvAdapter_genres_movieDetail extends RecyclerView.Adapter<rvAdapter_genres_movieDetail
        .rvAdapter_genresFragment_movieDetailHolder> {

    private List<Movies.Genres> listGenres;
    private Context context;

    private List<Movies.Genres> getListGenres(){
        return listGenres;
    }

    public void setListGenresAdapter(List<Movies.Genres> listGenres){
        this.listGenres=listGenres;
    }

    public rvAdapter_genres_movieDetail(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public rvAdapter_genresFragment_movieDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_genres_movie_detail, parent, false);
        return new rvAdapter_genresFragment_movieDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter_genres_movieDetail.rvAdapter_genresFragment_movieDetailHolder holder, int position) {
        final Movies.Genres resultsGenre = getListGenres().get(position);
        holder.genre_fragment_movie_detail.setText(resultsGenre.getName());
    }

    @Override
    public int getItemCount() {
        return getListGenres().size();
    }

    public class rvAdapter_genresFragment_movieDetailHolder extends RecyclerView.ViewHolder {
        TextView genre_fragment_movie_detail;
        public rvAdapter_genresFragment_movieDetailHolder(@NonNull View itemView) {
            super(itemView);
            genre_fragment_movie_detail=itemView.findViewById(R.id.genre_fragment_movie_detail);
        }
    }
}


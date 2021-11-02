package com.example.afl_moviedb_0706012010013.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.models.TrendingMovies;

import java.util.List;

public class rvAdapter_nowPlaying_TrendingWeek extends RecyclerView.Adapter<rvAdapter_nowPlaying_TrendingWeek
        .rvAdapter_nowPlaying_TrendingWeekHolder> {

    private List<TrendingMovies.Results> listTrendingWeekMovies;
    private Context context;

    public List<TrendingMovies.Results> getListTrendingWeekMovies() {
        return listTrendingWeekMovies;
    }

    public void setListTrendingWeekMoviesAdapter(List<TrendingMovies.Results> listTrendingWeekMovies) {
        this.listTrendingWeekMovies = listTrendingWeekMovies;
    }

    public rvAdapter_nowPlaying_TrendingWeek(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public rvAdapter_nowPlaying_TrendingWeekHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trending, parent, false);
        return new rvAdapter_nowPlaying_TrendingWeekHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter_nowPlaying_TrendingWeekHolder holder, int position) {
        final TrendingMovies.Results resultsTrendingWeekMovies = getListTrendingWeekMovies().get(position);

        if(resultsTrendingWeekMovies.getPoster_path()!=null) {
            Glide.with(context)
                    .load(Const.IMAGE_PATH + resultsTrendingWeekMovies.getPoster_path())
                    .into(holder.card_poster_trending);
        }else{
            holder.card_poster_trending.setImageResource(R.drawable.photo);
        }

        if(resultsTrendingWeekMovies.getTitle().length()>10) {
            holder.card_title_trending.setText(resultsTrendingWeekMovies.getTitle().subSequence(0, 10)+"...");
        }else {
            holder.card_title_trending.setText(resultsTrendingWeekMovies.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return getListTrendingWeekMovies().size();
    }

    public class rvAdapter_nowPlaying_TrendingWeekHolder extends RecyclerView.ViewHolder {
        ImageView card_poster_trending;
        TextView card_title_trending;

        public rvAdapter_nowPlaying_TrendingWeekHolder(@NonNull View itemView) {
            super(itemView);

            card_poster_trending = itemView.findViewById(R.id.card_poster_trending);
            card_title_trending = itemView.findViewById(R.id.card_title_trending);
        }
    }
}
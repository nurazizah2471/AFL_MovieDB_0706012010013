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

public class rvAdapter_nowPlaying_TrendingDay extends RecyclerView.Adapter<rvAdapter_nowPlaying_TrendingDay.rvAdapter_nowPlaying_TrendingDayHolder> {

    private List<TrendingMovies.Results> listTrendingDayMovies;
    private Context context;

    public List<TrendingMovies.Results> getListTrendingDayMovies() {
        return listTrendingDayMovies;
    }

    public void setListTrendingDayMoviesAdapter(List<TrendingMovies.Results> listTrendingDayMovies) {
        this.listTrendingDayMovies = listTrendingDayMovies;
    }

    public rvAdapter_nowPlaying_TrendingDay(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public rvAdapter_nowPlaying_TrendingDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_trending, parent, false);
        return new rvAdapter_nowPlaying_TrendingDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter_nowPlaying_TrendingDayHolder holder, int position) {
        final TrendingMovies.Results resultsTrendingDayMovies = getListTrendingDayMovies().get(position);

        if(resultsTrendingDayMovies.getPoster_path()!=null) {
            Glide.with(context)
                    .load(Const.IMAGE_PATH + resultsTrendingDayMovies.getPoster_path())
                    .into(holder.card_poster_trending);
        }else{
            holder.card_poster_trending.setImageResource(R.drawable.photo);
        }

        if(resultsTrendingDayMovies.getTitle().length()>10) {
            holder.card_title_trending.setText(resultsTrendingDayMovies.getTitle().subSequence(0, 10)+"...");
        }else {
            holder.card_title_trending.setText(resultsTrendingDayMovies.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return getListTrendingDayMovies().size();
    }

    public class rvAdapter_nowPlaying_TrendingDayHolder extends RecyclerView.ViewHolder {
        ImageView card_poster_trending;
        TextView card_title_trending;

        public rvAdapter_nowPlaying_TrendingDayHolder(@NonNull View itemView) {
            super(itemView);

            card_poster_trending = itemView.findViewById(R.id.card_poster_trending);
            card_title_trending = itemView.findViewById(R.id.card_title_trending);
        }
    }
}

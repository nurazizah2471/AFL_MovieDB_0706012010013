package com.example.afl_moviedb_0706012010013.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.models.Credit;

import java.util.List;

public class rvAdapter_peopleCreditMovie extends RecyclerView.Adapter<rvAdapter_peopleCreditMovie
        .rvAdapter_peopleCreditMovieHolder> {

    private List<Credit.Cast> listpeopleCreditMovie;
    private Context context;

    public List<Credit.Cast> getListpeopleCreditMovie(){
        return listpeopleCreditMovie;
    }

    public void setListpeopleCreditMovieAdapter(List<Credit.Cast> listpeopleCreditMovie){
        this.listpeopleCreditMovie=listpeopleCreditMovie;
    }

    public rvAdapter_peopleCreditMovie(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public rvAdapter_peopleCreditMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_people_cast_movie, parent, false);
        return new rvAdapter_peopleCreditMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter_peopleCreditMovieHolder holder, int position) {
        final Credit.Cast resultsPeopleCastMovie = getListpeopleCreditMovie().get(position);

        if(resultsPeopleCastMovie.getProfile_path()!=null) {
            Glide.with(context)
                    .load(Const.IMAGE_PATH + resultsPeopleCastMovie.getProfile_path())
                    .into(holder.peopleCastMovie_photo);
        }else{
            if(resultsPeopleCastMovie.getGender()==1) {
                holder.peopleCastMovie_photo.setImageResource(R.drawable.celebrity_female);
            }else if(resultsPeopleCastMovie.getGender()==2){
                holder.peopleCastMovie_photo.setImageResource(R.drawable.celebrity_male);
            }else{
                holder.peopleCastMovie_photo.setImageResource(R.drawable.user_default);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getListpeopleCreditMovie().size();
    }

    public class rvAdapter_peopleCreditMovieHolder extends RecyclerView.ViewHolder {
        ImageView peopleCastMovie_photo;
        public rvAdapter_peopleCreditMovieHolder(@NonNull View itemView) {
            super(itemView);
            peopleCastMovie_photo=itemView.findViewById(R.id.peopleCastMovie_photo);
        }
    }
}



package com.example.afl_moviedb_0706012010013.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.models.Movies;

import java.util.List;

public class rvAdapter_productioncompanies_movieDetail extends RecyclerView.Adapter<rvAdapter_productioncompanies_movieDetail
        .rvAdapter_productioncompaniesFragment_movieDetailHolder> {

    private List<Movies.ProductionCompanies> listproductioncompanies;
    private Context context;

    private List<Movies.ProductionCompanies> getListproductioncompanies(){
        return listproductioncompanies;
    }

    public void setListproductioncompaniesAdapter(List<Movies.ProductionCompanies> listproductioncompanies){
        this.listproductioncompanies=listproductioncompanies;
    }

    public rvAdapter_productioncompanies_movieDetail(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public rvAdapter_productioncompaniesFragment_movieDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_productioncompanies_movie_detail, parent, false);
        return new rvAdapter_productioncompaniesFragment_movieDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter_productioncompaniesFragment_movieDetailHolder holder, int position) {
        final Movies.ProductionCompanies resultsProductionCompanies = getListproductioncompanies().get(position);
        if(resultsProductionCompanies.getLogo_path()!=null) {
            Glide.with(context)
                    .load(Const.IMAGE_PATH + resultsProductionCompanies.getLogo_path())
                    .into(holder.productioncompanies_photo);
        }else{
            holder.productioncompanies_photo.setImageResource(R.drawable.ic_baseline_productioncompanieslogodefault_filter_24);
        }
    }

    @Override
    public int getItemCount() {
        return getListproductioncompanies().size();
    }

    public class rvAdapter_productioncompaniesFragment_movieDetailHolder extends RecyclerView.ViewHolder {
        ImageView productioncompanies_photo;
        public rvAdapter_productioncompaniesFragment_movieDetailHolder(@NonNull View itemView) {
            super(itemView);
            productioncompanies_photo=itemView.findViewById(R.id.productioncompanies_photo);
        }
    }
}
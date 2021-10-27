package com.example.afl_moviedb_0706012010013.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class rvAdapter_nowPlaying_movieDetail extends RecyclerView.Adapter<rvAdapter_nowPlaying_movieDetail.NowPlayingHolder> {

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;
    private static final int ITEM =0;
    private static final int LOADING=1;
    private boolean isLoadingAdded=false;

    public List<NowPlaying.Results> getListNowPlaying(){
        return this.listNowPlaying;
    }

    public void setListNowPlayingAdapter(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying=listNowPlaying;
    }

    public rvAdapter_nowPlaying_movieDetail(Context context) {
        this.context = context;
        this.listNowPlaying=new ArrayList<>();
    }

    @Override
    public NowPlayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NowPlayingHolder viewHolder = null;
        LayoutInflater  inflater=LayoutInflater.from(parent.getContext());

        switch (viewType){
            case ITEM:
                viewHolder=getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view=inflater.inflate(R.layout.item_loadprogress, parent, false);
                viewHolder=new LoadingVH(view);
                break;
        }
        return viewHolder;
    }

    private NowPlayingHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        NowPlayingHolder viewHolder;
        View view1=inflater.inflate(R.layout.card_nowplaying, parent, false);
        viewHolder= new NowPlayingHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingHolder holder, int position) {
        final NowPlaying.Results resultNowPlaying = getListNowPlaying().get(position);

        switch (getItemViewType(position)){
            case ITEM:

                holder.title_nowplaying.setText(resultNowPlaying.getTitle());
                holder.overview_listNowPlaying.setText(resultNowPlaying.getOverview());
                holder.rating_nowplaying.setText(String.valueOf(resultNowPlaying.getVote_average()));

                Glide.with(context)
                        .load(Const.IMAGE_PATH +resultNowPlaying.getPoster_path())
                        .into(holder.imagePoster_nowplaying);
                break;

            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
       return listNowPlaying==null ? 0 :listNowPlaying.size();
    }

    @Override
    public int getItemViewType(int position) {
       return (position==listNowPlaying.size()-1 && isLoadingAdded)? LOADING:ITEM;
    }

    private void add(NowPlaying.Results nowplaying){
        listNowPlaying.add(nowplaying);
    }

    public void addAll(List<NowPlaying.Results> listNowPlayingmove){
       for(NowPlaying.Results results : listNowPlayingmove) {
           add(results);
       }
    }

    public void remove(NowPlaying.Results nowplaying){
        int position=listNowPlaying.indexOf(nowplaying);
        if(position>-1){
            listNowPlaying.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear(){
        isLoadingAdded=false;
        while(getItemCount()>0){
            remove(getItem(0));
        }
    }

    public boolean isEmpty(){
        return getItemCount()==0;
    }

    public void addLoadingFooter(){
        isLoadingAdded=true;
        add(new NowPlaying.Results());
    }

    public void removeLoadingFooter(){
        isLoadingAdded=false;

        int position=listNowPlaying.size()-1;
        NowPlaying.Results results=getItem(position);

        if(results!=null){
            listNowPlaying.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NowPlaying.Results getItem(int position){
        return listNowPlaying.get(position);
    }

    public class NowPlayingHolder extends RecyclerView.ViewHolder {

        TextView title_nowplaying, rating_nowplaying, overview_listNowPlaying;
        ImageView imagePoster_nowplaying;

        public NowPlayingHolder(@NonNull View itemView) {
            super(itemView);

            title_nowplaying = itemView.findViewById(R.id.title_nowplaying);
            rating_nowplaying = itemView.findViewById(R.id.rating_nowplaying);
            imagePoster_nowplaying = itemView.findViewById(R.id.imagePoster_nowplaying);
            overview_listNowPlaying=itemView.findViewById(R.id.overview_listNowPlaying);
        }
    }

    protected class LoadingVH extends NowPlayingHolder {
        public LoadingVH(View view) {
            super(view);
        }
    }
}

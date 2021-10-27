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
import com.example.afl_moviedb_0706012010013.models.UpComing;

import java.util.ArrayList;
import java.util.List;

public class rvAdapter_upComing_movieDetail extends RecyclerView.Adapter<rvAdapter_upComing_movieDetail.UpComingHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;
    private static final int ITEM =0;
    private static final int LOADING=1;
    private boolean isLoadingAdded=false;

    public List<UpComing.Results> getListUpComing(){
        return this.listUpComing;
    }

    public void setListUpComingAdapter(List<UpComing.Results> listUpComing){
        this.listUpComing=listUpComing;
    }

    public rvAdapter_upComing_movieDetail(Context context) {
        this.context = context;
        this.listUpComing=new ArrayList<>();
    }

    @Override
    public UpComingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UpComingHolder viewHolder = null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

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

    private UpComingHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        UpComingHolder viewHolder;
        View view1=inflater.inflate(R.layout.card_nowplaying, parent, false);
        viewHolder= new UpComingHolder(view1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingHolder holder, int position) {
        final UpComing.Results resultUpComing = getListUpComing().get(position);

        switch (getItemViewType(position)){
            case ITEM:

                holder.title_nowplaying.setText(resultUpComing.getTitle());
                holder.overview_listNowPlaying.setText(resultUpComing.getOverview());
                holder.rating_nowplaying.setText(String.valueOf(resultUpComing.getVote_average()));

                Glide.with(context)
                        .load(Const.IMAGE_PATH +resultUpComing.getPoster_path())
                        .into(holder.imagePoster_nowplaying);
                break;

            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listUpComing==null ? 0 :listUpComing.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position==listUpComing.size()-1 && isLoadingAdded)? LOADING:ITEM;
    }

    private void add(UpComing.Results upcoming){
        listUpComing.add(upcoming);
    }

    public void addAll(List<UpComing.Results> listUpComingmove){
        for(UpComing.Results results : listUpComingmove) {
            add(results);
        }
    }

    public void remove(UpComing.Results upcoming){
        int position=listUpComing.indexOf(upcoming);
        if(position>-1){
            listUpComing.remove(position);
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
        add(new UpComing.Results());
    }

    public void removeLoadingFooter(){
        isLoadingAdded=false;

        int position=listUpComing.size()-1;
        UpComing.Results results=getItem(position);

        if(results!=null){
            listUpComing.remove(position);
            notifyItemRemoved(position);
        }
    }

    public UpComing.Results getItem(int position){
        return listUpComing.get(position);
    }

    public class UpComingHolder extends RecyclerView.ViewHolder {

        TextView title_nowplaying, rating_nowplaying, overview_listNowPlaying;
        ImageView imagePoster_nowplaying;

        public UpComingHolder(@NonNull View itemView) {
            super(itemView);

            title_nowplaying = itemView.findViewById(R.id.title_nowplaying);
            rating_nowplaying = itemView.findViewById(R.id.rating_nowplaying);
            imagePoster_nowplaying = itemView.findViewById(R.id.imagePoster_nowplaying);
            overview_listNowPlaying=itemView.findViewById(R.id.overview_listNowPlaying);
        }
    }

    protected class LoadingVH extends UpComingHolder {
        public LoadingVH(View view) {
            super(view);
        }
    }
}

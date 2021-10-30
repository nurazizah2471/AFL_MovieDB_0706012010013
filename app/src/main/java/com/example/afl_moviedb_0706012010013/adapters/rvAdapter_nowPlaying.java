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
import com.example.afl_moviedb_0706012010013.models.NowPlaying;

import java.util.ArrayList;
import java.util.List;

public class rvAdapter_nowPlaying extends RecyclerView.Adapter<rvAdapter_nowPlaying.NowPlayingHolder> {

    private Context context;
    private static List<NowPlaying.Results> listNowPlaying;
    private static final int ITEM =0;
    private static final int LOADING=1;
    private boolean isLoadingAdded=false;


    public List<NowPlaying.Results> getListNowPlaying(){
        return this.listNowPlaying;
    }



    public void setListNowPlayingAdapter(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying=listNowPlaying;
    }

    public static void removeListNowPlaying(){
        for (int p=0;p<listNowPlaying.size();p++){
            listNowPlaying.remove(p);
        }

    }

    public rvAdapter_nowPlaying(Context context) {
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





        switch (getItemViewType(position)){
            case ITEM:

                final NowPlaying.Results resultNowPlaying = getListNowPlaying().get(position);

                holder.title_nowplaying.setText(resultNowPlaying.getTitle());
                holder.rating_nowplaying.setText(String.valueOf(resultNowPlaying.getVote_average()));

                Glide.with(context)
                        .load(Const.IMAGE_PATH +resultNowPlaying.getPoster_path())
                        .into(holder.imagePoster_nowplaying);

                holder.releasedAt_nowplaying.setText("Released At: " + resultNowPlaying.getRelease_date());
                holder.popularity_nowplaying.setText(resultNowPlaying.getPopularity()+" Popular");

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
        notifyItemInserted(getItemCount()-1);
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
        }
    }

    public void clear(){
        isLoadingAdded=false;
        while(getItemCount()>0){
            remove(getItem(0));
        }

        notifyDataSetChanged();
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
            notifyDataSetChanged();
        }
    }

    public NowPlaying.Results getItem(int position){
        return listNowPlaying.get(position);
    }

    public class NowPlayingHolder extends RecyclerView.ViewHolder {

        TextView title_nowplaying, rating_nowplaying, releasedAt_nowplaying, popularity_nowplaying;
        ImageView imagePoster_nowplaying;

        public NowPlayingHolder(@NonNull View itemView) {
            super(itemView);

            title_nowplaying = itemView.findViewById(R.id.title_nowplaying);
            rating_nowplaying = itemView.findViewById(R.id.rating_nowplaying);
            imagePoster_nowplaying = itemView.findViewById(R.id.imagePoster_nowplaying);
            releasedAt_nowplaying=itemView.findViewById(R.id.releasedAt_nowplaying);
            popularity_nowplaying=itemView.findViewById(R.id.popularity_nowplaying);
        }
    }

    protected class LoadingVH extends NowPlayingHolder {
        public LoadingVH(View view) {
            super(view);
        }
    }
}

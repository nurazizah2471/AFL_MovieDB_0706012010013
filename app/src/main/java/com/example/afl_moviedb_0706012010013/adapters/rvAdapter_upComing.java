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

public class rvAdapter_upComing extends RecyclerView.Adapter<rvAdapter_upComing.UpComingHolder> {

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

    public rvAdapter_upComing(Context context) {
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

        switch (getItemViewType(position)){
            case ITEM:

                final UpComing.Results resultUpComing = getListUpComing().get(position);

                holder.title_upcoming.setText(resultUpComing.getTitle());
                holder.rating_upcoming.setText(String.valueOf(resultUpComing.getVote_average()));

                if(resultUpComing.getPoster_path()!=null) {
                    Glide.with(context)
                            .load(Const.IMAGE_PATH + resultUpComing.getPoster_path())
                            .into(holder.imagePoster_upcoming);
                }else {
                    holder.imagePoster_upcoming.setImageResource(R.drawable.photo);
                }

                String released="";

                if(resultUpComing.getRelease_date()!=null) {
                    released+=resultUpComing.getRelease_date();
                }else {
                    released+="-";
                }

                holder.releasedAt_upcoming.setText("Released At: " + released);

                if(resultUpComing.getPopularity()>0) {
                    holder.popularity_upcoming.setText(resultUpComing.getPopularity() + " Popular");
                }
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
        notifyItemInserted(getItemCount()-1);
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
        add(new UpComing.Results());
    }

    public void removeLoadingFooter(){
        isLoadingAdded=false;

        int position=listUpComing.size()-1;
        UpComing.Results results=getItem(position);

        if(results!=null){
            listUpComing.remove(position);
            notifyDataSetChanged();
        }
    }

    public UpComing.Results getItem(int position){
        return listUpComing.get(position);
    }

    public class UpComingHolder extends RecyclerView.ViewHolder {

        TextView title_upcoming, rating_upcoming, releasedAt_upcoming, popularity_upcoming;
        ImageView imagePoster_upcoming;

        public UpComingHolder(@NonNull View itemView) {
            super(itemView);

            title_upcoming = itemView.findViewById(R.id.title_nowplaying);
            rating_upcoming = itemView.findViewById(R.id.rating_nowplaying);
            imagePoster_upcoming = itemView.findViewById(R.id.imagePoster_nowplaying);
            releasedAt_upcoming=itemView.findViewById(R.id.releasedAt_nowplaying);
            popularity_upcoming=itemView.findViewById(R.id.popularity_nowplaying);
        }
    }

    protected class LoadingVH extends UpComingHolder {
        public LoadingVH(View view) {
            super(view);
        }
    }
}

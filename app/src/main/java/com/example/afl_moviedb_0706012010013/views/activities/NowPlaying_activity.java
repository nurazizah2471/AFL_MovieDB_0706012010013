package com.example.afl_moviedb_0706012010013.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.ViewModels.MovieViewModel;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_movieDetail;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;
public class NowPlaying_activity extends AppCompatActivity {

    private RecyclerView rv_nowplaying;
    private MovieViewModel movieViewModel_nowplaying;
    private Toolbar toolbar_listNowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        inisialisasi();
        setToolbar();

        movieViewModel_nowplaying.getNowPlaying();
        movieViewModel_nowplaying.getResultGetNowPlaying().observe(NowPlaying_activity.this, showresultNowPlaying);
    }

    private Observer<NowPlaying> showresultNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            setRV_listNowPlaying(nowPlaying);
        }
    };

    private void setRV_listNowPlaying(NowPlaying nowPlaying){
        rv_nowplaying.setLayoutManager(new LinearLayoutManager(NowPlaying_activity.this));
        rvAdapter_nowPlaying_movieDetail adapter = new rvAdapter_nowPlaying_movieDetail(NowPlaying_activity.this);
        adapter.setListNowPlayingAdapter(nowPlaying.getResults());
        rv_nowplaying.setAdapter(adapter);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar_listNowPlaying);
        getSupportActionBar().setTitle("Now Playing");
    }

    private void inisialisasi(){
        rv_nowplaying=findViewById(R.id.rv_nowplaying);
        toolbar_listNowPlaying=findViewById(R.id.toolbar_listNowPlaying);

        movieViewModel_nowplaying=new ViewModelProvider(NowPlaying_activity.this).get(MovieViewModel.class);
    }
}
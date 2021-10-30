package com.example.afl_moviedb_0706012010013.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.afl_moviedb_0706012010013.models.Movies;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;
import com.example.afl_moviedb_0706012010013.models.TrendingMovies;
import com.example.afl_moviedb_0706012010013.models.UpComing;
import com.example.afl_moviedb_0706012010013.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository= MovieRepository.getInstance();
    }

    //==Begin of viewModel to get Movie ById
    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();

    public void getMovieById(String movieId){
        resultGetMovieById = movieRepository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }
    //==End of viewModel to get Movie ById


    //==Begin of viewModel to getnowplaying

    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();

    public void getNowPlaying(String page){
       resultGetNowPlaying=movieRepository.getNowPlaying(page);

    }
    public LiveData<NowPlaying> getResultGetNowPlaying(){
        return resultGetNowPlaying;
    }
    //==End of viewModel to getnowplaying

    //==Begin of viewModel to getupcoming

    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();

    public void getUpComing(String page){
        resultGetUpComing=movieRepository.getUpComing(page);

    }
    public LiveData<UpComing> getResultGetUpComing(){
        return resultGetUpComing;
    }
    //==End of viewModel to getupcoming

    //==Begin of viewModel to getTrendingDayMovies

    private MutableLiveData<TrendingMovies> resultGetTrendingDayMovies = new MutableLiveData<>();

    public void getTrendingDayMovies(){
        resultGetTrendingDayMovies=movieRepository.getMovieDataTrending("movie", "day" );

    }
    public LiveData<TrendingMovies> getResultGetTrendingDayMovies(){
        return resultGetTrendingDayMovies;
    }
    //==End of viewModel to getTrendingDayMovies

    //==Begin of viewModel to getTrendingWeekMovies

    private MutableLiveData<TrendingMovies> resultGetTrendingWeekMovies = new MutableLiveData<>();

    public void getTrendingWeekMovies(){
        resultGetTrendingWeekMovies=movieRepository.getMovieDataTrending("movie", "week" );

    }
    public LiveData<TrendingMovies> getResultGetTrendingWeekMovies(){
        return resultGetTrendingWeekMovies;
    }
    //==End of viewModel to getTrendingWeekMovies
}

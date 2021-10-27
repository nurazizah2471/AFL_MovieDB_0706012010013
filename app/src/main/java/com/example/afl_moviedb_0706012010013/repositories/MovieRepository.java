package com.example.afl_moviedb_0706012010013.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.models.Movies;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;
import com.example.afl_moviedb_0706012010013.models.UpComing;
import com.example.afl_moviedb_0706012010013.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;

    private MovieRepository() {
    }

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    public MutableLiveData<Movies> getMovieData(String movie_id) {

        final MutableLiveData<Movies> result = new MutableLiveData<>();

        ApiService.endpoint().getMoviesById(Integer.parseInt(movie_id), Const.API_KEY).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });
        return result;
    }

    public MutableLiveData<NowPlaying> getNowPlaying(int page) {

        final MutableLiveData<NowPlaying> result = new MutableLiveData<>();

        ApiService.endpoint().getNowPlaying(page, Const.API_KEY).enqueue(new Callback<NowPlaying>() {

            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {

            }
        });
        return result;
    }

    public MutableLiveData<UpComing> getUpComing(int page) {

        final MutableLiveData<UpComing> result = new MutableLiveData<>();

        ApiService.endpoint().getUpComing(page, Const.API_KEY).enqueue(new Callback<UpComing>() {

            @Override
            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UpComing> call, Throwable t) {

            }
        });
        return result;
    }
}

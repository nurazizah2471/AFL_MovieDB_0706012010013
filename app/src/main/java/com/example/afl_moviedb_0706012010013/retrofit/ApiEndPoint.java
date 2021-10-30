package com.example.afl_moviedb_0706012010013.retrofit;

import com.example.afl_moviedb_0706012010013.models.Credit;
import com.example.afl_moviedb_0706012010013.models.Movies;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;
import com.example.afl_moviedb_0706012010013.models.TrendingMovies;
import com.example.afl_moviedb_0706012010013.models.UpComing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMoviesById(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
        );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/upcoming")
    Call<UpComing> getUpComing(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("trending/{media_type}/{time_window}")
    Call<TrendingMovies> getMoviesTrendingDay(
            @Path("media_type") String mediaType,
            @Path("time_window") String timeWindow,
            @Query("api_key") String apiKey
    );
    @GET("movie/{movie_id}/credits")
    Call<Credit> getMoviesCredit(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

}

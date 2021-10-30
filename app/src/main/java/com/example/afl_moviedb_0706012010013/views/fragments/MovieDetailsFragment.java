package com.example.afl_moviedb_0706012010013.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.ViewModels.MovieViewModel;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_genres_movieDetail;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_productioncompanies_movieDetail;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_upComing;
import com.example.afl_moviedb_0706012010013.helpers.Const;
import com.example.afl_moviedb_0706012010013.helpers.ItemClickSupport;
import com.example.afl_moviedb_0706012010013.models.Movies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView title_movie_detail, release_detail_movie, popularity_detail_movie, voteAverage_movie_detail,
            voteCount_movie_detail, overviewText_movieDetails, originalTitle_movieDetail, originalLanguage_movieDetail,
            tagline_movie_detail;
    private ImageView posterPath_movie_detail, bc_Path;
    private RecyclerView rv_genre_movieDetail, rv_productioncompanies_movieDetail;
    private MovieViewModel movieViewModel;
    private ProgressBar progressBar;
    private ConstraintLayout mainContent;

    private rvAdapter_nowPlaying rvAdapter_nowPlaying;
    private rvAdapter_upComing rvAdapter_upComing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_movie_detail, container, false);

        inisialisasi(view);


        String movieId=getArguments().getString("movieId");

        movieViewModel.getMovieById(movieId);
        movieViewModel.getResultGetMovieById().observe(getActivity(), showResultMovieinDetail);
        return view;
    }

    private void onBackPress(View v) {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
           public void handleOnBackPressed() {
               getActivity().onBackPressed();

            }
        });
    }

    private Observer<Movies> showResultMovieinDetail = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {

            setImageView(movies);

            setTextView(movies);

            setRV(movies);

            progressBar.setVisibility(View.GONE); // Hide Progress bar
            mainContent.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE); // Hide Progress bar
                }
            }, 200);
            mainContent.setVisibility(View.VISIBLE);

            addItemClickSupport(movies);

        }
    };
    private void inisialisasi(View view) {
        title_movie_detail=view.findViewById(R.id.title_movie_detail);
        release_detail_movie=view.findViewById(R.id.release_detail_movie);
        popularity_detail_movie=view.findViewById(R.id.popularity_detail_movie);
        voteAverage_movie_detail=view.findViewById(R.id.voteAverage_movie_detail);
        voteCount_movie_detail=view.findViewById(R.id.voteCount_movie_detail);
        posterPath_movie_detail=view.findViewById(R.id.posterPath_movie_detail);
        overviewText_movieDetails=view.findViewById(R.id.overviewText_movieDetails);
        tagline_movie_detail=view.findViewById(R.id.tagline_movie_detail);
        originalLanguage_movieDetail=view.findViewById(R.id.originalLanguage_movieDetail);
        bc_Path=view.findViewById(R.id.bc_Path);
        originalTitle_movieDetail=view.findViewById(R.id.originalTitle_movieDetail);
        rv_genre_movieDetail=view.findViewById(R.id.rv_genre_movieDetail);
        rv_productioncompanies_movieDetail=view.findViewById(R.id.rv_productioncompanies_movieDetail);
        progressBar = view.findViewById(R.id.progressBar_MoviesDetailsFragment); // Get ProgressBar reference
        mainContent = view.findViewById(R.id.ConstraintLayout_MovieDetailsFragment);

        movieViewModel=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
    }
    private void setTextView(Movies movies){
        title_movie_detail.setText(movies.getTitle());
        release_detail_movie.setText(movies.getRelease_date());
        popularity_detail_movie.setText(String.valueOf(movies.getPopularity()));
        voteAverage_movie_detail.setText(String.valueOf(movies.getVote_average()));
        voteCount_movie_detail.setText("from "+String.valueOf(movies.getVote_count())+" Peoples");
        overviewText_movieDetails.setText(movies.getOverview());
        originalTitle_movieDetail.setText("Original Title: "+movies.getOriginal_title());
        originalLanguage_movieDetail.setText(movies.getOriginal_language());
        tagline_movie_detail.setText(movies.getTagline());
    }

    private void setImageView(Movies movies){
        Glide.with(getActivity())
                .load(Const.IMAGE_PATH +movies.getPoster_path())
                .into(posterPath_movie_detail);

        Glide.with(getActivity())
                .load(Const.IMAGE_PATH +movies.getBackdrop_path())
                .into(bc_Path);
    }

    private void setRV(Movies movies){

        setRV_Genre(movies);
        setRV_ProductionCompanies(movies);
    }

    private void setRV_Genre(Movies movies) {
        rv_genre_movieDetail.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        rvAdapter_genres_movieDetail adapter = new rvAdapter_genres_movieDetail(getActivity());
        adapter.setListGenresAdapter(movies.getGenres());
        rv_genre_movieDetail.setAdapter(adapter);
    }

    private void setRV_ProductionCompanies(Movies movies) {
        rv_productioncompanies_movieDetail.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        rvAdapter_productioncompanies_movieDetail adapterProductionCompanies = new rvAdapter_productioncompanies_movieDetail(getActivity());
        adapterProductionCompanies.setListproductioncompaniesAdapter(movies.getProduction_companies());
        rv_productioncompanies_movieDetail.setAdapter(adapterProductionCompanies);
    }

    private void addItemClickSupport(Movies movies){
        ItemClickSupport.addTo(rv_productioncompanies_movieDetail).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), movies.getProduction_companies().get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
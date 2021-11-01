package com.example.afl_moviedb_0706012010013.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.ViewModels.MovieViewModel;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_TrendingDay;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_TrendingWeek;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying;
import com.example.afl_moviedb_0706012010013.helpers.ItemClickSupport;
import com.example.afl_moviedb_0706012010013.helpers.PaginationScrollListener;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;
import com.example.afl_moviedb_0706012010013.models.TrendingMovies;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private RecyclerView rv_nowplaying_f, rv_trendingDay_Movies, rv_trendingWeek_Movies;
    private MovieViewModel movieViewModel_nowplaying_f;
    private rvAdapter_nowPlaying rvAdapter_nowPlaying;
    private rvAdapter_nowPlaying_TrendingDay rvAdapter_nowPlaying_TrendingDay;
    private rvAdapter_nowPlaying_TrendingWeek rvAdapter_nowPlaying_TrendingWeek;
    private LinearLayoutManager linearLayoutManager, linearLayoutManagerRVNowplaying;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading;
    private boolean isLastPage;
    private long TOTAL_PAGE;
    private long currentPage;
    private long MaxPage=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_now_playing, container, false);

        inisialisasi(view);

        setRV_NowPlayingMovies();

        loadDataTrendingMovies("day");

        loadDataTrendingMovies("week");

        currentPage=PAGE_START;
        isLoading=false;
        isLastPage=false;

        loadDataNowPlaying();

        rv_nowplaying_f.addOnScrollListener(new PaginationScrollListener(linearLayoutManagerRVNowplaying) {
            @Override
            protected void loadMoreItems() {
                loadDataNowPlaying();
            }

            @Override
            public long getTotalPageCount() {
                return TOTAL_PAGE;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isloading() {
                return isLoading;
            }
        });

        addItemClickSupport();

        return view;
    }

    private void loadDataTrendingMovies(String keterangan) {
        if(keterangan=="day"){
            movieViewModel_nowplaying_f.getTrendingDayMovies();
            movieViewModel_nowplaying_f.getResultGetTrendingDayMovies().observe(getActivity(), showresultTrendingDayMovies);
        }else{
            movieViewModel_nowplaying_f.getTrendingWeekMovies();
            movieViewModel_nowplaying_f.getResultGetTrendingWeekMovies().observe(getActivity(), showresultTrendingWeekMovies);
        }
    }

    private void loadDataNowPlaying() {
        getData();
    }

    private void getData() {

            if ((getCurrentPageNowPlaying() < getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() != 0) ||
                    (getCurrentPageNowPlaying() <= getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() == 0) ) {

                if ((getCurrentPageNowPlaying() < getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() != 0)) {

                    isLoading = true;
                    setCurrentPageNowPlaying();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            movieViewModel_nowplaying_f.getNowPlaying(String.valueOf(getCurrentPageNowPlaying()));
                            movieViewModel_nowplaying_f.getResultGetNowPlaying().observe(getActivity(), showresultNowPlaying);
                        }
                    }, 300);

                } else if((getCurrentPageNowPlaying() == getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() == 0)
                ||  (getCurrentPageNowPlaying() == PAGE_START && rvAdapter_nowPlaying.getListNowPlaying().size() == 0)){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            movieViewModel_nowplaying_f.getNowPlaying(String.valueOf(getCurrentPageNowPlaying()));
                            movieViewModel_nowplaying_f.getResultGetNowPlaying().observe(getActivity(), showresultNowPlaying);
                        }
                    }, 300);

                }
            }  else {
                rvAdapter_nowPlaying.removeLoadingFooter();
                isLoading = false;
                isLastPage = true;
            }
    }

    private Observer<NowPlaying> showresultNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {

            progressBar.setVisibility(View.GONE);

            setMaxPageNowPlaying(nowPlaying.getTotal_pages());

            setTOTAL_PAGE(nowPlaying.getResults().size());

            if(getCurrentPageNowPlaying() <= getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() == 0) {

                setDataRV_listNowPlaying(nowPlaying.getResults());

                    if (getCurrentPageNowPlaying() <= getMaxPageNowPlaying()) {
                        rvAdapter_nowPlaying.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }

            }else if (getCurrentPageNowPlaying() <= getMaxPageNowPlaying() && rvAdapter_nowPlaying.getListNowPlaying().size() != 0) {

                rvAdapter_nowPlaying.removeLoadingFooter();
                isLoading = false;
                setDataRV_listNowPlaying(nowPlaying.getResults());

                if(getCurrentPageNowPlaying() <= getMaxPageNowPlaying()) {
                    rvAdapter_nowPlaying.addLoadingFooter();
                }

                else {
                    isLastPage= true;
                }
            }
        }
    };

    public long getMaxPageNowPlaying() {
        return this.MaxPage;
    }

    private Observer<TrendingMovies> showresultTrendingDayMovies = new Observer<TrendingMovies>() {
        @Override
        public void onChanged(TrendingMovies trendingMoviesDay) {
            List<TrendingMovies.Results> lisresult = trendingMoviesDay.getResults();
            setRV_TrendingDayMovies(lisresult);
        }
    };

    private Observer<TrendingMovies> showresultTrendingWeekMovies = new Observer<TrendingMovies>() {
        @Override
        public void onChanged(TrendingMovies trendingMoviesWeek) {
            setRV_TrendingWeekMovies(trendingMoviesWeek.getResults());
        }
    };

    public void setTOTAL_PAGE(long TOTAL_PAGE){
        this.TOTAL_PAGE=TOTAL_PAGE;
    }
    public long getCurrentPageNowPlaying(){
        return this.currentPage;
    }
    public long getTOTAL_PAGE(){
        return this.TOTAL_PAGE;
    }
    public void setMaxPageNowPlaying(long MaxPages) {
        this.MaxPage= MaxPages;
    }
    public void setCurrentPageNowPlaying(){
        this.currentPage++;
    }
    private void setDataRV_listNowPlaying(List<NowPlaying.Results> nowPlaying){
        rvAdapter_nowPlaying.addAll(nowPlaying);
    }

    private void inisialisasi(View view){
        rv_nowplaying_f=view.findViewById(R.id.rv_nowplaying_f);
        progressBar=view.findViewById(R.id.progressBar_NowPlayingFragment);
        rv_trendingDay_Movies=view.findViewById(R.id.rv_trendingDay_Movies);
        rv_trendingWeek_Movies=view.findViewById(R.id.rv_trendingWeek_Movies);

        movieViewModel_nowplaying_f=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
    }

    private void setRV_TrendingDayMovies(List<TrendingMovies.Results> listTrendingDay){
        rvAdapter_nowPlaying_TrendingDay=new rvAdapter_nowPlaying_TrendingDay(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_trendingDay_Movies.setLayoutManager(linearLayoutManager);
        rvAdapter_nowPlaying_TrendingDay.setListTrendingDayMoviesAdapter(listTrendingDay);
        rv_trendingDay_Movies.setAdapter(rvAdapter_nowPlaying_TrendingDay);
    }

    private void setRV_NowPlayingMovies(){
        rvAdapter_nowPlaying =new rvAdapter_nowPlaying(getActivity());
        linearLayoutManagerRVNowplaying=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_nowplaying_f.setLayoutManager(linearLayoutManagerRVNowplaying);
        rv_nowplaying_f.setAdapter(rvAdapter_nowPlaying);
    }

    private void setRV_TrendingWeekMovies(List<TrendingMovies.Results> listTrendingWeek){
        rvAdapter_nowPlaying_TrendingWeek=new rvAdapter_nowPlaying_TrendingWeek(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_trendingWeek_Movies.setLayoutManager(linearLayoutManager);
        rvAdapter_nowPlaying_TrendingWeek.setListTrendingWeekMoviesAdapter(listTrendingWeek);
        rv_trendingWeek_Movies.setAdapter(rvAdapter_nowPlaying_TrendingWeek);
    }

    private void addItemClickSupport(){
        ItemClickSupport.addTo(rv_nowplaying_f).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_nowPlaying.getListNowPlaying().get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailFragment,bundle);
            }
        });

        ItemClickSupport.addTo(rv_trendingDay_Movies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_nowPlaying_TrendingDay.getListTrendingDayMovies().get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailFragment,bundle);

            }
        });

        ItemClickSupport.addTo(rv_trendingWeek_Movies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_nowPlaying_TrendingWeek.getListTrendingWeekMovies().get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailFragment,bundle);

            }
        });
    }
}
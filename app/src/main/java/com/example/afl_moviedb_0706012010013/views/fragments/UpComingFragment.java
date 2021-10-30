package com.example.afl_moviedb_0706012010013.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.ViewModels.MovieViewModel;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_TrendingDay;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_TrendingWeek;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_upComing;
import com.example.afl_moviedb_0706012010013.helpers.ItemClickSupport;
import com.example.afl_moviedb_0706012010013.helpers.PaginationScrollListener;
import com.example.afl_moviedb_0706012010013.models.TrendingMovies;
import com.example.afl_moviedb_0706012010013.models.UpComing;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpComingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
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

    private RecyclerView rv_upcoming_f,  rv_trendingDayUpComing_Movies, rv_trendingWeekUpComing_Movies;
    private MovieViewModel movieViewModel_upcoming_f;
    private rvAdapter_upComing rvAdapter_upComing;
    private rvAdapter_nowPlaying_TrendingDay rvAdapter_nowPlaying_TrendingDay;
    private rvAdapter_nowPlaying_TrendingWeek rvAdapter_nowPlaying_TrendingWeek;
    private LinearLayoutManager linearLayoutManager, linearLayoutManagerRVUpcoming;
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

        View view= inflater.inflate(R.layout.fragment_up_coming, container, false);

        inisialisasi(view);

        setRV_UpComingMovies();

        loadDataTrendingMovies("day");

        loadDataTrendingMovies("week");

        currentPage=PAGE_START;
        isLoading=false;
        isLastPage=false;

        loadDataUpComing();

        rv_upcoming_f.addOnScrollListener(new PaginationScrollListener(linearLayoutManagerRVUpcoming) {
            @Override
            protected void loadMoreItems() {
                loadDataUpComing();
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
            movieViewModel_upcoming_f.getTrendingDayMovies();
            movieViewModel_upcoming_f.getResultGetTrendingDayMovies().observe(getActivity(), showresultTrendingDayMovies);
        }else{
            movieViewModel_upcoming_f.getTrendingWeekMovies();
            movieViewModel_upcoming_f.getResultGetTrendingWeekMovies().observe(getActivity(), showresultTrendingWeekMovies);
        }
    }

    private void loadDataUpComing(){
        getData();
    }

    private void getData() {

        if((getCurrentPageUpComing()<getMaxPageUpComing() && rvAdapter_upComing.getListUpComing().size()!=0) ||
                (getCurrentPageUpComing()<=getMaxPageUpComing() && rvAdapter_upComing.getListUpComing().size()==0)){
           if((getCurrentPageUpComing()<getMaxPageUpComing() && rvAdapter_upComing.getListUpComing().size()!=0)){

               isLoading=true;
               setCurrentPageUpComing();

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       movieViewModel_upcoming_f.getUpComing(String.valueOf(getCurrentPageUpComing()));
                       movieViewModel_upcoming_f.getResultGetUpComing().observe(getActivity(), showresultUpComing);
                   }
               }, 300);

           } else if((getCurrentPageUpComing()==getMaxPageUpComing() && rvAdapter_upComing.getListUpComing().size()==0)||
                   (getCurrentPageUpComing()==PAGE_START && rvAdapter_upComing.getListUpComing().size()==0)){

               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       movieViewModel_upcoming_f.getUpComing(String.valueOf(getCurrentPageUpComing()));
                       movieViewModel_upcoming_f.getResultGetUpComing().observe(getActivity(), showresultUpComing);
                   }
               }, 300);

           }
        }else{
            rvAdapter_upComing.removeLoadingFooter();
            isLoading=false;
            isLastPage=true;
        }
    }

    private Observer<UpComing> showresultUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {

            progressBar.setVisibility(View.GONE);

            setMaxPageUpComing(upComing.getTotal_pages());

            setTOTAL_PAGE(upComing.getResults().size());

            if(getCurrentPageUpComing()<=getMaxPageUpComing() && rvAdapter_upComing.getListUpComing().size()==0){
                setDataRV_listUpComing(upComing.getResults());

                if(getCurrentPageUpComing()<=getMaxPageUpComing()) {
                    rvAdapter_upComing.addLoadingFooter();
                }

                else {
                    isLastPage= true;
                }

            }else if(getCurrentPageUpComing()<=getMaxPageUpComing()&& rvAdapter_upComing.getListUpComing().size() !=0){
                rvAdapter_upComing.removeLoadingFooter();
                isLoading = false;
                setDataRV_listUpComing(upComing.getResults());

                if(getCurrentPageUpComing()<=getMaxPageUpComing()) {
                    rvAdapter_upComing.addLoadingFooter();
                }
                else {
                    isLastPage= true;
                }
            }
        }
    };

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

    public long getCurrentPageUpComing(){
        return this.currentPage;
    }

    public long getTOTAL_PAGE(){
        return this.TOTAL_PAGE;
    }

    public long getMaxPageUpComing() {
        return this.MaxPage;
    }
    public void setMaxPageUpComing(long MaxPage){
        this.MaxPage=MaxPage;
    }

    public void setCurrentPageUpComing(){
        this.currentPage++;
    }

    private void setDataRV_listUpComing(List<UpComing.Results> upComing){
        rvAdapter_upComing.addAll(upComing);
    }

    private void inisialisasi(View view){
        rv_upcoming_f=view.findViewById(R.id.rv_upcoming_f);
        progressBar=view.findViewById(R.id.progressBar_upComingFragment);
        rv_trendingDayUpComing_Movies=view.findViewById(R.id.rv_trendingDayUpComing_Movies);
        rv_trendingWeekUpComing_Movies=view.findViewById(R.id.rv_trendingWeekUpComing_Movies);

        movieViewModel_upcoming_f=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
    }

    private void setRV_TrendingDayMovies(List<TrendingMovies.Results> listTrendingDay){
        rvAdapter_nowPlaying_TrendingDay=new rvAdapter_nowPlaying_TrendingDay(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_trendingDayUpComing_Movies.setLayoutManager(linearLayoutManager);
        rvAdapter_nowPlaying_TrendingDay.setListTrendingDayMoviesAdapter(listTrendingDay);
        rv_trendingDayUpComing_Movies.setAdapter(rvAdapter_nowPlaying_TrendingDay);
    }
    private void setRV_UpComingMovies(){
        rvAdapter_upComing =new rvAdapter_upComing(getActivity());
        linearLayoutManagerRVUpcoming=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_upcoming_f.setLayoutManager(linearLayoutManagerRVUpcoming);
        rv_upcoming_f.setAdapter(rvAdapter_upComing);
    }

    private void setRV_TrendingWeekMovies(List<TrendingMovies.Results> listTrendingWeek){
        rvAdapter_nowPlaying_TrendingWeek=new rvAdapter_nowPlaying_TrendingWeek(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rv_trendingWeekUpComing_Movies.setLayoutManager(linearLayoutManager);
        rvAdapter_nowPlaying_TrendingWeek.setListTrendingWeekMoviesAdapter(listTrendingWeek);
        rv_trendingWeekUpComing_Movies.setAdapter(rvAdapter_nowPlaying_TrendingWeek);
    }

    private void addItemClickSupport(){
        ItemClickSupport.addTo(rv_upcoming_f).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_upComing.getListUpComing().get(position).getId());
                bundle.putString("fromFragment", "UpComingFragment");
                Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailFragment,bundle);
            }
        });

        ItemClickSupport.addTo(rv_trendingDayUpComing_Movies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_nowPlaying_TrendingDay.getListTrendingDayMovies().get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailFragment,bundle);

            }
        });

        ItemClickSupport.addTo(rv_trendingWeekUpComing_Movies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+ rvAdapter_nowPlaying_TrendingWeek.getListTrendingWeekMovies().get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailFragment,bundle);

            }
        });
    }
}
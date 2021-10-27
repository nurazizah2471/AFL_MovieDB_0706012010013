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
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_upComing_movieDetail;
import com.example.afl_moviedb_0706012010013.helpers.ItemClickSupport;
import com.example.afl_moviedb_0706012010013.helpers.PaginationScrollListener;
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

    private RecyclerView rv_upcoming_f;
    private MovieViewModel movieViewModel_upcoming_f;
    private rvAdapter_upComing_movieDetail rvAdapter_upComing_movieDetail;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading=false;
    private boolean isLastPage=false;
    private int TOTAL_PAGE;
    private int currentPage = PAGE_START;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_up_coming, container, false);

        inisialisasi(view);
        loadFirstPage(currentPage);

        rv_upcoming_f.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if(currentPage<2){
                    isLoading=true;
                    currentPage+=1;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadNextPage(currentPage);//(currentPage);
                        }
                    }, 200);
                }else{
                    rvAdapter_upComing_movieDetail.removeLoadingFooter();
                    isLoading=false;
                    isLastPage=true;
                }
            }

            @Override
            public int getTotalPageCount() {
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

        return view;
    }


    private void loadFirstPage(int currentPage) {
        getData(currentPage);
    }

    private void loadNextPage(int currentPage){
        getData(currentPage);
    }

    private void getData(int currentPage) {

        if(currentPage>PAGE_START && rvAdapter_upComing_movieDetail.getListUpComing().size()==0){
            this.currentPage=PAGE_START;

        }

        movieViewModel_upcoming_f.getUpComing(currentPage);
        movieViewModel_upcoming_f.getResultGetUpComing().observe(getActivity(), showresultUpComing);

    }

    private Observer<UpComing> showresultUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {

            progressBar.setVisibility(View.GONE);

            List<UpComing.Results> upcomingresult=upComing.getResults();

            TOTAL_PAGE=upcomingresult.size();

            if(currentPage==1){
                setRV_listUpComing(upcomingresult);

                if(currentPage<=TOTAL_PAGE) {
                    rvAdapter_upComing_movieDetail.addLoadingFooter();
                }

                else {
                    isLastPage= true;
                }

            }else {
                rvAdapter_upComing_movieDetail.removeLoadingFooter();
                isLoading = false;
                setRV_listUpComing(upcomingresult);

                if(currentPage!=TOTAL_PAGE) {
                    rvAdapter_upComing_movieDetail.addLoadingFooter();
                }
                else {
                    isLastPage= true;
                }

            }

            addItemClickSupport();
        }
    };

    private void setRV_listUpComing(List<UpComing.Results> upComing){
        rvAdapter_upComing_movieDetail.addAll(upComing);
        rvAdapter_upComing_movieDetail.notifyDataSetChanged();
    }

    private void inisialisasi(View view){
        rv_upcoming_f=view.findViewById(R.id.rv_upcoming_f);
        progressBar=view.findViewById(R.id.progressBar_upComingFragment);
        rvAdapter_upComing_movieDetail=new rvAdapter_upComing_movieDetail(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_upcoming_f.setLayoutManager(linearLayoutManager);
        rv_upcoming_f.setAdapter(rvAdapter_upComing_movieDetail);

        movieViewModel_upcoming_f=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
    }

    private void addItemClickSupport(){
        ItemClickSupport.addTo(rv_upcoming_f).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle=new Bundle();
                bundle.putString("movieId", ""+rvAdapter_upComing_movieDetail.getListUpComing().get(position).getId());
                bundle.putString("fromFragment", "UpComingFragment");
                Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailFragment,bundle);
            }
        });
    }

}
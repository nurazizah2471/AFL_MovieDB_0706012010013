package com.example.afl_moviedb_0706012010013.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afl_moviedb_0706012010013.R;
import com.example.afl_moviedb_0706012010013.ViewModels.MovieViewModel;
import com.example.afl_moviedb_0706012010013.adapters.rvAdapter_nowPlaying_movieDetail;
import com.example.afl_moviedb_0706012010013.helpers.ItemClickSupport;
import com.example.afl_moviedb_0706012010013.models.NowPlaying;

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

    private RecyclerView rv_nowplaying_f;
    private MovieViewModel movieViewModel_nowplaying_f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_now_playing, container, false);

        inisialisasi(view);

        movieViewModel_nowplaying_f=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        movieViewModel_nowplaying_f.getNowPlaying();
        movieViewModel_nowplaying_f.getResultGetNowPlaying().observe(getActivity(), showresultNowPlaying);
        return view;
    }

    private Observer<NowPlaying> showresultNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            setRV_listNowPlaying(nowPlaying);

            ItemClickSupport.addTo(rv_nowplaying_f).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("movieId", ""+nowPlaying.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailFragment,bundle);
                }
            });

            ItemClickSupport.addTo(rv_nowplaying_f).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    Toast.makeText(getActivity(), "halo", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    };
    private void setRV_listNowPlaying(NowPlaying nowPlaying){
        rv_nowplaying_f.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter_nowPlaying_movieDetail adapter = new rvAdapter_nowPlaying_movieDetail(getActivity());
        adapter.setListNowPlayingAdapter(nowPlaying.getResults());
        rv_nowplaying_f.setAdapter(adapter);
    }
    private void inisialisasi(View view){
        rv_nowplaying_f=view.findViewById(R.id.rv_nowplaying_f);
        movieViewModel_nowplaying_f=new ViewModelProvider(getActivity()).get(MovieViewModel.class);
    }


}
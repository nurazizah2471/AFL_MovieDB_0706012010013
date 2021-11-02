package com.example.afl_moviedb_0706012010013.views.activities;

import android.os.Bundle;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.afl_moviedb_0706012010013.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavHostFragment nav_fragment_main_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        inisialisasi();

        nav_fragment_main_menu=(NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_fragment_main_menu);

        AppBarConfiguration appBarConfiguration=new AppBarConfiguration.Builder(R.id.nowPlayingFragment,
                R.id.upComingFragment).build();

        NavigationUI.setupActionBarWithNavController(this, nav_fragment_main_menu.getNavController(), appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, nav_fragment_main_menu.getNavController());
    }

    private void inisialisasi() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return nav_fragment_main_menu.getNavController().navigateUp()||super.onSupportNavigateUp();
    }

}
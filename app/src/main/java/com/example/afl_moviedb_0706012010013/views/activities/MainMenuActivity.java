package com.example.afl_moviedb_0706012010013.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.afl_moviedb_0706012010013.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavHostFragment nav_fragment_main_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        nav_fragment_main_menu=(NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_fragment_main_menu);

        AppBarConfiguration appBarConfiguration=new AppBarConfiguration.Builder(R.id.nowPlayingFragment,
                R.id.upComingFragment).build();


        NavigationUI.setupActionBarWithNavController(this, nav_fragment_main_menu.getNavController(), appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, nav_fragment_main_menu.getNavController());

    }
}
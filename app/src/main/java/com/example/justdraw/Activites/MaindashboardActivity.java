package com.example.justdraw.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.justdraw.Fragments.CrearteCanvaFragment;
import com.example.justdraw.Fragments.HomeFargment;
import com.example.justdraw.Fragments.ProfileFragment;
import com.example.justdraw.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MaindashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindashboard);
        ///loadingFragments
        loadFragment(new HomeFargment());
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFargment();
                break;

            case R.id.create_canva:
                fragment = new CrearteCanvaFragment();
                break;

            case R.id.Profile:
                fragment = new ProfileFragment();
                break;


        }

        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
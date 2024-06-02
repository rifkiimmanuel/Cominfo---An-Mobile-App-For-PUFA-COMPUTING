package com.example.projectfinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{


    private BottomNavigationView bottomNavigationView;
    private ProfileFragment profileFragment = new ProfileFragment();
    private HomeFragment homeFragment = new HomeFragment();

    private Contact settingFragment = new Contact();



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Ganti ini dengan pemanggilan HomeFragment pertama kali
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
            return true;
        } else if (item.getItemId() == R.id.profile) {
            // Check if the user is logged in
            if (userIsLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
            } else {
                // User is not logged in, navigate to login.java
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
            }
            return true;
        } else if (item.getItemId() == R.id.event) {
            Intent intent = new Intent(this, Event1.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingFragment).commit();
            return true;
        }

        return false;
    }

    private boolean userIsLoggedIn() {
        // Modify this method to check Firebase Authentication or any other authentication method you are using
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser() != null;
    }



}
package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class lecturerIT extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    LecturerAdapter lecturerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_it);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rv);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null && (userEmail.equals("admin@gmail.com") || userEmail.equals("admin123@gmail.com"))) {
                // Show FloatingActionButton for admin users
                fab.setVisibility(View.VISIBLE);
            } else {
                // Hide FloatingActionButton for non-admin users
                fab.setVisibility(View.GONE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Datalecturer> options =
                new FirebaseRecyclerOptions.Builder<Datalecturer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lecturer_it"), Datalecturer.class)
                        .build();


        lecturerAdapter = new LecturerAdapter(options);
        recyclerView.setAdapter(lecturerAdapter);
        lecturerAdapter.startListening(); // Start listening for data changes

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(lecturerIT.this, uploadlecturer.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        lecturerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lecturerAdapter.stopListening();
    }
}

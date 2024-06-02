package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pumait extends AppCompatActivity {

    Button btnback;
    Button clickmember;

    Button lecturermember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumait);

        btnback = findViewById(R.id.buttonback);
        clickmember = findViewById(R.id.memberit);
        lecturermember = findViewById(R.id.lecturerit);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clickmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumait.this, pumait_member.class);
                startActivity(intent);
            }
        });

        lecturermember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumait.this, lecturerIT.class);
                startActivity(intent);
            }
        });

    }
}
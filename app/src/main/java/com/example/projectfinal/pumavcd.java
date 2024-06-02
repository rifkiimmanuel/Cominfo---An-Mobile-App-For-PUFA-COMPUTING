package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pumavcd extends AppCompatActivity {

    Button btnback;
    Button btnlecturer;

    Button btnmember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumavcd);

        btnback = findViewById(R.id.backpumavcd);
        btnlecturer = findViewById(R.id.lecturervcd);
        btnmember = findViewById(R.id.memberVCD);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnlecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumavcd.this, lecturervcd.class);
                startActivity(intent);
            }
        });

        btnmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumavcd.this, pumavcd_member.class);
                startActivity(intent);
            }
        });

    }
}

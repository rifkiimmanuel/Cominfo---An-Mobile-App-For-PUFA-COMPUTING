package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pumais extends AppCompatActivity {

    Button backpumais, memberis, lecturerIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumais);

        backpumais = findViewById(R.id.backpumais);
        memberis = findViewById(R.id.memberIS);
        lecturerIS = findViewById(R.id.lecturerIS);

        backpumais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        memberis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumais.this, pumais_member.class);
                startActivity(intent);
            }
        });

        lecturerIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pumais.this, LecturerIS.class);
                startActivity(intent);
            }
        });
    }
}

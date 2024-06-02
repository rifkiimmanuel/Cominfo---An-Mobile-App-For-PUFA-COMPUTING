package com.example.projectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class register extends AppCompatActivity {

    TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.signupText);

        String text = "Have an account? Log in";

        // Membuat objek SpannableString
        SpannableString spannableString = new SpannableString(text);

        // Menetapkan warna berbeda untuk "Log in"
        int startIndex = text.indexOf(" Log in");
        int endIndex = startIndex + " Log in".length();
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Menetapkan warna berbeda untuk "Have an account"
        int startSecondIndex = text.indexOf("Have an account?");
        int endSecondIndex = startSecondIndex + "Have an account?".length();
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), startSecondIndex, endSecondIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Menetapkan SpannableString ke TextView
        register.setText(spannableString);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // Add intent to navigate to MainActivity
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                }
        });
    }

}
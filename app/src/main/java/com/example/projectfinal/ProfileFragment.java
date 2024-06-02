package com.example.projectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private TextView userEmailTextView;
    private Button logoutButton;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userEmailTextView = view.findViewById(R.id.userEmailTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        auth = FirebaseAuth.getInstance();

        // Check if the user is logged in
        if (auth.getCurrentUser() != null) {
            // User is logged in, show email and logout button
            userEmailTextView.setText(auth.getCurrentUser().getEmail());
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logout the user
                    auth.signOut();
                    // Redirect to login activity or handle as needed
                    // For example, you can use Intent to navigate to login activity
                    Intent intent = new Intent(getActivity(), login.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}

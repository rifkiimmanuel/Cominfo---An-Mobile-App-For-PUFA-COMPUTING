package com.example.projectfinal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class event extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // Declare buttons at the class level
    private Button btnAllEvent;
    private Button btnMyEvent;

    public event() {
        // Required empty public constructor
    }

    public static event newInstance(String param1, String param2) {
        event fragment = new event();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        // Initialize buttons
        btnAllEvent = view.findViewById(R.id.btnAll);
        btnMyEvent = view.findViewById(R.id.btnMy);

        btnAllEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllEventFragment();
                btnAllEvent.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.black));
                btnMyEvent.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
            }
        });

        btnMyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyEventFragment();
                btnAllEvent.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
                btnMyEvent.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.black));
            }
        });

        btnAllEvent.setSelected(true);
        btnMyEvent.setSelected(false);

        return view;
    }

    private void showAllEventFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, allEvent.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

        btnAllEvent.setSelected(true);
        btnMyEvent.setSelected(false);
    }

    private void showMyEventFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, myEvent.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();

        btnAllEvent.setSelected(false);
        btnMyEvent.setSelected(true);
    }
}

package com.example.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class Setting extends Fragment {

    private static final String PREFS_NAME = "settings_prefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize views
        LinearLayout privacyPolicySection = view.findViewById(R.id.privacyPolicySection);
        LinearLayout aboutUsSection = view.findViewById(R.id.aboutUsSection);

        // Set click listeners
        privacyPolicySection.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        aboutUsSection.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
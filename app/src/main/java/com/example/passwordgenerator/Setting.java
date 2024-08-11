package com.example.passwordgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class Setting extends Fragment {

    private static final String PREFS_NAME = "settings_prefs";
    private static final String DARK_MODE_KEY = "dark_mode";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize views
        SwitchCompat switchDarkMode = view.findViewById(R.id.switch_dark_mode);

        // Load saved dark mode state
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        switchDarkMode.setChecked(isDarkMode);

        // Set up listeners
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle dark mode switch
            int currentNightMode = AppCompatDelegate.getDefaultNightMode();
            int newNightMode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;

            if (currentNightMode != newNightMode) {
                AppCompatDelegate.setDefaultNightMode(newNightMode);

                // Save dark mode state
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(DARK_MODE_KEY, isChecked);
                editor.apply();
            }
        });

        return view;
    }
}
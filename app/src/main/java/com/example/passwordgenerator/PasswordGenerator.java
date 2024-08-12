package com.example.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.SecureRandom;

public class PasswordGenerator extends Fragment {

    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

    private SeekBar seekBarLength;
    private TextView textViewLengthValue;
    private Switch switchSmallLetters;
    private Switch switchCapitalLetters;
    private Switch switchNumbers;
    private Switch switchSpecialCharacters;
    private TextView textViewGeneratedPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_generator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        seekBarLength = view.findViewById(R.id.seekBarLength);
        textViewLengthValue = view.findViewById(R.id.textViewLengthValue);
        switchSmallLetters = view.findViewById(R.id.switchSmallLetters);
        switchCapitalLetters = view.findViewById(R.id.switchCapitalLetters);
        switchNumbers = view.findViewById(R.id.switchNumbers);
        switchSpecialCharacters = view.findViewById(R.id.switchSpecialCharacters);
        textViewGeneratedPassword = view.findViewById(R.id.textViewGeneratedPassword);
        Button buttonGenerate = view.findViewById(R.id.buttonGenerate);
        Button buttonCopy = view.findViewById(R.id.buttonCopy);

        // Set up SeekBar listener
        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewLengthValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Generate password on button click
        buttonGenerate.setOnClickListener(v -> {
            int length = seekBarLength.getProgress();
            boolean includeSmallLetters = switchSmallLetters.isChecked();
            boolean includeCapitalLetters = switchCapitalLetters.isChecked();
            boolean includeNumbers = switchNumbers.isChecked();
            boolean includeSpecialChars = switchSpecialCharacters.isChecked();

            if (length > 0) {
                String generatedPassword = generatePassword(length, includeSmallLetters, includeCapitalLetters, includeNumbers, includeSpecialChars);
                textViewGeneratedPassword.setText(generatedPassword);
            } else {
                Toast.makeText(getActivity(), "Please select a valid length", Toast.LENGTH_SHORT).show();
            }
        });

        // Copy password to clipboard
        buttonCopy.setOnClickListener(v -> {
            String password = textViewGeneratedPassword.getText().toString();
            if (!TextUtils.isEmpty(password)) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Generated Password", password);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Password copied to clipboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "No password to copy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generatePassword(int length, boolean includeSmallLetters, boolean includeCapitalLetters, boolean includeNumbers, boolean includeSpecialChars) {
        StringBuilder password = new StringBuilder();
        String characters = "";

        if (includeSmallLetters) {
            characters += LOWER_CASE;
        }
        if (includeCapitalLetters) {
            characters += UPPER_CASE;
        }
        if (includeNumbers) {
            characters += DIGITS;
        }
        if (includeSpecialChars) {
            characters += SPECIAL_CHARS;
        }

        if (characters.isEmpty()) {
            return "No character types selected";
        }

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}

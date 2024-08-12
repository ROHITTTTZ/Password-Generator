package com.example.passwordgenerator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.content.Intent;

public class AddDetails extends Fragment {

    EditText name, username, password, notes;
    Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editTextPassword = view.findViewById(R.id.editTextPassword);

        editTextPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (editTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility_off, 0);
                    } else {
                        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.visibility_icon, 0);
                    }
                    editTextPassword.setSelection(editTextPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        name = view.findViewById(R.id.editTextName);
        username = view.findViewById(R.id.editTextUsername);
        password = view.findViewById(R.id.editTextPassword);
        notes = view.findViewById(R.id.editTextNote);
        save = view.findViewById(R.id.buttonSaveDetails);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Notes = notes.getText().toString();

                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserInput", requireContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String uniqueKey = "data_" + System.currentTimeMillis();
                String value = Name + "|" + Username + "|" + Password + "|" + Notes;
                editor.putString(uniqueKey, value);
                editor.apply();

                name.setText("");
                username.setText("");
                password.setText("");
                notes.setText("");
            }
        });
    }
}
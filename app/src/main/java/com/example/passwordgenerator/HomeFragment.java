package com.example.passwordgenerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewPasswords;
    private PasswordAdapter passwordAdapter;
    private List<Password> passwordList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPasswords = view.findViewById(R.id.recyclerViewPasswords);
        recyclerViewPasswords.setLayoutManager(new LinearLayoutManager(getContext()));

        passwordList = new ArrayList<>();
        // Add sample data
        passwordList.add(new Password("Instagram", "user123", "password123", "Personal account"));
        passwordList.add(new Password("Facebook", "user456", "securepassword", "Work account"));
        passwordList.add(new Password("Twitter", "user789", "mypassword", "Social account"));
        passwordList.add(new Password("LinkedIn", "user101", "linkedinpass", "Professional account"));
        passwordList.add(new Password("Google", "user202", "googlepass", "Personal account"));
        passwordList.add(new Password("GitHub", "user303", "githubpass", "Development account"));
        passwordList.add(new Password("Reddit", "user404", "redditpass", "Community account"));
        passwordList.add(new Password("Amazon", "user505", "amazonpass", "Shopping account"));
        passwordList.add(new Password("Netflix", "user606", "netflixpass", "Entertainment account"));
        passwordAdapter = new PasswordAdapter(passwordList);
        recyclerViewPasswords.setAdapter(passwordAdapter);
    }
}
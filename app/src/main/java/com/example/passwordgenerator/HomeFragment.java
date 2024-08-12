package com.example.passwordgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewPasswords;
    private PasswordAdapter passwordAdapter;
    private List<Password> passwordList;
    private LinearLayout emptyStateLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewPasswords = view.findViewById(R.id.recyclerViewPasswords);
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout);
        recyclerViewPasswords.setLayoutManager(new LinearLayoutManager(getContext()));

        passwordList = new ArrayList<>();
        loadPasswordData();

        passwordAdapter = new PasswordAdapter(passwordList, getContext());
        recyclerViewPasswords.setAdapter(passwordAdapter);

        updateEmptyState();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPasswordData();
        passwordAdapter.notifyDataSetChanged();
        updateEmptyState();
    }

    private void loadPasswordData() {
        passwordList.clear();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserInput", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String value = (String) entry.getValue();
            String[] parts = value.split("\\|"); // Use '|' as delimiter
            if (parts.length == 4) {
                String name = parts[0];
                String username = parts[1];
                String password = parts[2];
                String notes = parts[3];
                passwordList.add(new Password(name, username, password, notes));
            }
        }
    }

    private void updateEmptyState() {
        if (passwordList.isEmpty()) {
            recyclerViewPasswords.setVisibility(View.GONE);
            emptyStateLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerViewPasswords.setVisibility(View.VISIBLE);
            emptyStateLayout.setVisibility(View.GONE);
        }
    }
}
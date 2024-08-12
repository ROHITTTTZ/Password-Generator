package com.example.passwordgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {

    private List<Password> passwordList;
    private Context context;

    public PasswordAdapter(List<Password> passwordList, Context context) {
        this.passwordList = passwordList;
        this.context = context;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        return new PasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password password = passwordList.get(position);
        holder.textViewName.setText(password.getName());
        holder.textViewUsername.setText(password.getUsername());
        holder.textViewPassword.setText(password.getPassword());
        holder.textViewNotes.setText(password.getNotes());

        holder.buttonDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(position);
        });
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.confirmation_dialog, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button buttonCancel = dialogView.findViewById(R.id.buttonDialogCancel);
        Button buttonConfirm = dialogView.findViewById(R.id.buttonDialogConfirm);

        buttonCancel.setOnClickListener(v -> dialog.dismiss());
        buttonConfirm.setOnClickListener(v -> {
            deleteItem(position);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteItem(int position) {
        Password password = passwordList.get(position);

        // Remove item from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInput", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Find and delete the entry
        for (String key : sharedPreferences.getAll().keySet()) {
            String value = sharedPreferences.getString(key, "");
            String[] parts = value.split("\\|");
            if (parts.length == 4 && parts[0].equals(password.getName()) &&
                    parts[1].equals(password.getUsername()) &&
                    parts[2].equals(password.getPassword()) &&
                    parts[3].equals(password.getNotes())) {
                editor.remove(key);
                break;
            }
        }
        editor.apply();

        // Remove item from the list and notify adapter
        passwordList.remove(position);
        notifyItemRemoved(position);
    }

    public static class PasswordViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewUsername;
        TextView textViewPassword;
        TextView textViewNotes;
        ImageButton buttonDelete;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewPassword = itemView.findViewById(R.id.textViewPassword);
            textViewNotes = itemView.findViewById(R.id.textViewNotes);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
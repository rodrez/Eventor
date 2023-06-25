package com.unyth.eventor;


import android.widget.Button;

import androidx.annotation.NonNull;

public class DataItem {
    private String title;
    private String subtitle;
    private String date;
    private final int id;
    private Button editButton;
    private Button deleteButton;
    private EditButtonClickListener editButtonClickListener;
    private DeleteButtonClickListener deleteButtonClickListener;

    public DataItem(int id, String title, String subtitle, String date) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NonNull
    public String toString() {
        return title + "\n" + subtitle + "\n" + date;
    }

    // Set the edit button and its click listener
    public void setEditButton(Button editButton) {
        this.editButton = editButton;
        editButton.setOnClickListener(v -> {
            if (editButtonClickListener != null) {
                editButtonClickListener.onEditButtonClick(this);
            }
        });
    }

    // Set the delete button and its click listener
    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
        deleteButton.setOnClickListener(v -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteButtonClick(this);
            }
        });
    }

    // Set the edit button click listener
    public void setEditButtonClickListener(EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    // Set the delete button click listener
    public void setDeleteButtonClickListener(DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }

    // Interface for edit button click listener
    public interface EditButtonClickListener {
        void onEditButtonClick(DataItem dataItem);
    }

    // Interface for delete button click listener
    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(DataItem dataItem);
    }
}

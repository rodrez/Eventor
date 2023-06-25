package com.unyth.eventor;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unyth.database.DbUtils;

public class EditEventActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText subtitleEditText;
    private EditText dateEditText;
    private Button updateButton;

    DbUtils dbMethods = new DbUtils(this);
    private DataItem eventorEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        titleEditText = findViewById(R.id.titleEditText);
        subtitleEditText = findViewById(R.id.subtitleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        updateButton = findViewById(R.id.updateButton);

        // Get the event ID from the intent
        int eventId = getIntent().getIntExtra("eventId", -1);

        if (eventId == -1) {
            Toast.makeText(this, "Error: Event not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch the event from the database
        eventorEvent = dbMethods.getEventById(eventId);

        if (eventorEvent == null) {
            Toast.makeText(this, "Error: Event not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set the existing event details in the edit text fields
        titleEditText.setText(eventorEvent.getTitle());
        subtitleEditText.setText(eventorEvent.getSubtitle());
        dateEditText.setText(eventorEvent.getDate());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent();
            }
        });
    }

    private void updateEvent() {
        // Get the updated input values from the edit text fields
        String title = titleEditText.getText().toString().trim();
        String subtitle = subtitleEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        if (title.isEmpty() || subtitle.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the event in the database
        eventorEvent.setTitle(title);
        eventorEvent.setSubtitle(subtitle);
        eventorEvent.setDate(date);

        boolean isUpdated = dbMethods.updateEvent(eventorEvent);

        if (isUpdated) {
            Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this, GridActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Failed to update event", Toast.LENGTH_SHORT).show();
        }
    }

    public void onEditItemClick(DataItem dataItem) {
        // Handle edit button click for the given DataItem
        // Open the EditEventActivity or perform any other desired action
        Intent intent = new Intent(this, EditEventActivity.class);
    }
}

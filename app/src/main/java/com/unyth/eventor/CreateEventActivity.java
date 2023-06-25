package com.unyth.eventor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unyth.database.DbUtils;

public class CreateEventActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText subtitleEditText;
    private EditText dateEditText;
    DbUtils dbMethods = new DbUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Find the views by their IDs
        titleEditText = findViewById(R.id.titleEditText);
        subtitleEditText = findViewById(R.id.subtitleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        Button createButton = findViewById(R.id.createButton);

        // Set a click listener for the create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
                Intent intent = new Intent(CreateEventActivity.this, GridActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createEvent() {
        String title = titleEditText.getText().toString().trim();
        String subtitle = subtitleEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        if (title.isEmpty() || subtitle.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long newRowId = dbMethods.createEvent(title, subtitle, date);

        if (newRowId != -1) {
            Toast.makeText(this, "Event created successfully", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another activity or perform other actions
            finish();
        } else {
            Toast.makeText(this, "Failed to create event", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.unyth.eventor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unyth.database.DbUtils;


import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataRecyclerViewAdapter adapter;
    private FloatingActionButton fabAdd;
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    private FloatingActionButton  permissionButton;
    private TextView permissionStatus;


    DbUtils dbMethods = new DbUtils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_screen);

        recyclerView = findViewById(R.id.recyclerView);

        // Create dummy data items
        List<DataItem> dataItems = dbMethods.getAllEvents();

        // Log the data items
        for (DataItem dataItem : dataItems) {
            String log = "Id: " + dataItem.getId() + " ,Title: " + dataItem.getTitle() + " ,Subtitle: " + dataItem.getSubtitle() + " ,Date: " + dataItem.getDate();
            // Writing Contacts to log
            System.out.println(log);
        }

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataRecyclerViewAdapter(dataItems);
        recyclerView.setAdapter(adapter);

        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action to add elements
                Intent intent = new Intent(GridActivity.this, CreateEventActivity.class);
                    startActivity(intent);
            }
        });

        adapter.setEditButtonClickListener(new DataRecyclerViewAdapter.EditButtonClickListener() {
            @Override
            public void onEditButtonClick(int position) {
                // Handle the edit button click event for the item at the given position
                // Retrieve the DataItem object from the dataItems list using the position
                DataItem dataItem = dataItems.get(position);

                // Pass the event ID instead of the entire DataItem object to the EditEventActivity
                Intent intent = new Intent(GridActivity.this, EditEventActivity.class);
                intent.putExtra("eventId", dataItem.getId());
                startActivity(intent);
            }
        });

        adapter.setDeleteButtonClickListener(new DataRecyclerViewAdapter.DeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {
                // Handle the delete button click event for the item at the given position
                // You can access the item using adapter.getItem(position) if needed
                // Perform the desired action here
                String eventName = adapter.getItem(position).getTitle();
                dbMethods.deleteEvent(adapter.getItem(position).getId());
                Toast.makeText(GridActivity.this, eventName + " deleted", Toast.LENGTH_SHORT).show();
                adapter.removeItem(position);

                Intent intent = new Intent(GridActivity.this, GridActivity.class);
                startActivity(intent);

            }
        });



        // Permission Stuff
        permissionButton = findViewById(R.id.permissionButton);

        permissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSmsPermission();
            }
        });

//     Check the permission status and update the UI accordingly
            if (isSmsPermissionGranted()) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }

    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    SMS_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//                permissionStatus.setText("Permission denied");
            }
        }
    }
}

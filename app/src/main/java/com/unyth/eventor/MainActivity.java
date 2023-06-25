package com.unyth.eventor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unyth.database.DbUtils;
import com.unyth.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create instance of DBMethods
        DbUtils dbMethods = new DbUtils(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Check if the user exists
        boolean userExists = dbMethods.checkUsernamePassword(username.toString(), password.toString());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userExists) {
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GridActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, GridActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

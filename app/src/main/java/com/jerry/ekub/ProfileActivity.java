package com.jerry.ekub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI components
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);

        // Get user details from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String email = intent.getStringExtra("EMAIL");
        String phone = intent.getStringExtra("PHONE");
        String address = intent.getStringExtra("ADDRESS");

        // Display user details
        nameTextView.setText("Name: " + name);
        emailTextView.setText("Email: " + email);
        phoneTextView.setText("Phone: " + phone);
        addressTextView.setText("Address: " + address);
    }
}
package com.jerry.ekub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView optionsRecyclerView;
    private OptionAdapter optionAdapter;
    private List<Ekub> allEkubs; // Original list of all Ekubs
    private List<Ekub> filteredEkubs; // Filtered list for display
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        optionsRecyclerView = findViewById(R.id.optionsRecyclerView);
        welcomeMessage = findViewById(R.id.welcomeMessage);

        // Get the username from the intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        welcomeMessage.setText("Welcome, " + username + "!");

        // Set up RecyclerView with GridLayoutManager (2 columns)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        optionsRecyclerView.setLayoutManager(gridLayoutManager);

        // Create a list of Ekubs (you can customize this later)
        allEkubs = new ArrayList<>();
        allEkubs.add(new Ekub("Top Ekub 1", 100, 10, "Top", "2023-12-31"));
        allEkubs.add(new Ekub("Daily Ekub 1", 50, 5, "Daily", "2023-11-15"));
        allEkubs.add(new Ekub("Weekly Ekub 1", 200, 20, "Weekly", "2023-12-01"));
        allEkubs.add(new Ekub("Monthly Ekub 1", 500, 50, "Monthly", "2024-01-01"));

        // Initially, show all Ekubs
        filteredEkubs = new ArrayList<>(allEkubs);

        // Set up the adapter
        optionAdapter = new OptionAdapter(filteredEkubs, this);
        optionsRecyclerView.setAdapter(optionAdapter);

        // Handle Top link click
        Button topLink = findViewById(R.id.topLink);
        topLink.setOnClickListener(v -> filterEkubs("Top"));

        // Handle Daily link click
        Button dailyLink = findViewById(R.id.dailyLink);
        dailyLink.setOnClickListener(v -> filterEkubs("Daily"));

        // Handle Weekly link click
        Button weeklyLink = findViewById(R.id.weeklyLink);
        weeklyLink.setOnClickListener(v -> filterEkubs("Weekly"));

        // Handle Monthly link click
        Button monthlyLink = findViewById(R.id.monthlyLink);
        monthlyLink.setOnClickListener(v -> filterEkubs("Monthly"));

        // Handle Profile link click
        TextView profileLink = findViewById(R.id.profileLink);
        profileLink.setOnClickListener(v -> {
            // Navigate to ProfileActivity and pass user details
            Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            profileIntent.putExtra("NAME", "Asfaw Yemane");
            profileIntent.putExtra("EMAIL", "asfawyemane21@gmail.com");
            profileIntent.putExtra("PHONE", "+0988492462");
            profileIntent.putExtra("ADDRESS", "Hawelti, Mekelle, Ethiopia");
            startActivity(profileIntent);
        });

        // Handle Home button click
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> filterEkubs("All"));

        // Handle Logout button click
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }

    // Method to filter Ekubs based on the selected category
    private void filterEkubs(String category) {
        filteredEkubs.clear();
        for (Ekub ekub : allEkubs) {
            if (category.equals("All") || ekub.getType().equals(category)) {
                filteredEkubs.add(ekub);
            }
        }
        optionAdapter.notifyDataSetChanged(); // Refresh the RecyclerView
    }
}
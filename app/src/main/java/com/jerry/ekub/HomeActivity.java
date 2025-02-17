package com.jerry.ekub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView optionsRecyclerView;
    private OptionAdapter optionAdapter;
    private List<Ekub> allEkubs; // List of all Ekubs
    private TextView welcomeMessage;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

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

        // Initialize list
        allEkubs = new ArrayList<>();

        // Set up the adapter
        optionAdapter = new OptionAdapter(allEkubs, this, this::onEkubClick);
        optionsRecyclerView.setAdapter(optionAdapter);

        // Fetch Ekubs from Firestore
        fetchEkubsFromFirestore();

        // Handle Profile link click
        TextView profileLink = findViewById(R.id.profileLink);
        profileLink.setOnClickListener(v -> {
            String userEmail = intent.getStringExtra("USERNAME");

            // Fetch user data from Firestore
            db.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String firstName = document.getString("firstName");
                                String lastName = document.getString("lastName");
                                String email = document.getString("email");
                                String phone = document.getString("phone");

                                // Navigate to ProfileActivity
                                Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                                profileIntent.putExtra("NAME", firstName);
                                profileIntent.putExtra("LastName", lastName);
                                profileIntent.putExtra("EMAIL", email);
                                profileIntent.putExtra("PHONE", phone);
                                startActivity(profileIntent);
                            }
                        } else {
                            Log.e("FirestoreError", "Error fetching user data", task.getException());
                        }
                    });
        });

        // Handle Logout button click
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }

    // Fetch Ekubs from Firestore (No Filtering)
    private void fetchEkubsFromFirestore() {
        db.collection("Ekubs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allEkubs.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Ekub ekub = document.toObject(Ekub.class);

                                // Manually convert amount if necessary
                                Object amountObj = document.get("amount");
                                if (amountObj instanceof String) {
                                    ekub.setAmount(Integer.parseInt((String) amountObj));
                                } else if (amountObj instanceof Long) {
                                    ekub.setAmount(((Long) amountObj).intValue());
                                }

                                allEkubs.add(ekub);
                            } catch (Exception e) {
                                Log.e("FirestoreError", "Error parsing Ekub data", e);
                            }
                        }
                        optionAdapter.notifyDataSetChanged(); // Refresh RecyclerView
                    }
                });
        // Inside HomeActivity's onCreate method
        Button ekubateyButton = findViewById(R.id.ekubateyButton);
        ekubateyButton.setOnClickListener(v -> {
            Intent ekubateyIntent = new Intent(HomeActivity.this, EkubateyActivity.class);
            startActivity(ekubateyIntent);
        });

    }
        // Handle Ekub item click
        private void onEkubClick(int position) {
            Ekub ekub = allEkubs.get(position);
            Intent detailsIntent = new Intent(HomeActivity.this, EkubDetailsActivity.class);
            detailsIntent.putExtra("EKUB", ekub); // Pass the selected Ekub object
            startActivity(detailsIntent);
        }


    }

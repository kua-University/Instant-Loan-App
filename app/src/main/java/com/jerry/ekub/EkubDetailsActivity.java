package com.jerry.ekub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EkubDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekub_details);

        // Get the selected Ekub from the intent
        Ekub ekub = (Ekub) getIntent().getSerializableExtra("EKUB");

        // Check if the Ekub object is null
        if (ekub == null) {
            Toast.makeText(this, "Ekub data is missing!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if Ekub is null
            return;
        }

        // Initialize UI components
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView amountTextView = findViewById(R.id.amountTextView);
        TextView durationTextView = findViewById(R.id.durationTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        Button applyButton = findViewById(R.id.applyButton);
        Button cancelButton = findViewById(R.id.cancelButton); // New Cancel Button

        // Set Ekub details
        nameTextView.setText(ekub.getName());
        amountTextView.setText("Amount: $" + ekub.getAmount());
        durationTextView.setText("Duration: " + ekub.getDuration() + " days");
        typeTextView.setText("Type: " + ekub.getType());
        dateTextView.setText("Date: " + ekub.getDate());

        applyButton.setOnClickListener(v -> {
            // Perform amortized calculations based on Ekub type
            double stake = ekub.getAmount();
            String type = ekub.getType().toLowerCase(); // Convert to lowercase for case-insensitive comparison
            double agentShare = 0;
            double userShare = 0;
            String calculationDetails = "";

            switch (type) {
                case "daily":
                    agentShare = stake * 30 * (1.0 / 30); // Agent gets 1/30 of the total stake
                    userShare = stake * 30 * (29.0 / 30); // User gets 29/30 of the total stake
                    calculationDetails = String.format("Daily Calculation:\nTotal Stake: $%.2f\nAgent Share: $%.2f\nUser Share: $%.2f", stake * 30, agentShare, userShare);
                    break;
                case "weekly":
                    agentShare = stake * 4 * (1.0 / 4); // Agent gets 1/4 of the total stake
                    userShare = stake * 4 * (3.0 / 4); // User gets 3/4 of the total stake
                    calculationDetails = String.format("Weekly Calculation:\nTotal Stake: $%.2f\nAgent Share: $%.2f\nUser Share: $%.2f", stake * 4, agentShare, userShare);
                    break;
                case "monthly":
                    agentShare = stake * (1.0 / 12); // Agent gets 1/12 of the total stake
                    userShare = stake * (11.0 / 12); // User gets 11/12 of the total stake
                    calculationDetails = String.format("Monthly Calculation:\nTotal Stake: $%.2f\nAgent Share: $%.2f\nUser Share: $%.2f", stake, agentShare, userShare);
                    break;
                default:
                    calculationDetails = "Invalid Ekub type.";
                    break;
            }

            // Show confirmation dialog with calculation details
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Calculation Details");
            builder.setMessage(calculationDetails);
            builder.setPositiveButton("Confirm", (dialog, which) -> {
                // User confirmed, submit the application
                submitApplication(ekub);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // User canceled, do nothing
                dialog.dismiss();
            });
            builder.setNeutralButton("Cancel and Go Home", (dialog, which) -> {
                // User canceled and wants to go home
                goToHomeActivity();
            });
            builder.show();
        });

        // Cancel Button Click Listener
        cancelButton.setOnClickListener(v -> {
            goToHomeActivity();
        });
    }

    private void submitApplication(Ekub ekub) {
        // Create a new EkubApplication object
        EkubApplication application = new EkubApplication();
        application.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        application.setEkubName(ekub.getName());
        application.setAmount(ekub.getAmount());
        application.setType(ekub.getType());

        // Save the application to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("EkubApplications")
                .add(application)
                .addOnSuccessListener(documentReference -> {
                    // Notify the user that the application was successful
                    Toast.makeText(this, "Application submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Toast.makeText(this, "Failed to submit application: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void goToHomeActivity() {
        // Navigate to the home activity
        Intent intent = new Intent(this, HomeActivity.class); // Replace with your home activity
        startActivity(intent);
        finish(); // Close the current activity
    }
}
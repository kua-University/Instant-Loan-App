package com.jerry.ekub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class EkubDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekub_details);

        // Get the selected Ekub from the intent
        Ekub ekub = (Ekub) getIntent().getSerializableExtra("EKUB");

        // Initialize UI components
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView amountTextView = findViewById(R.id.amountTextView);
        TextView durationTextView = findViewById(R.id.durationTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        Button applyButton = findViewById(R.id.applyButton);

        // Set Ekub details
        nameTextView.setText(ekub.getName());
        amountTextView.setText("Amount: $" + ekub.getAmount());
        durationTextView.setText("Duration: " + ekub.getDuration() + " days");
        typeTextView.setText("Type: " + ekub.getType());
        dateTextView.setText("Date: " + ekub.getDate());

        applyButton.setOnClickListener(v -> {
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
        });
    }
}
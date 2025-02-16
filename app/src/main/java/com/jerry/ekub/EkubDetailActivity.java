package com.jerry.ekub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EkubDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekub_detail);

        // Get the Ekub details from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("EKUB_NAME");
        int stake = intent.getIntExtra("EKUB_STAKE", 0);
        int totalQty = intent.getIntExtra("EKUB_TOTAL_QTY", 0);
        String type = intent.getStringExtra("EKUB_TYPE");
        String deadline = intent.getStringExtra("EKUB_DEADLINE");

        // Display the Ekub details
        TextView ekubName = findViewById(R.id.ekubName);
        TextView ekubStake = findViewById(R.id.ekubStake);
        TextView ekubTotalQty = findViewById(R.id.ekubTotalQty);
        TextView ekubType = findViewById(R.id.ekubType);
        TextView ekubDeadline = findViewById(R.id.ekubDeadline);

        ekubName.setText("Name: " + name);
        ekubStake.setText("Stake: " + stake);
        ekubTotalQty.setText("Total Quantity: " + totalQty);
        ekubType.setText("Type: " + type);
        ekubDeadline.setText("Deadline: " + deadline);
    }
}
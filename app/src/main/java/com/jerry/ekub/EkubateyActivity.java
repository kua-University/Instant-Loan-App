package com.jerry.ekub;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class EkubateyActivity extends AppCompatActivity implements EkubateyAdapter.OnEkubClickListener {

    private RecyclerView ekubateyRecyclerView;
    private EkubateyAdapter ekubateyAdapter;
    private List<Ekub> activeEkubs;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekubatey);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ekubateyRecyclerView = findViewById(R.id.ekubateyRecyclerView);
        activeEkubs = new ArrayList<>();

        ekubateyAdapter = new EkubateyAdapter(activeEkubs, this);
        ekubateyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ekubateyRecyclerView.setAdapter(ekubateyAdapter);

        fetchActiveEkubs();
    }

    private void fetchActiveEkubs() {
        db.collection("EkubApplications")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        activeEkubs.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ekub ekub = document.toObject(Ekub.class);
                            Log.d("EkubData", "Ekub Name: " + ekub.getName() + ", Amount: " + ekub.getAmount() + ", Type: " + ekub.getType());
                            activeEkubs.add(ekub);
                        }
                        ekubateyAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("FirestoreError", "Error fetching active Ekubs", task.getException());
                    }
                });
    }

    @Override
    public void onEkubClick(int position) {
        // Handle item click if needed
    }
}

package com.example.gamequest.Teacher.Module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamequest.R;
import com.example.gamequest.Teacher.LearningMaterialsPage;
import com.example.gamequest.databinding.ModuleListPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.gamequest.Utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class ModuleListPage extends AppCompatActivity implements ModuleItemSelectListener {

    ModuleListPageBinding binding;
    List<ModuleItem> moduleList = new ArrayList<>();

    String gradingPeriod = "2nd Grading";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ModuleListPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // This callback will intercept the back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                whenBackIsPressed();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // backbutton onclick
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        // get clicked grading period from previous page
        Intent intent = getIntent();
        gradingPeriod = intent.getStringExtra("gradingPeriod");

        // set page title based on grading period
        binding.pageTitle.setText(gradingPeriod + " Period");


        db.collection("lessons")
                .whereEqualTo("gradingPeriod", gradingPeriod) // Filter by gradingPeriod
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getString("title");
                                String description = document.getString("description");
                                moduleList.add(new ModuleItem(title, description + "..."));
                            }
                            // Notify the adapter
                            binding.recyclerview.setAdapter(new ModuleAdapter(getApplicationContext(), moduleList, this));
                            binding.recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        } else {
                            Toast.makeText(ModuleListPage.this, "No documents found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("FirestoreError", "Error: ", task.getException());
                        Toast.makeText(ModuleListPage.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, LearningMaterialsPage.class));
        finish();
    }

    @Override
    public void onModuleItemSelected(ModuleItem moduleItem) {
        Toast.makeText(this, moduleItem.lessonTitle + moduleItem.lessonDescription, Toast.LENGTH_SHORT).show();
    }
}
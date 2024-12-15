package com.example.gamequest.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamequest.ModuleListPage;
import com.example.gamequest.R;
import com.example.gamequest.Teacher.Module.ModuleAdapter;
import com.example.gamequest.Teacher.Module.ModuleItem;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.StudentAssessmentLogPageBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class StudentAssessmentLogPage extends AppCompatActivity {

    StudentAssessmentLogPageBinding binding;
    String lessonId = ModuleListPage.lessonId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = StudentAssessmentLogPageBinding.inflate(getLayoutInflater());
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

        // get lessons
        db.collection("assessmentLog")
                .whereEqualTo("lessonId", lessonId) // Filter by gradingPeriod
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userName = document.getString("userName");
                                String userScore = document.getString("userScore");
                                Utility.addTableRow(this, binding.tableLayout, userName, userScore);
                            }
                        }
                        else {
                            Toast.makeText(StudentAssessmentLogPage.this, "No documents found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Log.e("FirestoreError", "Error: ", task.getException());
                        Toast.makeText(StudentAssessmentLogPage.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });


//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
//        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(StudentAssessmentLogPage.this, ModuleListPage.class));
        finish();
    }
}
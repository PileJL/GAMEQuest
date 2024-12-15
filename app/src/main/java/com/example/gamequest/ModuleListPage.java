package com.example.gamequest;

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

import com.example.gamequest.Teacher.CreateLessonPage;
import com.example.gamequest.Teacher.Module.ModuleAdapter;
import com.example.gamequest.Teacher.Module.ModuleItem;
import com.example.gamequest.Teacher.Module.ModuleItemSelectListener;
import com.example.gamequest.Teacher.StudentAssessmentLogPage;
import com.example.gamequest.Teacher.TeacherHomePage;
import com.example.gamequest.databinding.CreateLessonPageBinding;
import com.example.gamequest.databinding.ModuleListPageBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.gamequest.Utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class ModuleListPage extends AppCompatActivity implements ModuleItemSelectListener {

    ModuleListPageBinding binding;
    List<ModuleItem> moduleList = new ArrayList<>();

    String gradingPeriod = LearningMaterialsPage.staticGradingPeriod;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String lessonTitle, lessonDescription, lessonId;

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

        // set page title based on grading period
        binding.pageTitle.setText(gradingPeriod + " Period");

        // profile button onclick
        binding.profileButton.setOnClickListener(v -> goToProfilePage());

        // get lessons
        db.collection("lessons")
                .whereEqualTo("gradingPeriod", gradingPeriod) // Filter by gradingPeriod
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getString("title");
                                String description = document.getString("description");
                                String lessonId = document.getId();
                                moduleList.add(new ModuleItem(title, description + "...", lessonId));
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

    private void goToProfilePage() {
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("backPage", ModuleListPage.class.getName());
        Utility.navigateToActivity(this, intent);
        finish();
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, LearningMaterialsPage.class));
        finish();
    }

    @Override
    public void onModuleItemSelected(ModuleItem moduleItem) {
        // set lesson details
        lessonId = moduleItem.getLessonId();
        lessonTitle = moduleItem.getLessonTitle();
        lessonDescription = moduleItem.getLessonDescription();
        // navigate to page accordingly
        if (TeacherHomePage.openStudentAssLog) {
            Utility.navigateToActivity(this, new Intent(this, StudentAssessmentLogPage.class));
        }
        else {
            Utility.navigateToActivity(this, new Intent(this, ModulePage.class));
        }
        finish();
    }
}
package com.example.gamequest.Teacher;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.LearningMaterialsPage;
import com.example.gamequest.R;
import com.example.gamequest.databinding.TeacherHomePageBinding;
import com.example.gamequest.Utilities.Utility;


public class TeacherHomePage extends AppCompatActivity {

    public static String userId;
    TeacherHomePageBinding binding;
    public static boolean openStudentAssLog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = TeacherHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // createLessonBttn onclick
        binding.createLessonBttn.setOnClickListener(v -> {
            Utility.navigateToActivity(TeacherHomePage.this, new Intent(TeacherHomePage.this, CreateLessonPage.class));
            finish();
        });

        // learningMaterialsBttn onclick
        binding.learningMaterialsBttn.setOnClickListener(v -> {
            Utility.navigateToActivity(TeacherHomePage.this, new Intent(TeacherHomePage.this, LearningMaterialsPage.class));
            finish();
        });

        // studAssLogBttn onclick
        binding.studAssLogBttn.setOnClickListener(v -> {
            openStudentAssLog = true;
            Utility.navigateToActivity(TeacherHomePage.this, new Intent(TeacherHomePage.this, LearningMaterialsPage.class));
            finish();
        });

    }
}
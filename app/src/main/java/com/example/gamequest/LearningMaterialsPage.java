package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Teacher.TeacherHomePage;
import com.example.gamequest.databinding.LearningMaterialsPageBinding;
import com.example.gamequest.Utilities.Utility;

public class LearningMaterialsPage extends AppCompatActivity {

    LearningMaterialsPageBinding binding;
    public static String staticGradingPeriod;
    boolean openStudentAssLog = TeacherHomePage.openStudentAssLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = LearningMaterialsPageBinding.inflate(getLayoutInflater());
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

        // backButton onclick
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        // set page title to Assessment Log if so
        if (openStudentAssLog) {
            binding.pageTitle.setText("Assessment Log");
            binding.message.setVisibility(View.GONE);
        }

        // grading periods onclick
        binding.firstGrading.setOnClickListener(v -> gradingPeriodOnClick("1st Grading"));
        binding.secondGrading.setOnClickListener(v -> gradingPeriodOnClick("2nd Grading"));
        binding.thirdGrading.setOnClickListener(v -> gradingPeriodOnClick("3rd Grading"));
        binding.fourthGrading.setOnClickListener(v -> gradingPeriodOnClick("4th Grading"));

    }

    private void gradingPeriodOnClick(String gradingPeriod) {
        staticGradingPeriod = gradingPeriod;
        Utility.navigateToActivity(LearningMaterialsPage.this,
                new Intent(LearningMaterialsPage.this, ModuleListPage.class));
        finish();

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, TeacherHomePage.class));
        finish();
    }
}
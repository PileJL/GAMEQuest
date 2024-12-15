package com.example.gamequest.Student;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.R;
import com.example.gamequest.LearningMaterialsPage;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.StudentHomePageBinding;

public class StudentHomePage extends AppCompatActivity {

    StudentHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = StudentHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // startbutton onclick
        binding.startButton.setOnClickListener(v -> {
            Utility.navigateToActivity(StudentHomePage.this, new Intent(StudentHomePage.this, LearningMaterialsPage.class));
            finish();
        });
    }
}
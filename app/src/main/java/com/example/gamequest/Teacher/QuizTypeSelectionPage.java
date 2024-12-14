package com.example.gamequest.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.R;
import com.example.gamequest.databinding.QuizTypeSelectionPageBinding;
import com.example.gamequest.Utilities.Utility;

public class QuizTypeSelectionPage extends AppCompatActivity {

    QuizTypeSelectionPageBinding binding;
    String lessonTitle, lessonDescription, gradingPeriod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = QuizTypeSelectionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        lessonTitle = intent.getStringExtra("lessonTitle");
        lessonDescription = intent.getStringExtra("lessonDescription");
        gradingPeriod = intent.getStringExtra("gradingPeriod");


        // back button onclick
        binding.backButton.setOnClickListener(v -> {
            Utility.navigateToActivity(this, new Intent(this, CreateLessonPage.class));
            finish();
        });

        // pickAndPlay button onclick
        binding.pickAndPlayBttn.setOnClickListener(v -> {
            Intent intent1 = new Intent(QuizTypeSelectionPage.this, CreateQuizPage.class);
            intent1.putExtra("lessonTitle", lessonTitle);
            intent1.putExtra("lessonDescription", lessonDescription);
            intent1.putExtra("gradingPeriod", gradingPeriod);
            Utility.navigateToActivity(QuizTypeSelectionPage.this, intent1);
            finish();
        });
    }
}
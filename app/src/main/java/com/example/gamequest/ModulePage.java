package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.ModuleListPageBinding;
import com.example.gamequest.databinding.ModulePageBinding;

public class ModulePage extends AppCompatActivity {

    ModulePageBinding binding;

    String lessonTitle = ModuleListPage.lessonTitle;
    String lessonDescription = ModuleListPage.lessonDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ModulePageBinding.inflate(getLayoutInflater());
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

        binding.lessonTitle.setText(lessonTitle);
        binding.lessonDescription.setText(lessonDescription);

        // proceedToQuiz onclick
        binding.proceedToQuizBttn.setOnClickListener(v -> {
            Utility.navigateToActivity(ModulePage.this, new Intent(ModulePage.this, QuizPage.class));
            finish();
        });
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, ModuleListPage.class));
        finish();
    }
}
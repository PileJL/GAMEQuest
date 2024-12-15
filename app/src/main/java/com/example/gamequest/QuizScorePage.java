package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.QuizScorePageBinding;

public class QuizScorePage extends AppCompatActivity {

    QuizScorePageBinding binding;
    public static int userScore = QuizPage.userScore;
    public static int quizTotalItem = QuizPage.quizTotalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = QuizScorePageBinding.inflate(getLayoutInflater());
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

        // back button onclick
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        // set score
        binding.message.setText(String.format("CONGRATS, YOU\nGOT %d/%d!", userScore, quizTotalItem));

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(QuizScorePage.this, LearningMaterialsPage.class));
    }
}
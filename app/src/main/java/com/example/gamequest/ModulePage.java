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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class ModulePage extends AppCompatActivity {

    ModulePageBinding binding;

    String lessonTitle = ModuleListPage.lessonTitle;
    String lessonDescription = ModuleListPage.lessonDescription;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        checkIfAlreadyTakenQuiz();

        // backbutton onclick
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        binding.lessonTitle.setText(lessonTitle);
        binding.lessonDescription.setText(lessonDescription);

        // proceedToQuiz onclick
        binding.proceedToQuizBttn.setOnClickListener(v -> {
            Utility.navigateToActivity(ModulePage.this, new Intent(ModulePage.this, QuizPage.class));
            finish();
        });

        binding.profileButton.setOnClickListener(v -> goToProfilePage());
    }

    private void goToProfilePage() {
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("backPage", ModulePage.class.getName());
        Utility.navigateToActivity(this, intent);
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, ModuleListPage.class));
        finish();
    }
    private void checkIfAlreadyTakenQuiz() {
        // check if username exists
        db.collection("assessmentLog")
                .whereEqualTo("lessonId", ModuleListPage.lessonId)
                .whereEqualTo("userName", SignInPage.userName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // hide proceedToQuiz button if already taken quiz
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            binding.proceedToQuizBttn.setVisibility(View.GONE);
                        }
                        else {
                            binding.proceedToQuizBttn.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        // Handle query errors
                        Toast.makeText(ModulePage.this, "Error checking account", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
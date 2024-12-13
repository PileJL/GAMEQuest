package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.UserSelectionBinding;

public class UserSelection extends AppCompatActivity {

    UserSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = UserSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.studentButton.setOnClickListener(view -> {
            goToLoginPage("student");
        });

        binding.teacherButton.setOnClickListener(view -> {
            goToLoginPage("teacher");
        });
    }

    private void goToLoginPage(String userType) {
        Intent intent = new Intent(this, LoginPage.class);
        intent.putExtra("userType", userType);
        Utility.navigateToActivity(this, intent);
        finish();
    }
}
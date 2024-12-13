package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.databinding.WelcomePageBinding;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.Teacher.TeacherHomePage;

public class WelcomePage extends AppCompatActivity {

    WelcomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = WelcomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String userType = intent.getStringExtra("userType");
        Toast.makeText(this, userType, Toast.LENGTH_SHORT).show();
        // change welcome title based on user type
        changeWelcomeTitle(userType);

        // nextButton onclick
        binding.nextButton.setOnClickListener(v -> goToHomePage(userType));


    }

    private void changeWelcomeTitle(String userType) {
        if (userType.equals("student")) {
            binding.welcomeTitle.setText("Well Done Student!!");
        } else if (userType.equals("teacher")) {
            binding.welcomeTitle.setText("Well Done Teacher!!");
        }
    }

    private void goToHomePage(String userType)  {
        if (userType.equals("teacher")) {
            Utility.navigateToActivity(this, new Intent(this, TeacherHomePage.class));
        }
        else {
            Toast.makeText(this, "Student Home Page", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
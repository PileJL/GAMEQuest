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
import com.example.gamequest.databinding.LoginPageBinding;

public class LoginPage extends AppCompatActivity {

    LoginPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = LoginPageBinding.inflate(getLayoutInflater());
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

        // get selected userType from previous page
        Intent intent = getIntent();
        String userType = intent.getStringExtra("userType");

        // createAccountButton onclick
        binding.createAccountButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginPage.this, SignUpPage.class);
            intent1.putExtra("userType", userType);
            Utility.navigateToActivity(LoginPage.this, intent1);
            finish();
        });

        // loginButton onclick
        binding.loginButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginPage.this, SignInPage.class);
            intent1.putExtra("userType", userType);
            Utility.navigateToActivity(LoginPage.this, intent1);
            finish();
        });

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, UserSelection.class));
        finish();
    }
}
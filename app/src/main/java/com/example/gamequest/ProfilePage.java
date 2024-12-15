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
import com.example.gamequest.databinding.ProfilePageBinding;

public class ProfilePage extends AppCompatActivity {

    ProfilePageBinding binding;
    String userName = SignInPage.userName;
    Class<?> backPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ProfilePageBinding.inflate(getLayoutInflater());
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

        // retrieve the destination Activity
        Intent intent = getIntent();
        try {
            backPage = Class.forName(intent.getStringExtra("backPage"));
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // set profile usrename
        binding.userName.setText(userName);

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(ProfilePage.this, backPage));
        finish();
    }
}
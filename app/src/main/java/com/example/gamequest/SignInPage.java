package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.SignInPageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class SignInPage extends AppCompatActivity {

    SignInPageBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = SignInPageBinding.inflate(getLayoutInflater());
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

        binding.signInButton.setOnClickListener(v -> logIn(userType, binding.userName.getText().toString(), binding.password.getText().toString()));

    }

    private void logIn(String userType, String username, String password) {
        // check if username and password are not empty
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Username or password can't be empty.", Toast.LENGTH_SHORT).show();
        }
        else {
            // check if username exists
            db.collection("users")
                    .whereEqualTo("userName", username)
                    .whereEqualTo("userPassword", password)
                    .whereEqualTo("userType", userType)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // if account is found, go to Welcome Activity
                            if (task.getResult() != null && !task.getResult().isEmpty()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    userID = document.getId();
                                    Intent intent = new Intent(SignInPage.this, WelcomePage.class);
                                    intent.putExtra("userType", userType);
                                    Utility.navigateToActivity(SignInPage.this, intent);
                                    finish();
                                }
                            }
                            else {
                                // else, display a toast message
                                Toast.makeText(SignInPage.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            // Handle query errors
                            Toast.makeText(SignInPage.this, "Error checking account", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, UserSelection.class));
        finish();
    }
}
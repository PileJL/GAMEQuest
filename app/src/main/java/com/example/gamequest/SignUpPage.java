package com.example.gamequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.gamequest.databinding.SignUpPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    SignUpPageBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = SignUpPageBinding.inflate(getLayoutInflater());
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

        // signUpButton onclick
        binding.signUpButton.setOnClickListener(v -> createAccount(userType, binding.userName.getText().toString().trim(),
                binding.password.getText().toString().trim()));

    }

    private void createAccount(String userType, String userName, String password) {
        // check if username and password are not empty
        if (userName.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Username or password can't be empty.", Toast.LENGTH_SHORT).show();
        }
        // check if inputted password is not 8 characters or more
        else if (password.length() < 8) {
            Toast.makeText(this, "Please use a password with minimum of 8 characters.", Toast.LENGTH_SHORT).show();
        }
        else {
            // add user credentials to user object
            user.put("userType", userType);
            user.put("userName", userName);
            user.put("userPassword", password);

            // check if username exists
            db.collection("users")
                    .whereEqualTo("userName", userName)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // if username is already taken
                            if (task.getResult() != null && !task.getResult().isEmpty()) {
                                Toast.makeText(SignUpPage.this, "Username is already taken.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // else, add the account to database
                                db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener(documentReference -> {
                                            // empty out the fields
                                            Utility.emptyFields(new ArrayList<>(Arrays.asList(binding.userName, binding.password)));
                                            Toast.makeText(SignUpPage.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                        else {
                            // Handle query errors
                            Toast.makeText(SignUpPage.this, "Error checking account", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, UserSelection.class));
        finish();
    }
}

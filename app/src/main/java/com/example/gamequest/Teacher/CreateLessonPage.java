package com.example.gamequest.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.R;
import com.example.gamequest.databinding.CreateLessonPageBinding;

public class CreateLessonPage extends AppCompatActivity {

    CreateLessonPageBinding binding;
    String[] dropdownItems = {"1st Grading", "2nd Grading", "3rd Grading", "4th Grading"};
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = CreateLessonPageBinding.inflate(getLayoutInflater());
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
        // initializations
        adapterItems = new ArrayAdapter<>(this, R.layout.dropdown_item, dropdownItems);
        binding.gradingPeriod.setAdapter(adapterItems);
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());

        binding.gradingPeriod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(CreateLessonPage.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void whenBackIsPressed() {
        Utility.navigateToActivity(this, new Intent(this, TeacherHomePage.class));
        finish();
    }
}
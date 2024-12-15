package com.example.gamequest.Teacher;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamequest.R;
import com.example.gamequest.Utilities.Utility;
import com.example.gamequest.databinding.StudentAssessmentLogPageBinding;

public class StudentAssessmentLogPage extends AppCompatActivity {

    StudentAssessmentLogPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = StudentAssessmentLogPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");
        Utility.addTableRow(this, binding.tableLayout, "John Doe", "15");

    }
}
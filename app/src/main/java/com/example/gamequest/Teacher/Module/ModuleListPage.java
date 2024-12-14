package com.example.gamequest.Teacher.Module;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamequest.R;
import com.example.gamequest.databinding.ModuleListPageBinding;

import java.util.ArrayList;
import java.util.List;

public class ModuleListPage extends AppCompatActivity {

    ModuleListPageBinding binding;
    List<ModuleItem> moduleList = new ArrayList<>();

    String gradingPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ModuleListPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // get clicked grading period from previous page
        Intent intent = getIntent();
        gradingPeriod = intent.getStringExtra("gradingPeriod");

        Toast.makeText(this, gradingPeriod, Toast.LENGTH_SHORT).show();

        // add items to module list
        moduleList.add(new ModuleItem("Module 1", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 2", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 3", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 4", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 1", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 2", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 3", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 4", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 1", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 2", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 3", "World of Fantasy and Reality"));
        moduleList.add(new ModuleItem("Module 4", "World of Fantasy and Reality"));




        binding.recyclerview.setAdapter(new ModuleAdapter(getApplicationContext(), moduleList));
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}
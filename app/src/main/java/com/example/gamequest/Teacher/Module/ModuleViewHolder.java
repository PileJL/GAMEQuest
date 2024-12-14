package com.example.gamequest.Teacher.Module;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamequest.R;

public class ModuleViewHolder extends RecyclerView.ViewHolder {

    TextView moduleNumber, lessonTitle;

    public ModuleViewHolder(@NonNull View itemView) {
        super(itemView);

        moduleNumber = itemView.findViewById(R.id.moduleNumber);
        lessonTitle = itemView.findViewById(R.id.lessonTitle);

    }
}

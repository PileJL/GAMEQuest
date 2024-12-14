package com.example.gamequest.Teacher.Module;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamequest.R;

public class ModuleViewHolder extends RecyclerView.ViewHolder {

    TextView lessonTitle, lessonDescription;
    ConstraintLayout constraintLayout;

    public ModuleViewHolder(@NonNull View itemView) {
        super(itemView);

        lessonDescription = itemView.findViewById(R.id.lessonDescription);
        lessonTitle = itemView.findViewById(R.id.lessonTitle);
        constraintLayout = itemView.findViewById(R.id.constraintLayout);

    }
}

package com.example.gamequest.Teacher.Module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamequest.R;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleViewHolder> {

    Context context;
    List<ModuleItem> moduleList;

    public ModuleAdapter(Context context, List<ModuleItem> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.module_list_item, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        holder.moduleNumber.setText(moduleList.get(position).getModuleNumber());
        holder.lessonTitle.setText(moduleList.get(position).getLessonTitle());
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }
}

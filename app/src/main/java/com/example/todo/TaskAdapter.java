package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnMarkCompleteListener markCompleteListener;
    private OnEditListener editListener;
    private OnDeleteListener deleteListener;

    public TaskAdapter(List<Task> tasks, OnMarkCompleteListener markCompleteListener, OnEditListener editListener, OnDeleteListener deleteListener) {
        this.tasks = tasks;
        this.markCompleteListener = markCompleteListener;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskName.setText(task.name);
        holder.taskDeadline.setText(task.deadline);

        holder.markCompleteButton.setOnClickListener(v -> {
            if (markCompleteListener != null) {
                markCompleteListener.onMarkComplete(task);
            }
        });

        holder.editButton.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEdit(task);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDeadline;
        ImageButton markCompleteButton, editButton, deleteButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            taskDeadline = itemView.findViewById(R.id.taskDeadline);
            markCompleteButton = itemView.findViewById(R.id.markCompleteButton);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    // Define interfaces for each action
    public interface OnMarkCompleteListener {
        void onMarkComplete(Task task);
    }

    public interface OnEditListener {
        void onEdit(Task task);
    }

    public interface OnDeleteListener {
        void onDelete(Task task);
    }
}

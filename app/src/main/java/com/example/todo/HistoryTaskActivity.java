// HistoryTaskActivity.java
package com.example.todo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryTaskActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> completedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_task);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.historyRecyclerView);

        // Retrieve only completed tasks from the database
        completedTasks = dbHelper.getAllTasks(true); // true indicates only completed tasks

        // Pass null for listeners we don’t need
        taskAdapter = new TaskAdapter(completedTasks, null, null, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
    }
}

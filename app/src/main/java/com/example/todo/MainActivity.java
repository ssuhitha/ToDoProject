package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText taskNameInput, taskDeadlineInput;
    private FloatingActionButton addTaskFab;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.splashScreen).setVisibility(View.GONE);
        findViewById(R.id.mainContent).setVisibility(View.VISIBLE);


    // Initialize UI components
        taskNameInput = findViewById(R.id.taskNameInput);
        taskDeadlineInput = findViewById(R.id.taskDeadlineInput);
        addTaskFab = findViewById(R.id.addTaskFab);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);
        taskList = new ArrayList<>();

        // Load tasks from database
        loadTasks(false);

        // Setup RecyclerView and adapter
        taskAdapter = new TaskAdapter(taskList,
                this::markTaskAsCompleted,
                this::editTask,
                this::deleteTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Floating Action Button for Adding Task
        addTaskFab.setOnClickListener(v -> {
            String taskName = taskNameInput.getText().toString().trim();
            String deadlineText = taskDeadlineInput.getText().toString().trim();
            if (!taskName.isEmpty() && !deadlineText.isEmpty()) {
                dbHelper.addTask(taskName, deadlineText);
                loadTasks(false);
                taskNameInput.setText("");
                taskDeadlineInput.setText("");
            } else {
                Toast.makeText(this, "Please enter both name and deadline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.historyMenuItem) {
            Intent intent = new Intent(this, HistoryTaskActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTasks(boolean showCompleted) {
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks(showCompleted));
        taskAdapter.notifyDataSetChanged();
    }

    private void markTaskAsCompleted(Task task) {
        dbHelper.markTaskAsCompleted(task.id);
        loadTasks(false);
    }

    private void editTask(Task task) {
        Toast.makeText(this, "Edit functionality not yet implemented.", Toast.LENGTH_SHORT).show();
    }

    private void deleteTask(Task task) {
        dbHelper.deleteTask(task.id);
        loadTasks(false);
    }
}

package com.example.todo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View flashScreen, homeScreen, addTaskScreen, historyScreen;
    private RecyclerView recyclerView, historyRecyclerView;
    private FloatingActionButton addTaskButton;
    private Button historyButton, submitTaskButton;
    private EditText taskName, taskDescription, taskDeadline;

    private List<Task> taskList = new ArrayList<>();
    private List<Task> completedTaskList = new ArrayList<>();
    private TaskAdapter taskAdapter, historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        flashScreen = findViewById(R.id.flashScreen);
        homeScreen = findViewById(R.id.homeScreen);
        addTaskScreen = findViewById(R.id.addTaskScreen);
        historyScreen = findViewById(R.id.historyScreen);

        recyclerView = findViewById(R.id.recyclerView);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        addTaskButton = findViewById(R.id.addTaskButton);
        historyButton = findViewById(R.id.historyButton);
        submitTaskButton = findViewById(R.id.submitTaskButton);

        taskName = findViewById(R.id.taskName);
        taskDescription = findViewById(R.id.taskDescription);
        taskDeadline = findViewById(R.id.taskDeadline);

        // Set up RecyclerView for tasks
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Set up RecyclerView for history
        historyAdapter = new TaskAdapter(completedTaskList);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(historyAdapter);

        // Display flash screen for 2 seconds, then switch to home screen
        new Handler().postDelayed(() -> {
            flashScreen.setVisibility(View.GONE);
            homeScreen.setVisibility(View.VISIBLE);
        }, 2000);

        // Button listeners
        addTaskButton.setOnClickListener(v -> showAddTaskScreen());
        historyButton.setOnClickListener(v -> showHistoryScreen());
        submitTaskButton.setOnClickListener(v -> addTask());
    }

    private void showAddTaskScreen() {
        homeScreen.setVisibility(View.GONE);
        addTaskScreen.setVisibility(View.VISIBLE);
    }

    private void showHistoryScreen() {
        homeScreen.setVisibility(View.GONE);
        historyScreen.setVisibility(View.VISIBLE);
        historyAdapter.notifyDataSetChanged(); // Refresh history view
    }

    private void addTask() {
        String name = taskName.getText().toString().trim();
        String description = taskDescription.getText().toString().trim();
        String deadline = taskDeadline.getText().toString().trim();

        if (!name.isEmpty()) {
            Task newTask = new Task(name, description, deadline);
            taskList.add(newTask);
            taskAdapter.notifyDataSetChanged();

            // Clear fields and return to home screen
            taskName.setText("");
            taskDescription.setText("");
            taskDeadline.setText("");

            addTaskScreen.setVisibility(View.GONE);
            homeScreen.setVisibility(View.VISIBLE);
        }
    }

    // Task model class
    class Task {
        String name, description, deadline;
        boolean isCompleted;

        Task(String name, String description, String deadline) {
            this.name = name;
            this.description = description;
            this.deadline = deadline;
            this.isCompleted = false;
        }
    }
}

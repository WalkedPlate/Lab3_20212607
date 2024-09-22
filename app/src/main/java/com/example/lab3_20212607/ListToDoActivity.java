package com.example.lab3_20212607;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20212607.dto.ToDo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ListToDoActivity extends AppCompatActivity {

    TextView taskLabel;
    Spinner taskSpinner;
    Button updateStatusButton;

    private Bundle loggedUserData;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    private boolean hasTasks;
    private List<ToDo> taskList;

    List<String> taskDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_to_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUserData();

        taskLabel = findViewById(R.id.tareas_label);
        taskLabel.setText("Ver tareas de: " + firstName);

        taskSpinner = findViewById(R.id.spinner);

        for (ToDo task : taskList) {
            String status = task.isCompleted() ? "Completado" : "No Completado";
            taskDescriptions.add(task.getTodo() + " - " + status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, taskDescriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(adapter);

        taskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updateStatusButton = findViewById(R.id.estado_button);

        updateStatusButton.setOnClickListener(view -> {
            int position = taskSpinner.getSelectedItemPosition();
            ToDo selectedTask = taskList.get(position);

            selectedTask.setCompleted(!selectedTask.isCompleted());

            taskDescriptions.set(position, selectedTask.getTodo() + " - " + (selectedTask.isCompleted() ? "Completado" : "No Completado"));
            adapter.notifyDataSetChanged();

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Cambio Exitoso")
                    .setMessage("El estado de la tarea ha sido actualizado")
                    .setPositiveButton("Entendido", null)
                    .show();
        });
    }

    public void loadUserData() {
        Intent intent = getIntent();
        loggedUserData = intent.getExtras();

        userId = loggedUserData.getInt("idUser", 0);
        firstName = loggedUserData.getString("firstName", "Username");
        lastName = loggedUserData.getString("lastName", "LastName");
        email = loggedUserData.getString("email", "Email");
        gender = loggedUserData.getString("gender", "male");

        hasTasks = loggedUserData.getBoolean("hasTasks");
        taskList = (List<ToDo>) loggedUserData.getSerializable("taskList");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_power) {


            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, TimerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu, menu);
        return true;
    }
}
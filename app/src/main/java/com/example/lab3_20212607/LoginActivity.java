package com.example.lab3_20212607;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20212607.dto.LoginRequest;
import com.example.lab3_20212607.dto.LoginResponse;
import com.example.lab3_20212607.dto.ToDo;
import com.example.lab3_20212607.dto.ToDoResponse;
import com.example.lab3_20212607.service.ApiService;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ApiService apiService;
    Bundle datosUsuarioBundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                intentarLogin(username, password);
            }
        });







    }


    private void intentarLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Los campos de usuario y contraseña no pueden estar vacíos.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    datosUsuarioBundle.putString("idUser", loginResponse.getId());
                    datosUsuarioBundle.putString("firstName", loginResponse.getFirstName());
                    datosUsuarioBundle.putString("lastName", loginResponse.getLastName());
                    datosUsuarioBundle.putString("email", loginResponse.getEmail());
                    datosUsuarioBundle.putString("gender", loginResponse.getGender());

                    apiService.getTodosByUserId(Integer.parseInt(loginResponse.getId())).enqueue(new Callback<ToDoResponse>() {
                        @Override
                        public void onResponse(Call<ToDoResponse> call, Response<ToDoResponse> response) {

                            if (response.isSuccessful()) {

                            ToDoResponse toDosResponse = response.body();
                                ArrayList<ToDo> toDos = toDosResponse.getTodos();
                                datosUsuarioBundle.putBoolean("existToDos", true);
                                datosUsuarioBundle.putSerializable("todosList", (Serializable) toDos);


                                loginSuccess(loginResponse);
                            } else {
                                datosUsuarioBundle.putBoolean("existToDos", false);

                                Log.d("NO TO DO", "NO EXISTEN TAREAS");
                            }
                        }

                        @Override
                        public void onFailure(Call<ToDoResponse> call, Throwable t) {
                            Log.e("ERROR", "Error de red: " + t.getMessage());
                        }
                    });


                } else {
                    Toast.makeText(LoginActivity.this, "Fallo al iniciar sesión.", Toast.LENGTH_SHORT).show();
                }
            }

            //Para el fallo de red
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Fallo en la conexión de red.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginSuccess(LoginResponse loginResponse) {
        Toast.makeText(this, "Inicio de sesión correcto" + loginResponse.getFirstName(), Toast.LENGTH_SHORT).show();


        // Luego vamos al timer activity
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtras(datosUsuarioBundle);
        startActivity(intent);
        finish();
    }
}
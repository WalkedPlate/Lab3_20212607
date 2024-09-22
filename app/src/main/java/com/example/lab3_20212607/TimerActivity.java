package com.example.lab3_20212607;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20212607.dto.ToDo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    private Bundle datosUsuarioLogeado;
    private String nombre;
    private String apellido;
    private String correo;
    private String genero;

    private boolean existenToDos;
    private List<ToDo> toDos;
    private int userId;

    CountDownTimer pomodoroCountDownTimer;
    long pomodoroTimer = TimeUnit.MINUTES.toMillis(25);
    CountDownTimer descansoCountDownTimer;
    long descansoTimer = TimeUnit.MINUTES.toMillis(5);

    ImageButton controlButton;
    TextView timerCountdown;
    TextView estadoLabel;
    int timerOn = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        datosUsuarioLogeado = intent.getExtras();

        if (datosUsuarioLogeado != null) {
            getData();
        } else {
            Log.e("Datos Login", "Es null");
        }

        if (datosUsuarioLogeado != null) {


            TextView nombreUsuario = findViewById(R.id.nombre);
            nombreUsuario.setText(nombre + ' ' + apellido);

            TextView correoUsuario = findViewById(R.id.correo);
            correoUsuario.setText(correo);

            ImageView generoUsuario = findViewById(R.id.genero);

            if (genero.equals("male")){
                generoUsuario.setImageResource(R.drawable.ic_man);
            } else if (genero.equals("female")){
                generoUsuario.setImageResource(R.drawable.ic_woman);
            }

        } else {
            Log.e("Error obteniendo datos", "No existen datos.");
        }


        timerCountdown = findViewById(R.id.timerText);

        estadoLabel = findViewById(R.id.estadoLabel);

        controlButton = findViewById(R.id.actionButton);

        controlButton.setOnClickListener(view -> actionButton());



    }


    public void getData() {
        Intent intent = getIntent();
        datosUsuarioLogeado = intent.getExtras();

        userId = datosUsuarioLogeado.getInt("idUser", 0);
        genero = datosUsuarioLogeado.getString("gender", "male");
        nombre = datosUsuarioLogeado.getString("firstName", "Username");
        apellido = datosUsuarioLogeado.getString("lastName", "LastName");
        correo = datosUsuarioLogeado.getString("email", "Email");

        existenToDos = datosUsuarioLogeado.getBoolean("existToDos");
        toDos = (List<ToDo>) datosUsuarioLogeado.getSerializable("toDosList");

    }


    private void actionButton() {

        if (timerOn == 1){
            controlButton.setImageResource(R.drawable.ic_refresh);
        } else if (timerOn == 0) {
            controlButton.setImageResource(R.drawable.ic_play);
        }

        restart();
    }


    private void restart(){

        if (pomodoroCountDownTimer != null){
            pomodoroCountDownTimer.cancel();
        }

        estadoLabel.setText("Descanso: 5:00");
        timerCountdown.setText("25:00");

        pomodoroCountDownTimer = new CountDownTimer(pomodoroTimer, 1000) {
            @Override
            public void onTick(long milisegundosRestantes) {
                int minutos = (int) ((milisegundosRestantes) / 1000 % 3600)/60;
                int segundos = (int) ((milisegundosRestantes) / 1000 % 60);

                String tiempoRestante = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
                timerCountdown.setText(tiempoRestante);
            }

            @Override
            public void onFinish() {
                timerCountdown.setText("00:00");
                new MaterialAlertDialogBuilder(TimerActivity.this)
                        .setTitle("¡Felicidades!")
                        .setMessage("Empezó el tiempo de descanso")
                        .setPositiveButton("Entendido", null)
                        .show();


                descanso();
            }
        }.start();
    }

    private void descanso(){

        if (pomodoroCountDownTimer != null){
            pomodoroCountDownTimer.cancel();
        }

        estadoLabel.setText("En descanso");

        timerCountdown.setText("5:00");
        controlButton.setClickable(false);

        descansoCountDownTimer = new CountDownTimer(descansoTimer, 1000) {
            @Override
            public void onTick(long milisegundosRestantes) {
                int minutos = (int) ((milisegundosRestantes) / 1000 % 3600)/60;
                int segundos = (int) ((milisegundosRestantes) / 1000 % 60);

                String tiempoRestante = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
                timerCountdown.setText(tiempoRestante);
            }

            @Override
            public void onFinish() {
                timerCountdown.setText("00:00");

                if (existenToDos) {

                    controlButton.setClickable(true);

                    Intent intent = new Intent(TimerActivity.this, ListToDoActivity.class);
                    intent.putExtras(datosUsuarioLogeado);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                } else {

                    estadoLabel.setText("Fin del descanso");
                    controlButton.setClickable(true);

                    new MaterialAlertDialogBuilder(TimerActivity.this)
                            .setTitle("Atención")
                            .setMessage("Terminó el tiempo de descanso. Dale al botón de reinicio para comenzar otro ciclo.")
                            .setPositiveButton("Entendido", null)
                            .show();
                }

            }
        }.start();



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_power) {

            Log.d("EXIT", "Session cerrada.");

            if (pomodoroCountDownTimer != null) {
                pomodoroCountDownTimer.cancel();
                pomodoroCountDownTimer = null;  // Libera la referencia
            }
            if (descansoCountDownTimer != null) {
                descansoCountDownTimer.cancel();
                descansoCountDownTimer = null;  // Libera la referencia
            }

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu,menu);
        return true;
    }




}
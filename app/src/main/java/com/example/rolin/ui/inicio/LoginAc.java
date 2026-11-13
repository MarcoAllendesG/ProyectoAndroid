package com.example.rolin.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rolin.R;
import com.example.rolin.SalaAc;
import com.example.rolin.data.DatabaseHelper;
/**
 * Actividad inicial de la aplicación encargada de gestionar el inicio de sesión
 * y el registro de nuevos usuarios en la plataforma.
 *
 * @author MarcoAllendesG
 */
public class LoginAc extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        // Vinculamos las vistas del XML
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        setupListeners();
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (validateInputs(email, password)) {
                    boolean isValid = dbHelper.checkUserLogin(email, password);
                    if (isValid) {
                        Toast.makeText(LoginAc.this, "¡Bienvenido a My DnD Pal!", Toast.LENGTH_SHORT).show();
                        goToMainMenu();
                    } else {
                        Toast.makeText(LoginAc.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (validateInputs(email, password)) {
                    boolean isRegistered = dbHelper.registerUser(email, password);
                    if (isRegistered) {
                        Toast.makeText(LoginAc.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        goToMainMenu();
                    } else {
                        Toast.makeText(LoginAc.this, "Error: El correo ya está registrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    /**
     * Valida que los campos de correo y contraseña no se encuentren vacíos.
     *
     * @param email Cadena con el correo ingresado.
     * @param password Cadena con la contraseña ingresada.
     * @return true si ambos campos son válidos, false en caso contrario.
     */
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Redirige al usuario autenticado hacia la pantalla de selección de rol (SalaAc),
     * transfiriendo su correo electrónico mediante un Intent.
     */
    private void goToMainMenu() {
        Intent intent = new Intent(LoginAc.this, SalaAc.class);
        startActivity(intent);
        finish();
    }
}
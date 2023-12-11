package com.example.rental;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private TextView blm;
    private Button loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        blm = findViewById(R.id.blm);
        loginButton = findViewById(R.id.loginButton);
        blm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gantilah logika ini dengan autentikasi yang sesuai
                // Di sini, kita hanya melakukan pengujian sederhana
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidLogin(username, password)) {
                    // Simpan informasi peran pengguna ke SharedPreferences
                    saveUserRole(username);

                    // Pindah ke halaman utama sesuai peran
                    navigateToHome();
                } else {
                    // Tampilkan pesan kesalahan jika login tidak valid
                    // ...
                }
            }
        });
    }

    private boolean isValidLogin(String username, String password) {
        // Logika autentikasi sederhana (ganti dengan yang sesuai)
        return username.equals("admin") && password.equals("admin") ||
                username.equals("user") && password.equals("user");
    }

    private void saveUserRole(String username) {
        // Simpan informasi peran pengguna ke SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (username.equals("admin")) {
            editor.putString("user_role", "admin");
        } else if (username.equals("user")) {
            editor.putString("user_role", "user");
        }

        editor.apply();
    }

    private void navigateToHome() {
        // Pindah ke halaman utama sesuai peran pengguna
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userRole = preferences.getString("user_role", "");

        Intent intent;

        if (userRole.equals("admin")) {
            intent = new Intent(LoginActivity.this, MainActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, UserHomeActivity.class);
        }

        startActivity(intent);
        finish();
    }
}

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

public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, roleEditText;
    private TextView sdh;
    private Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        roleEditText = findViewById(R.id.roleEditText);
        sdh = findViewById(R.id.sdh);
        registerButton = findViewById(R.id.registerButton);

        sdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String role = roleEditText.getText().toString();

                // Simpan informasi pengguna ke SharedPreferences
                saveUserInformation(username, password, role);

                // Pindah ke halaman utama sesuai peran pengguna
                navigateToHome(role);
            }
        });
    }

    private void saveUserInformation(String username, String password, String role) {
        // Simpan informasi pengguna ke SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("user_role", role);

        editor.apply();
    }

    private void navigateToHome(String role) {
        // Pindah ke halaman utama sesuai peran pengguna
        Intent intent;

        if (role.equals("admin")) {
            intent = new Intent(RegistrationActivity.this, MainActivity.class);
        } else {
            intent = new Intent(RegistrationActivity.this, UserHomeActivity.class);
        }

        startActivity(intent);
        finish();
    }
}

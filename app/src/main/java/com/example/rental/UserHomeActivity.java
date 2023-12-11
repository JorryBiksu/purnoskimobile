package com.example.rental;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {
    private Button back;
    private List<Car> cars;
    private CarAdapter carAdapter;
    private DBHelper dbHelper;
    private RecyclerView recyclerView; // Pindahkan deklarasi recyclerView ke sini

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tambahkan OnItemTouchListener setelah inisialisasi RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Handle item click
                onCarItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // Handle item long click
            }
        }));

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView); // Inisialisasi recyclerView di sini

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Handle item click
                onCarItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // Handle item long click
            }
        }));

        dbHelper = new DBHelper(this);
        cars = new ArrayList<>();
        carAdapter = new CarAdapter(cars, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(carAdapter);
        // Load mobil dari database
        loadCars();
    }

    private void onCarItemClick(int position) {
        // Ambil data mobil yang diklik
        Car selectedCar = cars.get(position);

        // Isi formulir dengan data mobil yang diklik
        fillFormWithCarData(selectedCar);
    }

    private void fillFormWithCarData(Car car) {
        // Ambil elemen-elemen formulir
        EditText brandEditText = findViewById(R.id.brandEditText);
        EditText modelEditText = findViewById(R.id.modelEditText);
        EditText yearEditText = findViewById(R.id.yearEditText);

        // Isi formulir dengan data mobil yang diklik
        brandEditText.setText(car.getBrand());
        modelEditText.setText(car.getModel());
        yearEditText.setText(String.valueOf(car.getYear())); // Konversi int ke String
    }

    private void loadCars() {
        cars.clear();
        cars.addAll(dbHelper.getAllCars());
        carAdapter.notifyDataSetChanged();
    }
}

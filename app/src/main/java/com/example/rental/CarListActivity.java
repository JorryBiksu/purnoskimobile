package com.example.rental;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> cars;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        recyclerView = findViewById(R.id.carListRecyclerView);

        dbHelper = new DBHelper(this);
        cars = dbHelper.getAllCars();

        carAdapter = new CarAdapter(cars, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(carAdapter);

        // Tambahkan listener untuk menghapus mobil ketika diklik dua kali
        carAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle single click (if needed)
            }

            @Override
            public void onItemDoubleClick(int position) {
                // Handle double click
                deleteCar(position);
            }
        });
    }

    private void deleteCar(int position) {
        Car carToDelete = cars.get(position);
        dbHelper.deleteCar(carToDelete.getId()); // Hapus mobil dari database
        cars.remove(position); // Hapus mobil dari daftar
        carAdapter.notifyItemRemoved(position); // Perbarui RecyclerView
    }
}

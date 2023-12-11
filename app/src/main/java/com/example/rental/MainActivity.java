package com.example.rental;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText brandEditText, modelEditText, yearEditText, imageUrlEditText;
    private Button addButton, chooseImageButton,back;
    private CarAdapter carAdapter;
    private List<Car> cars;
    private DBHelper dbHelper;
    private ImageView carImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brandEditText = findViewById(R.id.brandEditText);
        modelEditText = findViewById(R.id.modelEditText);
        yearEditText = findViewById(R.id.yearEditText);
        imageUrlEditText = findViewById(R.id.imageUrlEditText);
        addButton = findViewById(R.id.addButton);
        chooseImageButton = findViewById(R.id.chooseImageButton);
        carImageView = findViewById(R.id.carImageView);
        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




        Button viewCarsButton = findViewById(R.id.viewCarsButton);
        viewCarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(this);
        cars = new ArrayList<>();
        carAdapter = new CarAdapter(cars, this);

        loadCars();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar();
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }


    private void loadCars() {
        cars.clear();
        cars.addAll(dbHelper.getAllCars());
        carAdapter.notifyDataSetChanged();
    }

    private void addCar() {
        String brand = brandEditText.getText().toString();
        String model = modelEditText.getText().toString();
        String yearStr = yearEditText.getText().toString(); // Ambil nilai tahun sebagai String
        String imageUrl = imageUrlEditText.getText().toString();

        if (brand.isEmpty() || model.isEmpty() || yearStr.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ubah String tahun ke int
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid year format", Toast.LENGTH_SHORT).show();
            return;
        }

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year); // Gunakan nilai tahun yang sudah diubah menjadi int
        car.setImageUrl(imageUrl);

        dbHelper.addCar(car);
        loadCars();
        clearInputFields();
    }


    private void clearInputFields() {
        brandEditText.setText("");
        modelEditText.setText("");
        yearEditText.setText("");
        imageUrlEditText.setText("");
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Picasso.get().load(imageUri).into(carImageView);
            imageUrlEditText.setText(imageUri.toString());
        }
    }
}

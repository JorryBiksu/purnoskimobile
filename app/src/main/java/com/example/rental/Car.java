package com.example.rental;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;  // Ubah tipe data year menjadi int
    private String imageUrl;  // path gambar mobil

    // Konstruktor
    public Car() {
    }

    public Car(String brand, String model, int year, String imageUrl) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

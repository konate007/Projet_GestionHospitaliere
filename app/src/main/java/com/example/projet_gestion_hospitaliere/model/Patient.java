package com.example.projet_gestion_hospitaliere.model;

public class Patient {
    private long id;
    private String name;
    private String age;
    private String phone;
    private String address ;
    private String image;

    public Patient() {
    }

    public Patient(String name, String age, String phone, String address, String image) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

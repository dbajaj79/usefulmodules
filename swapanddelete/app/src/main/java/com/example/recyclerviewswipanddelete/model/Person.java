package com.example.recyclerviewswipanddelete.model;

public class Person {
    String name;
    int age;
    String phone,email;
    String imageurl;


    public Person(String name, int age, String phone, String email, String imageurl) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getImageurl() {
        return imageurl;
    }
}

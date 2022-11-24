package com.lifestyleapp

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User constructor(fullName: String, age: Int, city: String, country: String, height: Double, weight: Double, gender: Int, profilePhotoPath: String?, profilePhotoSize: Int?, steps: Int?, sedentary:Boolean,pounds:Double)
{

    @PrimaryKey
    @NonNull
    var fullName: String;  // fullName is the primary key for the user table, can never be null
    var age: Int;
    var city: String;
    var country: String;
    var height: Double;
    var weight: Double;
    var gender: Int;      // 1 male, 0 female;
    var profilePhotoPath: String?;
    var profilePhotoSize: Int?;
    var steps: Int?;
    var sedentary: Boolean;
    var pounds:Double;

    init {
        this.fullName = fullName;
        this.age = age;
        this.city = city;
        this.country = country;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.profilePhotoPath = profilePhotoPath;
        this.profilePhotoSize = profilePhotoSize;
        this.sedentary= sedentary;
        this.steps = steps;
        this.pounds = pounds;
    }

}
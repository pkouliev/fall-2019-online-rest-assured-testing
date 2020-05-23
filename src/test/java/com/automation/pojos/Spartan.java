package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * This class represents spartan POJO
 * Eample of JSON response:
 * {
 * "id": 393,                   -> private int id;
 * "name": "Michael Scott",     -> provate String name;
 * "gender": "Male",            -> private String gender;
 * "phone": 6969696969          -> @SerializedName("phone") private long phoneNumber;
 * }
 * SerializedName - an annotation that indicates this member should be serialized to JSON with
 * *  the provided name value as its field name.
 * For POJO constructors are not required, we create them for our convenience to create objects
 */

public class Spartan {
    private int id; // id will not be included in request, because we can't set id anyways, auto-generated
    private String name;
    private String gender;
    @SerializedName("phone")
    private long phoneNumber;
    // instance variables are properties to describe object

    // create constructor for creating object, constructors can overload, to set values to instance variables
    public Spartan(String name, String gender, long phoneNumber) { // id will be automatically assigned by server
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Spartan(int id, String name, String gender, long phoneNumber) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Spartan() { // no arguments constructor

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() { // will be overridden
        return "Spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spartan spartan = (Spartan) o;
        return id == spartan.id;  // && // if id is same, we look into same objects
//                phoneNumber == spartan.phoneNumber &&
//                Objects.equals(name, spartan.name) &&
//                Objects.equals(gender, spartan.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, phoneNumber);
    }
}

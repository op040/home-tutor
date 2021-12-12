package com.codebrust.hometutionnepal.Model;

import androidx.annotation.StringDef;

import java.io.Serializable;

public class Student implements Serializable,User{
    String FullName;
    String Email;
    String Password;
    String PhoneNumber;
    String Usertype;

    public Student(){}

    public Student(String fullName, String email, String phoneNumber) {
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        Usertype = "student";
    }

    public Student(String fullName, String email, String phoneNumber, @UserType String usertype) {
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        Usertype = usertype;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(@UserType String usertype) {
        Usertype = usertype;
    }
}

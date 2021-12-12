package com.codebrust.hometutionnepal.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Teacher implements User, Serializable{
    String FullName;
    String Email;
    String PhoneNumber;
    String Usertype;
    String Bio;
    String Gender;
    String Address;
    Integer YearofBirth;
    String Education;
    String Experience;
    String TeachingMethod;
    String LocationPreference;
    List<String> Levels;
    List<String> Subjects;

    public Teacher(){}

    public Teacher(String fullName, String email, String phoneNumber, String bio, String gender, String address, String yearofBirth, String education, String experience, String teachingMethod, String locationPreference, List<String> levels, List<String> subjects) {
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        Bio = bio;
        Gender = gender;
        Address = address;
        YearofBirth = Integer.valueOf(yearofBirth);
        Education = education;
        Experience = experience;
        TeachingMethod = teachingMethod;
        LocationPreference = locationPreference;
        Levels = levels;
        Subjects = subjects;
        Usertype = UserType.TEACHER;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String usertype) {
        Usertype = usertype;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getYearofBirth() {
        return YearofBirth;
    }

    public void setYearofBirth(Integer yearofBirth) {
        YearofBirth = yearofBirth;
    }

    public Integer getAge(){
        return Calendar.getInstance().get(Calendar.YEAR)-YearofBirth;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getTeachingMethod() {
        return TeachingMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        TeachingMethod = teachingMethod;
    }

    public String getLocationPreference() {
        return LocationPreference;
    }

    public void setLocationPreference(String locationPreference) {
        LocationPreference = locationPreference;
    }

    public List<String> getLevels() {
        return Levels;
    }

    public void setLevels(List<String> levels) {
        Levels = levels;
    }

    public List<String> getSubjects() {
        return Subjects;
    }

    public void setSubjects(List<String> subjects) {
        Subjects = subjects;
    }

    public String getSubjectIteach(){
        StringBuilder builder = new StringBuilder();
        for (String s :
                getSubjects()) {
            if(getSubjects().size()>1&&getSubjects().indexOf(s)==getSubjects().size()-1)
                builder.append("and ");
            builder.append(s.substring(0,1).toUpperCase()+s.substring(1)+" ");
        }
        return builder.toString();
    }

    public String getTeaches(){
        StringBuilder builder = new StringBuilder();
        builder.append("Teaches ");
        for (String s : getSubjects()) {
            if(getSubjects().size()>1&&getSubjects().indexOf(s)==getSubjects().size()-1)
                builder.append("and ");
            builder.append(s.substring(0,1).toUpperCase() +s.substring(1)+ " ");
        }
        if(getTeachingMethod().equals("Online"))
            builder.append("Online");
        return builder.toString();
    }
}

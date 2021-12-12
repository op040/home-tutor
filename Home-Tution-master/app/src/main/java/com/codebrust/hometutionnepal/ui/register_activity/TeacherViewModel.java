package com.codebrust.hometutionnepal.ui.register_activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TeacherViewModel extends ViewModel {

    private MutableLiveData<String> Name;
    private MutableLiveData<String> Email;
    private MutableLiveData<String> PhoneNumber;
    private MutableLiveData<String> Password;
    private MutableLiveData<String> Bio;
    private MutableLiveData<String> Gender;
    private MutableLiveData<String> Address;
    private MutableLiveData<String> YearofBirth;
    private MutableLiveData<String> Education;
    private MutableLiveData<String> Experience;
    private MutableLiveData<String> TeachingMethod;
    private MutableLiveData<String> Preference;
    private MutableLiveData<List<String>> Levels;
    private MutableLiveData<List<String>> Subjects;

    public TeacherViewModel() {
        Name = new MutableLiveData<>();
        Email = new MutableLiveData<>();
        PhoneNumber = new MutableLiveData<>();
        Password = new MutableLiveData<>();
        Bio = new MutableLiveData<>();
        Gender = new MutableLiveData<>();
        Address = new MutableLiveData<>();
        YearofBirth = new MutableLiveData<>();
        Education = new MutableLiveData<>();
        Experience = new MutableLiveData<>();
        TeachingMethod = new MutableLiveData<>();
        Preference = new MutableLiveData<>();
        Levels = new MutableLiveData<>();
        Subjects = new MutableLiveData<>();
        Gender.setValue("Male");
        Education.setValue("Secondary Level");
        Experience.setValue("None");
        TeachingMethod.setValue("Online");
        Preference.setValue("Student's Home");
        Levels.setValue(new ArrayList<>());
        Subjects.setValue(new ArrayList<>());

    }

    public void setName(String name) {
        Name.setValue(name);
    }

    public LiveData<String> getName() {
        return Name;
    }

    public LiveData<String> getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email.setValue(email);
    }

    public LiveData<String> getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber.setValue(phoneNumber);
    }

    public LiveData<String> getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password.setValue(password);
    }

    public LiveData<String> getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio.setValue(bio);
    }

    public LiveData<String> getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender.setValue(gender);
    }

    public LiveData<String> getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address.setValue(address);
    }

    public LiveData<String> getYearofBirth() {
        return YearofBirth;
    }

    public void setYearofBirth(String yearofBirth) {
        YearofBirth.setValue(yearofBirth);
    }

    public LiveData<String> getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education.setValue(education);
    }

    public LiveData<String> getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience.setValue(experience);
    }

    public LiveData<String> getTeachingMethod() {
        return TeachingMethod;
    }

    public void setTeachingMethod(String teachingMethod) {
        TeachingMethod.setValue(teachingMethod);
    }

    public LiveData<String> getPreference() {
        return Preference;
    }

    public void setPreference(String preference) {
        Preference.setValue(preference);
    }

    public LiveData<List<String>> getLevels() {
        return Levels;
    }

    public void setLevels(List<String> levels) {
        Levels.setValue(levels);
    }

    public LiveData<List<String>> getSubjects() {
        return Subjects;
    }

    public void setSubjects(List<String> subjects) {
        Subjects.setValue(subjects);
    }
}

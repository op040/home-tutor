package com.codebrust.hometutionnepal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codebrust.hometutionnepal.Model.Student;
import com.codebrust.hometutionnepal.Model.Teacher;
import com.codebrust.hometutionnepal.Model.User;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel{
    private MutableLiveData<User> user;
    private MutableLiveData<List<Teacher>> searchTeachers;
    private MutableLiveData<List<Teacher>> myteachers;


    public MainViewModel(){
        user = new MutableLiveData<>();
        searchTeachers = new MutableLiveData<>();
        myteachers = new MutableLiveData<>();
        user.setValue(null);
        searchTeachers.setValue(new ArrayList<>());
        myteachers.setValue(new ArrayList<>());
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user){
        this.user.setValue(user);
    }

    public LiveData<List<Teacher>> getSearchTeachers() {
        return searchTeachers;
    }

    public void setSearchTeachers(List<Teacher> searchTeachers) {
        this.searchTeachers.setValue(searchTeachers);
    }

    public LiveData<List<Teacher>> getMyteachers() {
        return myteachers;
    }

    public void setMyteachers(List<Teacher> myteachers) {
        this.myteachers.setValue(myteachers);
    }
}

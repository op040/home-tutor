package com.codebrust.hometutionnepal.ui.register_activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.codebrust.hometutionnepal.R;

public class RegisterActivity extends AppCompatActivity {
    TeacherViewModel teacherViewModel;
    private static final String TAG = "Register Activity";
    private NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);
        Toolbar toolbar = findViewById(R.id.register_toolbar);
        teacherViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(
                ).build();
        navController = Navigation.findNavController(
                this,
                R.id.register_nav_host_fragment
        );
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(!navController.navigateUp())
            onBackPressed();
        return true;
    }
}

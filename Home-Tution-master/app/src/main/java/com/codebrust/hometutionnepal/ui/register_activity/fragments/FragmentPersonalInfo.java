package com.codebrust.hometutionnepal.ui.register_activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.R;
import com.codebrust.hometutionnepal.ui.register_activity.TeacherViewModel;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.Arrays;
import java.util.List;

public class FragmentPersonalInfo extends Fragment {
    private static final String TAG = "FragmentPersonalProfile";
    TeacherViewModel teacherViewModel;
    private NavController navController;

    EditText Bio;
    Spinner SpinnerGender;
    EditText Address;
    EditText YearofBirth;
    Spinner SpinnerEducation;
    Spinner SpinnerExperience;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        teacherViewModel = new ViewModelProvider(requireActivity()).get(TeacherViewModel.class);
        View view =  getLayoutInflater().inflate(R.layout.fragment_personal_info, container, false);
        Bio = view.findViewById(R.id.txt_profile_bio);
        SpinnerGender = view.findViewById(R.id.spinner_gender);
        Address = view.findViewById(R.id.txt_profile_address);
        YearofBirth = view.findViewById(R.id.txt_register_yearofbirth);
        SpinnerEducation = view.findViewById(R.id.spinner_education);
        SpinnerExperience = view.findViewById(R.id.spinner_experience);
        teacherViewModel.getGender().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                List<String> gender = Arrays.asList(context.getResources().getStringArray(R.array.genders));
                SpinnerGender.setSelection(gender.indexOf(s));
            }
        });
        teacherViewModel.getEducation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                List<String> education = Arrays.asList(context.getResources().getStringArray(R.array.education));
                SpinnerEducation.setSelection(education.indexOf(s));
            }
        });
        teacherViewModel.getExperience().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                List<String> experience = Arrays.asList(context.getResources().getStringArray(R.array.teachingexperience));
                SpinnerExperience.setSelection(experience.indexOf(s));
            }
        });
        teacherViewModel.getBio().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Bio.setText(s);
            }
        });
        teacherViewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Address.setText(s);
            }
        });
        teacherViewModel.getYearofBirth().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                YearofBirth.setText(s);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        SpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherViewModel.setGender(SpinnerGender.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherViewModel.setEducation(SpinnerEducation.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherViewModel.setExperience(SpinnerExperience.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validateinputs()) {
                            teacherViewModel.setBio(Bio.getText().toString());
                            teacherViewModel.setAddress(Address.getText().toString());
                            teacherViewModel.setYearofBirth(YearofBirth.getText().toString());
                            navController.navigate(R.id.nav_register_basic_to_preference);
                        }
                    }
                });
        view.findViewById(R.id.btn_previous)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        teacherViewModel.setBio(Bio.getText().toString());
                        teacherViewModel.setAddress(Address.getText().toString());
                        teacherViewModel.setYearofBirth(YearofBirth.getText().toString());
                        navController.popBackStack();
                    }
                });
    }
    private boolean validateinputs() {
        Boolean value = false;
        if(Bio.getText().toString().isEmpty()){
            Bio.setError("Please Enter Your Bio");
            Bio.requestFocus();
        }else if(Address.getText().toString().isEmpty()){
            Address.setError("Please Enter you Address");
            Address.requestFocus();
        }else if(YearofBirth.getText().toString().isEmpty()) {
            YearofBirth.setError("Please Enter Your Year of Birth");
            YearofBirth.requestFocus();
        }else {
            return true;
        }
        return value;
    }

}

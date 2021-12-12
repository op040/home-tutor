package com.codebrust.hometutionnepal.ui.register_activity.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.Model.Teacher;
import com.codebrust.hometutionnepal.R;
import com.codebrust.hometutionnepal.ui.register_activity.TeacherViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentTeachingPreference extends Fragment {
    private final static String TAG = "FragmentPreference";

    private NavController navController;
    private TeacherViewModel teacherViewModel;
    Context context;
    private EditText SubjectText;
    private ChipGroup LevelsGroup;
    private ChipGroup SubjectsGroup;
    private Spinner SpinnerMethod;
    private Spinner SpinnerPreference;

    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        teacherViewModel = new ViewModelProvider(requireActivity()).get(TeacherViewModel.class);
        View view =  getLayoutInflater().inflate(R.layout.fragment_teaching_preference, container, false);
        SubjectText = view.findViewById(R.id.txtsubject_input);
        LevelsGroup = view.findViewById(R.id.chips_group_levels);
        SubjectsGroup = view.findViewById(R.id.chip_group_subjects);
        SpinnerMethod = view.findViewById(R.id.spinner_teaching_method);
        SpinnerPreference = view.findViewById(R.id.spinner_teaching_perference);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        CompoundButton.OnCheckedChangeListener checklistener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<String> levels = new ArrayList<>();
                for(int id:LevelsGroup.getCheckedChipIds()){
                    Chip chip = LevelsGroup.findViewById(id);
                    levels.add(chip.getText().toString());
                }
                teacherViewModel.setLevels(levels);
            }
        };
        for(int i=0;i<LevelsGroup.getChildCount();i++){
            Chip chip = (Chip) LevelsGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(checklistener);
        }
        teacherViewModel.getLevels().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                for(int i=0;i<LevelsGroup.getChildCount();i++){
                    Chip chip = (Chip) LevelsGroup.getChildAt(i);
                    chip.setOnCheckedChangeListener(checklistener);
                    for(String s:strings){
                        if(chip.getText().toString().equals(s)){
                            chip.setChecked(true);
                        }
                    }
                }
            }
        });
        teacherViewModel.getSubjects().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                SubjectsGroup.removeViews(0,SubjectsGroup.getChildCount()-1);
                for(String s:strings){
                    addChips(s);
                }
            }
        });
        teacherViewModel.getTeachingMethod().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                List<String> methods = Arrays.asList(context.getResources().getStringArray(R.array.teachingmethods));
                SpinnerMethod.setSelection(methods.indexOf(s));
            }
        });
        teacherViewModel.getPreference().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                List<String> prefs = Arrays.asList(context.getResources().getStringArray(R.array.placepreference));
                SpinnerPreference.setSelection(prefs.indexOf(s));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        SubjectText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    if(SubjectText.length()>2){
                        List<String> subjects = teacherViewModel.getSubjects().getValue();
                        subjects.add(SubjectText.getText().toString());
                        SubjectText.setText("");
                        teacherViewModel.setSubjects(subjects);
                    } else {
                        Functions.dialouge(context,"Insufficient Character","Name of Subject must be at least 3 characters");
                    }
                }
                return false;
            }
        });
        SpinnerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherViewModel.setTeachingMethod(SpinnerMethod.getSelectedItem().toString());
                if(SpinnerMethod.getSelectedItem().toString().equals("Online")){
                    SpinnerPreference.setEnabled(false);
                    SpinnerPreference.setSelection(0);
                }else {
                    SpinnerPreference.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerPreference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherViewModel.setPreference(SpinnerPreference.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        LevelsGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                String level = chip.getText().toString();
                Log.d(TAG,level);

            }
        });
//        LevelsGroup.
        view.findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(teacherViewModel.getSubjects().getValue().size()<1){
                            Functions.dialouge(context,"Required","Please enter subjects");
                            SubjectText.requestFocus();
                        }else if(teacherViewModel.getLevels().getValue().size()<1){
                            Functions.dialouge(context,"Required","Please selects levles");
                        }else {
                            mAuth.createUserWithEmailAndPassword(teacherViewModel.getEmail().getValue(),teacherViewModel.getPassword().getValue())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(context!=null){
                                                if(task.isSuccessful()){
                                                    Log.d(TAG,"onSuccess:User is Registered");
                                                    String userID = mAuth.getCurrentUser().getUid();
                                                    DocumentReference documentReference = firestore.collection("users")
                                                            .document(userID);
                                                    final Teacher teacher = new Teacher(
                                                            teacherViewModel.getName().getValue(),
                                                            teacherViewModel.getEmail().getValue(),
                                                            teacherViewModel.getPhoneNumber().getValue(),
                                                            teacherViewModel.getBio().getValue(),
                                                            teacherViewModel.getGender().getValue(),
                                                            teacherViewModel.getAddress().getValue(),
                                                            teacherViewModel.getYearofBirth().getValue(),
                                                            teacherViewModel.getEducation().getValue(),
                                                            teacherViewModel.getExperience().getValue(),
                                                            teacherViewModel.getTeachingMethod().getValue(),
                                                            teacherViewModel.getPreference().getValue(),
                                                            teacherViewModel.getLevels().getValue(),
                                                            teacherViewModel.getSubjects().getValue()
                                                    );
                                                    documentReference.set(teacher).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            if(context!=null){
                                                                Log.d(TAG,"onSuccess: User Profile is Created");
//                                                                mainViewModel.setUser(student);
                                                                Functions.cancel_loader();
                                                                Functions.dialouge(context,"Success","You are Registered");
                                                                Intent i = new Intent();
                                                                i.putExtra("user",teacher);
                                                                getActivity().setResult(Activity.RESULT_OK,i);
                                                                getActivity().finish();
                                                            }

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d(TAG,"onFailure" + e.toString());
                                                            Functions.dialouge(context,"Failure",e.getMessage());
                                                        }
                                                    });
                                                }else {
                                                    Functions.dialouge(context,"Failure",task.getException().getMessage());
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
        view.findViewById(R.id.btn_previous)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navController.popBackStack();
                    }
                });
    }

    void addChips(String text){
        Chip chip = new Chip(getContext());
        ChipDrawable drawable = ChipDrawable
                .createFromAttributes(
                        context,
                        null,
                        0,
                        R.style.Widget_MaterialComponents_Chip_Entry
                );
        chip.setChipDrawable(drawable);
        chip.setText(text);
        chip.setCheckable(false);
        SubjectsGroup.addView((View) chip, SubjectsGroup.getChildCount()-1);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubjectsGroup.removeView((View)chip);
            }
        });
    }
}

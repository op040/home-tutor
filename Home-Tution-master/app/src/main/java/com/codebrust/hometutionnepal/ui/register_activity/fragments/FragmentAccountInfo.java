package com.codebrust.hometutionnepal.ui.register_activity.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.R;
import com.codebrust.hometutionnepal.ui.register_activity.TeacherViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import org.w3c.dom.Text;

public class FragmentAccountInfo extends Fragment {
    private static final String TAG = "FragmentAccountInfo";
    Context context;
    TeacherViewModel teacherViewModel;
    EditText txtFullName;
    EditText txtEmail;
    EditText txtPhone;
    EditText txtPassword;
    View btnNext;

    NavController navController;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        mAuth = FirebaseAuth.getInstance();
        teacherViewModel = new ViewModelProvider(requireActivity()).get(TeacherViewModel.class);
        View view =  getLayoutInflater().inflate(R.layout.fragment_account_info, container, false);
        txtFullName = view.findViewById(R.id.txt_account_fullname);
        txtEmail = view.findViewById(R.id.txt_account_email);
        txtPhone = view.findViewById(R.id.txt_account_phone);
        txtPassword = view.findViewById(R.id.txt_account_password);
        btnNext = view.findViewById(R.id.btn_next);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ImageView eye = view.findViewById(R.id.hide_btn);
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getTransformationMethod()==null){
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    eye.setImageResource(R.drawable.ic_hide);
                }else {
                    txtPassword.setTransformationMethod(null);
                    eye.setImageResource(R.drawable.ic_show);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherViewModel.setName(txtFullName.getText().toString());
                if(validateinputs()){
                    Functions.show_loader(context,false,false);
                    mAuth.fetchSignInMethodsForEmail(txtEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    Functions.cancel_loader();
                                    if(task.isSuccessful()) {
                                        if (context != null) {
                                            if (task.getResult().getSignInMethods().size() == 1) {
                                                txtEmail.requestFocus();
                                                Functions.dialouge(context, "Sorry", "This email address is already registered");
                                            } else {
                                                teacherViewModel.setName(txtFullName.getText().toString());
                                                teacherViewModel.setEmail(txtEmail.getText().toString());
                                                teacherViewModel.setPhoneNumber(txtPhone.getText().toString());
                                                teacherViewModel.setPassword(txtPassword.getText().toString());
                                                navController.navigate(R.id.nav_register_accout_to_basic);
                                            }
                                        }
                                    }else {
                                        Functions.dialouge(context,"Error",task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validateinputs() {
        if(txtFullName.getText().toString().isEmpty()){
            txtFullName.setError("Please Enter your Name");
            txtFullName.requestFocus();
        }else if(!Functions.isValidEmail(txtEmail.getText().toString())){
            Log.d(TAG,txtEmail.getText().toString());
            txtEmail.setError("Please Enter a Valid Email");
            txtEmail.requestFocus();
        }else if(txtPhone.length()<10){
            txtPhone.setError("Please Enter a Valid Phone Number");
            txtPhone.requestFocus();
        }else if(txtPassword.length()<5){
            txtPassword.setError("Password is too Weak!");
            txtPassword.requestFocus();
        }else {
            return true;
        }
        return false;
    }
}

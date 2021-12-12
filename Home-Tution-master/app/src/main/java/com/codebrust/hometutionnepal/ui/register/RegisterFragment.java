package com.codebrust.hometutionnepal.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.MainViewModel;
import com.codebrust.hometutionnepal.Model.Student;
import com.codebrust.hometutionnepal.R;
import com.codebrust.hometutionnepal.ui.register_activity.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterFragment extends Fragment {
    private static final String TAG = "Register Fragment";
    private static final int REGISTERREQUESTCODE = 1;

    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtFullName;
    private EditText txtPhoneNumber;

    private Button btnRegister;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    Context context;

    NavController navController;
    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        return getLayoutInflater().inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        btnRegister = view.findViewById(R.id.btn_register);
        txtFullName = view.findViewById(R.id.txt_register_fullname);
        txtEmail = view.findViewById(R.id.txt_register_email);
        txtPassword = view.findViewById(R.id.txt_register_password);
        txtPhoneNumber = view.findViewById(R.id.txt_register_phone);
        ImageView hide = view.findViewById(R.id.hide_btn);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getTransformationMethod()==null){
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    hide.setImageResource(R.drawable.ic_hide);
                }else {
                    txtPassword.setTransformationMethod(null);
                    hide.setImageResource(R.drawable.ic_show);
                }
            }
        });
        View linkRegister = view.findViewById(R.id.link_register_as_teacher);
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, RegisterActivity.class),REGISTERREQUESTCODE);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinputs()){
                    String email = txtEmail.getText().toString();
                    String password = txtPassword.getText().toString();
                    String name = txtFullName.getText().toString();
                    String number = txtPhoneNumber.getText().toString();
                    Functions.show_loader(context,false,false);
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(context!=null){
                                        if(task.isSuccessful()){
                                            Log.d(TAG,"onSuccess:User is Registered");
                                            String userID = mAuth.getCurrentUser().getUid();
                                            DocumentReference documentReference = firestore.collection("users")
                                                    .document(userID);
                                            final Student student = new Student(
                                                    name,
                                                    email,
                                                    number
                                            );
                                            documentReference.set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    if(context!=null){
                                                        Log.d(TAG,"onSuccess: User Profile is Created");
                                                        mainViewModel.setUser(student);
                                                        Functions.cancel_loader();
                                                        Functions.dialouge(context,"Success","You are Registered");
                                                        navController.popBackStack();
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
    }

    private boolean validateinputs() {
        Boolean value = false;
        if(txtFullName.getText().toString().isEmpty()){
            txtFullName.setError("Please Enter your Name");
            txtFullName.requestFocus();
            return  false;
        }
        if(Functions.isValidEmail(txtEmail.getText().toString())){
            if(txtPassword.length()>5){
                value = true;
            }else {
                txtPassword.setError("Password is too Weak!");
                txtPassword.requestFocus();
            }
        }else {
            txtEmail.setError("Please Enter a Valid Email");
            txtEmail.requestFocus();
        };
        return value;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==getActivity().RESULT_OK){
                navController.popBackStack();
            }
        }
    }
}

package com.codebrust.hometutionnepal.ui.forgetpassword;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordFragment extends Fragment {
    EditText txtEmail;

    Context context;
    FirebaseAuth mAuth;
    NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        mAuth = FirebaseAuth.getInstance();
        return getLayoutInflater().inflate(R.layout.fragment_forget_password,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        txtEmail = view.findViewById(R.id.txt_reset_email);
        Button btnReset = view.findViewById(R.id.btn_reset_password);
        ProgressBar progressBar = view.findViewById(R.id.reset_progress);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinputs()){
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(txtEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(context!=null){
                                        progressBar.setVisibility(View.GONE);
                                        if(task.isSuccessful()){
                                            Functions.dialouge(context,"Success","Password reset link is sent to your email. Check your mail");
                                        }else {
                                            Functions.dialouge(context,"Failure",task.getException().getMessage());

                                        }
                                        navController.popBackStack();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validateinputs() {
        Boolean value = false;
        if(Functions.isValidEmail(txtEmail.getText().toString())){
            value = true;
        }else {
            txtEmail.setError("Please Enter a Valid Email");
        };
        return value;
    }
}

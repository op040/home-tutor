package com.codebrust.hometutionnepal.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {
    final String TAG = "Fragment ChangePassword";

    private Context context;
    private EditText txtPassword;
    private EditText txtNewPassword;
    private EditText txtNewPasswordRepeat;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = getLayoutInflater().inflate(R.layout.fragment_change_password,container,false);
        txtPassword = view.findViewById(R.id.et_password);
        txtNewPassword = view.findViewById(R.id.et_new_password);
        txtNewPasswordRepeat = view.findViewById(R.id.et_confirm_password);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG,user.getEmail());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View btnChangePass = view.findViewById(R.id.btn_change_pass);
        ImageView passwordHideView = view.findViewById(R.id.iv_hide_password);
        ImageView npasswordHideView = view.findViewById(R.id.iv_hide_new_pass);
        passwordHideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show();
                if(txtPassword.getTransformationMethod()==null){
                    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    passwordHideView.setImageResource(R.drawable.ic_hide);
                }else {
                    txtPassword.setTransformationMethod(null);
                    passwordHideView.setImageResource(R.drawable.ic_show);
                }
            }
        });
        npasswordHideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNewPassword.getTransformationMethod()==null){
                    txtNewPassword.setTransformationMethod(new PasswordTransformationMethod());
                    txtNewPasswordRepeat.setTransformationMethod(new PasswordTransformationMethod());
                    npasswordHideView.setImageResource(R.drawable.ic_hide);
                }else {
                    txtNewPassword.setTransformationMethod(null);
                    txtNewPasswordRepeat.setTransformationMethod(null);
                    npasswordHideView.setImageResource(R.drawable.ic_show);
                }
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateinputs()){
                    String password = txtPassword.getText().toString();
                    String newPassword = txtNewPassword.getText().toString();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), password);
                    Functions.show_loader(context,false,false);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Functions.cancel_loader();
                                                if(context!=null){
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Password updated");
                                                        Functions.dialouge(context,"Success","Password Updated");
                                                    } else {
                                                        Log.d(TAG, "Error password not updated");
                                                        Functions.dialouge(context,"Error","Password Not Updated");
                                                    }
                                                }
                                            }
                                        });
                                    } else {
                                        if(context!=null){
                                            Functions.cancel_loader();
                                            Functions.dialouge(context,"Error","Error Authentication Failed");
                                        }
                                        Log.d(TAG, "Error auth failed");

                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean validateinputs() {
        if(txtPassword.getText().toString().isEmpty()){
            txtPassword.setError("Please Enter Your Password");
            txtPassword.requestFocus();
            return false;
        }
        if (txtNewPassword.getText().toString().isEmpty()){
            txtNewPassword.setError("Please Enter new Password");
            return false;
        }
        if(txtNewPassword.length()<6){
            txtNewPassword.setError("Password it too Weak!");
            return false;
        }
        if(!txtNewPassword.getText().toString().equals(txtNewPasswordRepeat.getText().toString())){
            txtNewPasswordRepeat.setError("Password do not Match");
            return false;
        }
        return true;
    }
}

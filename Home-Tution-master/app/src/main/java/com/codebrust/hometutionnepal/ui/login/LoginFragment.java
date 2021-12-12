package com.codebrust.hometutionnepal.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {
    private static final String TAG = "Login Fragment";
    private EditText txtEmail;
    private EditText txtPassword;
    FirebaseAuth mAuth;
    Context context;

    NavController navController;
    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        context = getContext();
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        return getLayoutInflater().inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtEmail = view.findViewById(R.id.et_email);
        txtPassword = view.findViewById(R.id.et_password);
        ImageView hide = view.findViewById(R.id.iv_hide);
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
        navController = Navigation.findNavController(view);
        if(mAuth.getCurrentUser()!=null){
            navController.popBackStack();
            navController.navigate(R.id.navigation_profile);
        }

        View linkForget = view.findViewById(R.id.link_forget_password);
        linkForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_forget_password);
            }
        });
        View linkRegister = view.findViewById(R.id.link_register);
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_register);
            }
        });

        View btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if(validateinputs()){
                    Functions.show_loader(context,false,false);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                        DocumentReference document = firestore.collection("users")
                                                .document(mAuth.getCurrentUser().getUid());
                                        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    Functions.cancel_loader();
                                                    Student student = new Student(
                                                            task.getResult().getString("fullName"),
                                                            task.getResult().getString("email"),
                                                            task.getResult().getString("phoneNumber")
                                                    );
                                                    mainViewModel.setUser(student);
                                                    navController.navigate(R.id.navigation_profile);

                                                }else {
                                                    Functions.cancel_loader();
                                                    Log.w(TAG, "FetchData:FAilure", task.getException());
                                                    Functions.dialouge(context,"FAILURE",task.getException().getMessage());
                                                }

                                            }
                                        });
                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "createUserWithEmail:success");
//                                    updateUI(user);
                                    } else {
                                        Functions.cancel_loader();
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Functions.dialouge(context,"FAILURE",task.getException().getMessage());

//                                    updateUI(null);
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
}

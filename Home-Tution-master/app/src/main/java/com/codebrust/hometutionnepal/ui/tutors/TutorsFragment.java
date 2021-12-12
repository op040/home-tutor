package com.codebrust.hometutionnepal.ui.tutors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.R;
import com.google.firebase.auth.FirebaseAuth;

public class TutorsFragment extends Fragment {
    FirebaseAuth mAuth;
    private Context context;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        context = getContext();
        return getLayoutInflater().inflate(R.layout.fragment_tutors,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(mAuth.getCurrentUser()==null){
            Functions.dialouge(context,"Please Login", "You are not Logged in");
            navController.popBackStack();
            navController.navigate(R.id.navigation_login);
        }

    }
}

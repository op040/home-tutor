package com.codebrust.hometutionnepal.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codebrust.hometutionnepal.Functions;
import com.codebrust.hometutionnepal.MainViewModel;
import com.codebrust.hometutionnepal.Model.Student;
import com.codebrust.hometutionnepal.Model.User;
import com.codebrust.hometutionnepal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private NavController navController;
    TextView Name;
    TextView Email;
    TextView PhoneNumber;
    private Context context;
    private FirebaseFirestore firestore;
    private DocumentReference document;
    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View view = getLayoutInflater().inflate(R.layout.fragment_profile,container,false);
        Name = view.findViewById(R.id.txt_profile_name);
        Email = view.findViewById(R.id.txt_profile_email);
        PhoneNumber = view.findViewById(R.id.txt_profile_phone_no);
        mainViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Student student = (Student) user;
                if(student !=null){
                    Name.setText(student.getFullName());
                    Email.setText(student.getEmail());
                    PhoneNumber.setText(student.getPhoneNumber());
                }else {
                    Functions.show_loader(context,false,false);
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    DocumentReference document = firestore.collection("users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                }
            }
        });
        firestore = FirebaseFirestore.getInstance();
        document = firestore.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        View btnLogout = view.findViewById(R.id.view_profile_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                navController.popBackStack();
                navController.navigate(R.id.navigation_login);
            }
        });
        View btnChangePassword = view.findViewById(R.id.view_profile_change_password);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_change_password);
            }
        });
    }
}

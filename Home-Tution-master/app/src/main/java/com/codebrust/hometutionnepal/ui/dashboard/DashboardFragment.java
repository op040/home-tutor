package com.codebrust.hometutionnepal.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebrust.hometutionnepal.MainViewModel;
import com.codebrust.hometutionnepal.Model.Teacher;
import com.codebrust.hometutionnepal.R;
import com.codebrust.hometutionnepal.TeacherDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    MainViewModel mainViewModel;
    Context context;
    private RecyclerView recyclerview;
    FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        db = FirebaseFirestore.getInstance();
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerview = view.findViewById(R.id.dashboardlistview);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        mainViewModel.getSearchTeachers().observe(getViewLifecycleOwner(), new Observer<List<Teacher>>() {
            @Override
            public void onChanged(List<Teacher> teachers) {
                recyclerview.setAdapter(new TeacherItemAdapter(context,teachers));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mainViewModel.getSearchTeachers().getValue().size()==0){
            db.collection("users")
                    .whereEqualTo("usertype","teacher")
                    .limit(10)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Teacher> teachers = new ArrayList<>();
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                for(DocumentSnapshot document:documents){
                                    teachers.add(document.toObject(Teacher.class));
                                }
                                mainViewModel.setSearchTeachers(teachers);
                            }
                        }
                    });
        }
    }

    private class TeacherItemAdapter extends RecyclerView.Adapter<ViewHolderTeacherItem> {
        Context context;
        List<Teacher> teachers;

        public TeacherItemAdapter(Context context, List<Teacher> teachers) {
            this.context = context;
            this.teachers = teachers;
        }

        @NonNull
        @Override
        public ViewHolderTeacherItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.viewholder_teacher_item,parent,false);
            return new ViewHolderTeacherItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderTeacherItem holder, int position) {
            holder.Name.setText(teachers.get(position).getFullName());
            holder.Gender.setText(teachers.get(position).getGender());
            holder.Age.setText(teachers.get(position).getAge().toString());
            holder.Education.setText(teachers.get(position).getEducation());
            holder.Experience.setText(teachers.get(position).getExperience());
            holder.Teaches.setText(teachers.get(position).getTeaches());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, TeacherDetailsActivity.class).putExtra("data",teachers.get(position)));
                }
            });

        }

        @Override
        public int getItemCount() {
            return teachers.size();
        }
    }

    private class ViewHolderTeacherItem extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Gender;
        TextView Age;
        TextView Education;
        TextView Experience;
        TextView Teaches;
        public ViewHolderTeacherItem(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.fullname);
            Gender = itemView.findViewById(R.id.gender);
            Age = itemView.findViewById(R.id.age);
            Education = itemView.findViewById(R.id.tutor_view_degree);
            Experience = itemView.findViewById(R.id.tutor_view_experience);
            Teaches = itemView.findViewById(R.id.tutor_teaches);
        }
    }
}
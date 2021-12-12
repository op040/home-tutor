package com.codebrust.hometutionnepal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codebrust.hometutionnepal.Model.Teacher;

public class TeacherDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView FullName;
    private TextView Gender;
    private TextView Age;
    private TextView Aboutme;
    private TextView Subjects;
    private TextView Contact;
    private TextView Email;
    private TextView Education;
    private TextView Experience;
    private TextView Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        Teacher teacher = (Teacher) getIntent().getExtras().getSerializable("data");
        toolbar = findViewById(R.id.tutor_toolbar);
        setSupportActionBar(toolbar);
        setTitle(teacher.getFullName());
        FullName = (TextView)findViewById(R.id.fullname);
        Gender = findViewById(R.id.gender);
        Age = findViewById(R.id.age);
        Aboutme = findViewById(R.id.aboutme);
        Subjects = findViewById(R.id.subject);
        Contact = findViewById(R.id.contact);
        Email = findViewById(R.id.email);
        Education = findViewById(R.id.education);
        Experience = findViewById(R.id.experience);
        Address = findViewById(R.id.address);

        FullName.setText(teacher.getFullName());
        Gender.setText(teacher.getGender());
        Age.setText(teacher.getAge().toString());
        Aboutme.setText(teacher.getBio());
        Subjects.setText(teacher.getSubjectIteach());
        Contact.setText(teacher.getPhoneNumber());
        Email.setText(teacher.getEmail());
        Education.setText(teacher.getEducation());
        Experience.setText(teacher.getExperience());
        Address.setText(teacher.getAddress());
    }
}
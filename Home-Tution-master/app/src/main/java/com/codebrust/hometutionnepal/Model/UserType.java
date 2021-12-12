package com.codebrust.hometutionnepal.Model;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.codebrust.hometutionnepal.Model.UserType.STUDENT;
import static com.codebrust.hometutionnepal.Model.UserType.TEACHER;

@StringDef({STUDENT, TEACHER})
@Retention(RetentionPolicy.SOURCE)
public @interface UserType {
    public static final String STUDENT = "student";
    public static final String TEACHER = "teacher";
}

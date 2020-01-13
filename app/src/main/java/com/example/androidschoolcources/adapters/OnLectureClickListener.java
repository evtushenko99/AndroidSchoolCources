package com.example.androidschoolcources.adapters;

import androidx.annotation.NonNull;

import com.example.androidschoolcources.models.Lecture;

public interface OnLectureClickListener {
    void onItemClick(@NonNull Lecture lecture);
}

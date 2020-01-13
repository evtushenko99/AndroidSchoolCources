package com.example.androidschoolcources.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidschoolcources.R;
import com.example.androidschoolcources.adapters.DetailsFragmentAdapter;
import com.example.androidschoolcources.models.Lecture;

public class DetailsFragment extends Fragment {
    private static final String ARG_LECTURE = "ARG_LECTURE";

    public static Fragment newInstance(@NonNull Lecture lecture) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LECTURE, lecture);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_lecture_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Lecture lecture = getLectureFromArgs();
        ((TextView) view.findViewById(R.id.number)).setText(String.valueOf(lecture.getNumber()));
        ((TextView) view.findViewById(R.id.date)).setText(lecture.getDate());
        ((TextView) view.findViewById(R.id.theme)).setText(lecture.getTheme());
        ((TextView) view.findViewById(R.id.lector)).setText(lecture.getLector());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new DetailsFragmentAdapter(lecture.getSubtopics()));

    }

    @NonNull
    private Lecture getLectureFromArgs() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("Arguments must be set");
        }
        Lecture lecture = arguments.getParcelable(ARG_LECTURE);
        if (lecture == null) {
            throw new IllegalStateException("Lecture must be set");
        }
        return lecture;
    }
}

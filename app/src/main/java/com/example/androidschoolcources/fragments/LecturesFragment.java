package com.example.androidschoolcources.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidschoolcources.R;
import com.example.androidschoolcources.adapters.DisplayModeSpinnerAdapter;
import com.example.androidschoolcources.adapters.LectorSpinnerAdapter;
import com.example.androidschoolcources.adapters.LecturesAdapter;
import com.example.androidschoolcources.adapters.OnLectureClickListener;
import com.example.androidschoolcources.dataprovider.LearningProgramProvider;
import com.example.androidschoolcources.models.DisplayMode;
import com.example.androidschoolcources.models.Lecture;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LecturesFragment extends Fragment {
    private static final int POSITION_ALL = 0;
    private LearningProgramProvider mLearningProgramProvider = new LearningProgramProvider();
    private LecturesAdapter mLecturesAdapter;

    private View mLoadingView;
    private RecyclerView mRecyclerView;
    private Spinner mLectorsSpinner;
    private Spinner mDisplayModeSpinner;
    private OnLectureClickListener mOnLectureClickListener = new OnLectureClickListener() {
        @Override
        public void onItemClick(@NonNull Lecture lecture) {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(lecture))
                    .addToBackStack(DetailsFragment.class.getSimpleName())
                    .commit();
        }
    };

    public static Fragment newInstance() {
        return new LecturesFragment();
    }

    {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lectures_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingView = view.findViewById(R.id.loading_view);
        mRecyclerView = view.findViewById(R.id.learning_program_recycler);
        mLectorsSpinner = view.findViewById(R.id.lectors_spinner);
        mDisplayModeSpinner = view.findViewById(R.id.display_mode_spinner);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Lecture> lectures = mLearningProgramProvider.provideLectures();
        if (lectures == null) {
            new LoadLecturesTask(this, savedInstanceState == null).execute();
        } else {
            initRecyclerView(savedInstanceState == null, lectures);
            initLectorsSpinner();
            initDisplayModeSpinner();
        }
    }

    private void initRecyclerView(boolean isFirstCreate, @NonNull List<Lecture> lectures) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mLecturesAdapter = new LecturesAdapter(getResources());
        mLecturesAdapter.setLectures(lectures);
        mLecturesAdapter.setClickListener(mOnLectureClickListener);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mLecturesAdapter);
        if (isFirstCreate) {
            Lecture nextLecture = mLearningProgramProvider.getLectureNextTo(lectures, new Date());
            int positionOfNextLecture = mLecturesAdapter.getPositionOf(nextLecture);
            if (positionOfNextLecture != -1) {
                mRecyclerView.scrollToPosition(positionOfNextLecture);
            }
        }
    }

    private void initLectorsSpinner() {

        final List<String> spinnerItems = mLearningProgramProvider.providerLectors();
        Collections.sort(spinnerItems);
        spinnerItems.add(POSITION_ALL, getResources().getString(R.string.all));
        LectorSpinnerAdapter adapter = new LectorSpinnerAdapter(spinnerItems);
        mLectorsSpinner.setAdapter(adapter);

        mLectorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final List<Lecture> lectures = position == POSITION_ALL ?
                        mLearningProgramProvider.provideLectures() :
                        mLearningProgramProvider.filterBy(spinnerItems.get(position));
                mLecturesAdapter.setLectures(lectures);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initDisplayModeSpinner() {

        mDisplayModeSpinner.setAdapter(new DisplayModeSpinnerAdapter());
        mDisplayModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DisplayMode selectedDisplayMode = DisplayMode.values()[position];
                mLecturesAdapter.setDisplayMode(selectedDisplayMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private static class LoadLecturesTask extends AsyncTask<Void, Void, List<Lecture>> {
        private final WeakReference<LecturesFragment> mFragmentRef;
        private final LearningProgramProvider mProvider;
        private final boolean mIsFirstCreate;

        private LoadLecturesTask(@NonNull LecturesFragment fragment, boolean isFirstCreate) {
            mFragmentRef = new WeakReference<>(fragment);
            mProvider = fragment.mLearningProgramProvider;
            mIsFirstCreate = isFirstCreate;
        }

        @Override
        protected void onPreExecute() {
            LecturesFragment fragment = mFragmentRef.get();
            if (fragment != null) {
                fragment.mLoadingView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Lecture> doInBackground(Void... arg) {
            return mProvider.loadLecturesFromWeb();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            LecturesFragment fragment = mFragmentRef.get();
            if (fragment == null) {
                return;
            }
            fragment.mLoadingView.setVisibility(View.GONE);
            if (lectures == null) {
                Toast.makeText(fragment.requireContext(), R.string.failed_to_load_lectures, Toast.LENGTH_SHORT).show();
            } else {
                fragment.initRecyclerView(mIsFirstCreate, lectures);
                fragment.initLectorsSpinner();
                fragment.initDisplayModeSpinner();
            }
        }

    }

}

package com.example.androidschoolcources.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Модель лекции
 *
 * @author Evgeny Chumak
 **/
public class Lecture implements Parcelable {
    private static final int LECTURES_PER_WEEK = 3;

    private final int mNumber;
    private final String mDate;
    private final String mTheme;
    private final String mLector;
    private final int weekIndex;

    private final List<String> mSubtopics;

    public Lecture(
            @JsonProperty("number") int number,
            @JsonProperty("date") @NonNull String date,
            @JsonProperty("theme") @NonNull String theme,
            @JsonProperty("lector") @NonNull String lector,
            @JsonProperty("subtopics") @NonNull List<String> subtopics) {
        mNumber = number;
        mDate = date;
        mTheme = theme;
        mLector = lector;
        mSubtopics = new ArrayList<>(subtopics);
        weekIndex = (mNumber - 1) / LECTURES_PER_WEEK;
    }

    protected Lecture(Parcel in){
        mNumber = in.readInt();
        mDate = in.readString();
        mTheme = in.readString();
        mLector = in.readString();
        weekIndex = in.readInt();
        mSubtopics = in.createStringArrayList();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNumber);
        dest.writeString(mDate);
        dest.writeString(mTheme);
        dest.writeString(mLector);
        dest.writeInt(weekIndex);
        dest.writeStringList(mSubtopics);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel source) {
            return new Lecture(source);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return mNumber == lecture.mNumber &&
                weekIndex == lecture.weekIndex &&
                Objects.equals(mDate, lecture.mDate) &&
                Objects.equals(mTheme, lecture.mTheme) &&
                Objects.equals(mLector, lecture.mLector) &&
                Objects.equals(mSubtopics, lecture.mSubtopics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNumber, mDate, mTheme, mLector, weekIndex,mSubtopics);
    }

    public int getNumber() {
        return mNumber;
    }

    public String getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLector() {
        return mLector;
    }

    public int getWeekIndex() {
        return weekIndex;
    }
    public List<String> getSubtopics() {
        return mSubtopics==null?null:new ArrayList<>(mSubtopics);
    }



}
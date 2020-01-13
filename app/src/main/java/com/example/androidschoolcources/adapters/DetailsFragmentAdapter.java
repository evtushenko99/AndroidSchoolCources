package com.example.androidschoolcources.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidschoolcources.R;

import java.util.List;

public class DetailsFragmentAdapter extends RecyclerView.Adapter<DetailsFragmentAdapter.DetailsFragmentViewHolder> {
    private final List<String> mSubtopics;

    public DetailsFragmentAdapter(@NonNull List<String> subtopics) {
        mSubtopics = subtopics;
    }

    @NonNull
    @Override
    public DetailsFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new DetailsFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsFragmentViewHolder holder, int position) {
        holder.mName.setText(mSubtopics.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

    static class DetailsFragmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private DetailsFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(android.R.id.text1);
        }

        protected void bindView() {

        }

    }
}

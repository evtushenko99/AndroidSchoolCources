package com.example.androidschoolcources.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidschoolcources.models.DisplayMode;

/**
 * Адаптер режимов отображения списка лекций
 *
 * @author Evgeny Chumak
 **/
public class DisplayModeSpinnerAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return DisplayMode.values().length;
    }

    @Override
    public DisplayMode getItem(int position) {
        return DisplayMode.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            convertView.setTag(new DisplayModeHolder(convertView));
        }
        DisplayModeHolder holder = (DisplayModeHolder) convertView.getTag();
        int titleResourceId = getItem(position).getTitleStringResourceId();
        holder.mTitle.setText(titleResourceId);
        return convertView;
    }

    private class DisplayModeHolder {
        private final TextView mTitle;

        private DisplayModeHolder(View root) {
            mTitle = root.findViewById(android.R.id.text1);
        }
    }
}

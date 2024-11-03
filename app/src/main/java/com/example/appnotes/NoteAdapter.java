package com.example.appnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<String> {

    public NoteAdapter(Context context, List<String> titles) {
        super(context, 0, titles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.noteTitleTextView);
        TextView previewTextView = convertView.findViewById(R.id.noteContentPreviewTextView);

        String title = getItem(position);
        titleTextView.setText(title);
        previewTextView.setText("Prepare by Vladyslav Nester");

        return convertView;
    }
}

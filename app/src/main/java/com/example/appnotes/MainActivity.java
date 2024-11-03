package com.example.appnotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> titlesList;
    private NoteAdapter noteAdapter;
    private ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        loadNotes();
    }

    private void initializeUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton addButton = findViewById(R.id.action_add);
        addButton.setOnClickListener(v -> openAddNoteActivity());

        ImageButton refreshButton = findViewById(R.id.action_refresh);
        refreshButton.setOnClickListener(v -> {
            Toast.makeText(this, "Refreshing notes", Toast.LENGTH_SHORT).show();
            loadNotes();
        });

        notesListView = findViewById(R.id.NotesListView);
        titlesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, titlesList);
        notesListView.setAdapter(noteAdapter);

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            String title = titlesList.get(position);
            Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
            intent.putExtra("note_title", title);
            startActivity(intent);
        });
    }

    private void loadNotes() {
        titlesList.clear();
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".txt")) {
                    String title = file.getName().replace(".txt", "");
                    titlesList.add(title);
                }
            }
        }
        noteAdapter.notifyDataSetChanged();
        if (!titlesList.isEmpty()) {
            Snackbar.make(notesListView, "Notes loaded successfully", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(notesListView, "No notes found", Snackbar.LENGTH_LONG).show();
        }
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}

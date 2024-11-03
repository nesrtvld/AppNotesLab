package com.example.appnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NoteDetailActivity extends AppCompatActivity {

    private String noteTitle;
    private TextView noteContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noteContentTextView = findViewById(R.id.noteContentTextView);
        noteTitle = getIntent().getStringExtra("note_title");

        if (noteTitle != null) {
            refreshNote();
        } else {
            noteContentTextView.setText("No content available");
        }
    }

    private String loadNoteContent(String title) {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = openFileInput(title + ".txt")) {
            int ch;
            while ((ch = fis.read()) != -1) {
                content.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void refreshNote() {
        String content = loadNoteContent(noteTitle);
        noteContentTextView.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            newNote();
            return true;
        } else if (id == R.id.action_edit) {
            editNote();
            return true;
        } else if (id == R.id.action_delete) {
            confirmDeleteNote();
            return true;
        } else if (id == R.id.action_home) {
            goHome();
            return true;
        } else if (id == R.id.action_refresh) {
            refreshNote();
            Toast.makeText(this, "Note refreshed", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void newNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void editNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("note_title", noteTitle);
        intent.putExtra("note_content", noteContentTextView.getText().toString());
        startActivity(intent);
    }

    private void confirmDeleteNote() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteNote() {
        File file = new File(getFilesDir(), noteTitle + ".txt");
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            goHome();
        } else {
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

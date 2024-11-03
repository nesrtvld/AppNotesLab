package com.example.appnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edTitle, edContent;
    private String originalTitle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edTitle = findViewById(R.id.edTitle);
        edContent = findViewById(R.id.edContent);

        Intent intent = getIntent();
        if (intent.hasExtra("note_title")) {
            originalTitle = intent.getStringExtra("note_title");
            String content = intent.getStringExtra("note_content");
            edTitle.setText(originalTitle);
            edContent.setText(content);
            edTitle.setEnabled(false);
        }
    }

    public void onSaveNoteClick(View view) {
        String title = edTitle.getText().toString().trim();
        String content = edContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (originalTitle != null) {
            saveNoteToFile(originalTitle, content);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            saveNoteToFile(title, content);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void saveNoteToFile(String title, String content) {
        try (FileOutputStream fos = openFileOutput(title + ".txt", Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
        }
    }
}

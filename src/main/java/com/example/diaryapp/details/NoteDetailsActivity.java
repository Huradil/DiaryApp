package com.example.diaryapp.details;

import android.annotation.SuppressLint;
import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.diaryapp.App;
import com.example.diaryapp.R;
import com.example.diaryapp.data.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    private Note note;

    private EditText editTitle;
    private EditText editText;

    private TextView timestamp;


    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Дневник");

        editTitle = findViewById(R.id.title);
        editText = findViewById(R.id.text);
        timestamp = findViewById(R.id.timestamp);

        if (getIntent().hasExtra(EXTRA_NOTE)) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editTitle.setText(note.title);
            editText.setText(note.text);
            if (note.timestamp > 0) {
                timestamp.setText(formatDateTime(note.timestamp));
            }
        }else {
                note = new Note();
            }



    }
    private String formatDateTime(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(new Date(timeInMillis));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.action_save) {
            String text = editText.getText().toString().trim();
            String title = editTitle.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                note.text = text;
                note.title = title;
                note.timestamp = System.currentTimeMillis();


                if (getIntent().hasExtra(EXTRA_NOTE)) {
                    App.getInstance().getNoteDao().update(note);
                } else {
                    App.getInstance().getNoteDao().insert(note);
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}

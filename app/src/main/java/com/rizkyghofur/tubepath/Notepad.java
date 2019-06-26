package com.rizkyghofur.tubepath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Notepad extends AppCompatActivity {

    private ListView mListNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notepad ");


        mListNotes = findViewById(R.id.main_listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notepad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create: //run NoteActivity in new note mode
                startActivity(new Intent(this, Catat.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mListNotes.setAdapter(null);
        ArrayList<LihatCatat> notes = Utilities.getAllSavedNotes(getApplicationContext());

        Collections.sort(notes, new Comparator<LihatCatat>() {
            @Override
            public int compare(LihatCatat lhs, LihatCatat rhs) {
                if(lhs.getDateTime() > rhs.getDateTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        if(notes != null && notes.size() > 0) { //check if we have any notes!
            final CatatAdapter na = new CatatAdapter(this, R.layout.activity_lihat_catat, notes);
            mListNotes.setAdapter(na);

            mListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String fileName = ((LihatCatat) mListNotes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), Catat.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Anda belum membuat catatan.\nSilahkan buat catatan Anda."
                    , Toast.LENGTH_SHORT).show();
        }
    }

}
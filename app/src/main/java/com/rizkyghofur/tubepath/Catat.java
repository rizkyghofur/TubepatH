package com.rizkyghofur.tubepath;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Catat extends AppCompatActivity {

    private boolean mIsViewingOrUpdating;
    private long mNoteCreationTime;
    private String mFileName;
    private LihatCatat mLoadedNote = null;
    private EditText mEtTitle;
    private EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notepad");

        mEtTitle = findViewById(R.id.note_et_title);
        mEtContent = findViewById(R.id.note_et_content);

        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION)) {
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null) {
                //update the widgets from the loaded note
                mEtTitle.setText(mLoadedNote.getTitle());
                mEtContent.setText(mLoadedNote.getContent());
                mNoteCreationTime = mLoadedNote.getDateTime();
                mIsViewingOrUpdating = true;
            }
        } else { //user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //load menu based on the state we are in (new, view/update/delete)
        if(mIsViewingOrUpdating) { //user is viewing or updating a note
            getMenuInflater().inflate(R.menu.menu_lihat_catat, menu);
        } else { //user wants to create a new note
            getMenuInflater().inflate(R.menu.menu_tambah_catat, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save_note: //save the note
            case R.id.action_update: //or update :P
                validateAndSaveNote();
                break;

            case R.id.action_delete:
                actionDelete();
                break;

            case R.id.action_cancel: //cancel the note
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        actionCancel();
    }

    private void actionDelete() {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                .setTitle("Hapus catatan")
                .setMessage("Yakin ingin menghapus catatan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName)) {
                            Toast.makeText(Catat.this, mLoadedNote.getTitle() + " terhapus"
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Catat.this, "Tidak dapat menghapus catatan '" + mLoadedNote.getTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null); //do nothing on clicking NO button :P

        dialogDelete.show();
    }

    /**
     * Handle cancel action
     */
    private void actionCancel() {

        if(!checkNoteAltred()) { //if note is not altered by user (user only viewed the note/or did not write anything)
            finish(); //just exit the activity and go back to MainActivity
        } else { //we want to remind user to decide about saving the changes or not, by showing a dialog
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this)
                    .setTitle("Perubahan tidak tersimpan")
                    .setMessage("Anda yakin tidak ingin menyimpan catatan ini?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish(); //just go back to main activity
                        }
                    })
                    .setNegativeButton("Tidak", null); //null = stay in the activity!
            dialogCancel.show();
        }
    }

    /**
     * Check to see if a loaded note/new note has been changed by user or not
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred() {
        if(mIsViewingOrUpdating) { //if in view/update mode
            return mLoadedNote != null && (!mEtTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getTitle())
                    || !mEtContent.getText().toString().equalsIgnoreCase(mLoadedNote.getContent()));
        } else { //if in new note mode
            return !mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty();
        }
    }

    /**
     * Validate the title and content and save the note and finally exit the activity and go back to MainActivity
     */
    private void validateAndSaveNote() {

        //get the content of widgets to make a note object
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();

        //see if user has entered anything :D lol
        if(title.isEmpty()) { //title
            Toast.makeText(Catat.this, "Mohon masukkan judul catatan"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty()) { //content
            Toast.makeText(Catat.this, "Masukkan isi catatan"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if(mLoadedNote != null) {
            mNoteCreationTime = mLoadedNote.getDateTime();
        } else {
            mNoteCreationTime = System.currentTimeMillis();
        }

        //finally save the note!
        if(Utilities.saveNote(this, new LihatCatat(mNoteCreationTime, title, content))) { //success!
            //tell user the note was saved!
            Toast.makeText(this, "Catatan tersimpan", Toast.LENGTH_SHORT).show();
        } else { //failed to save the note! but this should not really happen :P :D :|
            Toast.makeText(this, "Tidak dapat menyimpan catatan. Pastikan memori internal Anda mencukupi " +
                    "di perangkat Anda", Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the activity, should return us to MainActivity
    }
}
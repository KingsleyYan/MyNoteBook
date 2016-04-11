package com.android.kingyan.mynotebook;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteLab {
    private static final String TAG = "NoteLab";
    private static final String FILENAME = "mynotebook.json";
    private static NoteLab sNoteLab;
    private Context mContext;
    private ArrayList<Note> mNotes;
    private NoteBookJSONSerializer mNoteBookJSONSerializer;

    public NoteLab(Context context) {
        mContext = context;
        mNoteBookJSONSerializer = new NoteBookJSONSerializer(mContext, FILENAME);
        try {
            mNotes = mNoteBookJSONSerializer.getLoadNotes();
            Log.d(TAG, "notes " + mNotes.size());
            Log.d(TAG, "load success");
        } catch (Exception e) {
            mNotes = new ArrayList();
            Log.d(TAG, "load notes fail");
        }
    }

    public static NoteLab get(Context context) {
        if (sNoteLab == null) {
            sNoteLab = new NoteLab(context.getApplicationContext());
        }
        return sNoteLab;
    }

    public boolean saveNotes() {
        try {
            mNoteBookJSONSerializer.saveNotes(mNotes);
            Log.d(TAG, "notes " + mNotes.size());
            Log.d(TAG, "notes saved");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "notes saved fail");
            return false;
        }
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public Note getNote(UUID id) {
        for (Note note : mNotes) {
            if (note.getmId().equals(id)) {
                return note;
            }
        }
        return null;
    }

    public void addNote(Note note) {
        mNotes.add(note);
    }

}

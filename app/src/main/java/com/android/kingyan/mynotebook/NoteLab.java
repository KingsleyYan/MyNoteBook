package com.android.kingyan.mynotebook;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteLab {
    private static NoteLab sNoteLab;
    private Context mContext;
    private ArrayList<Note> mNotes;

    public NoteLab(Context context) {
        mContext = context;
        mNotes = new ArrayList<Note>();
    }

    public static NoteLab get(Context context) {
        if (sNoteLab == null) {
            sNoteLab = new NoteLab(context.getApplicationContext());
        }
        return sNoteLab;
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

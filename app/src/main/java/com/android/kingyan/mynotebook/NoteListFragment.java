package com.android.kingyan.mynotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteListFragment extends ListFragment {
    private ArrayList<Note> mNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.note_title);
        mNotes = NoteLab.get(getActivity()).getNotes();
    }
}

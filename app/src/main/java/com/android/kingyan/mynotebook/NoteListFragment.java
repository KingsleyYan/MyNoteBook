package com.android.kingyan.mynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteListFragment extends ListFragment {
    private static final String TAG = "NoteListFragment";
    private ArrayList<Note> mNotes;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Note note = ((NoteAdapter) getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(), NoteBookPaperActivity.class);
        intent.putExtra(NoteBookFragment.EXTRA_NOTEBOOK_ID, note.getmId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((NoteAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_notebook_menu, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_title);
        mNotes = NoteLab.get(getActivity()).getNotes();
        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);
    }

    private class NoteAdapter extends ArrayAdapter<Note> {
        public NoteAdapter(ArrayList<Note> notes) {
            super(getActivity(), 0, notes);//不使用预定义布局，传0作为布局ID
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_note, null);
            }
            Note note = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.note_list_item_titleTextView);
            titleTextView.setText(note.getmTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.note_list_item_dateTextView);
            dateTextView.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", note.getDate()));
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.note_list_item_solvedCheckbox);
            solvedCheckBox.setChecked(note.isSolved());
            return convertView;
        }
    }
}

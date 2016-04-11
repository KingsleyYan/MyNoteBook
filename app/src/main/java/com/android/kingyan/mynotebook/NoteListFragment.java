package com.android.kingyan.mynotebook;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private boolean mSubtitleVisible;
    private Button mEmptyToCreateButton;
    private TextView mEmptyTextView;

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
        inflater.inflate(R.menu.fragment_notebook_list, menu);
        MenuItem subtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (subtitle != null && mSubtitleVisible) {
            subtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_notebook, container, false);
        mEmptyToCreateButton = (Button) v.findViewById(R.id.button_empty_new_note);
        mEmptyToCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
            }
        }
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        mEmptyTextView = (TextView) v.findViewById(R.id.fragment_list_empty_textview);
        listView.setEmptyView(mEmptyTextView);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater menuInflater = mode.getMenuInflater();
                    menuInflater.inflate(R.menu.notebook_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_note:
                            NoteAdapter adapter = (NoteAdapter) getListAdapter();
                            NoteLab noteLab = NoteLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    noteLab.deleteNote(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        return v;
    }

    private void createNewNote() {
        Note note = new Note();
        NoteLab.get(getActivity()).addNote(note);
        Intent intent = new Intent(getActivity(), NoteBookPaperActivity.class);
        intent.putExtra(NoteBookFragment.EXTRA_NOTEBOOK_ID, note.getmId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.note_title);
        mNotes = NoteLab.get(getActivity()).getNotes();
        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_note:
                createNewNote();
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    getActivity().getActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.notebook_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        NoteAdapter adapter = (NoteAdapter) getListAdapter();
        Note note = adapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:
                NoteLab.get(getActivity()).deleteNote(note);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
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
            mEmptyToCreateButton.setVisibility(View.INVISIBLE);
            return convertView;
        }
    }
}

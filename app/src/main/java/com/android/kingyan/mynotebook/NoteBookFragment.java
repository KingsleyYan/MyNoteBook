package com.android.kingyan.mynotebook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteBookFragment extends Fragment {
    public static final String EXTRA_NOTEBOOK_ID = "com.android.kingyan.mynotebook.notebookfragment.notebook_id";
    private Note mNote;
    private EditText mTitleEditText;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static NoteBookFragment newInstance(UUID id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_NOTEBOOK_ID, id);
        NoteBookFragment noteBookFragment = new NoteBookFragment();
        noteBookFragment.setArguments(bundle);
        return noteBookFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id = (UUID) getArguments().getSerializable(EXTRA_NOTEBOOK_ID);
        mNote = NoteLab.get(getActivity()).getNote(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notebook, container, false);
        mTitleEditText = (EditText) v.findViewById(R.id.note_title);
        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mNote.setmTitle(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateButton = (Button) v.findViewById(R.id.button_date);
        mDateButton.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", mNote.getDate()));
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.checkbox_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.setSolved(isChecked);
            }
        });
        return v;
    }
}

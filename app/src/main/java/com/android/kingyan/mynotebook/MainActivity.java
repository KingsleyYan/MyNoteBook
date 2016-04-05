package com.android.kingyan.mynotebook;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID id = (UUID) getIntent().getSerializableExtra(NoteBookFragment.EXTRA_NOTEBOOK_ID);
        return NoteBookFragment.newInstance(id);
    }
}

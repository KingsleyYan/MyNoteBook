package com.android.kingyan.mynotebook;

import android.support.v4.app.Fragment;

/**
 * Created by yanj on 16/04/01.
 */
public class NoteListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }
}

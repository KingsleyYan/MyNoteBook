package com.android.kingyan.mynotebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yanj on 16/04/05.
 */
public class NoteBookPaperActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Note> mNotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mNotes = NoteLab.get(this).getNotes();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Note note = mNotes.get(position);
                return NoteBookFragment.newInstance(note.getmId());
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        });

        UUID id = (UUID) getIntent().getSerializableExtra(NoteBookFragment.EXTRA_NOTEBOOK_ID);
        for (int i = 0; i < mNotes.size(); i++) {
            if (mNotes.get(i).getmId().equals(id)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}

package com.android.kingyan.mynotebook;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yanj on 16/03/31.
 */
public class Note {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Note() {
        this.mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}

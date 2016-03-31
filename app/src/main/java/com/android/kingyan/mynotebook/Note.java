package com.android.kingyan.mynotebook;

import java.util.UUID;

/**
 * Created by yanj on 16/03/31.
 */
public class Note {
    private UUID mId;
    private String mTitle;

    public Note() {
        this.mId = UUID.randomUUID();
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
}

package com.android.kingyan.mynotebook;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yanj on 16/03/31.
 */
public class Note {
    public static final String JSON_ID = "id";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DATE = "date";
    public static final String JSON_SOLVED = "solved";
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Note() {
        this.mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Note(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        mDate = new Date(json.getLong(JSON_DATE));
        mSolved = json.getBoolean(JSON_SOLVED);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_DATE, mDate.getTime());
        json.put(JSON_SOLVED, mSolved);
        return json;
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

    @Override
    public String toString() {
        return mTitle;
    }
}

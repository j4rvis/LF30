package de.j4rvis.lowfat;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by micha on 23.09.15.
 */
public class Config {

    private int mAllPointsCount;
    private int mGreenPointsCount;
    private int mYellowPointsCount;
    private int mRedPointsCount;
    private String mStartDate;

    private Context context;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    public Config(Context context){
        settings = context.getApplicationContext().getSharedPreferences("Settings", 0);
        editor = settings.edit();

        mAllPointsCount = settings.getInt("allPoints", 30);
        mGreenPointsCount = settings.getInt("greenPoints", 18);
        mYellowPointsCount = settings.getInt("yellowPoints", 10);
        mRedPointsCount = settings.getInt("redPoints", 2);

        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        String defaultDate = sdf.format(Calendar.getInstance().getTime());
        mStartDate = settings.getString("startDate", defaultDate);
    }

    public int getAllPointsCount() {
        return mAllPointsCount;
    }

    public void setAllPointsCount(int allPointsCount) {
        mAllPointsCount = allPointsCount;
        editor.putInt("allPoints", mAllPointsCount);
        editor.commit();
    }

    public int getGreenPointsCount() {
        return mGreenPointsCount;
    }

    public void setGreenPointsCount(int greenPointsCount) {
        mGreenPointsCount = greenPointsCount;
        editor.putInt("greenPoints", mGreenPointsCount);
        editor.commit();
    }

    public int getYellowPointsCount() {
        return mYellowPointsCount;
    }

    public void setYellowPointsCount(int yellowPointsCount) {
        mYellowPointsCount = yellowPointsCount;
        editor.putInt("yellowPoints", mYellowPointsCount);
        editor.commit();
    }

    public int getRedPointsCount() {
        return mRedPointsCount;
    }

    public void setRedPointsCount(int redPointsCount) {
        mRedPointsCount = redPointsCount;
        editor.putInt("redPoints", mRedPointsCount);
        editor.commit();
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
        editor.putString("startDate", mStartDate);
        editor.commit();
    }
}

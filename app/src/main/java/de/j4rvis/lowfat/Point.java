package de.j4rvis.lowfat;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by j4rvis on 04.07.15.
 */

public class Point extends SugarRecord<Point> implements Comparable{

    private ColorEnum mColor;
    private double mSize;
    private PointList mPointList;
    private String mTimestamp;
    @Ignore
    private SimpleDateFormat sdf;

    public Point(){}

    public Point(ColorEnum color, double size, PointList pointList) {
        mColor = color;
        mSize = size;
        mPointList = pointList;
        sdf = new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss", Locale.GERMAN);
        mTimestamp = sdf.format(Calendar.getInstance().getTime());
    }

    public double getSize() {
        return mSize;
    }

    public Date getDate() throws ParseException {
        sdf = new SimpleDateFormat("dd.MM.yyyy - hh:MM:ss", Locale.GERMAN);
        return sdf.parse(mTimestamp);
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    @Override
    public String toString() {
        return "Point" + mSize + " " + mPointList + "_" + mTimestamp;
    }

    @Override
    public int compareTo(Object another) {
        try {
            return getDate().compareTo(((Point) another).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

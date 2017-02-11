package de.j4rvis.lowfat;

import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by j4rvis on 04.07.15.
 */
public class Day extends SugarRecord<Day> implements Comparable<Day> {

    private int mYear;
    private int mMonth;
    private int mDay;
    private long mMillis;
    private PointList mBreakfast;
    private PointList mLunch;
    private PointList mDinner;
    private PointList mSnack;

    @Ignore
    private List<Point> mPoints;

    public Day(){}

    public Day(long millis) {
        this.mMillis = millis;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(mMillis));
        this.mYear = calendar.get(Calendar.YEAR);
        this.mMonth = calendar.get(Calendar.MONTH) + 1;
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);

        this.mBreakfast = new PointList("Frühstück");
        this.mBreakfast.save();
        this.mLunch = new PointList("Mittag");
        this.mLunch.save();
        this.mDinner = new PointList("Abendessen");
        this.mDinner.save();
        this.mSnack = new PointList("Snack");
        this.mSnack.save();
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    public PointList getBreakfast() {
        return mBreakfast;
    }

    public PointList getLunch() {
        return mLunch;
    }

    public PointList getDinner() {
        return mDinner;
    }

    public PointList getSnack() {
        return mSnack;
    }

    public double getListSizeByColor(ColorEnum color){
        double size = 0;
        size += mBreakfast.getSizeByColor(color);
        size += mLunch.getSizeByColor(color);
        size += mDinner.getSizeByColor(color);
        size += mSnack.getSizeByColor(color);
        return size;
    }

    public List<Point> getAllPoints(){
        mPoints = new LinkedList<>();
        mPoints.addAll(mBreakfast.getPoints());
        mPoints.addAll(mLunch.getPoints());
        mPoints.addAll(mDinner.getPoints());
        mPoints.addAll(mSnack.getPoints());
        return mPoints;
    }

    public void removeLastPoint(){
        List<Point> points = getAllPoints();
        if(points.size()==0) return;
        Collections.sort(points);
        Collections.reverse(points);
        Point point = points.get(0);
        point.delete();
    }

    public double getListSize(){
        double size = 0;
        size += mBreakfast.getSize();
        size += mLunch.getSize();
        size += mDinner.getSize();
        size += mSnack.getSize();
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (getYear() != day.getYear()) return false;
        if (getMonth() != day.getMonth()) return false;
        return getDay() == day.getDay();
    }

    @Override
    public int hashCode() {
        int result = getYear();
        result = 31 * result + getMonth();
        result = 31 * result + getDay();
        return result;
    }

    @Override
    public String toString() {
        return "Day: " + mDay + "." + mMonth + "." + mYear;
    }

    @Override
    public int compareTo(Day day) {
        if(this.getYear() == day.getYear()){
            if(this.getMonth() == day.getMonth()){
                return Integer.compare(this.getDay(), day.getDay());
            } else{
                return Integer.compare(this.getMonth(), day.getMonth());
            }
        } else{
            return Integer.compare(this.getYear(), day.getYear());
        }
    }
}

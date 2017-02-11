package de.j4rvis.lowfat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ListDaysActivity extends Activity {

    private final static String TAG = ListDaysActivity.class.toString();
    private List<Day> mDays;
    private Context that = this;

    private Comparator<Day> dayComparator = new Comparator<Day>() {
        @Override
        public int compare(Day o, Day t1) {
            return o.compareTo(t1) ;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_days);

        ListView dayListView = (ListView) findViewById(R.id.dayList);

        checkForNewDay();
        Collections.reverse(mDays);

        final ArrayAdapter adapter = new ArrayAdapter<Day>(this,
                android.R.layout.simple_list_item_1, mDays);
        dayListView.setAdapter(adapter);

        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day item = mDays.get(position);
                Intent intent = new Intent(that, DayOverviewActivity.class);
                intent.putExtra("dayId", item.getId());
                startActivity(intent);
            }
        });

    }

    private void refreshDayList(){
        mDays = Day.listAll(Day.class);
    }

    private void checkForNewDay(){
        refreshDayList();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
//        formatter.setLenient(false);
//        long test = 0;
//        try {
//             test = formatter.parse("2015-09-11, 10:00:00").getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Day newDay = new Day(cal.getTimeInMillis());

        if(mDays.size() == 0){
            Day[] days = new Day[7];
            days[6] = new Day(cal.getTimeInMillis());
            for(int i = 1; i < 7; i++){
                cal.add(Calendar.DATE, -1);
                days[6-i] = new Day(cal.getTimeInMillis());
            }
            for(Day day : days){
                day.save();
            }
        } else {
            Collections.sort(mDays, dayComparator);
            Day lastDay = mDays.get(mDays.size()-1);
//            Log.d(TAG, lastDay.toString());
//            Log.d(TAG, newDay.toString());
//            Log.d(TAG, newDay.compareTo(lastDay)+"");
            if(newDay.compareTo(lastDay) == 1) newDay.save();
        }
        refreshDayList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

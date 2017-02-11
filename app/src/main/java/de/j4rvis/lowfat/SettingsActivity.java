package de.j4rvis.lowfat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SettingsActivity extends Activity
        implements View.OnClickListener{

    private Context that;
    private Config config;

    private Calendar myCalendar;
    private SimpleDateFormat sdf;

    private TextView displayDay;
    private Button buttonChangeDay;
    private Button buttonSaveSettings;

    private TextView editAllPoints;
    private TextView editGreenPoints;
    private TextView editYellowPoints;
    private TextView editRedPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Einstellungen");

        String myFormat = "dd.MM.yyyy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);
        config = new Config(this);
        myCalendar = Calendar.getInstance();
        displayDay = (TextView) findViewById(R.id.textViewDisplayDay);
        buttonChangeDay = (Button) findViewById(R.id.buttonChangeDay);
        buttonSaveSettings = (Button) findViewById(R.id.buttonSaveSettings);
        editAllPoints = (TextView) findViewById(R.id.editAllPoints);
        editGreenPoints = (TextView) findViewById(R.id.editGreenPoints);
        editYellowPoints = (TextView) findViewById(R.id.editYellowPoints);
        editRedPoints = (TextView) findViewById(R.id.editRedPoints);
        that = this;

        editAllPoints.setText(String.valueOf(config.getAllPointsCount()));
        editGreenPoints.setText(String.valueOf(config.getGreenPointsCount()));
        editYellowPoints.setText(String.valueOf(config.getYellowPointsCount()));
        editRedPoints.setText(String.valueOf(config.getRedPointsCount()));
        displayDay.setText(config.getStartDate());
        buttonSaveSettings.setOnClickListener(this);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                displayDay.setText(sdf.format(myCalendar.getTime()));
            }
        };

        buttonChangeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new DatePickerDialog(that, date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
            }
        );
    }

    @Override
    public void onClick(View v) {
        config.setAllPointsCount(Integer.parseInt(editAllPoints.getText().toString()));
        config.setGreenPointsCount(Integer.parseInt(editGreenPoints.getText().toString()));
        config.setYellowPointsCount(Integer.parseInt(editYellowPoints.getText().toString()));
        config.setRedPointsCount(Integer.parseInt(editRedPoints.getText().toString()));
        config.setStartDate(sdf.format(myCalendar.getTime()));
        finish();
    }
}

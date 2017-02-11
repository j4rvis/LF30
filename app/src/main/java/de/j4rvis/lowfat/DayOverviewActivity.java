package de.j4rvis.lowfat;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DayOverviewActivity extends FragmentActivity implements DialogAddPointFragment.DialogAddPointListener{

    private Day mDay;
    private Config mConfig;

    private View layoutBreakfast;
    private View layoutLunch;
    private View layoutDinner;
    private View layoutSnack;
    private View layoutAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_overview);
        Intent intent = getIntent();
        mDay = Day.findById(Day.class, intent.getLongExtra("dayId", 0));
        setTitle(mDay.toString());

        mConfig = new Config(this);

        layoutBreakfast = findViewById(R.id.listBreakfast);
        layoutLunch = findViewById(R.id.listLunch);
        layoutDinner = findViewById(R.id.listDinner);
        layoutSnack = findViewById(R.id.listSnack);
        layoutAvailable = findViewById(R.id.listAvailable);

        TextView textViewBreakfast = (TextView) layoutBreakfast.findViewById(R.id.fieldName);
        TextView textViewLunch = (TextView) layoutLunch.findViewById(R.id.fieldName);
        TextView textViewDinner = (TextView) layoutDinner.findViewById(R.id.fieldName);
        TextView textViewSnack = (TextView) layoutSnack.findViewById(R.id.fieldName);
        TextView textViewAvailable = (TextView) layoutAvailable.findViewById(R.id.fieldName);

        textViewBreakfast.setText(mDay.getBreakfast().getName());
        textViewLunch.setText(mDay.getLunch().getName());
        textViewDinner.setText(mDay.getDinner().getName());
        textViewSnack.setText(mDay.getSnack().getName());
        textViewAvailable.setText("Verfügbare Punkte");

        updateView();

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new DialogAddPointFragment();
                Bundle args = new Bundle();
                args.putLong("day", mDay.getId());
                dialog.setArguments(args);
                dialog.show(getFragmentManager(),"DialogAddPointFragment");
            }
        });
    }

    public void updateView(){
        mDay = Day.findById(Day.class, mDay.getId());

        ((TextView) layoutBreakfast.findViewById(R.id.allPoints))
                .setText(String.valueOf(mDay.getBreakfast().getSize()));
        ((TextView) layoutBreakfast.findViewById(R.id.greenPoints))
                .setText(String.valueOf(mDay.getBreakfast().getSizeByColor(ColorEnum.GREEN)));
        ((TextView) layoutBreakfast.findViewById(R.id.yellowPoints))
                .setText(String.valueOf(mDay.getBreakfast().getSizeByColor(ColorEnum.YELLOW)));
        ((TextView) layoutBreakfast.findViewById(R.id.redPoints))
                .setText(String.valueOf(mDay.getBreakfast().getSizeByColor(ColorEnum.RED)));

        ((TextView) layoutLunch.findViewById(R.id.allPoints))
                .setText(String.valueOf(mDay.getLunch().getSize()));
        ((TextView) layoutLunch.findViewById(R.id.greenPoints))
                .setText(String.valueOf(mDay.getLunch().getSizeByColor(ColorEnum.GREEN)));
        ((TextView) layoutLunch.findViewById(R.id.yellowPoints))
                .setText(String.valueOf(mDay.getLunch().getSizeByColor(ColorEnum.YELLOW)));
        ((TextView) layoutLunch.findViewById(R.id.redPoints))
                .setText(String.valueOf(mDay.getLunch().getSizeByColor(ColorEnum.RED)));

        ((TextView) layoutDinner.findViewById(R.id.allPoints))
                .setText(String.valueOf(mDay.getDinner().getSize()));
        ((TextView) layoutDinner.findViewById(R.id.greenPoints))
                .setText(String.valueOf(mDay.getDinner().getSizeByColor(ColorEnum.GREEN)));
        ((TextView) layoutDinner.findViewById(R.id.yellowPoints))
                .setText(String.valueOf(mDay.getDinner().getSizeByColor(ColorEnum.YELLOW)));
        ((TextView) layoutDinner.findViewById(R.id.redPoints))
                .setText(String.valueOf(mDay.getDinner().getSizeByColor(ColorEnum.RED)));

        ((TextView) layoutSnack.findViewById(R.id.allPoints))
                .setText(String.valueOf(mDay.getSnack().getSize()));
        ((TextView) layoutSnack.findViewById(R.id.greenPoints))
                .setText(String.valueOf(mDay.getSnack().getSizeByColor(ColorEnum.GREEN)));
        ((TextView) layoutSnack.findViewById(R.id.yellowPoints))
                .setText(String.valueOf(mDay.getSnack().getSizeByColor(ColorEnum.YELLOW)));
        ((TextView) layoutSnack.findViewById(R.id.redPoints))
                .setText(String.valueOf(mDay.getSnack().getSizeByColor(ColorEnum.RED)));

        ((TextView) layoutAvailable.findViewById(R.id.allPoints))
                .setText(String.valueOf(mConfig.getAllPointsCount()-mDay.getListSize()));
        ((TextView) layoutAvailable.findViewById(R.id.greenPoints))
                .setText(String.valueOf(mConfig.getGreenPointsCount()
                        -mDay.getListSizeByColor(ColorEnum.GREEN)));
        ((TextView) layoutAvailable.findViewById(R.id.yellowPoints))
                .setText(String.valueOf(mConfig.getYellowPointsCount()
                        -mDay.getListSizeByColor(ColorEnum.YELLOW)));
        ((TextView) layoutAvailable.findViewById(R.id.redPoints))
                .setText(String.valueOf(mConfig.getRedPointsCount()
                        -mDay.getListSizeByColor(ColorEnum.RED)));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        updateView();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_days, menu);
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
            mDay.removeLastPoint();
            updateView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

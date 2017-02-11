package de.j4rvis.lowfat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class DialogAddPointFragment extends DialogFragment {

    private Day mDay;

    private DialogAddPointListener mListener;

    public DialogAddPointFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.fragment_dialog, null);

        Bundle bundle = getArguments();
        mDay = Day.findById(Day.class, bundle.getLong("day"));

        final NumberPicker numberPicker = (NumberPicker) dialogLayout.findViewById(R.id.numberPicker);
        final String numberRange[] =  new String[40];
        for (int i = 0; i < 40; i++){
            numberRange[i] = String.valueOf((double)i/2);
        }
        numberPicker.setMaxValue(numberRange.length-1);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(numberRange);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        final NumberPicker colorPicker = (NumberPicker) dialogLayout.findViewById(R.id.colorPicker);
//        String colorRange[] = Arrays.copyOf(ColorEnum.values()., ColorEnum.values().length, String[].class);
        final String colorRange[] = {
                ColorEnum.GREEN.toString(),
                ColorEnum.YELLOW.toString(),
                ColorEnum.RED.toString()
        };
        colorPicker.setMaxValue(colorRange.length-1);
        colorPicker.setMinValue(0);
        colorPicker.setWrapSelectorWheel(false);
        colorPicker.setDisplayedValues(colorRange);
        colorPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        final NumberPicker fieldPicker = (NumberPicker) dialogLayout.findViewById(R.id.fieldPicker);
        final String fieldRange[] = {
                mDay.getBreakfast().toString(),
                mDay.getLunch().toString(),
                mDay.getDinner().toString(),
                mDay.getSnack().toString()
        };
        fieldPicker.setMaxValue(fieldRange.length-1);
        fieldPicker.setMinValue(0);
        fieldPicker.setWrapSelectorWheel(false);
        fieldPicker.setDisplayedValues(fieldRange);
        fieldPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogLayout)
                .setTitle("Neuen Punkt hinzufügen")
                // Add action buttons
                .setPositiveButton(R.string.addPoint, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        String number = numberRange[numberPicker.getValue()];
                        String colorValue = colorRange[colorPicker.getValue()];
                        String field = fieldRange[fieldPicker.getValue()];
                        ColorEnum color = null;
                        if(colorValue == "Grün") color = ColorEnum.GREEN;
                        if(colorValue == "Gelb") color = ColorEnum.YELLOW;
                        if(colorValue == "Rot") color = ColorEnum.RED;
                        PointList list = null;

                        if(field == mDay.getBreakfast().toString()) list = mDay.getBreakfast();
                        if(field == mDay.getLunch().toString()) list = mDay.getLunch();
                        if(field == mDay.getDinner().toString()) list = mDay.getDinner();
                        if(field == mDay.getSnack().toString()) list = mDay.getSnack();

                        Point point = new Point(color, Double.parseDouble(number), list);
                        point.save();

                        mListener.onDialogPositiveClick(DialogAddPointFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddPointFragment.this.getDialog().cancel();
                        mListener.onDialogNegativeClick(DialogAddPointFragment.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogAddPointListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DialogAddPointListener {
        // TODO: Update argument type and name
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

}

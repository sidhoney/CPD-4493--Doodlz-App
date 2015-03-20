// ColorDialogFragment.java
// Allows user to set the drawing color on the DoodleView
package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

// class for the Select Color dialog   
public class BackgroundColorDialogFragment extends DialogFragment {

    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View bkgrndcolorView;
    private int bkgrndColor;

    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity());
        View colorDialogView
                = getActivity().getLayoutInflater().inflate(
                        R.layout.fragment_color, null);
        builder.setView(colorDialogView); // add GUI to dialog

        // set the AlertDialog's message 
        builder.setTitle(R.string.title_color_dialog);
        builder.setCancelable(true);

        // get the color SeekBars and set their onChange listeners
        alphaSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogView.findViewById(
                R.id.blueSeekBar);
        bkgrndcolorView = colorDialogView.findViewById(R.id.colorView);

        // register SeekBar event listeners
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        // use current drawing color to set SeekBar values
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        bkgrndColor = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(bkgrndColor));
        redSeekBar.setProgress(Color.red(bkgrndColor));
        greenSeekBar.setProgress(Color.green(bkgrndColor));
        blueSeekBar.setProgress(Color.blue(bkgrndColor));

        // add Set Color Button
        builder.setPositiveButton(R.string.button_set_color,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doodleView.setDrawingColor(bkgrndColor);
                    }
                }
        ); // end call to setPositiveButton

        return builder.create(); // return dialog
    } // end method onCreateDialog   

    // gets a reference to the DoodleFragment
    private DoodleFragment getDoodleFragment() {
        return (DoodleFragment) getFragmentManager().findFragmentById(
                R.id.doodleFragment);
    }

    // tell DoodleFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null) {
            fragment.setDialogOnScreen(true);
        }
    }

    // tell DoodleFragment that dialog is no longer displayed
    @Override
    public void onDetach() {
        super.onDetach();
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null) {
            fragment.setDialogOnScreen(false);
        }
    }

    // OnSeekBarChangeListener for the SeekBars in the color dialog
    private OnSeekBarChangeListener colorChangedListener
            = new OnSeekBarChangeListener() {
                // display the updated color
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                        boolean fromUser) {
                    if (fromUser) // user, not program, changed SeekBar progress
                    {
                    	bkgrndColor = Color.argb(alphaSeekBar.getProgress(),
                                redSeekBar.getProgress(), greenSeekBar.getProgress(),
                                blueSeekBar.getProgress());
                    }
                    bkgrndcolorView.setBackgroundColor(bkgrndColor);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) // required
                {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) // required
                {
                }
            }; // end colorChanged
} // end class ColorDialogFragment

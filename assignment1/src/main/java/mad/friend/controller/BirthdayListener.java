package mad.friend.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mad.friend.model.Friend;

/**
 * Referenced http://abhiandroid.com/ui/datepicker
 * modified to select and display birthday
 *
 * Displays a birthday using the Date Picker class and sets text to be visible upon selection
 */

public class BirthdayListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Activity current;
    TextView editText;
    Friend friend;

    public BirthdayListener(Activity caller, TextView editText, Friend friend)
    {
        current = caller;
        this.editText = editText;
        this.friend = friend;
    }

    /**
     *  When clicked, a Date Picker Dialog will display so user can select the date
     */
    @Override
    public void onClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpa = new DatePickerDialog(current, this, year, month, day);
        dpa.show();
    }

    /**
     *  Sets the birthday text field to the selected one from the Date Picker dialog
     *  Sets friends current birthday to the selected one
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date newBirthday = calendar.getTime();
        friend.setBirthday(newBirthday);
        editText.setText(new SimpleDateFormat("dd MMMM yyyy").format(newBirthday));
    }
}

package mad.friend.controller.meeting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mad.friend.model.Meeting;

/**
 * Meeting Date Listener
 * Activate a DatePicker Dialog to set the meeting date
 */
public class MeetingDateListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener
{
    Activity current;
    TextView editText;
    Meeting meeting;

    public MeetingDateListener(Activity caller, TextView editText, Meeting meeting)
    {
        current = caller;
        this.editText = editText;
        this.meeting = meeting;
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date newDate = calendar.getTime();
        meeting.setDate(newDate);
        editText.setText(new SimpleDateFormat("dd MMMM yyyy").format(newDate));
    }
}

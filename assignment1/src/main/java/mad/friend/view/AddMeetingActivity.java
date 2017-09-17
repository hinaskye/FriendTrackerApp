package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import hinaskye.assignment1.R;
import mad.friend.controller.AddMeetingListener;
import mad.friend.controller.MeetingDateListener;
import mad.friend.controller.MeetingTimeListener;
import mad.friend.model.Meeting;

/**
 * Add Meeting Activity
 * Current just shows layout for adding a meeting
 */
public class AddMeetingActivity extends AppCompatActivity
{
    private String LOG_TAG = this.getClass().getName();
    private Meeting meeting = new Meeting();
    private int startTime = 0, endTime = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");

        setContentView(R.layout.add_meeting);

        // listeners
        // Date Listener
        TextView meetingDate = (EditText) findViewById(R.id.meeting_edit_date);
        meetingDate.setOnClickListener(new MeetingDateListener(this, meetingDate, meeting));

        // Time Listener
        TextView meetingStartTime = (EditText) findViewById(R.id.meeting_start_time);
        meetingStartTime.setOnClickListener(
                new MeetingTimeListener(this, meetingStartTime, meeting, startTime));

        TextView meetingEndTime = (EditText) findViewById(R.id.meeting_end_time);
        meetingEndTime.setOnClickListener(
                new MeetingTimeListener(this, meetingEndTime, meeting, endTime));



    }
}

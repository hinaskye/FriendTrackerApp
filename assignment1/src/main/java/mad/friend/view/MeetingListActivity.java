package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.controller.meeting.AddMeetingListener;
import mad.friend.controller.friend.DisplayFriendListListener;
import mad.friend.controller.meeting.EditMeetingListener;
import mad.friend.controller.meeting.SuggestNowListener;
import mad.friend.model.database.DBMeetingHelper;
import mad.friend.view.model.MeetingListAdapter;
import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;

/**
 * Meeting List Activity
 * Displays a list of meetings by listview
 * Current has hard coded meetings to test layout
 * Other meeting functionality not yet implemented
 */
public class MeetingListActivity extends AppCompatActivity
{
    private String LOG_TAG = this.getClass().getName();

    private ArrayAdapter meeting_list_adapter;
    private ListView meetingListView;

    // hard coded to test layout
    private List<String> meeting_list_strings = new ArrayList<String>();
    private DBMeetingHelper dbMeeting;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list);

        /* // Hard coded meetings
        // Hard coded date time
        Calendar c = Calendar.getInstance();
        c.set(2017, 9, 9, 12, 30);
        long dummyStartTime = c.getTimeInMillis();
        c.set(2017, 9, 9, 13, 30);
        long dummyEndTime = c.getTimeInMillis();

        // Hard coded meetings to test layout
        Meeting m1 = new Meeting("meeting 1", new Time(dummyStartTime), new Time(dummyEndTime));
        Meeting m2 = new Meeting("meeting 2", new Time(dummyStartTime), new Time(dummyEndTime));
        Meeting m3 = new Meeting("meeting 3", new Time(dummyEndTime), new Time(dummyEndTime+1000));
        Meeting m4 = new Meeting("meeting 4", new Time(dummyEndTime), new Time(dummyEndTime+1000));

        // add hard coded meetings to our meeting model
        MeetingModel.getInstance().getMeetings().add(m1);
        MeetingModel.getInstance().getMeetings().add(m2);
        MeetingModel.getInstance().getMeetings().add(m3);
        MeetingModel.getInstance().getMeetings().add(m4);*/

        Button suggestNow = (Button) findViewById(R.id.suggest_now);
        suggestNow.setOnClickListener(new SuggestNowListener(this));

        // Set view based on meeting model
        meeting_list_adapter = new MeetingListAdapter(this, R.layout.meeting_list_content,
                MeetingModel.getInstance().getMeetings());

        meetingListView = (ListView) findViewById(R.id.meeting_list);
        meetingListView.setAdapter(meeting_list_adapter);

        // Listeners
        // Edit meeting listener
        meetingListView.setOnItemClickListener(new EditMeetingListener(this));

        // Listener for adding a meeting to friend tracker app
        View fab_add_contact = findViewById(R.id.fab_add_meeting);
        fab_add_contact.setOnClickListener(new AddMeetingListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.meeting_list_menu_options,menu);

        //Menu items listeners

        //Add Meeting
        MenuItem addMeetingMenuItem = menu.findItem(R.id.add_meeting_item);
        addMeetingMenuItem.setOnMenuItemClickListener(new AddMeetingListener(this));


        //Display friend list
        MenuItem displayFriendListMenuItem = menu.findItem(R.id.display_friend_list_item);
        displayFriendListMenuItem.setOnMenuItemClickListener(new DisplayFriendListListener(this));

        return true;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");

        dbMeeting = new DBMeetingHelper(this);
        dbMeeting.loadMeetings();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        meetingListView.setAdapter(meeting_list_adapter);
        Log.i(LOG_TAG, "onResume()");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.i(LOG_TAG, "onStop()");
        dbMeeting.saveMeetings(MeetingModel.getInstance().getMeetings());
    }
}

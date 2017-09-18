package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.controller.meeting.AddMeetingListener;
import mad.friend.controller.friend.DisplayFriendListListener;
import mad.friend.controller.meeting.MeetingListAdapter;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list);

        // Hard coded meetings to test layout
        Meeting m1 = new Meeting("meeting 1");
        Meeting m2 = new Meeting("meeting 2");
        Meeting m3 = new Meeting("meeting 3");
        Meeting m4 = new Meeting("meeting 4");

        // add hard coded meetings to our meeting model
        MeetingModel.getInstance().getMeetings().add(m1);
        MeetingModel.getInstance().getMeetings().add(m2);
        MeetingModel.getInstance().getMeetings().add(m3);
        MeetingModel.getInstance().getMeetings().add(m4);

        // Set view based on meeting model
        meeting_list_adapter = new MeetingListAdapter(this, R.layout.meeting_list_content,
                MeetingModel.getInstance().getMeetings());

        meetingListView = (ListView) findViewById(R.id.meeting_list);
        meetingListView.setAdapter(meeting_list_adapter);

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
}

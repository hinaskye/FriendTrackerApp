package mad.friend.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.controller.meeting.CompleteAddMeetingListener;
import mad.friend.controller.meeting.MeetingAddFriendListener;
import mad.friend.controller.meeting.MeetingDateListener;
import mad.friend.controller.meeting.MeetingFriendListAdapter;
import mad.friend.controller.meeting.MeetingLocationListener;
import mad.friend.controller.meeting.MeetingTimeListener;
import mad.friend.controller.meeting.MeetingTitleListener;
import mad.friend.model.Friend;
import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import util.FriendTrackerUtil;

/**
 * Add Meeting Activity
 * Adds a new meeting to the MeetingModel upon completion
 */
public class AddMeetingActivity extends AppCompatActivity
{
    private String LOG_TAG = this.getClass().getName();
    private Meeting meeting = new Meeting();
    private List<Friend> meeting_friends = new ArrayList<>();
    private int startTime = 0, endTime = 1;
    private int latitude = 0, longitude = 1;

    ArrayAdapter adapter;
    ListView meeting_friend_list;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");

        setContentView(R.layout.add_meeting);

        // set new meeting id to random unique id of length 4
        meeting.setId(FriendTrackerUtil.uniqueId(4));

        // Friend list view for each friend added
        meeting_friend_list = (ListView) findViewById(R.id.meeting_friends_invited);
        adapter = new MeetingFriendListAdapter(this, R.layout.meeting_friend_list_content,
                meeting.getInvited());
        meeting_friend_list.setAdapter(adapter);

        setListViewHeightBasedOnChildren(meeting_friend_list);

        // Listeners
        // Title Listener
        TextView meetingTitle = (EditText) findViewById(R.id.edit_meeting_title);
        meetingTitle.addTextChangedListener(new MeetingTitleListener(this, meeting));

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

        // Add Friend Listener
        Button addFriend = (Button) findViewById(R.id.meeting_add_friend_button);
        addFriend.setOnClickListener(new MeetingAddFriendListener(this));

        // Location Listener
        // Latitude
        TextView meetingLatitude = (EditText) findViewById((R.id.meeting_latitude));
        meetingLatitude.addTextChangedListener(new MeetingLocationListener(this, meeting, latitude));

        // Longitude
        TextView meetingLongitude = (EditText) findViewById(R.id.meeting_longitude);
        meetingLongitude.addTextChangedListener(new MeetingLocationListener(this, meeting, longitude));

        // Complete Add Meeting Listener
        Button addMeeting = (Button) findViewById(R.id.add_meeting_button);
        addMeeting.setOnClickListener(new CompleteAddMeetingListener(this, meeting));
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
        // refresh view based on List<Friend> meeting_friends to be added to meeting model
        meeting_friend_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(meeting_friend_list); // fixes listview within scrollview
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FriendTrackerUtil.MEETING_ADD_FRIEND) {
            if(resultCode == RESULT_OK){
                Friend newMeetingFriend = (Friend) data.getSerializableExtra("friend");
                if(newMeetingFriend!=null)
                    meeting.addFriend(newMeetingFriend);
            }
        }
    }//onActivityResult

    // Code taken from https://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
    }
}

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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import hinaskye.assignment1.R;
import mad.friend.controller.DisplayMapsListener;
import mad.friend.controller.meeting.DeleteMeetingListener;
import mad.friend.controller.meeting.MeetingAddFriendListener;
import mad.friend.controller.meeting.MeetingDateListener;
import mad.friend.controller.meeting.MeetingFriendListAdapter;
import mad.friend.controller.meeting.MeetingLocationListener;
import mad.friend.controller.meeting.MeetingTimeListener;
import mad.friend.controller.meeting.MeetingTitleListener;
import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;
import mad.friend.model.database.DBFriendHelper;
import mad.friend.model.database.DBMeetingHelper;
import util.FriendTrackerUtil;

/**
 * EditMeetingActivity
 * Allows user to edit a meeting details
 */
public class EditMeetingActivity extends AppCompatActivity {

    private String LOG_TAG = this.getClass().getName();
    private Meeting meetingSelected;
    private int startTime = 0, endTime = 1;
    private int latitude = 0, longitude = 1;

    private DBFriendHelper dbFriend;
    private DBMeetingHelper dbMeeting;
    ArrayAdapter adapter;
    ListView meeting_friend_list;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");

        dbFriend = new DBFriendHelper(this);
        dbMeeting = new DBMeetingHelper(this);

        // set view
        setContentView(R.layout.edit_meeting);

        // variables
        EditText editTile = (EditText) findViewById(R.id.edit_meeting_title);
        EditText editDate = (EditText) findViewById(R.id.meeting_edit_date);

        // This view should only be called when we select a meeting from our meeting list activity
        // extras includes position on listview
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int position = extras.getInt("position");
            // return the friend object from the given position
            meetingSelected = MeetingModel.getInstance().getMeetings().get(position);

            // friend list view for each friend added
            meeting_friend_list = (ListView) findViewById(R.id.meeting_friends_invited);
            adapter = new MeetingFriendListAdapter(this, R.layout.meeting_friend_list_content,
                    meetingSelected.getInvited());
            meeting_friend_list.setAdapter(adapter);

            setListViewHeightBasedOnChildren(meeting_friend_list);

            // listeners
            // Title Listener
            TextView meetingTitle = (EditText) findViewById(R.id.edit_meeting_title);
            meetingTitle.setText(meetingSelected.getTitle());
            meetingTitle.addTextChangedListener(new MeetingTitleListener(this, meetingSelected));

            // Date Listener
            TextView meetingDate = (EditText) findViewById(R.id.meeting_edit_date);
            meetingDate.setText(new SimpleDateFormat("dd MMM yyyy").format(meetingSelected.getDate()));
            meetingDate.setOnClickListener(new MeetingDateListener(this, meetingDate, meetingSelected));

            // Time Listener
            TextView meetingStartTime = (EditText) findViewById(R.id.meeting_start_time);
            meetingStartTime.setText(meetingSelected.getStartTime().toString());
            meetingStartTime.setOnClickListener(
                    new MeetingTimeListener(this, meetingStartTime, meetingSelected, startTime));

            TextView meetingEndTime = (EditText) findViewById(R.id.meeting_end_time);
            meetingEndTime.setText(meetingSelected.getEndTime().toString());
            meetingEndTime.setOnClickListener(
                    new MeetingTimeListener(this, meetingEndTime, meetingSelected, endTime));

            // Add Friend Listener
            Button addFriend = (Button) findViewById(R.id.meeting_add_friend_button);
            addFriend.setOnClickListener(new MeetingAddFriendListener(this));

            // Location Listener
            TextView meetingLocation = (TextView) findViewById(R.id.meeting_location_text);
            if(meetingSelected.getLatitude() != 0 && meetingSelected.getLongitude() != 0)
            {
                ImageView locationIcon = (ImageView) findViewById(R.id.location_icon);
                locationIcon.setVisibility(View.VISIBLE);
                locationIcon.setOnClickListener(new DisplayMapsListener(this,
                        meetingSelected.getLatitude(), meetingSelected.getLongitude()));
                meetingLocation.setOnClickListener(new DisplayMapsListener(this,
                        meetingSelected.getLatitude(), meetingSelected.getLongitude()));
            }
            // Latitude
            TextView meetingLatitude = (EditText) findViewById((R.id.meeting_latitude));
            meetingLatitude.setText(String.format(Locale.getDefault(),"%.2f",meetingSelected.getLatitude()));
            meetingLatitude.addTextChangedListener(new MeetingLocationListener(this, meetingSelected, latitude));

            // Longitude
            TextView meetingLongitude = (EditText) findViewById(R.id.meeting_longitude);
            meetingLongitude.setText(String.format(Locale.getDefault(),"%.2f",meetingSelected.getLongitude()));
            meetingLongitude.addTextChangedListener(new MeetingLocationListener(this, meetingSelected, longitude));

            // Complete Add Meeting Listener
            Button removeMeeting = (Button) findViewById(R.id.remove_meeting_button);
            removeMeeting.setOnClickListener(new DeleteMeetingListener(this, meetingSelected));
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
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
        dbFriend.saveFriends(FriendModel.getInstance().getFriends());
        dbMeeting.saveMeetings(MeetingModel.getInstance().getMeetings());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FriendTrackerUtil.MEETING_ADD_FRIEND) {
            if(resultCode == RESULT_OK){
                Friend newMeetingFriend = (Friend) data.getSerializableExtra("friend");
                if(newMeetingFriend!=null)
                    meetingSelected.addFriend(newMeetingFriend);
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
}

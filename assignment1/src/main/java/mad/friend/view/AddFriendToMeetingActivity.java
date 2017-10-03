package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import hinaskye.assignment1.R;
import mad.friend.controller.meeting.AddFriendToMeetingListener;
import mad.friend.controller.friend.FriendListAdapter;
import mad.friend.model.FriendModel;

/**
 * Add Friend To Meeting Activity
 */
public class AddFriendToMeetingActivity extends AppCompatActivity
{
    // variables
    private String LOG_TAG = this.getClass().getName();

    private ArrayAdapter adapter;
    private ListView friendListView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.friend_list_view);

        Log.i(LOG_TAG, "OnCreate()");

        // Sets friend list contents based on friend model list
        adapter = new FriendListAdapter(this, R.layout.friend_list_content,
                FriendModel.getInstance().getFriends());

        friendListView = (ListView) findViewById(R.id.friend_list_view);
        friendListView.setAdapter(adapter);

        // Set onclick listener for each friend
        friendListView.setOnItemClickListener(new AddFriendToMeetingListener(this));
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

        // Repaints the contents of the friendListView based on our friend model
        friendListView.setAdapter(adapter);
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
}//class

package mad.friend.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import hinaskye.assignment1.R;
import mad.friend.SaveInDBAsyncTask;
import mad.friend.controller.DistanceCalcThread;
import mad.friend.receiver.LocationReceiver;
import mad.friend.LocationService;
import mad.friend.receiver.NetworkChangeReceiver;
import mad.friend.receiver.NotificationReceiver;
import mad.friend.controller.friend.AddContactListener;
import mad.friend.controller.meeting.DisplayMeetingListListener;
import mad.friend.controller.friend.EditFriendListener;
import mad.friend.model.database.DBMeetingHelper;
import mad.friend.view.model.FriendListAdapter;
import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.model.contact.ContactDataManager;
import mad.friend.model.database.DBFriendHelper;
import mad.friend.model.stub.TestLocationService;
import util.FriendTrackerUtil;

/**
 * Friend List Activity
 * Shows your list of friends and their location if its available
 */
public class FriendListActivity extends AppCompatActivity
{
    // variables
    private String LOG_TAG = this.getClass().getName();
    private String friendTrackerPrefs = "FriendTrackerPrefs";

    protected static final int PICK_CONTACTS = 100;
    private final int ID_LENGTH = 4; //~4^36 available ids from a-z0-9
    private boolean receiversCreated = false;

    private ArrayAdapter adapter;
    private ListView friendListView;

    private DBFriendHelper dbFriend;
    private DBMeetingHelper dbMeeting;
    BroadcastReceiver networkChangeReceiver;
    BroadcastReceiver locationReceiver;
    BroadcastReceiver notificationReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.friend_list_view);

        dbFriend = new DBFriendHelper(this);
        dbMeeting = new DBMeetingHelper(this);

        Log.i(LOG_TAG, "OnCreate()");

        // Sets friend list contents based on friend model list
        adapter = new FriendListAdapter(this, R.layout.friend_list_content,
                FriendModel.getInstance().getFriends());

        friendListView = (ListView) findViewById(R.id.friend_list_view);
        friendListView.setAdapter(adapter);

        // Add an edit listener to each item on long click
        friendListView.setOnItemClickListener(new EditFriendListener(this));

        // Listener for adding contact to friend tracker app on our floating action bar
        View fab_add_contact = findViewById(R.id.fab_add_contact);
        fab_add_contact.setOnClickListener(new AddContactListener(this));

        // Set the default settings of our shared preferences
        setSharedPref();

        // Requests for location permission on start up,
        // refer to controller.meeting.SuggestNowListener for example of request rational
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationPermission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FriendTrackerUtil.LOCATION_PERMISSION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.friend_list_menu_options,menu);

        // Menu items listeners
        // Add contact
        MenuItem addFriendMenuItem = menu.findItem(R.id.add_contact_item);
        addFriendMenuItem.setOnMenuItemClickListener(new AddContactListener(this));

        // Display meeting
        MenuItem displayMeetingMenuItem = menu.findItem(R.id.display_meeting_item);
        displayMeetingMenuItem.setOnMenuItemClickListener(new DisplayMeetingListListener(this));

        return true;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");

        dbFriend.loadFriends();
        dbMeeting.loadMeetings();
        Log.i(LOG_TAG, "Loaded Model from DB");

        /*
        // Upcoming Meeting Notifications
        UpcomingMeetingNotification upcomingMeetingNotification = new UpcomingMeetingNotification(this, 1);
        // Make notification for just the first meeting to test code
        Notification firstMeetingNotif = upcomingMeetingNotification.getNotification(MeetingModel.getInstance().getMeetings().get(0));
        // notify us in 5 seconds
        //new FriendTrackerAlarmManager(this).scheduleDelayNotification(firstMeetingNotif, id, 5000);
        upcomingMeetingNotification.repeatNotification(firstMeetingNotif, id, 0.5);*/

        Thread thread = new DistanceCalcThread(this);
        thread.start();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
        // Use dummy data to get location of friends
        TestLocationService.test(this);
        Log.i(LOG_TAG, FriendModel.getInstance().toString());
        FriendModel.getInstance().printFriendLocation();
        friendListView.setAdapter(adapter);

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(locationPermission == PackageManager.PERMISSION_GRANTED && !receiversCreated)
        {
            createReceivers();
            // Starts a service to get location data
            startService(new Intent(this, LocationService.class));
            receiversCreated = true;
        }
        // Sends an intent to check network changes, Registers network change receiver to class
        IntentFilter networkStatus = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        if(networkChangeReceiver != null)
        {
            this.registerReceiver(networkChangeReceiver, networkStatus);
        }
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data)
     {   // Code as given in ContactDataManager
         Log.i(LOG_TAG, "onActivityResult");
         if (requestCode == PICK_CONTACTS)
         {
             if (resultCode == RESULT_OK)
             {
                 ContactDataManager contactsManager = new ContactDataManager(this, data);
                 String name = "";
                 String email = "";

                 try
                 {
                     name = contactsManager.getContactName();
                     email = contactsManager.getContactEmail();

                     // Only add new friend if they are unique, checked by email address
                     if(FriendTrackerUtil.isUniqueEmail(email))
                     {
                         // Generate random id for new friend
                         String uniqueId = FriendTrackerUtil.uniqueId(ID_LENGTH);
                         // Add new friend to model with email
                         Friend newFriend = new Friend(uniqueId, name, email);

                         // For now will use hard coded id to match dummy data id to print location
                         /*
                         Friend newFriend = new Friend(
                                 Integer.toString(idForDummyData++), name, email);*/

                         // Add new friend to the model
                         FriendModel.getInstance().addFriend(newFriend);
                     }
                     else
                     {
                         Toast.makeText(this, "Friend already added", Toast.LENGTH_SHORT).show();
                     }

                     // Debugging
                     System.out.printf("FriendModel %s", FriendModel.getInstance().toString());
                 } catch (ContactDataManager.ContactQueryException e) {
                     Log.e(LOG_TAG, e.getMessage());
                 }//try catch
             }//if
         }//if
     }//onActivityResult

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

        AsyncTask saveInDB = new SaveInDBAsyncTask(this);
        saveInDB.execute();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy()");
        if(networkChangeReceiver!=null)
        {
            this.unregisterReceiver(networkChangeReceiver);
        }
    }

    public void createReceivers()
    {
        networkChangeReceiver = new NetworkChangeReceiver();
        locationReceiver = new LocationReceiver();
        notificationReceiver = new NotificationReceiver();
    }

    public void setSharedPref()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(friendTrackerPrefs, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("Meeting Suggest Frequency", 5);
        edit.putInt("Meeting Reminder Before", 5);
        edit.commit();
    }
}//class

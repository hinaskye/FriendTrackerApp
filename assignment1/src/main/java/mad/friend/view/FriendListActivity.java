package mad.friend.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hinaskye.assignment1.R;
import mad.friend.controller.AddContactListener;
import mad.friend.controller.DisplayMeetingListListener;
import mad.friend.controller.EditFriendListener;
import mad.friend.controller.FriendListAdapter;
import mad.friend.model.Friend;
import mad.friend.model.FriendModel;
import mad.friend.model.contact.ContactDataManager;
import mad.friend.model.stub.TestLocationService;
import util.FriendTrackerUtil;

/**
 * Friend List Activity
 */
public class FriendListActivity extends AppCompatActivity
{
    // variables
    private String LOG_TAG = this.getClass().getName();

    protected static final int PICK_CONTACTS = 100;
    private final int ID_LENGTH = 4; //~4^36 available ids from a-z0-9
    // idforDummyData is only used with dummy data, unique id code is also available
    private static int idForDummyData = 1;

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

        // Add an edit listener to each item on long click
        friendListView.setOnItemLongClickListener(new EditFriendListener(this));

        // Listener for adding contact to friend tracker app on our floating action bar
        View fab_add_contact = findViewById(R.id.fab_add_contact);
        fab_add_contact.setOnClickListener(new AddContactListener(this));

        // Use dummy data
        TestLocationService.test(this);
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
                         /* WORKING CODE
                         // Generate random id for new friend
                         String uniqueId = FriendTrackerUtil.uniqueId(ID_LENGTH);
                         // Add new friend to model with email
                         Friend newFriend = new Friend(uniqueId, name, email);*/

                         // For now will use hard coded id to match dummy data id to print location
                         Friend newFriend = new Friend(
                                 Integer.toString(idForDummyData++), name, email);

                         // Add new friend to the model
                         FriendModel.getInstance().addFriend(newFriend);
                     }

                     // Debugging
                     System.out.printf("FriendModel %s", FriendModel.getInstance().toString());
                 } catch (ContactDataManager.ContactQueryException e) {
                     Log.e(LOG_TAG, e.getMessage());
                 }//try catch
             }//if
         }//if
     }//onActivityResult
}//class

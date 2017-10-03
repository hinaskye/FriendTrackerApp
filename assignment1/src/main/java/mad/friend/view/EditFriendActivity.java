package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import hinaskye.assignment1.R;
import mad.friend.controller.friend.BirthdayListener;
import mad.friend.controller.friend.DeleteFriendListener;
import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * Edit Friend Activity
 *
 */
public class EditFriendActivity extends AppCompatActivity
{
    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");

        // set view
        setContentView(R.layout.edit_friend);

        // variables
        TextView name = (TextView) findViewById(R.id.edit_friend_name);
        TextView email = (TextView) findViewById(R.id.friend_email_text);
        TextView birthday = (TextView) findViewById(R.id.edit_birthday);
        Bundle extras = getIntent().getExtras();

        // This view should only be called when we select a friend on our friend list
        // extras includes position on listview
        if(extras != null)
        {
            int position = extras.getInt("position");
            // return the friend object from the given position
            Friend friendSelected = FriendModel.getInstance().getFriends().get(position);

            // set text fields of friend name and email
            name.setText(friendSelected.getName());
            email.setText(friendSelected.getEmail());
            // displays birthday if it is set
            if(friendSelected.getBirthday() != null)
            {
                birthday.setText(new SimpleDateFormat("dd MMMM yyyy").format(
                        friendSelected.getBirthday()));
            }

            // Edit friend birthday and listener
            birthday.setOnClickListener(new BirthdayListener(this, birthday, friendSelected));

            // Remove friend button and listener
            View removeFriendButton = findViewById(R.id.remove_friend_button);
            removeFriendButton.setOnClickListener(new DeleteFriendListener(this,friendSelected));
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
    }
}

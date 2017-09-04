package mad.friend.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import hinaskye.assignment1.R;

/**
 * Add Meeting Activity
 * Current just shows layout for adding a meeting
 */
public class AddMeetingActivity extends AppCompatActivity
{
    private String LOG_TAG = this.getClass().getName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");

        setContentView(R.layout.add_meeting);
    }
}

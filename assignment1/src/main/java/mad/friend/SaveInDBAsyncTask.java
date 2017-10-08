package mad.friend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import mad.friend.model.FriendModel;
import mad.friend.model.MeetingModel;
import mad.friend.model.database.DBFriendHelper;
import mad.friend.model.database.DBMeetingHelper;

/**
 * Save Model to Database
 */
public class SaveInDBAsyncTask extends AsyncTask {

    private Context context;
    private DBFriendHelper DBFriend;
    private DBMeetingHelper DBMeeting;
    private String LOG_TAG = this.getClass().getName();

    public SaveInDBAsyncTask(Context context)
    {
        this.context = context;
        DBFriend = new DBFriendHelper(context);
        DBMeeting = new DBMeetingHelper(context);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        DBFriend.saveFriends(FriendModel.getInstance().getFriends());
        DBMeeting.saveMeetings(MeetingModel.getInstance().getMeetings());
        Log.i(LOG_TAG, "Saved model in DB");
        return null;
    }
}

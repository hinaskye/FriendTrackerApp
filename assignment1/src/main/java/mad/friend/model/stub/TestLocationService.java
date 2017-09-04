package mad.friend.model.stub;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import mad.friend.model.FriendModel;

// Suppoort code by Caspar for MAD assignment s2 2017
// Simple example to test dummy location service (for demonstration only)
// Usage: add class in appropriate package (also see TestLocationService.java)
//       TestLocationService.test(this); // pass a valid Context (NOTE: Activity extends Context)
public class TestLocationService
{
    private static final String LOG_TAG = DummyLocationService.class.getName();

    // call this method to run simple hard coded test
    public static void test(Context context)
    {
        DummyLocationService dummyLocationService=DummyLocationService.getSingletonInstance(context);

        Log.i(LOG_TAG, "File Contents:");
        dummyLocationService.logAll();
        List<DummyLocationService.FriendLocation> matched = null;
        try
        {
            // 2 mins either side of 9:46:30 AM
            matched = dummyLocationService.getFriendLocationsForTime(DateFormat.getTimeInstance(
                    DateFormat.MEDIUM).parse("9:44:30 AM"), 2, 0);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Matched Query:");
        dummyLocationService.log(matched);
        // Will any any matched result of friend's location to our friend model
        for(DummyLocationService.FriendLocation match : matched)
        {
            match.addToFriendModel();
        }
    }
}

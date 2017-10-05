package mad.friend.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import mad.friend.model.Meeting;
import mad.friend.model.MeetingModel;

/**
 * Created by Hinaskye on 5/10/2017.
 */

public class DBMeetingHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FriendTracker.db";
    public static final String MEETING_TABLE_NAME = "meeting";
    public static final String MEETING_COLUMN_ID = "id";
    public static final String MEETING_COLUMN_TITLE = "title";
    public static final String MEETING_COLUMN_DATE = "date";
    public static final String MEETING_COLUMN_START_TIME = "startTime";
    public static final String MEETING_COLUMN_END_TIME = "endTime";
    public static final String MEETING_COLUMN_LATITUDE = "latitude";
    public static final String MEETING_COLUMN_LONGITUDE = "longitude";

    private String LOG_TAG = this.getClass().getName();

    public DBMeetingHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS meeting(id TEXT PRIMARY KEY, title TEXT," +
                "date INTEGER, startTime INTEGER, endTime INTEGER, latitude INTEGER," +
                "longitude INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    public Cursor getAllMeetings()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        // placed here for now as getting errors that meeting table does not exist
        db.execSQL("CREATE TABLE IF NOT EXISTS meeting(id TEXT PRIMARY KEY, title TEXT," +
                "date INTEGER, startTime INTEGER, endTime INTEGER, latitude INTEGER," +
                "longitude INTEGER);");
        Cursor resultSet = db.rawQuery("SELECT * FROM meeting", null);
        return resultSet;
    }

    public void loadMeetings()
    {
        Cursor resultSet = getAllMeetings();
        while (resultSet.moveToNext())
        {
            String id = resultSet.getString(resultSet.getColumnIndex(MEETING_COLUMN_ID));
            String title = resultSet.getString(resultSet.getColumnIndex(MEETING_COLUMN_TITLE));
            long date = resultSet.getLong(resultSet.getColumnIndex(MEETING_COLUMN_DATE));
            long startTime = resultSet.getLong(resultSet.getColumnIndex(MEETING_COLUMN_START_TIME));
            long endTime = resultSet.getLong(resultSet.getColumnIndex(MEETING_COLUMN_END_TIME));
            double latitude = resultSet.getDouble(resultSet.getColumnIndex(MEETING_COLUMN_LATITUDE));
            double longitude = resultSet.getDouble(resultSet.getColumnIndex(MEETING_COLUMN_LONGITUDE));
            Meeting meeting = new Meeting(id, title, new Date(date), new Time(startTime),
                    new Time(endTime), latitude, longitude);
            MeetingModel.getInstance().addMeeting(meeting);
        }
    }

    public void saveMeetings(List<Meeting> meetings)
    {
        boolean inDB = false;
        Cursor resultSet = getAllMeetings();
        for(Meeting meeting : meetings)
        {
            // check if meeting already in DB
            while (resultSet.moveToNext())
            {
                String id = resultSet.getString(resultSet.getColumnIndex(MEETING_COLUMN_ID));
                if(meeting.getId().equals(id))
                {
                    inDB = true;
                }
            }

            // add meeting to database if not in DB
            if(!inDB)
            {
                insertMeeting(meeting);
            }
        }
    }

    public void insertMeeting(Meeting meeting)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues meeting_values = new ContentValues();
        meeting_values.put(MEETING_COLUMN_ID, meeting.getId());
        meeting_values.put(MEETING_COLUMN_TITLE, meeting.getTitle());
        meeting_values.put(MEETING_COLUMN_DATE, meeting.getDate().getTime());
        meeting_values.put(MEETING_COLUMN_START_TIME, meeting.getStartTime().getTime());
        meeting_values.put(MEETING_COLUMN_END_TIME, meeting.getEndTime().getTime());
        meeting_values.put(MEETING_COLUMN_LATITUDE, meeting.getLatitude());
        meeting_values.put(MEETING_COLUMN_LONGITUDE, meeting.getLongitude());

        long newRowId = db.insert(MEETING_TABLE_NAME, null, meeting_values);
        Log.i(LOG_TAG, "Meeting inserted:"+meeting.toString());
    }

    public void deleteMeeting(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM meeting WHERE id='"+id+"';");
    }

    // Need to insert edit meeting options
}

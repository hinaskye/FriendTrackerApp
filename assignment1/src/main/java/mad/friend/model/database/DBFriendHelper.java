package mad.friend.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.List;

import mad.friend.model.Friend;
import mad.friend.model.FriendModel;

/**
 * Created by Hinaskye on 3/10/2017.
 */

public class DBFriendHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FriendTracker.db";
    public static final String FRIEND_TABLE_NAME = "friend";
    public static final String FRIEND_COLUMN_ID = "id";
    public static final String FRIEND_COLUMN_NAME = "name";
    public static final String FRIEND_COLUMN_EMAIL = "email";
    public static final String FRIEND_COLUMN_BIRTHDAY = "birthday";

    private String LOG_TAG = this.getClass().getName();

    public DBFriendHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS friend(id TEXT PRIMARY KEY, name TEXT," +
                "email TEXT, birthday INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    public Cursor getAllFriends()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("SELECT * FROM friend", null);
        return resultSet;
    }

    public Cursor getAllFriendsId()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = db.rawQuery("SELECT id FROM friend", null);
        return resultSet;
    }

    public void loadFriends()
    {
        Cursor resultSet = getAllFriends();
        while(resultSet.moveToNext())
        {
            String id = resultSet.getString(resultSet.getColumnIndex(FRIEND_COLUMN_ID));
            String name = resultSet.getString(resultSet.getColumnIndex(FRIEND_COLUMN_NAME));
            String email = resultSet.getString(resultSet.getColumnIndex(FRIEND_COLUMN_EMAIL));
            long birthday = resultSet.getLong(resultSet.getColumnIndex(FRIEND_COLUMN_BIRTHDAY));
            Friend friend;
            // check if birthday is not assigned
            if(birthday == 0)
            {
                friend = new Friend(id, name, email);
            }
            else
                friend = new Friend(id, name, email, new Date(birthday));

            FriendModel.getInstance().addFriend(friend);
        }
        resultSet.close();
    }

    public void saveFriends(List<Friend> friends)
    {
        boolean inDB = false;
        Cursor resultSet = getAllFriends();

        for(Friend friend : friends)
        {
            Log.i(LOG_TAG, String.format("%s", friend.getName()));
            // Check if friend already in DB
            while(resultSet.moveToNext())
            {
                if(friend.getId().equals(resultSet.getString(
                        resultSet.getColumnIndex(FRIEND_COLUMN_ID))))
                {
                    Log.i(LOG_TAG, String.format("%s in database", friend.getName()));
                    inDB = true;
                }
            }
            resultSet.moveToFirst();

            // Else insert friend to DB
            if(inDB == false)
            {
                Log.i(LOG_TAG, String.format("%s Not in database", friend.getName()));
                insertFriend(friend);
            }
            // reset for each friend
            inDB = false;
        }
        resultSet.close();
    }

    public void insertFriend(Friend friend)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues friend_values = new ContentValues();
        friend_values.put(FRIEND_COLUMN_ID, friend.getId());
        friend_values.put(FRIEND_COLUMN_NAME, friend.getName());
        friend_values.put(FRIEND_COLUMN_EMAIL, friend.getEmail());
        if(friend.getBirthday() != null)
        {
            friend_values.put(FRIEND_COLUMN_BIRTHDAY, friend.getBirthday().getTime());
            long newRowId = db.insert(FRIEND_TABLE_NAME, null, friend_values);
        }
        else
        {
            long newRowId = db.insert(FRIEND_TABLE_NAME, null, friend_values);
        }
        Log.i(LOG_TAG, "Friend inserted:"+friend.toString());
    }

    public void updateBirthday(Friend friend)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE friend SET birthday='"+friend.getBirthday().getTime()+"' WHERE " +
                "id='"+friend.getId()+"';");
    }

    public void deleteFriend(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM friend WHERE id='"+id+"';");
    }
}// class

package mad.friend.model.contact;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

/**
 * <p>
 * Class used to retrieve information about a Contact selected through the Android
 * Contacts Picker Activity. requires permission android.permission.READ_CONTACTS
 * </p>
 * <p>
 *    Simplified manifest permissions for targetSdkVersion<=22
 *        <uses-permission android:name="android.permission.READ_CONTACTS" />
 *
 * Below is a simple example usage of this class from an Activity class
 * <p>
 * launch the contact picker and to promote the user to select an attendee
 * <p>
 * <code>
 * protected static final int PICK_CONTACTS = 100;
 * <p>
 * Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
 * startActivityForResult(contactPickerIntent, PICK_CONTACTS);
 * </code>
 * <p>
 * grab the result in onActivityResult()
 * <code>
 *
 * @author ermyasabebe
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * if (requestCode == PICK_CONTACTS) {
 * if (resultCode == RESULT_OK) {
 * ContactDataManager contactsManager = new ContactDataManager(this, data);
 * String name = "";
 * String email = "";
 * try {
 * name = contactsManager.getContactName();
 * email = contactsManager.getContactEmail();
 * } catch (ContactQueryException e) {
 * Log.e(LOG_TAG, e.getMessage());
 * }
 * <p>
 * }
 * <p>
 * }
 * }
 * </code>
 */
public class ContactDataManager
{
    private static final String LOG_TAG = ContactDataManager.class.getName();
    private Context context;
    private Intent intent;

    public class ContactQueryException extends Exception
    {
        private static final long serialVersionUID = 1L;
        public ContactQueryException(String message)
        {
            super(message);
        }
    }

    /**
     * @param aContext The context through which the Android Contacts Picker Activity
     *                 was launched
     * @param anIntent The intent returned from the Android Contacts Picker Activity
     */
    public ContactDataManager(Context aContext, Intent anIntent)
    {
        this.context = aContext;
        this.intent = anIntent;
    }

    /**
     * Retrieves the display Name of a contact
     *
     * @return Name of the contact referred to by the URI specified through the
     * intent, {@link ContactDataManager#intent}
     * @throws ContactQueryException if querying the Contact Details Fails
     */
    public String getContactName() throws ContactQueryException
    {
        Cursor cursor = null;
        String name = null;
        try
        {
            cursor = context.getContentResolver().query(intent.getData(), null,
                    null, null, null);
            if (cursor.moveToFirst())
                name = cursor.getString(cursor
                        .getColumnIndexOrThrow(Contacts.DISPLAY_NAME));

        } catch (Exception e)
        {
            Log.e(LOG_TAG, e.getMessage());
            throw new ContactQueryException(e.getMessage());
        } finally
        {
            if (cursor != null)
                cursor.close();
        }

        return name;
    }

    /**
     * Retrieves the email of a contact
     *
     * @return Email of the contact referred to by the URI specified through the
     * intent, {@link ContactDataManager#intent}
     * @throws ContactQueryException if querying the Contact Details Fails
     */
    public String getContactEmail() throws ContactQueryException
    {
        Cursor cursor = null;
        String email = null;
        try
        {

            cursor = context.getContentResolver().query(Email.CONTENT_URI,
                    null, Email.CONTACT_ID + "=?",
                    new String[]{intent.getData().getLastPathSegment()},
                    null);

            if (cursor.moveToFirst())
                email = cursor.getString(cursor.getColumnIndex(Email.DATA));

        } catch (Exception e)
        {
            Log.e(LOG_TAG, e.getMessage());
            throw new ContactQueryException(e.getMessage());
        } finally
        {
            if (cursor != null)
                cursor.close();
        }

        return email;
    }

}
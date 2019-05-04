package com.rachit.mycontactsapp.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.database.ContactsRepository;

public class ContactObserver extends ContentObserver {
    private static final String TAG = "ContactObserver";

    private Application application;

    ContactObserver(Handler handler, Application application) {
        super(handler);
        this.application = application;
    }

    @Override
    public boolean deliverSelfNotifications() {
        Log.e(TAG, "deliverSelfNotifications: Self Changed");
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        ContentResolver resolver = application.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP + " DESC");
        if (cursor != null) {
            cursor.moveToNext();
            Contact contact = new Contact(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
            );
            ContactsRepository repo = new ContactsRepository(application);
            Contact dbContact = repo.getContactById(contact.getId());
            if (contact.getId().equals(dbContact.getId())) {
                if (!contact.equals(dbContact)) {
                    repo.updateContact(contact);
                }
            } else repo.insert(contact);

            Log.e(TAG, "onChange: " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            cursor.close();
        }
    }
}

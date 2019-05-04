package com.rachit.mycontactsapp.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.utils.ContactService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactsRepository {

    private ContactsDao dao;
    private LiveData<List<Contact>> contacts;
    private ContentResolver resolver;

    public ContactsRepository(Application application) {
        ContactsDb db = ContactsDb.getInstance(application);
        dao = db.contactsDao();
        contacts = dao.getAllContacts();
        this.resolver = application.getContentResolver();
        application.startService(new Intent(application, ContactService.class));
    }

    public LiveData<List<Contact>> getAllContacts() {
        if (contacts.getValue() == null || contacts.getValue().isEmpty()) {
            fetchContacts();
        }
        return contacts;
    }

    private void fetchContacts() {
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<Contact> contacts = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Contact contact = new Contact(id, contactName, photoUri);
                contacts.add(contact);
            }
            cursor.close();
            insertAll(contacts);
        }
    }


    public Contact getContactById(String id) {
        List<Contact> contact = dao.getContactById(id);
        if (contact != null && contact.size() > 0)
            return contact.get(0);
        else return new Contact("-1", "-1", "-1");
    }

    public void updateContact(Contact contact) {
        dao.updateContact(contact);
    }

    public void insert(Contact contact) {
        new InsertAsyncTask(dao).execute(contact);
    }

    private void insertAll(ArrayList<Contact> contacts) {
        new InsertAsyncTask(dao).execute(contacts.toArray(new Contact[0]));
    }

    private static class InsertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactsDao mAsyncTaskDao;

        InsertAsyncTask(ContactsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insertAll(new ArrayList<>(Arrays.asList(params)));
            return null;
        }
    }

}

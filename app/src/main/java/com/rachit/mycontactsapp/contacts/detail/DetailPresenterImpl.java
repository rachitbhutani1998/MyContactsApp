package com.rachit.mycontactsapp.contacts.detail;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.rachit.mycontactsapp.models.Contact;

public class DetailPresenterImpl implements DetailPresenter {

    private DetailView view;
    private ContentResolver contentResolver;

    DetailPresenterImpl(DetailView view, Context context) {
        this.view = view;
        contentResolver = context.getContentResolver();
    }

    @Override
    public void loadDetails(Contact contact) {
        if (contact.getPhoneNo().isEmpty()) {
            Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contact.getId()}, null);
            if (cursor != null && cursor.moveToNext()) {
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.setPhoneNo(phone);
                view.updateContact(contact);
                cursor.close();
            }
        }
        if (contact.getMailId().isEmpty()) {
            Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contact.getId()}, null);
            if (cursor != null && cursor.moveToNext()) {
                String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                contact.setMailId(email);
                view.updateContact(contact);
                cursor.close();
            }
        }
    }
}

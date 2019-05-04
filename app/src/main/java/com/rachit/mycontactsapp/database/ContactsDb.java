package com.rachit.mycontactsapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rachit.mycontactsapp.models.Contact;

@Database(entities = {Contact.class}, version = 1)
abstract class ContactsDb extends RoomDatabase {
    abstract ContactsDao contactsDao();

    private static volatile ContactsDb DB_INSTANCE;

    static ContactsDb getInstance(final Context context) {
        if (DB_INSTANCE == null) {
            synchronized (ContactsDb.class) {
                if (DB_INSTANCE == null) {
                    DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactsDb.class, "contacts_db")
                            .build();
                }
            }
        }
        return DB_INSTANCE;
    }

}

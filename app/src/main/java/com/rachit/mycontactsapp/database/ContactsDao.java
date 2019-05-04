package com.rachit.mycontactsapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.rachit.mycontactsapp.models.Contact;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Contact> contacts);

    @Update
    void updateContact(Contact contact);

    @Query("SELECT * FROM CONTACTS_TABLE WHERE ID = :id")
    List<Contact> getContactById(String id);

    @Query("SELECT * FROM CONTACTS_TABLE ORDER BY name")
    LiveData<List<Contact>> getAllContacts();
}

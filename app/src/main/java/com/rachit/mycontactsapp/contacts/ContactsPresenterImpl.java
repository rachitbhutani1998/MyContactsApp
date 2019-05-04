package com.rachit.mycontactsapp.contacts;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.database.ContactsRepository;

import java.util.List;

public class ContactsPresenterImpl implements ContactsPresenter {

    private ContactsView view;
    private LiveData<List<Contact>> contacts;

    public ContactsPresenterImpl(ContactsView view, Application application) {
        this.view = view;
        ContactsRepository mRepo = new ContactsRepository(application);
        contacts = mRepo.getAllContacts();
    }

    @Override
    public void fetchContacts() {
        contacts.observeForever(new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                if (contacts != null && contacts.size() > 0) {
                    view.showEmptyView(false);
                    view.updateContactList(contacts);
                } else view.showEmptyView(true);
            }
        });
    }
}

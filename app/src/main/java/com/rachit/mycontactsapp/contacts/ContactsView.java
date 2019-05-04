package com.rachit.mycontactsapp.contacts;

import com.rachit.mycontactsapp.models.Contact;

import java.util.List;

public interface ContactsView {
    void updateContactList(List<Contact> contacts);

    void showEmptyView(boolean show);
}

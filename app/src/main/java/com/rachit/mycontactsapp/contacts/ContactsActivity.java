package com.rachit.mycontactsapp.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.R;
import com.rachit.mycontactsapp.contacts.detail.DetailFragment;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements ContactsView {

    private static final String TAG = ContactsActivity.class.getName();
    private static final int REQUEST_CONTACTS_PERM = 91;
    private ContactsPresenter mPresenter;
    private ContactsAdapter mAdapter;

    RecyclerView contactsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsRv = findViewById(R.id.contacts_rv);
        contactsRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactsAdapter(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            initializePresenter();
        } else
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACTS_PERM);
    }

    @Override
    public void updateContactList(List<Contact> contacts) {
        mAdapter.setCallback(contactCallback);
        mAdapter.setContacts(contacts);
        if (contactsRv.getAdapter() == null)
            contactsRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView(boolean show) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACTS_PERM && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            initializePresenter();
    }

    private void initializePresenter() {
        mPresenter = new ContactsPresenterImpl(this, getApplication());
        mPresenter.fetchContacts();
    }

    ContactsAdapter.ContactCallback contactCallback = new ContactsAdapter.ContactCallback() {
        @Override
        public void onContactClicked(Contact contact) {
            DetailFragment dialogFragment = new DetailFragment();
            dialogFragment.setContact(contact);
            dialogFragment.show(getSupportFragmentManager(), DetailFragment.CONTACT_KEY);
        }
    };
}

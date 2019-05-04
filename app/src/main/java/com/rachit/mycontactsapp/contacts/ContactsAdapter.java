package com.rachit.mycontactsapp.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private Context mContext;
    private List<Contact> mContacts;
    private ContactCallback callback;

    public void setCallback(ContactCallback callback) {
        this.callback = callback;
    }

    ContactsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    void setContacts(List<Contact> mContacts) {
        this.mContacts = mContacts;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ContactsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_contact, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder contactsViewHolder, int pos) {
        Contact contact = mContacts.get(pos);
        contactsViewHolder.nameTv.setText(contact.getName());
        if (contact.getPhotoUri() == null || contact.getPhotoUri().isEmpty()) {
            contactsViewHolder.imageIv.setVisibility(View.INVISIBLE);
            contactsViewHolder.imgPlaceHolder.setVisibility(View.VISIBLE);
            contactsViewHolder.imgPlaceHolder.setText(contact.getName().charAt(0) + "");
        } else {
            contactsViewHolder.imageIv.setVisibility(View.VISIBLE);
            contactsViewHolder.imgPlaceHolder.setVisibility(View.INVISIBLE);
            Glide.with(mContext).load(contact.getPhotoUri()).centerInside().into(contactsViewHolder.imageIv);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        TextView imgPlaceHolder;
        CircleImageView imageIv;

        ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            imageIv = itemView.findViewById(R.id.contact_image_view);
            imgPlaceHolder = itemView.findViewById(R.id.img_place_holder);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onContactClicked(mContacts.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ContactCallback {
        void onContactClicked(Contact contact);
    }
}

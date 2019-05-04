package com.rachit.mycontactsapp.contacts.detail;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rachit.mycontactsapp.models.Contact;
import com.rachit.mycontactsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DialogFragment implements DetailView {

    public static final String CONTACT_KEY = "contact";
    ImageView contactImage;
    TextView contactName;
    TextView phoneNoView;
    TextView mailView;

    Contact mContact;
    DetailPresenter presenter;

    public void setContact(Contact mContact) {
        this.mContact = mContact;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContext() != null)
            presenter = new DetailPresenterImpl(this, getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactImage = view.findViewById(R.id.detail_img);
        contactName = view.findViewById(R.id.detail_name);
        phoneNoView = view.findViewById(R.id.detail_phone_tv);
        mailView = view.findViewById(R.id.detail_mail_tv);
        if (mContact != null) {
            if (getContext() != null)
                Glide.with(getContext()).load(mContact.getPhotoUri()).into(contactImage);
            if (mContact.getPhoneNo().isEmpty() || mContact.getMailId().isEmpty())
                presenter.loadDetails(mContact);
            else {
                phoneNoView.setText(mContact.getPhoneNo().isEmpty() ? "-" : mContact.getPhoneNo());
                mailView.setText(mContact.getMailId().isEmpty() ? "-" : mContact.getMailId());
            }
            contactName.setText(mContact.getName());
        }

        mailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + mailView.getText()));
                startActivity(intent);
            }
        });
        phoneNoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNoView.getText()));
                startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void updateContact(Contact contact) {
        mContact = contact;
        phoneNoView.setText(mContact.getPhoneNo().isEmpty() ? "-" : mContact.getPhoneNo());
        mailView.setText(mContact.getMailId().isEmpty() ? "-" : mContact.getMailId());
    }
}

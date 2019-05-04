package com.rachit.mycontactsapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "contacts_table")
public class Contact implements Parcelable {

    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String photoUri;
    private String mailId;
    private String phoneNo;

    public Contact(@NonNull String id, String name, String photoUri) {
        this.id = id;
        this.name = name;
        this.photoUri = photoUri;
        this.mailId = "";
        this.phoneNo = "";
    }

    protected Contact(Parcel in) {
        id = in.readString();
        name = in.readString();
        photoUri = in.readString();
        mailId = in.readString();
        phoneNo = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getMailId() {
        return mailId == null ? "" : mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPhoneNo() {
        return phoneNo == null ? "" : phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getPhotoUri() {
        return photoUri == null ? "" : photoUri;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Contact) {
            return ((Contact) obj).getId().equals(this.getId())
                    && ((Contact) obj).getMailId().equals(this.getMailId())
                    && ((Contact) obj).getName().equals(this.getName())
                    && ((Contact) obj).getPhotoUri().equals(this.getPhotoUri())
                    && ((Contact) obj).getPhoneNo().equals(this.getPhoneNo());
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mailId);
        dest.writeString(this.phoneNo);
        dest.writeString(this.photoUri);
    }
}
package com.rachit.mycontactsapp.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

public class ContactService extends Service {

    Looper mServiceLooper;
    private ContactHandler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = handler.obtainMessage();
        msg.arg1 = startId;
        handler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread("contacts_handler",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        handler = new ContactHandler(mServiceLooper);

    }

    private final class ContactHandler extends Handler {
        ContactHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                startContactObserver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startContactObserver() {
        getApplication().
                getContentResolver().
                registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
                        true,
                        new ContactObserver(new Handler(), getApplication()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

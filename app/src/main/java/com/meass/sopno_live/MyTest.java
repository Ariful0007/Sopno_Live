package com.meass.sopno_live;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;

import java.net.MalformedURLException;
import java.net.URL;

public class MyTest extends AppCompatActivity {
JitsiMeetView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);
        try {

            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setWelcomePageEnabled(false)
                    .setRoom("https://meet.jit.si/456")
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setFeatureFlag("invite.enabled", false)
                    .setFeatureFlag("pip.enabled",false) // <- this line you have to add
                    .setFeatureFlag("calendar.enabled",false)  // optional
                    .setFeatureFlag("call-integration.enabled",false)  // optional
                    .setFeatureFlag("pip.enabled",false)
                    .setFeatureFlag("calendar.enabled",false)
                    .setFeatureFlag("call-integration.enabled",false)
                    .setFeatureFlag("close-captions.enabled",false)
                    .setFeatureFlag("chat.enabled",false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("live-streaming.enabled",false)
                    .setFeatureFlag("meeting-name.enabled",false)
                    .setFeatureFlag("meeting-password.enabled",false)
                    .setFeatureFlag("raise-hand.enabled",false)
                    .setFeatureFlag("video-share.enabled",false)
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeetActivity.launch(MyTest.this, options);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
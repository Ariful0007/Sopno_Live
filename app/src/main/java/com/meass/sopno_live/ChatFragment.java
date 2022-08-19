package com.meass.sopno_live;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChatFragment extends Fragment {

  View view;
    public ChatFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chat, container, false);
        /*
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

            JitsiMeetActivity.launch(TestLive.this, options);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


         */
        return view;
    }
}
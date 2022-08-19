package com.meass.sopno_live;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.owater.library.CircleTextView;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

import es.dmoral.toasty.Toasty;


public class LiveFrament extends Fragment {

 View view;
 CircleTextView cir,endcall;
 RelativeLayout endcall_button,startcall_call;
 FirebaseAuth firebaseAuth;
 FirebaseFirestore firebaseFirestore;
    public LiveFrament() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_live_frament, container, false);
        endcall_button=view.findViewById(R.id.endcall_button);
        startcall_call=view.findViewById(R.id.startcall_call);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Live_users").document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                endcall_button.setVisibility(View.VISIBLE);
                                startcall_call.setVisibility(View.GONE);
                            }
                            else {
                                startcall_call.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
        cir=view.findViewById(R.id.cir);
        endcall=view.findViewById(R.id.endcall);
        cir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation")
                        .setMessage("Are  you want to start this live ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Start Live.....")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("User2")
                                .document(firebaseAuth.getCurrentUser().getEmail())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                try {
                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                 String   ts = tsLong.toString();
                                                    LiveModel liveModel=new LiveModel(firebaseAuth.getCurrentUser().getEmail(),
                                                            task.getResult().getString("name"), task.getResult().getString("image"),
                                                            ts,task.getResult().getString("name"));
                                                    String meeting=task.getResult().getString("name");
                                                    firebaseFirestore.collection("Live_users")
                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                            .set(liveModel)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        progressDialog.dismiss();
                                                                        try {

                                                                            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                                                                    .setServerURL(new URL("https://meet.jit.si"))
                                                                                    .setWelcomePageEnabled(false)
                                                                                    .setRoom("https://meet.jit.si/"+meeting)
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

                                                                            JitsiMeetActivity.launch(v.getContext(), options);


                                                                        } catch (MalformedURLException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }catch (Exception e) {
                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                    String   ts = tsLong.toString();
                                                    LiveModel liveModel=new LiveModel(firebaseAuth.getCurrentUser().getEmail(),
                                                            task.getResult().getString("name"), task.getResult().getString("image"),
                                                            ts,task.getResult().getString("name"));
                                                    String meeting=task.getResult().getString("name");
                                                    firebaseFirestore.collection("Live_users")
                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                            .set(liveModel)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        progressDialog.dismiss();
                                                                        try {

                                                                            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                                                                    .setServerURL(new URL("https://meet.jit.si"))
                                                                                    .setWelcomePageEnabled(false)
                                                                                    .setRoom("https://meet.jit.si/"+meeting)
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

                                                                            JitsiMeetActivity.launch(v.getContext(), options);


                                                                        } catch (MalformedURLException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                }).create().show();
            }
        });
        endcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmation")
                        .setMessage("Are you want to end this live?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        firebaseFirestore.collection("Live_users").document(firebaseAuth.getCurrentUser().getEmail())
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toasty.success(getContext(),"You are successfully stop this live?",Toasty.LENGTH_SHORT,true).show();
                                            return;
                                        }
                                    }
                                });
                    }
                }).create().show();
            }
        });





        return view;
    }
}
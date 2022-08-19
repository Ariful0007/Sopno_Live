package com.meass.sopno_live;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.myview> {
    public List<LiveModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public LiveAdapter(List<LiveModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LiveAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_cardview_layout,parent,false);
        return new LiveAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveAdapter.myview holder, final int position) {
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
        String image2 = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        String userimage=data.get(position).getImage();
        if (userimage.equals(image2)
        ) {

            try {
                Picasso.get().load(R.drawable.profile_image).into(holder.profileImage);
                Picasso.get().load(R.drawable.llive).into(holder.cardimage);
            } catch (Exception e) {
                Picasso.get().load(R.drawable.profile_image).into(holder.profileImage);
                Picasso.get().load(R.drawable.llive).into(holder.cardimage);
            }
        }
        else {
            try {
                Picasso.get().load(userimage).into(holder.profileImage);
                Picasso.get().load(userimage).into(holder.cardimage);
            } catch (Exception e) {
                Picasso.get().load(userimage).into(holder.profileImage);
                Picasso.get().load(userimage).into(holder.cardimage);
            }
        }
        if (data.get(position).getUderid().equals(firebaseAuth.getCurrentUser().getEmail())) {
            holder.linearLayout.setVisibility(View.GONE);
        }
        else {
            holder.linearLayout.setVisibility(View.VISIBLE);
        }
        holder.nameTv.setText(data.get(position).getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final KProgressHUD progressDialog=  KProgressHUD.create(v.getContext())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Joining Live.....")
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
                                            firebaseFirestore.collection("FollowerList")
                                                    .document(data.get(position).getUderid())
                                                    .collection("List")
                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                    .set(liveModel)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {

                                                                try {
                                                                    progressDialog.dismiss();

                                                                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                                                            .setServerURL(new URL("https://meet.jit.si"))
                                                                            .setWelcomePageEnabled(false)
                                                                            .setRoom("https://meet.jit.si/"+data.get(position).getMeeting_id())
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
                                            firebaseFirestore.collection("FollowerList")
                                                    .document(data.get(position).getUderid())
                                                    .collection("List")
                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                    .set(liveModel)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {

                                                                try {
                                                                    progressDialog.dismiss();

                                                                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                                                            .setServerURL(new URL("https://meet.jit.si"))
                                                                            .setWelcomePageEnabled(false)
                                                                            .setRoom("https://meet.jit.si/"+data.get(position).getMeeting_id())
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
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView nameTv,customer_number,customer_area,logout;
        ImageView cardimage,profileImage;
        LinearLayout linearLayout;
        public myview(@NonNull View itemView) {
            super(itemView);
            cardimage=itemView.findViewById(R.id.cardimage);
            profileImage=itemView.findViewById(R.id.profileImage);
            nameTv=itemView.findViewById(R.id.nameTv);
            linearLayout=itemView.findViewById(R.id.linearLayout);

        }
    }
}


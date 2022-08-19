package com.meass.sopno_live;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;


public class ProfileFragment extends Fragment {

    View view;
    TextView name,phone,coinsT1v,coinsT1v1;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView image1;
    CardView dailyCheckCard;
    LinearLayout edit_profile,withdraw,deletee;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        coinsT1v=view.findViewById(R.id.coinsT1v);
        coinsT1v1=view.findViewById(R.id.coinsT1v1);
        image1=view.findViewById(R.id.image1);
        withdraw=view.findViewById(R.id.withdraw);
        //profileImage
        String image2 = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {

                                    String image = task.getResult().getString("image");
                                    //Toast.makeText(view.getContext(), ""+image, Toast.LENGTH_SHORT).show();
                                    if (image.equals(image2)) {


                                    } else {
                                        try {
                                            Picasso.get().load(image).into(image1);
                                        } catch (Exception e) {
                                            Picasso.get().load(image).into(image1);
                                        }
                                    }
                                    name.setText(task.getResult().getString("name"));
                                    phone.setText(task.getResult().getString("number"));

                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                });
        firebaseFirestore.collection("FollowerList")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int ncount = 0;
                        for (DocumentSnapshot document : task.getResult()) {
                            ncount++;
                        }
                        coinsT1v1.setText(""+ncount);
                    }
                });
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Main_Balance")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    coinsT1v.setText(task.getResult().getString("main_balance"));
                                }catch (Exception e) {
                                    coinsT1v.setText(task.getResult().getString("main_balance"));
                                }
                            }
                        }
                    }
                });
        coinsT1v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(),FollowerList.class));
            }
        });
        coinsT1v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(),PaymentActivity.class));
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(),PaymentActivity.class));
            }
        });
        dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(),ProfileUpdate.class));
            }
        });
        edit_profile=view.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(),ProfileUpdate.class));
            }
        });
        deletee=view.findViewById(R.id.deletee);
        deletee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmation")
                        .setMessage("Are you wan to delete your account?")
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
                                .setLabel("Deleting Data.....")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid())
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firebaseFirestore.collection("User2").document(firebaseAuth.getCurrentUser().getEmail())
                                                    .delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                firebaseFirestore.collection("Password").document(firebaseAuth.getCurrentUser().getEmail())

                                                                        .delete()
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Toasty.success(getContext(),"Your account is successfully delete",Toasty.LENGTH_SHORT,true).show();
                                                                                    firebaseAuth.signOut();
                                                                                    startActivity(new Intent(getContext(),MainActivity.class));
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
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
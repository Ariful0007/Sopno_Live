package com.meass.sopno_live;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {
    TextView name,phone,coinsT1v,coinsT1v1;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView image1;
    CardView dailyCheckCard;
    LinearLayout edit_profile,withdraw,deletee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(" Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        coinsT1v=findViewById(R.id.coinsT1v);
        coinsT1v1=findViewById(R.id.coinsT1v1);
        image1=findViewById(R.id.image1);
        withdraw=findViewById(R.id.withdraw);
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
               startActivity(new Intent(getApplicationContext(),FollowerList.class));
            }
        });
        coinsT1v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
            }
        });
        dailyCheckCard=findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileUpdate.class));
            }
        });
        edit_profile=findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),ProfileUpdate.class));
            }
        });
        deletee=findViewById(R.id.deletee);
        deletee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
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
                        final KProgressHUD progressDialog=  KProgressHUD.create(ProfileActivity.this)
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
                                                                                    Toasty.success(getApplicationContext(),"Your account is successfully delete",Toasty.LENGTH_SHORT,true).show();
                                                                                    firebaseAuth.signOut();
                                                                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
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

    }
}
package com.meass.sopno_live;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class HomeACTIVITY extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    KProgressHUD progressHUD;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    // String  email;
    int count = 0,count1=0;
    TextView totaluser,paid,pending,blocking,notificationn,invalid,todaystask,margo_signup;
    int count11=0,countpaid,block=0;


    //
    EditText methodename,minwith,hint;
    Button logo,login_button;
    ImageView myImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    Dialog mDialog;
    // EditText name,ammount;
    private HashMap<String, String> user;
    private String name, email, photo, mobile,username;
    FirebaseFirestore db;
    UserSession session;
    Context ctx;
    private BottomNavigationView mainBottomNav;
    HomeFragment homeFragment;
    Searchfragement searchfragement;
    LiveFrament liveFrament;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);
        new CheckInternetConnection(this).checkConnection();
        Toolbar toolbar = findViewById(R.id.toolbar);

        blocking=findViewById(R.id.blocking);



        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        progressHUD = KProgressHUD.create(HomeACTIVITY.this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        mainBottomNav=findViewById(R.id.bottomnavigationview);
        homeFragment=new HomeFragment();
        searchfragement=new Searchfragement();
        liveFrament=new LiveFrament();
        chatFragment=new ChatFragment();
        profileFragment=new ProfileFragment();
        initilizeFragment();
        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.home) {
                    replaceFragment(homeFragment);

                }
                else if (id==R.id.profile) {
                    replaceFragment(searchfragement);
                }
                if (id==R.id.bank) {
                    replaceFragment(liveFrament);
                }
                if (id==R.id.Message) {
                    replaceFragment(chatFragment);
                }
                if (id==R.id.profidle) {
                    replaceFragment(profileFragment);
                }
                return true;
            }
        });
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.addNote) {
                    Toasty.success(getApplicationContext(),"Now you are in home.",Toasty.LENGTH_SHORT,true).show();

                }
                if (id==R.id.add) {
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                }
                if (id==R.id.shareapp2) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);
                    builder.setTitle("Logout")
                            .setMessage("Are you want to logout?")
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            firebaseAuth.signOut();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }).create().show();
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {

        new CheckInternetConnection(this).checkConnection();
        super.onResume();
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(HomeACTIVITY.this);
        builder.setTitle("Exit")
                .setMessage("Are you want to exit?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        builder.create().show();
    }
    private void initilizeFragment() {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content,homeFragment);
        fragmentTransaction.add(R.id.content,searchfragement);
        fragmentTransaction.add(R.id.content,liveFrament);
        fragmentTransaction.add(R.id.content,chatFragment);
        fragmentTransaction.add(R.id.content,profileFragment);
        fragmentTransaction.hide(searchfragement);
        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.hide(liveFrament);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            HomeFragment fragment2 = new HomeFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;



                    }
                    return false;
                }
            };
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (fragment==homeFragment)
        {
            fragmentTransaction.hide(searchfragement);
            fragmentTransaction.hide(liveFrament);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(profileFragment);

        }
        else if(fragment==searchfragement) {
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(liveFrament);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(profileFragment);
        }
        else if(fragment==liveFrament) {
            fragmentTransaction.hide(searchfragement);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(profileFragment);
        }
        else if (fragment==chatFragment) {
            fragmentTransaction.hide(searchfragement);
            fragmentTransaction.hide(liveFrament);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);
        }
        else if (fragment==profileFragment) {
            fragmentTransaction.hide(searchfragement);
            fragmentTransaction.hide(liveFrament);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(homeFragment);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.shareapp2:
                AlertDialog.Builder warning = new AlertDialog.Builder(HomeACTIVITY.this)
                        .setTitle("Logout")
                        .setMessage("Are you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();



                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user


                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();


                            }
                        });

                warning.show();
                break;


        }

        return false;
    }

    public void logout(View view) {
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }
}
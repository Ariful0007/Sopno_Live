package com.meass.sopno_live;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ProfileUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerTextSize, spinnerTextSize1, spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
ImageView profileImage;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
EditText spinner1,number,preos;
Spinner mainspinner;
    DatePickerDialog.OnDateSetListener dateSetListener;
    int date_mon,date_year,date_day;

TextView dob;
//

    private StorageReference mStorageRef;
    //
    TextView changeProfilePhoto;
    ImageButton image_button;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;//Firebase

    DocumentReference documentReference;
    Button floatingActionButton;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    Button generate_btn;
    //doctor
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Update Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        profileImage=findViewById(R.id.profileImage);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        spinner1=findViewById(R.id.spinner1);
        number=findViewById(R.id.number);
        preos=findViewById(R.id.preos);
        mainspinner=findViewById(R.id.mainspinner);
mainspinner.setOnItemSelectedListener(this);
        String[] textSizes = getResources().getStringArray(R.array.gender);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainspinner.setAdapter(adapter);
        storageReference = FirebaseStorage.getInstance().getReference();



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
                                    spinner1.setText(task.getResult().getString("name"));
                                    number.setText(task.getResult().getString("number"));
                                    String image = task.getResult().getString("image");
                                    if (image.equals(image2)) {
                                        try {
                                            Picasso.get().load(R.drawable.profile_image).into(profileImage);
                                        } catch (Exception e) {
                                            Picasso.get().load(R.drawable.profile_image).into(profileImage);
                                        }
                                    } else {
                                        try {
                                            Picasso.get().load(image).into(profileImage);
                                        } catch (Exception e) {
                                            Picasso.get().load(image).into(profileImage);
                                        }

                                    }


                                } catch (Exception e) {

                                }
                            }
                        }
                    }
                });
        dob=findViewById(R.id.dob);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ProfileUpdate.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int  monthh=i1+1;
                String dob1=monthh+"/"+i2+"/"+i;
                //  String dob = "01/08/1990";
                dob.setText(dob1);



            }
        };
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        Button cirLoginButton=findViewById(R.id.cirLoginButton);
    }
    int detect=1;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);
                detect=2;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("Doctor_images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            final Uri downloadUri = uriTask.getResult();


                            if (uriTask.isSuccessful()) {
                                firebaseFirestore.collection("Users")
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .update("image", downloadUri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    // startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                                    upload_data(spinner1.getText().toString(),preos.getText().toString(),dob.getText().toString(),
                                                            valueFromSpinner,downloadUri.toString());

                                                }
                                            }
                                        });


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileUpdate.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void upload_data(String p_name, String professonial, String dateofb, String gender, String image) {
        final KProgressHUD progressDialog=  KProgressHUD.create(ProfileUpdate.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Updating Data.....")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid())
                .update("name",p_name,"profession",professonial,"dob",dateofb,
                        "sex",gender,"image",image)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseFirestore.collection("User2").document(firebaseAuth.getCurrentUser().getEmail())
                                    .update("name",p_name,"profession",professonial,"dob",dateofb,
                                            "sex",gender,"image",image)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toasty.success(getApplicationContext(),"Profile Updation is complete",Toasty.LENGTH_SHORT,true).show();
                                                startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
        return super.onNavigateUp();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.mainspinner) {
            valueFromSpinner = parent.getItemAtPosition(position).toString();


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void cli(View view) {
        String image2 = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";

        if (TextUtils.isEmpty(number.getText().toString())||TextUtils.isEmpty(spinner1.getText().toString())||
                TextUtils.isEmpty(preos.getText().toString())||TextUtils.isEmpty(dob.getText().toString())) {
            Toasty.error(getApplicationContext(),"Enter all information",Toasty.LENGTH_SHORT,true).show();
            return;
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(ProfileUpdate.this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you want to update all information?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (detect==1) {
                        upload_data(spinner1.getText().toString(),preos.getText().toString(),dob.getText().toString(),
                                valueFromSpinner,image2);
                    }
                    else {
                        uploadImage();
                    }
                }
            }).create().show();
        }
    }
}
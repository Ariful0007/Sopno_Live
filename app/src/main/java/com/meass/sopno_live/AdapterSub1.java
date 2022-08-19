package com.meass.sopno_live;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSub1 extends RecyclerView.Adapter<AdapterSub1.myview> {
    public List<LiveModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public AdapterSub1(List<LiveModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterSub1.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new AdapterSub1.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSub1.myview holder, final int position) {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText("Follower");
        String image2 = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        String userimage=data.get(position).getImage();
        if (userimage.equals(image2)
        ) {

            try {
                Picasso.get().load(R.drawable.profile_image).into(holder.profileImage);

            } catch (Exception e) {
                Picasso.get().load(R.drawable.profile_image).into(holder.profileImage);

            }
        }
        else {
            try {
                Picasso.get().load(userimage).into(holder.profileImage);

            } catch (Exception e) {
                Picasso.get().load(userimage).into(holder.profileImage);

            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout,customer_area3,customer_area8;
        CardView card_view8;
        ImageView profileImage;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            logout=itemView.findViewById(R.id.logout);
            card_view8=itemView.findViewById(R.id.card_view8);
            profileImage=itemView.findViewById(R.id.profileImage);
        }
    }
}



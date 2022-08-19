package com.meass.sopno_live;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.myview> {
    public List<MemberModel> data;
    FirebaseFirestore firebaseFirestore;

    public MemberAdapter(List<MemberModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MemberAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_cardview_layout,parent,false);
        return new MemberAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.myview holder, final int position) {

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
        holder.nameTv.setText(data.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView nameTv,customer_number,customer_area,logout;
        ImageView cardimage,profileImage;
        public myview(@NonNull View itemView) {
            super(itemView);
            cardimage=itemView.findViewById(R.id.cardimage);
            profileImage=itemView.findViewById(R.id.profileImage);
            nameTv=itemView.findViewById(R.id.nameTv);

        }
    }
}


package com.meass.sopno_live;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Payment_Adapter  extends RecyclerView.Adapter<Payment_Adapter.myview> {
    public List<Payment_request> data;
    FirebaseFirestore firebaseFirestore;

    public Payment_Adapter(List<Payment_request> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Payment_Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment,parent,false);
        return new Payment_Adapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Payment_Adapter.myview holder, final int position) {
        holder.customer_name.setText("Username : "+data.get(position).getUsername());
        holder.customer_number.setText("Amount  : "+data.get(position).getAmmount());
        holder.customer_area.setText(data.get(position).getDate());
        holder.logout.setText(data.get(position).getStatus());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
        }
    }
}


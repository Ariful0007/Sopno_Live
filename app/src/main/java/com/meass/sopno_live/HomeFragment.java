package com.meass.sopno_live;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class HomeFragment extends Fragment {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    LiveAdapter getDataAdapter1;
    List<LiveModel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    private StaggeredGridLayoutManager mLayoutManager;
   View view;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        getList = new ArrayList<>();
        getDataAdapter1 = new LiveAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //
        empty_cart=view.findViewById(R.id.empty_cart);
        firebaseFirestore.collection("Live_users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            if (count == 0) {
                                empty_cart.setVisibility(View.VISIBLE);

                            } else {
                                empty_cart.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                });
        //
        documentReference  =  firebaseFirestore.collection("Live_users").document();
        recyclerView =view.findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
        return view;
    }
    private void reciveData() {

        firebaseFirestore.collection("Live_users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        LiveModel get = ds.getDocument().toObject(LiveModel.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });

    }
}
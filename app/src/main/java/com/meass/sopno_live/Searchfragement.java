package com.meass.sopno_live;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Searchfragement extends Fragment {
View view;
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


    String classify;
    SearchView name;
    public Searchfragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_searchfragement, container, false);
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
        name=view.findViewById(R.id.name);
        name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //fullsearch(query);

                //phoneSerach(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchAllUser(newText);

                //phoneSerach1(newText);
                return false;
            }
        });
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
    private void searchAllUser(String newText) {
        firebaseFirestore
                .collection("Live_users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        getList.clear();
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            String dta = documentSnapshot.getString("name");
                            if (dta.toLowerCase().toString().contains(newText.toLowerCase().toString())) {
                                LiveModel add_customer=new LiveModel(documentSnapshot.getString("uderid"),
                                        documentSnapshot.getString("name")
                                        , documentSnapshot.getString("image"),
                                        documentSnapshot.getString("time"),
                                        documentSnapshot.getString("meeting_id")

                                );
                                getList.add(add_customer);

                            }
                            getDataAdapter1 = new LiveAdapter(getList);
                            recyclerView.setAdapter(getDataAdapter1);


                        }
                    }
                });
    }
}
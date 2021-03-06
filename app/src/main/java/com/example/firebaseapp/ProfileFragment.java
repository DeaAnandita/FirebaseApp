package com.example.firebaseapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    ImageView ivavatar;
    TextView tvname , tvemail, tvphone;


    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        tvname = view.findViewById(R.id.tvname);
        tvemail = view.findViewById(R.id.tvemail);
        tvphone = view.findViewById(R.id.tvphone);
        ivavatar = view.findViewById(R.id.ivavatar);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String image = "" + ds.child("image").getValue();

                    tvname.setText(name);
                    tvemail.setText(email);
                    tvphone.setText(phone);
                    try {

                        Picasso.get().load(image).into(ivavatar);

                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_add_image).into(ivavatar);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;


    }

}

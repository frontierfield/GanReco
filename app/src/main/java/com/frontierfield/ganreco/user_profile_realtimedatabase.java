package com.frontierfield.ganreco;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kkarimu on 2018/06/18.
 * rdb用構造体
 */

@IgnoreExtraProperties
public class user_profile_realtimedatabase{
    public String UID = null;
    public String lastName = null;
    public String firstName = null;
    public String email = null;
    public int year_Index = -1, month_Index = -1,day_Index = -1;
    public int sex_Index = -1;
    public String zipfront = null;
    public String ziprear = null;
    public String address = null;
    public user_profile_realtimedatabase(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public user_profile_realtimedatabase(user_profile up){
        this.UID = up.UID;
        this.lastName = up.lastName;
        this.firstName = up.firstName;
        this.email = up.email;
        this.year_Index = up.year_Index;
        this.month_Index = up.month_Index;
        this.day_Index = up.day_Index;
        this.sex_Index = up.sex_Index;
        this.zipfront = up.zipfront;
        this.ziprear = up.ziprear;
        this.address = up.address;
    }
    public void user_profile_add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("users").child(this.UID).setValue(this);
    }
    public void get_user_profile_and_input_static(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("users").child(mAuthUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String t_y,t_m,t_d,t_s;
                user_profile up = new user_profile();
                up.UID = dataSnapshot.child("UID").getValue(String.class);
                up.lastName = dataSnapshot.child("lastName").getValue(String.class);
                up.firstName = dataSnapshot.child("firstName").getValue(String.class);
                up.email = dataSnapshot.child("email").getValue(String.class);

                if(dataSnapshot.child("year_Index").getValue(Long.class) != null){
                    t_y = dataSnapshot.child("year_Index").getValue(Long.class).toString();
                    up.year_Index = Integer.parseInt(t_y);
                }
                if(dataSnapshot.child("month_Index").getValue(Long.class) != null){
                    t_m = dataSnapshot.child("month_Index").getValue(Long.class).toString();
                    up.month_Index = Integer.parseInt(t_m);
                }
                if(dataSnapshot.child("day_Index").getValue(Long.class) != null){
                    t_d = dataSnapshot.child("day_Index").getValue(Long.class).toString();
                    up.day_Index = Integer.parseInt(t_d);
                }
                if(dataSnapshot.child("sex_Index").getValue(Long.class) != null){
                    t_s = dataSnapshot.child("sex_Index").getValue(Long.class).toString();
                    up.sex_Index = Integer.parseInt(t_s);
                }


                up.zipfront = dataSnapshot.child("zipfront").getValue(String.class);
                up.ziprear = dataSnapshot.child("ziprear").getValue(String.class);
                up.address = dataSnapshot.child("address").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("RDB error", "Failed to read value.");
            }
        });
    }


}

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

@IgnoreExtraProperties
public class UserProfileRDB {

    private String UID = null;
    private String lastName = null;
    private String firstName = null;
    private String email = null;
    private int year_Index = -1, month_Index = -1,day_Index = -1;
    private int sex_Index = -1;
    private String zipfront = null;
    private String ziprear = null;
    private String address = null;
    private String tel = null;
    private String cancerType = null;
    private boolean isSaved = false;

    UserProfile userProfile;

    public UserProfileRDB(){
        userProfile = UserProfile.getInstance();
        this.UID = userProfile.getUID();
        this.lastName = userProfile.getLastName();
        this.firstName = userProfile.getFirstName();
        this.email = userProfile.getEmail();
        this.year_Index = userProfile.getYear_Index();
        this.month_Index = userProfile.getMonth_Index();
        this.day_Index = userProfile.getDay_Index();
        this.sex_Index = userProfile.getSex_Index();
        this.zipfront = userProfile.getZipfront();
        this.ziprear = userProfile.getZiprear();
        this.address = userProfile.getAddress();
        this.tel = userProfile.getTel();
        this.cancerType = userProfile.getCancerType();
        this.isSaved = userProfile.isSaved();
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
                UserProfile userProfile = UserProfile.getInstance();
                userProfile.setUID(dataSnapshot.child("UID").getValue(String.class));
                userProfile.setLastName(dataSnapshot.child("lastName").getValue(String.class));
                userProfile.setFirstName(dataSnapshot.child("firstName").getValue(String.class));
                userProfile.setEmail(dataSnapshot.child("email").getValue(String.class));

                if(dataSnapshot.child("year_Index").getValue(Long.class) != null){
                    t_y = dataSnapshot.child("year_Index").getValue(Long.class).toString();
                    userProfile.setYear_Index(Integer.parseInt(t_y));
                }
                if(dataSnapshot.child("month_Index").getValue(Long.class) != null){
                    t_m = dataSnapshot.child("month_Index").getValue(Long.class).toString();
                    userProfile.setMonth_Index(Integer.parseInt(t_m));
                }
                if(dataSnapshot.child("day_Index").getValue(Long.class) != null){
                    t_d = dataSnapshot.child("day_Index").getValue(Long.class).toString();
                    userProfile.setDay_Index(Integer.parseInt(t_d));
                }
                if(dataSnapshot.child("sex_Index").getValue(Long.class) != null){
                    t_s = dataSnapshot.child("sex_Index").getValue(Long.class).toString();
                    userProfile.setSex_Index(Integer.parseInt(t_s));
                }

                userProfile.setZipfront(dataSnapshot.child("zipfront").getValue(String.class));
                userProfile.setZiprear(dataSnapshot.child("ziprear").getValue(String.class));
                userProfile.setAddress(dataSnapshot.child("address").getValue(String.class));
                userProfile.setAddress(dataSnapshot.child("tel").getValue(String.class));
                userProfile.setAddress(dataSnapshot.child("cancerType").getValue(String.class));
                userProfile.setAddress(dataSnapshot.child("isSaved").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("RDB error", "Failed to read value.");
            }
        });
    }
}

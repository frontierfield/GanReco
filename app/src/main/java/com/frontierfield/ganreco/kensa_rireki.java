package com.frontierfield.ganreco;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class kensa_rireki {
    String ID; /*RDB kensarireki key*/
    Boolean t; /*true head*/
    int y_index,m_index,d_index;
    String hospital;
    String detail;

    String uripath;
    String storagepath;
    String thum_path;

    public kensa_rireki(){
    }

    public void kensa_rireki_add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        if(ID != null) {
            myRef.child("kensa_rireki").
                    child(mAuthUser.getUid()).
                    child(ID).
                    setValue(this);
        }else{
            DatabaseReference newkr =
                    myRef.child("kensa_rireki").
                            child(mAuthUser.getUid()).
                            push();
            this.ID = newkr.getKey();
            newkr.setValue(this);
        }
    }
}

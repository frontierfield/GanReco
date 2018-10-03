package com.frontierfield.ganreco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//firebaseとの連携
public class TsuinRirekiFirebaseStorage {
    public static void addTsuinRirekiFirebaseStorage(Uri uri,Context context,String fileName) throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
        InputStream inputStream=null;
        try {
            inputStream=context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //サムネ入力
        StorageReference thumRef= storageReference.child(mAuthUser.getUid()).child(String.format("TsuinRirekiThum/rireki_%s.jpg",fileName));

        //Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(inputStream));
        Bitmap bitmap = Global_Util.getPreResizedBitmap(uri,context);
        Bitmap thumbitmap = Bitmap.createScaledBitmap(bitmap,75,75,false);//正方形にリサイズ
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = thumRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        //普通サイズのデータ入力
        StorageReference imagesRef = storageReference.child(mAuthUser.getUid()).child(String.format("TsuinRireki/rireki_%s.jpg",fileName));//ここのchild内を書き換えて

        UploadTask uploadTask2 = imagesRef.putStream(inputStream);
        uploadTask2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            }
        });
    }

    public static void getTsuinRirekiThum(String fileName){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
        StorageReference thumRef= storageReference.child(mAuthUser.getUid()).child(String.format("TsuinRirekiThum/rireki_%s.jpg",fileName));
        thumRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}

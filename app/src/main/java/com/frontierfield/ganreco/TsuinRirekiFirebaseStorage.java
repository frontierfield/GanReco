package com.frontierfield.ganreco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
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
import java.util.Optional;
import java.util.stream.Stream;

//firebaseとの連携
public class TsuinRirekiFirebaseStorage {
    public static void saveTsuinRirekiFirebaseStorage(Bitmap bitmap,String fileName,Context context){
        //初回で向きなりサイズなりを調整して上げたいからbitmapから上げる
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();

        StorageReference imagesRef = storageReference.child(mAuthUser.getUid()).child(String.format("TsuinRireki/rireki_%s.jpg",fileName));

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);
            Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imagesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        for(int i=0;i<TsuinRirekiList.getInstance().size();i++){
                            if(TsuinRirekiList.getInstance().get(i).getFileName()==fileName){
                                //pathを変更する処理
                                TsuinRirekiList.getInstance().get(i).setFilePath(task.getResult().toString());
                                TsuinRirekiList.getInstance().get(i).setStoragePath(task.getResult().toString());
                                //ローカルの画像削除
                                context.getContentResolver().delete(Uri.parse(TsuinRirekiList.getInstance().get(i).getLocalPath()),null,null);
                                break;
                            }
                        }
                        //OCRたたく
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

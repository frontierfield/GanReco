package com.frontierfield.ganreco;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class KensaRirekiFirebaseStorage {
    public static void saveKensaRirekiFirebaseStorage(Bitmap bitmap, String fileName, Context context) {
        //初回で向きなりサイズなりを調整して上げたいからbitmapから上げる
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference imagesRef = storageReference.child(mAuthUser.getUid()).child(String.format("KensaRireki/rireki_%s.jpg", fileName));

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imagesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        for (int i = 0; i < KensaRirekiList.getInstance().size(); i++) {
                            if (KensaRirekiList.getInstance().get(i).getFileName() == fileName) {
                                //pathを変更する処理
                                KensaRirekiList.getInstance().get(i).setFilePath(task.getResult().toString());
                                KensaRirekiList.getInstance().get(i).setStoragePath(task.getResult().toString());
                                KensaRirekiRDB.saveKensaRirekiRDB();
                                //ローカルの画像削除
                                context.getContentResolver().delete(Uri.parse(KensaRirekiList.getInstance().get(i).getLocalPath()), null, null);
                                break;
                            }
                        }
                        //OCRたたく
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteKensaRirekiFirebaseStorage(String fileName) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        FirebaseUser mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference imagesRef = storageReference.child(mAuthUser.getUid()).child(String.format("KensaRireki/rireki_%s.jpg", fileName));
        try {
            imagesRef.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

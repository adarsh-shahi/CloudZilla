package com.example.cloudzilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addFB, imageFB, videoFB;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFB = findViewById(R.id.addFb);
        imageFB = findViewById(R.id.imageFb);
        videoFB = findViewById(R.id.videoFb);

        addFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(MainActivity.this,     //IF permission is already granted.
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        if(imageFB.getVisibility() == View.INVISIBLE) {
                            imageFB.setVisibility(View.VISIBLE);
                            videoFB.setVisibility(View.VISIBLE);
                        }
                        else {
                            imageFB.setVisibility(View.INVISIBLE);
                            videoFB.setVisibility(View.INVISIBLE);
                        }
                }
                else
                    requestStoragePermission();
            }
        });

        imageFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePost();

            }
        });

    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(MainActivity.this).setTitle("Permission needed").setMessage("We need permission to access your files")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "You have denied the request please go into app info and allow the permissions", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void choosePost() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
        }
    }

//    private void uploadPic() {
//
//        String time = currentTime.toString();
//        String cutTime = time.substring(0,20);
//        descText = desc.getText().toString().trim();
//
//        db.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(!value.isEmpty()){
//                    postsCount=value.getDocumentChanges().size();
//                }
//            }
//        });
//
//        ProgressDialog pd = new ProgressDialog(AddPostDetails.this);
//        pd.setTitle("Uploading Image....");
//        pd.show();
//
//        final String randomKey = UUID.randomUUID().toString();
//
//        StorageReference riversRef = storageReference.child("posts/"+randomKey);
//
//        riversRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String imageUrl = uri.toString();
//                                Map<String,Object> note = new HashMap<>();
//                                note.put("imageUrl",imageUrl);
//                                note.put("name","Santoshi Shete");
//                                note.put("desc",descText );
//                                note.put("count",postsCount);
//                                note.put("time",cutTime);
//                                db.collection("Posts").document("parth darekar"+postsCount).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        pd.dismiss();
//                                        Toast.makeText(AddPostDetails.this, "Image Uploaded", Toast.LENGTH_LONG).show();
//                                        startActivity(new Intent(AddPostDetails.this,MainActivity.class));
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//
//                                    }
//                                });
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                double progressPercentage = (100.00* taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                pd.setMessage(" "+(int) progressPercentage+"%");
//            }
//        });
//    }

}
package com.uni.study;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uni.study.databinding.ActivityAddfileBinding;
import com.uni.study.databinding.ActivityDocsBinding;
import com.uni.study.model.pdfData;

import java.util.Objects;

public class addDocs extends AppCompatActivity {

  EditText fileDir;
  Button upButton;
  StorageReference storageReference;
  DatabaseReference databaseReference;
  ImageButton returnBtn;

  ActivityAddfileBinding binding;
  ActivityResultLauncher<String> getFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_addfile);
        fileDir = findViewById(R.id.file);
        upButton = findViewById(R.id.upBtn);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadDoc");
        returnBtn = findViewById(R.id.dashReturn);
        upButton.setEnabled(false);

         getFile = registerForActivityResult(
                 new ActivityResultContracts.GetContent(),
                 new ActivityResultCallback<Uri>() {
                     @Override
                     public void onActivityResult(Uri o) {
                         upButton.setEnabled(true);
                         System.out.println(o.toString());
                         String filename = o.toString();
                         fileDir.setText(filename.substring(filename.lastIndexOf("/")+1));
                     }
                 }
         );

       fileDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFile.launch("application/pdf");

            }
       });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addDocs.this, dash.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            upButton.setEnabled(true);
            fileDir.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
        }

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(addDocs.this);
                progressDialog.setTitle("loading");
                progressDialog.show();
                StorageReference storage = storageReference.child("upload"+System.currentTimeMillis()+".pdf");

                storage.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        pdfData pdf = new pdfData(fileDir.getText().toString(), uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(pdf);
                        Toast.makeText(addDocs.this, "File upload ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded.." + (int) progress + "%");
                    }
                });

            }
        });

    }
}

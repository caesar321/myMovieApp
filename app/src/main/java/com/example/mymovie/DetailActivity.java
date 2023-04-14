package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgView;
    private TextView DetailTitle, DetailDate, DetailOverView;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgView = findViewById(R.id.detailImageView);
        DetailTitle = findViewById(R.id.detailTitle);
        DetailDate = findViewById(R.id.detailReleased);
        progressDialog = new ProgressDialog(this);
        DetailOverView = findViewById(R.id.detailOverView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("movies");
        storageReference = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        DetailTitle.setText(bundle.getString("title"));
        DetailDate.setText(bundle.getString("date"));
        DetailOverView.setText(bundle.getString("view"));

        // Load the image using Picasso and display in the ImageView
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + bundle.getString("image")).into(imgView);

        Button addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Saving Data.....");
                progressDialog.show();

                // Create a unique name for the image file based on the movie title and timestamp
                String imageFileName = bundle.getString("title") + "_" + System.currentTimeMillis() + ".jpg";
                String imageUrl = getIntent().getStringExtra("image");

                // Load the image from the URL into a Bitmap object
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // Convert the Bitmap to a byte array
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageData = baos.toByteArray();

                        StorageReference imageRef = storageReference.child("images/" + imageFileName);
                        UploadTask uploadTask = imageRef.putBytes(imageData);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the download URL for the uploaded image
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Save the movie data and image URL to Firebase Realtime Database
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("title", bundle.getString("title"));
                                        hashMap.put("date", bundle.getString("date"));
                                        hashMap.put("image_url", uri.toString());
                                        databaseReference = FirebaseDatabase.getInstance().getReference("movieSave");
                                        databaseReference.child(bundle.getString("title")).setValue(hashMap);

                                        progressDialog.dismiss();
                                        Toast.makeText(DetailActivity.this, "Saving data successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DetailActivity.this, "An error occured while trying to upload your data..", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailActivity.this, "An error occured while trying to load the image..", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        });


    }
}
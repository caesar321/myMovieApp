package com.example.mymovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ModelClass2> myModelClass;
    DatabaseReference databaseReference;
    private myAdapterForDisplay myAdapterForDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        recyclerView= findViewById(R.id.recyclerViewDisplay);
        myModelClass = new ArrayList<>();
        myAdapterForDisplay= new myAdapterForDisplay(this,myModelClass);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference("movieSave");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myModelClass.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    ModelClass2 class2 = snap.getValue(ModelClass2.class);
                    myModelClass.add(class2);
                }
                myAdapterForDisplay.notifyDataSetChanged();
                recyclerView.setAdapter(myAdapterForDisplay);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
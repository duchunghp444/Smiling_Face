package com.example.firebase_smilingface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class FaceScreen extends AppCompatActivity {
    private ImageButton btnvui,btnbt,btnbuon;
    private int happy_num=0;
    private int normal_num=0;
    private int sad_num=0;
    private FirebaseDatabase database;
    private Button btnfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_screen);

        btnvui=findViewById(R.id.btnvui);
        btnbt=findViewById(R.id.btnbt);
        btnbuon=findViewById(R.id.btnbuon);
        btnfinish=findViewById(R.id.btnFinish);
        database = FirebaseDatabase.getInstance();

        btnvui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DatabaseReference data=database.getReference("Users/-MaE59N8e0WElO5_cw55/happy");

                DatabaseReference data=database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("happy");
                Toast.makeText(FaceScreen.this,""+data,Toast.LENGTH_SHORT).show();
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            happy_num= Integer.parseInt(snapshot.getValue().toString());
                            happy_num++;
                            data.setValue(happy_num);
                            Toast.makeText(FaceScreen.this,"You happy,keep going :D x"+data,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DatabaseReference data=database.getReference("Users/-MaE59N8e0WElO5_cw55/normal");
                DatabaseReference data1=database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("normal");

                data1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            normal_num= Integer.parseInt(snapshot.getValue().toString());
                            normal_num++;
                            data1.setValue(normal_num);
                            Toast.makeText(FaceScreen.this,"You great, look positive :D x"+data1,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnbuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DatabaseReference data=database.getReference("Users/-MaE59N8e0WElO5_cw55/sad");
                DatabaseReference data2=database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sad");

                data2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            sad_num= Integer.parseInt(snapshot.getValue().toString());
                            sad_num++;
                            data2.setValue(sad_num);
                            Toast.makeText(FaceScreen.this,"Don't worry, everything fine :D x"+data2,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnfinish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(FaceScreen.this,"Have a good day :D x",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FaceScreen.this, MainActivity.class));

            }
        });


    }
}
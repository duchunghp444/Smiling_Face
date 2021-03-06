package com.example.firebase_smilingface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {
    private EditText inputEmail, inputPassword,inputPassword2,name;
    private Button  btnSignUp;
    private FirebaseAuth auth;
    private TextView btnSignIn;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER="Users";
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn =  findViewById(R.id.dangnhapS3);
        btnSignUp = (Button) findViewById(R.id.dangkyS3);
        inputEmail = (EditText) findViewById(R.id.emailS3);
        inputPassword = (EditText) findViewById(R.id.passwordS3);
        inputPassword2 = (EditText) findViewById(R.id.password2S3);
        name= (EditText) findViewById(R.id.nameS3);

         database = FirebaseDatabase.getInstance();
         mDatabase = database.getReference(USER);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, SignInScreen.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String password2 = inputPassword2.getText().toString().trim();
                String namene= name.getText().toString().trim();
                user= new Users(namene,email,password,0,0,0);

                if(TextUtils.isEmpty(namene)){
                    Toast.makeText(getApplicationContext(), "Invalid name confirm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals(password2)==false){
                    Toast.makeText(getApplicationContext(), "Invalid password confirm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                    registerUser(email,password);






            }
        });
    }
    private void registerUser(String email,String password){
//create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //storeNewUsersData(namene,email,password);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterScreen.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            UpdateUI(null);
                        } else {
                            Toast.makeText(RegisterScreen.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            FirebaseUser user= auth.getCurrentUser();
                            UpdateUI(user);

                            finish();
                        }
                    }
                });
    }
    private void UpdateUI(FirebaseUser currentUser){

        mDatabase.child(auth.getCurrentUser().getUid()).setValue(user);
        startActivity(new Intent(RegisterScreen.this, FaceScreen.class));
    }

}
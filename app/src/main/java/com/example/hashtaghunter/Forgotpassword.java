package com.example.hashtaghunter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    private EditText emailedittext;
    private Button reset;
    private ProgressBar progressbar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailedittext = (EditText) findViewById(R.id.email);
        reset = (Button) findViewById(R.id.resetpassword);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    private void resetPassword() {
        String email = emailedittext.getText().toString().trim();

        if(email.isEmpty()){
            emailedittext.setError("Email is required.");
            emailedittext.requestFocus();
            return;
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            emailedittext.setError("Please enter a valid email address !");
            emailedittext.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Forgotpassword.this,"Check your email to reset the password!",Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                }
                else {

                    Toast.makeText(Forgotpassword.this,"try again! something went wrong :(",Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}
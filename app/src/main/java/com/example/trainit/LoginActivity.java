package com.example.trainit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity {
    EditText emailedittext,passwordedittext;
    Button loginbtn;
    ProgressBar progressbar;
    TextView createaccountbtntextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailedittext = findViewById(R.id.email_edit_text);
        passwordedittext = findViewById(R.id.password_edit_text);
        loginbtn = findViewById(R.id.log_in_btn);
        progressbar = findViewById(R.id.progress_bar);
        createaccountbtntextview = findViewById(R.id.create_account_text_view_btn);

        loginbtn.setOnClickListener((v)-> loginUser() );
        createaccountbtntextview.setOnClickListener((v)->startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)) );

    }

    void loginUser(){
        String email  = emailedittext.getText().toString();
        String password  = passwordedittext.getText().toString();


        boolean isValidated = validateData(email,password);
        if(!isValidated){
            return;
        }

        loginAccountInFirebase(email,password);

    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //login is success
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to mainactivity
                        startActivity(new Intent(LoginActivity.this,StartButtonActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Email not verified, Please verify your email.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //login failed
                    Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressbar.setVisibility(View.VISIBLE);
            loginbtn.setVisibility(View.GONE);
        }else{
            progressbar.setVisibility(View.GONE);
            loginbtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email,String password){
        //validate the data that are input by user.

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedittext.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            passwordedittext.setError("Password length is invalid");
            return false;
        }
        return true;
    }
}

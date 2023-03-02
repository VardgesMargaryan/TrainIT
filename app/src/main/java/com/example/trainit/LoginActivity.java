package com.example.trainit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button logInBtn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        logInBtn = findViewById(R.id.log_in_btn);
        progressBar = findViewById(R.id.progress_bar);
        googleBtn = findViewById(R.id.google_btn);
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        logInBtn.setOnClickListener((v)-> loginUser() );
        createAccountBtnTextView.setOnClickListener((v)->startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)) );


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            navigateToStartActivity();
        }



        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }

        });



    }

    void signIn(){
        Intent signIntent = gsc.getSignInIntent();
        startActivityForResult(signIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToStartActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToStartActivity(){
        finish();
        Intent intent = new Intent(LoginActivity.this,StartButtonActivity.class);
        startActivity(intent);
    }


    void loginUser(){
        String email  = emailEditText.getText().toString();
        String password  = passwordEditText.getText().toString();


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
                    //Login is success
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        //Go to StartActivity
                        startActivity(new Intent(LoginActivity.this, StartButtonActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Email not verified, Please verify your email.", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    //Login failed
                    Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            logInBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            logInBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email,String password){
        //validate the data that are input by user.

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length()<6){
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        return true;
    }
}

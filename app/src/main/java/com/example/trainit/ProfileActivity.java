package com.example.trainit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView logOut, email, changeLanguage;

    CircleImageView profileImageView;
    Button changeProfileImage, saveBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_profile);

        logOut = findViewById(R.id.logout_txt);
        email = findViewById(R.id.email_show_tv);
        changeLanguage = findViewById(R.id.change_language_tv);
        changeProfileImage = findViewById(R.id.change_pfp_btn);
        saveBtn = findViewById(R.id.save_btn);
        profileImageView = findViewById(R.id.profile_icon);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Picture");




        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String personEmail = acct.getEmail();
            email.setText(personEmail);
        }else{
            String emailShow = mAuth.getCurrentUser().getEmail();
            email.setText(emailShow);

        }

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();

            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }


    private void showChangeLanguageDialog() {
        final String[] languages = {"Русский", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(languages, -1, (dialog, which) -> {
            if(which == 0){
                //Russian
                setLocale("ru");
                recreate();
            }
            else if(which == 1){
                //English
                setLocale("en");
                recreate();
            }

            dialog.dismiss();

        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
    //--

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = prefs.getString("My_Lang", "");
        setLocale(lang);
    }


}

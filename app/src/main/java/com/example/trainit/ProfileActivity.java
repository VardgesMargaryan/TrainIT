package com.example.trainit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView logOut, email, changeLanguage, aboutUs;

    private CircleImageView profileImageView;

    private FirebaseStorage storage;

    private StorageReference storageReference;

    public Uri imageUri;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_profile);

        logOut = findViewById(R.id.logout_txt);
        email = findViewById(R.id.email_show_tv);
        changeLanguage = findViewById(R.id.change_language_tv);
        profileImageView = findViewById(R.id.profile_icon);
        aboutUs = findViewById(R.id.about_us_tv);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();





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


        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

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

//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosePicture();
//            }
//        });
//
//
//    }
//
//    private void choosePicture() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, 1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
//            imageUri = data.getData();
//            profileImageView.setImageURI(imageUri);
//
//
//
//        }
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

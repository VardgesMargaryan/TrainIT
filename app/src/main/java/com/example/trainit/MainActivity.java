package com.example.trainit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);




        Button startBtn = findViewById(R.id.start_btn);
        Button changeLanguage = findViewById(R.id.changeLang_btn);

        startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartButtonActivity.class);
            startActivity(intent);


        });

        changeLanguage.setOnClickListener(v -> showChangeLanguageDialog());
    }

    private void showChangeLanguageDialog() {
        final String[] languages = {"Русский", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
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
package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class StartButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_button);

        Button cond = findViewById(R.id.conditional_btn);
        Button cyc = findViewById(R.id.cycles_btn);
        Button mass = findViewById(R.id.massives_btn);
        Button str = findViewById(R.id.strings_btn);
        ImageView menu = findViewById(R.id.menu_icon);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(acct == null){
            Intent intent = new Intent(StartButtonActivity.this, LoginActivity.class);
            startActivity(intent);
        }



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });





        cond.setOnClickListener(v -> {
            Intent intent = new Intent(StartButtonActivity.this, ConditionalsActivity.class);
            startActivity(intent);

        });

        cyc.setOnClickListener(v -> {
            Intent intent = new Intent(StartButtonActivity.this, CyclesActivity.class);
            startActivity(intent);

        });

        mass.setOnClickListener(v -> {
            Intent intent = new Intent(StartButtonActivity.this, MassivesActivity.class);
            startActivity(intent);

        });

        str.setOnClickListener(v -> {
            Intent intent = new Intent(StartButtonActivity.this, StringsActivity.class);
            startActivity(intent);

        });
    }

    private void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(StartButtonActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.profile_opt:
                        Intent intent = new Intent(StartButtonActivity.this, ProfileActivity.class);
                        startActivity(intent);
                }
                return true;

            }
        });

        popupMenu.show();

    }
}
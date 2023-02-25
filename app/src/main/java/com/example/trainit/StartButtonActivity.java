package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
}
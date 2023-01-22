package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class StartButtonActivity extends AppCompatActivity {
    Button cond, cyc, mass, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_button);

        cond = (Button) findViewById(R.id.conditional_btn);
        cyc = (Button) findViewById(R.id.cycles_btn);
        mass = (Button) findViewById(R.id.massives_btn);
        str = (Button) findViewById(R.id.strings_btn);

        cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartButtonActivity.this, ConditionalsActivity.class);
                startActivity(intent);

            }
        });

        cyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartButtonActivity.this, CyclesActivity.class);
                startActivity(intent);

            }
        });

        mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartButtonActivity.this, MassivesActivity.class);
                startActivity(intent);

            }
        });

        str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartButtonActivity.this, StringsActivity.class);
                startActivity(intent);

            }
        });
    }
}
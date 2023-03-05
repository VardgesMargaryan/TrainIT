package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
    TextView textViewResult;
    Button returnBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        textViewResult = findViewById(R.id.tv_result);
        returnBtn = findViewById(R.id.return_btn);

        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);

        textViewResult.setText("Correct: " + correct + "\n\n\nWrong: " + wrong);


        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndActivity.this, StartButtonActivity.class);
                startActivity(intent);
            }
        });
    }
}
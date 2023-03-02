package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        textViewResult = findViewById(R.id.tv_result);

        int correct = getIntent().getIntExtra("correct", 0);
        int wrong = getIntent().getIntExtra("wrong", 0);

        textViewResult.setText("Correct: " + correct + "\nWrong: " + wrong);
    }
}
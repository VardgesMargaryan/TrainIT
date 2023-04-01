package com.example.trainit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringsActivity extends AppCompatActivity {

    TextView textViewProblem;
    Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;

    List<Problem> problemItems;

    int curProblem = 0;

    int correct = 0; int wrong = 0;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strings);


        textViewProblem = findViewById(R.id.tv_problem_s);
        btnAnswer1 = findViewById(R.id.btn_answer1_s);
        btnAnswer2 = findViewById(R.id.btn_answer2_s);
        btnAnswer3 = findViewById(R.id.btn_answer3_s);
        btnAnswer4 = findViewById(R.id.btn_answer4_s);

        loadAllProblems();

        Collections.shuffle(problemItems);

        setProblemOnScreen(curProblem);


        btnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(problemItems.get(curProblem).getAnswer1()
                        .equals(problemItems.get(curProblem).getCorrect())){
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }else{
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong! Correct Answer: "+ problemItems.get(curProblem).getCorrect(), Toast.LENGTH_SHORT).show();

                }

                if(curProblem < problemItems.size()-1){
                    curProblem++;
                    setProblemOnScreen(curProblem);
                }else{
                    Intent intent = new Intent(StringsActivity.this, EndActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                }

            }
        });

        btnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(problemItems.get(curProblem).getAnswer2()
                        .equals(problemItems.get(curProblem).getCorrect())){
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }else{
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong! Correct Answer: "+ problemItems.get(curProblem).getCorrect(), Toast.LENGTH_SHORT).show();

                }

                if(curProblem < problemItems.size()-1){
                    curProblem++;
                    setProblemOnScreen(curProblem);
                }else{
                    Intent intent = new Intent(StringsActivity.this, EndActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                }

            }
        });

        btnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(problemItems.get(curProblem).getAnswer3()
                        .equals(problemItems.get(curProblem).getCorrect())){
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }else{
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong! Correct Answer: "+ problemItems.get(curProblem).getCorrect(), Toast.LENGTH_SHORT).show();

                }

                if(curProblem < problemItems.size()-1){
                    curProblem++;
                    setProblemOnScreen(curProblem);
                }else{
                    Intent intent = new Intent(StringsActivity.this, EndActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                }

            }
        });

        btnAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(problemItems.get(curProblem).getAnswer4()
                        .equals(problemItems.get(curProblem).getCorrect())){
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }else{
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong! Correct Answer: "+ problemItems.get(curProblem).getCorrect(), Toast.LENGTH_SHORT).show();

                }

                if(curProblem < problemItems.size()-1){
                    curProblem++;
                    setProblemOnScreen(curProblem);
                }else{
                    Intent intent = new Intent(StringsActivity.this, EndActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                }

            }
        });
    }

    private void setProblemOnScreen(int number){
        textViewProblem.setText(problemItems.get(number).getProblem());
        btnAnswer1.setText(problemItems.get(number).getAnswer1());
        btnAnswer2.setText(problemItems.get(number).getAnswer2());
        btnAnswer3.setText(problemItems.get(number).getAnswer3());
        btnAnswer4.setText(problemItems.get(number).getAnswer4());

    }

    private void loadAllProblems(){
        problemItems = new ArrayList<>();

        String jsonStr = loadJSONFromAsset("StringProblems.json");

        try{
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray problems = jsonObj.getJSONArray("strings");



            for(int i = 0; i < problems.length(); i++){
                JSONObject problem = problems.getJSONObject(i);


                String problemString = problem.getString("problem");
                String answer1String = problem.getString("answer1");
                String answer2String = problem.getString("answer2");
                String answer3String = problem.getString("answer3");
                String answer4String = problem.getString("answer4");
                String correctString = problem.getString("correct");

                problemItems.add(new Problem(
                        problemString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString

                ));



            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String file) {
        String json = "";
        try {
            InputStream is = getAssets().open("StringProblems.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
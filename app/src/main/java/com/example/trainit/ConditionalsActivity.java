package com.example.trainit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class ConditionalsActivity extends AppCompatActivity {
    String probJSON;
    TextView showJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditionals);
        showJson = findViewById(R.id.showjson);


        Gson gson = new Gson();
        Problem problem = gson.fromJson(probJSON, Problem.class);
        Log.i("GSON", "Problem: "+problem.problem +" Answer:"+problem.answer);




    }

    public String loadJSONFromAsset(Context context) {
         String json;
        try {
            InputStream is = context.getAssets().open("ConditionalProblems.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        probJSON = json;
        return probJSON;

    }
}
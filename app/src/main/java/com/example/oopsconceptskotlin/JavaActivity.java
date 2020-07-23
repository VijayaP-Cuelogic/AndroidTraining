package com.example.oopsconceptskotlin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Intent myIntent = getIntent(); // this is just for example purpose
        final String keyIdentifier = myIntent.getStringExtra("keyIdentifier");
        System.out.println(keyIdentifier);
    }
}

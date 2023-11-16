package com.example.hackaton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
    }
    public void onClickDec(View view) {
        Intent intent = new Intent(this, Declaretion.class);
        startActivity(intent);
    }
    public void onClickHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
    public void onClickSign(View view) {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
}
package com.example.hackaton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignAndSendActivity extends AppCompatActivity {

    String nameFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_and_send);

        Intent intent = getIntent();
        nameFile = intent.getStringExtra(Declaretion.NAME_FILE);

        TextView textNameFile = (TextView) findViewById(R.id.textNameFile);
        textNameFile.setText(nameFile);

        try {
            FileInputStream fin = openFileInput(nameFile);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            fin.close();

            String list = new String(bytes);

            TextView textViewFile = (TextView) findViewById(R.id.textViewFile);
            textViewFile.setText(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onClickSignDec(View view) {
        try {
            FileInputStream fin = openFileInput(nameFile);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            fin.close();

            String list = new String(bytes);
            if (list.indexOf("Подписано(типо того)")==-1)
                if (list.indexOf("Отказано")==-1)
                    list = list + "\nПодписано(типо того)";

            FileOutputStream fos = openFileOutput(nameFile, MODE_PRIVATE);
            fos.write(list.getBytes());

            TextView textViewFile = (TextView) findViewById(R.id.textViewFile);
            textViewFile.setText(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onClickRejDec(View view) {
        try {
            FileInputStream fin = openFileInput(nameFile);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            fin.close();

            String list = new String(bytes);
            if ((list.indexOf("Подписано(типо того)")==-1) && (list.indexOf("Отказано")==-1))
                list = list + "\nОтказано";

            FileOutputStream fos = openFileOutput(nameFile, MODE_PRIVATE);
            fos.write(list.getBytes());

            TextView textViewFile = (TextView) findViewById(R.id.textViewFile);
            textViewFile.setText(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package com.example.hackaton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AuthoAdmin extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private String[] mUsernameArray = {"admin", "admin1", "user"};
    private String[] mPasswordArray = {"password", "password1", "pass"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autho_admin);

        // Привязка элементов интерфейса к переменным
        mUsername = findViewById(R.id.login);
        mUsername.setText("admin");
        mPassword = findViewById(R.id.password);
        mPassword.setText("password");
    }
    public void onCickAutho(View view) {
        String enteredUsername = mUsername.getText().toString();
        String enteredPassword = mPassword.getText().toString();

        // Проверка логина и пароля
        boolean isLoginValid = false;
        for (int i = 0; i < mUsernameArray.length; i++) {
            if (enteredUsername.equals(mUsernameArray[i]) && enteredPassword.equals(mPasswordArray[i])) {
            isLoginValid = true;
            break;
            }
        }

        // Вывод toast сообщения об успешности входа либо об ошибке
        if (isLoginValid) {
            Intent intent = new Intent(this, MainAdmin.class);
            startActivity(intent);
        } else
            Toast.makeText(this, "Неправильный логин или пароль!", Toast.LENGTH_SHORT).show();
    }
}
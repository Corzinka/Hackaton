package com.example.hackaton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileOutputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    private EditText editFirstName, editLastName, editSecondName, editFac, editCaf, editType, editMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация EditText
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editSecondName = (EditText) findViewById(R.id.editSecondName);
        editFac = (EditText) findViewById(R.id.editFac);
        editCaf = (EditText) findViewById(R.id.editCaf);
        editType = (EditText) findViewById(R.id.editType);
        editMail = (EditText) findViewById(R.id.editMail);
    }
    public void sendData(View view) {
        // Получение данных из EditText
        String data1 = editFirstName.getText().toString();
        String data2 = editLastName.getText().toString();
        String data3 = editSecondName.getText().toString();
        String data4 = editFac.getText().toString();
        String data5 = editCaf.getText().toString();
        String data6 = editType.getText().toString();
        String data7 = editMail.getText().toString();

        // Проверка на заполненность всех полей
        if (data1.isEmpty() || data2.isEmpty() || data3.isEmpty() || data4.isEmpty() || data5.isEmpty() || data6.isEmpty() || data7.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {

            //create file
            FileOutputStream fos = null;
            try {
                String nameFile = "dec:";
                nameFile = nameFile + data1.charAt(0) + data2.charAt(0) + data3.charAt(0) + data4.charAt(0) + data5.charAt(0) + data6.charAt(0) + ".txt";
                String text = data1 + "\n" + data2 + "\n" + data3 + "\n" + data4 + "\n" + data5 + "\n" + data6 + "\n" + data7;
                fos = openFileOutput(nameFile, MODE_PRIVATE);
                fos.write(text.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            Toast.makeText(this, "Данные успешно отправлены", Toast.LENGTH_SHORT).show();
        }
    }
    public void authoAdmin(View view) {
        Intent intent = new Intent(this, AuthoAdmin.class);
        startActivity(intent);
    }
}
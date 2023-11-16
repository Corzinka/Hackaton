package com.example.hackaton;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.NoSuchAlgorithmException;

import java.security.PublicKey;
import java.security.SecureRandom;

import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class AdminActivity extends AppCompatActivity {
    SecureRandom secureRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        try {
            TextView textView = (TextView) findViewById(R.id.listSign);

            FileInputStream fin = openFileInput("ListOfSign.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            fin.close();
            String list = new String(bytes);

            textView.setText(list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private KeyPair createPairKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            saveText(keyPair.getPublic().getEncoded(), false);

            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private void createSign() {
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        try {
            // Генерация ключей
            KeyPair keyPair = createPairKey();

            // Создание подписи
            secureRandom = new SecureRandom();
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(keyPair.getPrivate(), secureRandom);

            byte[] data = editTextName.getText().toString().getBytes();
            signature.update(data);
            byte[] digitalSignature = signature.sign();

            saveText(digitalSignature, true);

            Toast.makeText(this, "Подпись создана и сохранена", Toast.LENGTH_SHORT).show();

        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkSign() {
        FileInputStream fin = null;
        String FILE_NAME = null;
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        TextView editTextKey = (TextView) findViewById(R.id.textKey);
        try {
            Signature signature = Signature.getInstance("SHA256WithRSA");

            for (int i=0;i<100;i++) {
                FILE_NAME = "sign" + i + ".txt";
                File f = new File(getFilesDir() + "/" + FILE_NAME);
                if (f.exists()) {
                    fin = openFileInput(FILE_NAME);
                    byte[] bytesName = new byte[fin.available()];
                    fin.read(bytesName);

                    fin = openFileInput("key"+i+".txt");
                    byte[] bytesKey = new byte[fin.available()];
                    fin.read(bytesKey);

                    KeyFactory kf = KeyFactory.getInstance("RSA");
                    PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(bytesKey));

                    signature.initVerify(publicKey);
                    signature.update(editTextName.getText().toString().getBytes());

                    if (signature.verify(bytesName)) {
                        editTextKey.setText(FILE_NAME);
                        break;
                    } else editTextKey.setText("Не найдено");
                }
                else
                    Toast.makeText(this, "Подпись не найдена", Toast.LENGTH_SHORT).show();
            }
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveText(byte[] text, boolean sign) {
        FileOutputStream fos = null;
        TextView textView = (TextView) findViewById(R.id.listSign);
        EditText editText = (EditText) findViewById(R.id.editTextName);
        try {
            String FILE_NAME = null;
            for (int i = 0; i < 100; i++) {
                if (sign) FILE_NAME = "sign" + i + ".txt";
                else FILE_NAME = "key" + i + ".txt";
                File f = new File(getFilesDir() + "/" + FILE_NAME);
                if (!f.exists()) break;
            }
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text);

            if (sign) {
                FileInputStream fin = openFileInput("ListOfSign.txt");
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);
                fin.close();
                String list = new String(bytes);

                list = list + "\n" + editText.getText().toString() + ":" + FILE_NAME;
                textView.setText(list);

                fos = openFileOutput("ListOfSign.txt", MODE_PRIVATE);
                fos.write(list.getBytes());
            }
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void deleteSign() {
        TextView editTextName = (TextView) findViewById(R.id.editTextName);
        TextView editTextKey = (TextView) findViewById(R.id.textKey);
        TextView textView = (TextView) findViewById(R.id.listSign);
        String FILE_NAME = editTextKey.getText().toString();

        File f = new File(getFilesDir() + "/" + FILE_NAME);
        if (f.exists()) {
            f.delete();
            f = new File(getFilesDir() + "/key" + FILE_NAME.charAt(4) + ".txt");
            f.delete();

            FileInputStream fin = null;
            FileOutputStream fos = null;
            try {
                fin = openFileInput("ListOfSign.txt");
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);

                String list = new String(bytes);
                list = list.replace("\n" + editTextName.getText().toString() + ":" + FILE_NAME, " ");

                textView.setText(list);

                fos = openFileOutput("ListOfSign.txt", MODE_PRIVATE);
                fos.write(list.getBytes());

                fin.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    if (fos != null) fos.close();
                    if (fin != null) fin.close();
                } catch (IOException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else
            Toast.makeText(this, "Подпись не найдена", Toast.LENGTH_SHORT).show();
    }
    public void onButtonClickCreateSign(View v) {
        createSign();
    }
    public void onButtonClickCheckSign(View v) {
        checkSign();
    }
    public void onButtonClickDeleteSign(View v) {
        deleteSign();
    }
}
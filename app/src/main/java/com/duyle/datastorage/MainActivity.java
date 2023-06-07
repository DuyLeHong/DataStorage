package com.duyle.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    TextView tvKetqua;
    Button btnLuudulieu;
    Button btnDocdulieu;

    private void mapViews () {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        tvKetqua = findViewById(R.id.tv1);

        btnLuudulieu = findViewById(R.id.btn_luudulieu);
        btnDocdulieu = findViewById(R.id.btn_docdulieu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapViews();

        docDuLieu();

        btnLuudulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                try {
                    FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    fileOutputStream.write(userName.getBytes());
                    fileOutputStream.write(",".getBytes());
                    fileOutputStream.write(password.getBytes());
                    //fileOutputStream.write("\n".getBytes());
                    fileOutputStream.close();

                    Toast.makeText(MainActivity.this, "Luu du lieu thanh cong!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnDocdulieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docDuLieu();
            }
        });
    }

    private void docDuLieu () {
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            int read = -1;
            StringBuilder buffer = new StringBuilder();
            while ((read = fileInputStream.read()) != -1) {
                buffer.append((char) read);
            }

            String data = buffer.toString();

            String [] arrData = data.split(",");

            if (arrData != null && arrData.length > 0) {
                String userName = arrData[0];
                String password = arrData[1];

                edtUsername.setText(userName);
                edtPassword.setText(password);

                tvKetqua.setText(userName + " - " + password);
            }


        } catch (IOException e) {
            Log.e("MainActivity", e.getMessage());
        }
    }

    private final String FILE_NAME = "dulieu.txt";
}
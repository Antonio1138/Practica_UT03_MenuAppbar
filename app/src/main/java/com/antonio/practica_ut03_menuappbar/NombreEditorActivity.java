package com.antonio.practica_ut03_menuappbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NombreEditorActivity extends AppCompatActivity {

    int nombreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_editor);

        EditText editnombre = findViewById(R.id.edit_nombre);

        Intent intent = getIntent();
        nombreId = intent.getIntExtra("nombreId", -1);

        if (nombreId != -1){

            editnombre.setText(MainActivity.nombres.get(nombreId));

        } else  {

            MainActivity.nombres.add("");
            nombreId = MainActivity.nombres.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }




        editnombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                MainActivity.nombres.set(nombreId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.antonio.practica_ut03_menuappbar", Context.MODE_PRIVATE);

                HashSet<String> set  = new HashSet<>(MainActivity.nombres);

                sharedPreferences.edit().putStringSet("nombres", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

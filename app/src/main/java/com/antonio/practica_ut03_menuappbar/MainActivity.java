package com.antonio.practica_ut03_menuappbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> nombres = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_nombre_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_nombre) {

            Intent intent = new Intent(getApplicationContext(), NombreEditorActivity.class);

            startActivity(intent);

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listanombres = findViewById(R.id.listview_nombres);



        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("com.antonio.practica_ut03_menuappbar", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("nombres",null);

        if (set == null) {

            nombres.add("Ejemplo Nombre Apellidos");

        } else {

            nombres = new ArrayList(set);
        }

        nombres.add("Ejemplo Nombre Apellidos");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres);




        listanombres.setAdapter(arrayAdapter);

        listanombres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent(getApplicationContext(), NombreEditorActivity.class);
                intent.putExtra("nombreId", i);
                startActivity(intent);
            }
        });

        listanombres.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {

                final int nombreABorrar = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Esta acción no se puede deshacer")
                        .setMessage("¿Está seguro que quiere borrar este nombre?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                nombres.remove(nombreABorrar);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext()
                                        .getSharedPreferences("com.antonio.practica_ut03_menuappbar", Context.MODE_PRIVATE);

                                HashSet<String> set  = new HashSet<>(MainActivity.nombres);

                                sharedPreferences.edit().putStringSet("nombres", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}

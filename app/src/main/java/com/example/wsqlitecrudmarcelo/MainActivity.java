package com.example.wsqlitecrudmarcelo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    datoContacto dato;
    Adaptador adapter;

    ArrayList<Contacto>lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dato = new datoContacto(MainActivity.this);
        lista = dato.verTodo();
        adapter = new Adaptador(this,lista, dato);
        ListView list = findViewById(R.id.lista);
        Button insertar = findViewById(R.id.btnInsertar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Codigo para manejar el elemento de la lista
            }
            });

        insertar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setTitle("Nuevo Registro");
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialogo);
                    dialog.show();
                    final EditText nombre = dialog.findViewById(R.id.etNombre);
                    final EditText apellido = dialog.findViewById(R.id.etApellido);
                    final EditText email = dialog.findViewById(R.id.etEmail);
                    final EditText telefono = dialog.findViewById(R.id.etTelefono);
                    final EditText ciudad = dialog.findViewById(R.id.etCiudad);
                    Button guardar = dialog.findViewById(R.id.btnAgregar);
                    guardar.setText("Agregar");
                    Button cancelar = dialog.findViewById(R.id.btnCancelar);
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                c = new Contacto(nombre.getText().toString(),
                                        apellido.getText().toString(),
                                        email.getText().toString(),
                                        telefono.getText().toString(),
                                        ciudad.getText().toString());
                                dato.insertar(c);
                                lista = dato.verTodo();
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }catch (Exception e){
                                Toast.makeText(getApplication(),"ERROR", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }

            });


        }
    }

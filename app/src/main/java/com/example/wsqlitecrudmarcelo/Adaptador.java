package com.example.wsqlitecrudmarcelo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Adaptador  extends BaseAdapter {

    ArrayList<Contacto> lista;
    datoContacto dato;
    Contacto c;
    Activity a;//referencia a la ctividad que utiliza este adaptador
    int id = 0;
    public Adaptador(Activity a, ArrayList<Contacto> lista, datoContacto dato){
        this.lista = lista;
        this.a = a;
        this.dato = dato;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    //devuelve la cantidad de elementos en la lista
    @Override
    public int getCount(){
        return lista.size();
    }
    @Override

    //no se utiliza en este contexto, devuelve NULL
    public Object getItem(int i){
        c = lista.get(i);
        return null;
    }

    //devuelve el ID de un contacto en la posision i.
    @Override
    public long getItemId(int i){
        c = lista.get(i);
        return c.getId();
    }

    //este metodo se utiliza para mostrar cada elementp de la lista en la vista de la actividad.
    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup){
        View v = view;
        if (v == null){
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        c = lista.get(posicion);

        TextView nombre = v.findViewById(R.id.nombre);
        TextView apellido = v.findViewById(R.id.apellido);
        TextView email = v.findViewById(R.id.email);
        TextView telefono = v.findViewById(R.id.telefono);
        TextView ciudad = v.findViewById(R.id.ciudad);

        //Botones
        Button editar = v.findViewById(R.id.btnEditar);
        Button eliminar = v.findViewById(R.id.btnEliminar);

        nombre.setText(c.getNombre());
        apellido.setText(c.getApellido());
        email.setText(c.getEmail());
        telefono.setText(c.getTelefono());
        ciudad.setText(c.getCiudad());
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        //Al hacer click en "editar", se muestra un cuadro de dialogo que permite al usuario
        // editar los detalles del contacto. los cambios se aplican a la base de datos

        editar.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){

            int pos = Integer.parseInt(view.getTag().toString());
            final Dialog dialog = new Dialog(a);
            dialog.setTitle("Editar Registro");
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogo);
            dialog.show();

            final EditText nombre = dialog.findViewById(R.id.etNombre);
            final EditText apellido = dialog.findViewById(R.id.etApellido);
            final EditText email = dialog.findViewById(R.id.etEmail);
            final EditText telefono = dialog.findViewById(R.id.etTelefono);
            final EditText ciudad = dialog.findViewById(R.id.etCiudad);

            //Botones
            Button guardar = dialog.findViewById(R.id.btnAgregar);
            Button cancelar = dialog.findViewById(R.id.btnCancelar);

            c = lista.get(pos);
            setId(c.getId());

            nombre.setText(c.getNombre());
            apellido.setText(c.getApellido());
            email.setText(c.getEmail());
            telefono.setText(c.getTelefono());
            ciudad.setText(c.getCiudad());

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        c = new Contacto(getId(), nombre.getText().toString(),
                                apellido.getText().toString(),
                                email.getText().toString(),
                                telefono.getText().toString(),
                                ciudad.getText().toString());

                        dato.editar(c);
                        lista = dato.verTodo();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }catch (Exception e){
                        Toast.makeText(a, "error", Toast.LENGTH_SHORT).show();
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
            eliminar.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view){
                //dialogo para confirmar si o no se elimine
        int pos = Integer.parseInt(view.getTag().toString());
        c = lista.get(posicion);
        setId(c.getId());
        AlertDialog.Builder del = new AlertDialog.Builder(a);
        del.setMessage("estas seguro de eliminar");
        del.setCancelable(false);
        del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dato.eliminar(getId());
                lista = dato.verTodo();
                notifyDataSetChanged();
            }
        });

        del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        del.show();
    }

});
        return v;




    }
}

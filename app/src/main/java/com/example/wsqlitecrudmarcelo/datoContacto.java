package com.example.wsqlitecrudmarcelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;


public class datoContacto {
    SQLiteDatabase bd;
    ArrayList<Contacto>lista = new ArrayList<Contacto>();
    Contacto c;
    Context ct;
    String nombreBD = "BDContactos";

    String tabla = "CREATE TABLE IF NOT EXISTS contacto(id integer PRIMARY KEY AUTOINCREMENT, nombre text, apellido text, email text, telefono text, ciudad text)";

    public datoContacto(Context c){
        this.ct = c;
        bd = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE,null);
        bd.execSQL(tabla);
    }

    public boolean insertar(Contacto c){
        //METODO PARA INERTAR UN NUEVO CONTACTO EN LA BASE DE DATOS
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return (bd.insert("contacto", null, contenedor))>0;
    }


    public boolean eliminar(int id){
        //METODO PARA ELIMINAR LA INFORMACION DE UN CONTACTO SEGUN EL ID
        return (bd.delete("contacto", "id=" + id, null))>0;
    }

    public boolean editar(Contacto c){
        //METODO PARA INERTAR UN NUEVO CONTACTO EN LA BASE DE DATOS
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("apellido", c.getApellido());
        contenedor.put("email", c.getEmail());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("ciudad", c.getCiudad());
        return (bd.update("contacto", contenedor,"id =" + c.getId(),null))>0;
    }

    public ArrayList<Contacto>verTodo(){
        //Metodo para recuperar una lista de contacto desde la base de datos
        lista.clear();
        Cursor cursor = bd.rawQuery("SELECT * FROM contacto", null);
        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();

            do{
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }while (cursor.moveToNext());

        }
        return lista;
    }
    public Contacto verUno(int posicion){
        // Método para buscar el contacto según su id en la base de datos y devolverlo como un objeto contacto
        Cursor cursor = bd.rawQuery("select * from contacto", null);
        cursor.moveToPosition(posicion);

        c = new Contacto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        return c;
    }


}

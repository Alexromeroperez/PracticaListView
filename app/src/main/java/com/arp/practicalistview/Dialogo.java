package com.arp.practicalistview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Alex on 14/10/2015.
 */
public class Dialogo {

    /****************************************************************/
    /****************************************************************/
    /***************   Todos los alertDialog  ***********************/
    /****************************************************************/
    /****************************************************************/
    /****************************************************************/


    public static void addContacto(final Context cont, final ArrayList<Contacto> lista){
        AlertDialog.Builder alert = new AlertDialog.Builder(cont);
        alert.setTitle(R.string.str_añadirContacto);
        LayoutInflater inflater = LayoutInflater.from(cont);
        final View vista = inflater.inflate(R.layout.detalle_modifica, null);
        final EditText et1 = (EditText) vista.findViewById(R.id.etModifica);
        alert.setView(vista);
        alert.setPositiveButton(R.string.str_añadirContacto,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Contacto c=new Contacto(0,et1.getText().toString(),new ArrayList<String>());
                        if(c.getNombre().compareTo("")!=0) {
                            lista.add(c);
                        }else {
                            tostada(cont,"Añade un nombre");
                        }

                    }
                });
        alert.setNegativeButton(R.string.str_cancelar, null);
        alert.show();
    }

    public static void addTlf(final Context c, final Contacto contacto){
        final ArrayList<String> tlf=contacto.getTlfs();
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle(R.string.str_añadirTlf);
        LayoutInflater inflater = LayoutInflater.from(c);
        final View vista = inflater.inflate(R.layout.detalle_modifica, null);
        alert.setView(vista);
        alert.setPositiveButton(R.string.str_añadirTlf,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText et1 = (EditText) vista.findViewById(R.id.etModifica);
                        if(et1.getText().toString().compareTo("")!=0) {
                            tlf.add(et1.getText().toString());
                            contacto.setTlfs(tlf);
                            tostada(c, "Telefono añadido");
                        }else {
                            tostada(c,"Añade un tlf");
                        }
                    }
                });
        alert.setNegativeButton(R.string.str_cancelar, null);
        alert.show();
    }

    public static void modificaContc(final Context c, final Contacto contacto){
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle(R.string.str_modifica);
        LayoutInflater inflater = LayoutInflater.from(c);
        final View vista = inflater.inflate(R.layout.detalle_modifica, null);
        final EditText et1 = (EditText) vista.findViewById(R.id.etModifica);
        et1.setText(contacto.getNombre());
        alert.setView(vista);
        alert.setPositiveButton(R.string.str_modifica,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        contacto.setNombre(et1.getText().toString());
                        tostada(c, "Contacto modificado");
                    }
                });
        alert.setNegativeButton(R.string.str_cancelar, null);
        alert.show();
    }

    public static void tostada(Context c,String text){

        Toast.makeText(c,text,Toast.LENGTH_SHORT).show();
    }
}

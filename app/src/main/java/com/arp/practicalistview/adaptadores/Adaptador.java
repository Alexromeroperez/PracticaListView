package com.arp.practicalistview.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arp.practicalistview.Contacto;
import com.arp.practicalistview.listas.ListaTlf;
import com.arp.practicalistview.R;
import com.arp.practicalistview.listas.Principal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 07/10/2015.
 */
public class Adaptador extends ArrayAdapter<Contacto> {

    private Context contexto;
    private int recurso;
    private ArrayList<Contacto> lista;
    private LayoutInflater i;

    public Adaptador(Context context, int resource, ArrayList<Contacto> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.recurso=resource;
        this.lista=objects;
        i=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(vh==null){
            vh=new ViewHolder();
            convertView=i.inflate(recurso,null);
            vh.iv=(ImageView)convertView.findViewById(R.id.ivFoto);
            vh.tvNombre=(TextView)convertView.findViewById(R.id.tvNombre);
            vh.tvTelefono=(TextView)convertView.findViewById(R.id.tvTlf);
            vh.btMostrar=(ImageButton)convertView.findViewById(R.id.btMostrar);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder)convertView.getTag();
        }
        Contacto c = lista.get(position);
        final ArrayList<String> telefonos=c.getTlfs();
        vh.tvNombre.setText(c.getNombre());
        if(telefonos.size()>0) {
            vh.tvTelefono.setText(telefonos.get(0));
        }else {
            vh.tvTelefono.setText(R.string.str_sintelef);
        }
        //muestra una imagen u otra dependiendo del tamaño de la lista de telefonos
        if(telefonos.size()>1) {
            vh.btMostrar.setImageResource(R.drawable.mas);
        }else {
            vh.btMostrar.setImageResource(R.drawable.menos);
        }

        //Carga una actividad donde muestra los telefonos del elemento elegido
        vh.btMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(contexto,ListaTlf.class);
                Bundle b=new Bundle();
                b.putStringArrayList("telefonos", telefonos);
                i.putExtras(b);
                contexto.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        private TextView tvNombre,tvTelefono;
        private ImageView iv;
        private ImageButton btMostrar;

    }

}

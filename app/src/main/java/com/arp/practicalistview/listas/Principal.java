package com.arp.practicalistview.listas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arp.practicalistview.Contacto;
import com.arp.practicalistview.Dialogo;
import com.arp.practicalistview.R;
import com.arp.practicalistview.adaptadores.Adaptador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class Principal extends AppCompatActivity {

    private ListView lv;
    private ArrayList <Contacto> listaC;
    private Adaptador ad;
    private int posicion;
    private final int MOSTRAR=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        lv=(ListView)findViewById(R.id.lvContacto);
        iniciar();

    }

    /*************************************************************************/
    /*************************************************************************/
    /*************************************************************************/
    /********************      Menus de la actividad    **********************/
    /*************************************************************************/
    /*************************************************************************/
    /*************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.añadir) {
            Dialogo.addContacto(this,listaC);
            ad.notifyDataSetChanged();
            return true;
        }else if (id == R.id.ordenarAsc) {
            Collections.sort(listaC);
            ad.notifyDataSetChanged();
            return true;
        }else if (id == R.id.ordenarDes) {
            Collections.sort(listaC, new Comparator<Contacto>() {
                @Override
                public int compare(Contacto c1, Contacto c2) {
                    return c2.getNombre().compareTo(c1.getNombre());
                }
            });
            ad.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_emergente, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        posicion=info.position;
            Contacto c=listaC.get(posicion);
        switch (item.getItemId()) {
            case R.id.añadirTlf:
                Dialogo.addTlf(this, c);
                Collections.sort(listaC);
                ad.notifyDataSetChanged();

                return true;
            case R.id.modificar:
                Dialogo.modificaContc(this, c);
                Collections.sort(listaC);
                ad.notifyDataSetChanged();

                return true;
            case R.id.borrar:
                listaC.remove(c);
                ad.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    /*************************************************************************/
    /*************************************************************************/
    /*************************************************************************/
    /***********************      Metodos Auxiliares    **********************/
    /*************************************************************************/
    /*************************************************************************/
    /*************************************************************************/

    private void añadirTlf(ArrayList <Contacto> lista){
        //Añade los telefonos correspondientes a cada elemento de la lista de contactos
        for (int i = 0; i <lista.size() ; i++) {
            Contacto c=lista.get(i);
            c.setTlfs((ArrayList<String>) Contacto.getListaTelefonos(this, c.getId()));
        }
    }

    private void iniciar(){
        //inicializa la lista con todos los contactos
        listaC=new ArrayList<>();
        listaC = (ArrayList<Contacto>) Contacto.getListaContactos(this);
        añadirTlf(listaC);
        ad=new Adaptador(this,R.layout.lista_detalle,listaC);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
    }

    public void mostrar(View v){
        ArrayList <String> tlf=(ArrayList<String>)v.getTag();
        Intent i=new Intent(this,ListaTlf.class);
        Bundle b=new Bundle();
        b.putStringArrayList("telefonos", tlf);
        i.putExtras(b);
        startActivityForResult(i, MOSTRAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MOSTRAR && resultCode==RESULT_OK){
            Bundle b=data.getExtras();
            Contacto c=listaC.get(posicion);
            c.setTlfs((ArrayList<String>)b.get("tlfs"));
            Collections.sort(listaC);
            ad.notifyDataSetChanged();
        }

    }
}

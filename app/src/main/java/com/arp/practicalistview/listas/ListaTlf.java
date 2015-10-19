package com.arp.practicalistview.listas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arp.practicalistview.Contacto;
import com.arp.practicalistview.Dialogo;
import com.arp.practicalistview.R;
import com.arp.practicalistview.adaptadores.AdaptadorTlf;

import java.util.ArrayList;

public class ListaTlf extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> tlfs;
    private AdaptadorTlf adt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tlf);
        lv=(ListView)findViewById(R.id.lvTlf);

        Bundle b=getIntent().getExtras();
        tlfs=new ArrayList<>();
        tlfs=(ArrayList)b.get("telefonos");
        adt=new AdaptadorTlf(this,R.layout.telefono_detalle,tlfs);
        lv.setAdapter(adt);
        registerForContextMenu(lv);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_tlf, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String c=tlfs.get(info.position);
        switch (item.getItemId()) {
            case R.id.modificar:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.str_modifica);
                LayoutInflater inflater = LayoutInflater.from(this);
                final View vista = inflater.inflate(R.layout.detalle_modifica, null);
                final EditText et1 = (EditText) vista.findViewById(R.id.etModifica);
                et1.setText(c);
                alert.setView(vista);
                alert.setPositiveButton(R.string.str_modifica,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tlfs.remove(info.position);
                                tlfs.add( et1.getText().toString());
                            }
                        });
                alert.setNegativeButton(R.string.str_cancelar, null);
                alert.show();
                adt.notifyDataSetChanged();
                return true;
            case R.id.borrar:
                tlfs.remove(c);
                adt.notifyDataSetChanged();
                Dialogo.tostada(this,"Has borrado el tlf "+c);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

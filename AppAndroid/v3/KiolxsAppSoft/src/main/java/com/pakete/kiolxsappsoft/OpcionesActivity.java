package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;

public class OpcionesActivity extends Activity {

    public String dataBaseName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_opciones);

        dataBaseName = getResources().getString(R.string.dataBaseName);

        LeerOpcionesBD();

        //Metodo Onlistener para el boton RefrescarLogEventos
        final Button button = (Button) findViewById(R.id.buttonOpGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ActualizarOpcionesBD();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void LeerOpcionesBD(){

        EditText ipEditText   = (EditText)findViewById(R.id.editTextOpIP);
        String ip = ipEditText.getText().toString();
        EditText puertoEditText   = (EditText)findViewById(R.id.editTextOpPuerto);
        String puerto = puertoEditText.getText().toString();
        EditText parametroEditText   = (EditText)findViewById(R.id.editTextOpParametro);
        String parametro = parametroEditText.getText().toString();
        EditText namedev1EditText   = (EditText)findViewById(R.id.editTextOpNameDev1);
        String namedev1 = namedev1EditText.getText().toString();
        EditText macdev1EditText   = (EditText)findViewById(R.id.editTextOpMACDEV1);
        String macdev1 = macdev1EditText.getText().toString();
        EditText namedev2EditText   = (EditText)findViewById(R.id.editTextOpNameDev2);
        String namedev2 = namedev2EditText.getText().toString();
        EditText macdev2EditText   = (EditText)findViewById(R.id.editTextOpMACDEV2);
        String macdev2 = macdev2EditText.getText().toString();

        DBHelper dbHelper = new DBHelper(OpcionesActivity.this, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM opciones ORDER BY id_opcion DESC LIMIT 1",null);
        //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                // displaying all data in textview
               ipEditText.setText(fila.getString(1));
               puertoEditText.setText(fila.getString(2));
               parametroEditText.setText(fila.getString(3));
               namedev1EditText.setText(fila.getString(4));
               macdev1EditText.setText(fila.getString(5));
               namedev2EditText.setText(fila.getString(6));
               macdev2EditText.setText(fila.getString(7));
            } while(fila.moveToNext()); }
        fila.close();

        db.close();
        dbHelper.close();
    }


    public void ActualizarOpcionesBD(){

        EditText ipEditText   = (EditText)findViewById(R.id.editTextOpIP);
        String ip = ipEditText.getText().toString();
        EditText puertoEditText   = (EditText)findViewById(R.id.editTextOpPuerto);
        String puerto = puertoEditText.getText().toString();
        EditText parametroEditText   = (EditText)findViewById(R.id.editTextOpParametro);
        String parametro = parametroEditText.getText().toString();
        EditText namedev1EditText   = (EditText)findViewById(R.id.editTextOpNameDev1);
        String namedev1 = namedev1EditText.getText().toString();
        EditText macdev1EditText   = (EditText)findViewById(R.id.editTextOpMACDEV1);
        String macdev1 = macdev1EditText.getText().toString();
        EditText namedev2EditText   = (EditText)findViewById(R.id.editTextOpNameDev2);
        String namedev2 = namedev2EditText.getText().toString();
        EditText macdev2EditText   = (EditText)findViewById(R.id.editTextOpMACDEV2);
        String macdev2 = macdev2EditText.getText().toString();

        DBHelper dbHelper = new DBHelper(OpcionesActivity.this, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_opcion FROM opciones ORDER BY id_opcion DESC LIMIT 1", null);

        if (cursor == null) {
            db.execSQL("UPDATE opciones SET ip='"+ip+"' , puerto'"+puerto+"', parametro='"+parametro+"' , namedev1='"+namedev1+"', macdev1='"+macdev1+"', namedev2='"+namedev2+"', macdev2='"+macdev2+"' WHERE id_opcion='1'");
        } else {
            //Insertamos las opciones por defecto
            ContentValues valuesOpciones = new ContentValues();
            valuesOpciones.put("ip", ip);
            valuesOpciones.put("puerto", puerto);
            valuesOpciones.put("parametro", parametro);
            valuesOpciones.put("namedev1", namedev1);
            valuesOpciones.put("macdev1", macdev1);
            valuesOpciones.put("namedev2", namedev2);
            valuesOpciones.put("macdev2", macdev2);
            db.insert(DBHelper.TABLAOpciones, null, valuesOpciones);
        }


        db.close();
        dbHelper.close();
    }


}

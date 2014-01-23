package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.os.AsyncTask;

public class LogActivity extends Activity {

    public String rutaParseo = "";
    public String dataBaseName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_eventos);

        //rutaParseo = getResources().getString(R.string.rutaParseo);
        dataBaseName = getResources().getString(R.string.dataBaseName);
        //Leemos las opciones de BD
        DBHelper dbHelper = new DBHelper(LogActivity.this, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM opciones ORDER BY id_opcion DESC",null);
        //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                // Metemos en la string la IP o servidor al que conectarnos IP:PUERTO-PARAMETRO
                rutaParseo = fila.getString(1)+":"+fila.getString(2)+fila.getString(3);
            } while(fila.moveToNext()); }
        fila.close();

        //Close the Database and the Helper
        db.close();
        dbHelper.close();

        //Metodo Onlistener para el boton RefrescarLogEventos
        final Button button = (Button) findViewById(R.id.buttonRefrescarLogEventos);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

             new ActualizarEditTextAsynTask().execute();

            }
        });

        //LLamamos a la funcion para actuzliar el EditText
        ActualizarEditText(LogActivity.this);
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

    public void ActualizarEditText(Context ctx){

        EditText txtLogEvento = (EditText)findViewById(R.id.editTextRegistroEventos);
        DBHelper dbHelper = new DBHelper(ctx, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        txtLogEvento.setText("");//Limpiamos el texto

        Cursor fila = db.rawQuery("SELECT id_evento,evento,fecha,status FROM logeventos ORDER BY id_evento ASC",null);
        //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                // displaying all data in textview
                txtLogEvento.append(" " + fila.getString(0) + " - " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3) + "\n");
            } while(fila.moveToNext()); }
        fila.close();

        //Close the Database and the Helper
        db.close();
        dbHelper.close();

    }


    private class ActualizarEditTextAsynTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            new Funciones(LogActivity.this, rutaParseo, false, "LogActivity");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ActualizarEditText(LogActivity.this);
        }
    }

}

package com.pakete.kiolxsappsoft;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.format.Time;
import java.io.File;


public class PrecargadorActivity extends Activity{

    public String dataBaseName = "";
    public String directorioINT = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_precargador);

        dataBaseName = getResources().getString(R.string.dataBaseName);
        directorioINT = getResources().getString(R.string.directorioINT);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //Creamos el directorio donde descargamos los ficheros temporales
                    crearDirectorioINT();

                    //Borramos la db por si las moscas y generamos una nueva
                    PrecargadorActivity.this.deleteDatabase(dataBaseName);

                    //Hacemos un Sleep un rato
                    Thread.sleep(5000);

                    //Iniciamos la Base de datos
                    DBHelper dbHelper = new DBHelper(PrecargadorActivity.this, dataBaseName);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    //Insertamos las opciones por defecto
                    ContentValues valuesLogEventos = new ContentValues();
                    valuesLogEventos.put("evento", "- Base de datos inicializada");
                    valuesLogEventos.put("fecha", getDatePhone());
                    valuesLogEventos.put("status", "Ok");
                    db.insert(DBHelper.TABLALogEventos, null, valuesLogEventos);

                    //Close the Database and the Helper
                    db.close();
                    dbHelper.close();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                }
                return null;
            }

            //Funcion que nos genera la hora actual para meterla en la BD
            private String getDatePhone(){
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                return today.format("%k:%M:%S");
            }

            @Override
            protected void onPostExecute(Void result) {
                Intent ArranquePrincipal = new Intent(PrecargadorActivity.this, PrincipalActivity.class);
                startActivity(ArranquePrincipal);
                finish();
            }
        };
        task.execute((Void[])null);


    }

    public void crearDirectorioINT(){
        File folder = new File(directorioINT);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }

    }


}//Fin de la clase
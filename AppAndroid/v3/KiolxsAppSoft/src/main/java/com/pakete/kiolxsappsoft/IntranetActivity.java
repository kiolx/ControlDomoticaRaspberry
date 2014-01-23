package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class IntranetActivity extends Activity {

    public String rutaParseo = "";
    public String dataBaseName = "";
    public String macDev1 = "";
    public String macDev2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intranet);

         TextView textViewIntra1   = (TextView)findViewById(R.id.textViewIntra1);
         TextView textViewIntra2   = (TextView)findViewById(R.id.textViewIntra2);

        //rutaParseo = getResources().getString(R.string.rutaParseo);
        dataBaseName = getResources().getString(R.string.dataBaseName);
        //Leemos las opciones de BD
        DBHelper dbHelper = new DBHelper(IntranetActivity.this, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM opciones ORDER BY id_opcion DESC LIMIT 1",null);
        //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                rutaParseo = fila.getString(1)+":"+fila.getString(2)+fila.getString(3);
                textViewIntra1.setText(fila.getString(4));
                macDev1 = fila.getString(5);
                textViewIntra2.setText(fila.getString(6));
                macDev2 = fila.getString(7);

            } while(fila.moveToNext()); }
        fila.close();

        //Close the Database and the Helper
        db.close();
        dbHelper.close();

        //Metodos OnSetListener de los views, para que accion hacer cuando se pulse sobre ellos //
        //////////////////////////////////////////////////////////////////////////////////////////

        ImageView WolDeviceOn1 =(ImageView)findViewById(R.id.imageViewIntra1);
        WolDeviceOn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textViewIntra1);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(IntranetActivity.this, rutaParseo+"?act=newReg&cmd=WolDeviceOn1*"+macDev1, true, "IntranetActivity");
            }
        });

        ImageView WolDeviceOn2 =(ImageView)findViewById(R.id.imageViewIntra3);
        WolDeviceOn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textViewIntra2);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(IntranetActivity.this, rutaParseo+"?act=newReg&cmd=WolDeviceOn2*"+macDev2, true, "IntranetActivity");
            }
        });

        ImageView WolShutDeviceOn1 =(ImageView)findViewById(R.id.imageViewIntra2);
        WolShutDeviceOn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textViewIntra1);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(IntranetActivity.this, rutaParseo+"?act=newReg&cmd=WolShutDeviceOn1*"+macDev1, true, "IntranetActivity");
            }
        });

        ImageView WolShutDeviceOn2 =(ImageView)findViewById(R.id.imageViewIntra4);
        WolShutDeviceOn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textViewIntra2);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(IntranetActivity.this, rutaParseo+"?act=newReg&cmd=WolShutDeviceOn2*"+macDev2, true, "IntranetActivity");
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


}

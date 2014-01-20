package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MiHabitacionActivity extends Activity {

    public String rutaParseo = "";

    //Esto funciona de la siguiente forma, llamammos a la funcion de DownloadFile de forma
    //asincronica, por lo que no bloqueamos el movil hasta que se complete el comando :D
    //la funcion lo que hará será que a la misma vez que madna el comando tipo puente.php?cmd=comepapa
    //hacemos que en php lo guarde en bd y lo meustree, y nuestra funcion descargue ese ficheor lo meta en BD
    //asi tenemos un registro que de verdad se ha completado la accion :D :P flama de la rama niñuuu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mi_habitacion);

        rutaParseo = getResources().getString(R.string.rutaParseo);

        //Metodos OnSetListener de los views, para que accion hacer cuando se pulse sobre ellos //
        //////////////////////////////////////////////////////////////////////////////////////////

        FrameLayout encenderAire =(FrameLayout)findViewById(R.id.Frameview21);
        encenderAire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView21);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                //Enviamos del tipo action=newReg&command=encender_aire
                //Enviamos del tip action=updateReg&command=..&id=...
                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=aireON", true, "MiHabitacionActivity");

            }
        });

        FrameLayout apagarAire =(FrameLayout)findViewById(R.id.Frameview22);
        apagarAire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView22);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=aireOFF", true, "MiHabitacionActivity");

            }
        });

        FrameLayout encenderTV =(FrameLayout)findViewById(R.id.Frameview23);
        encenderTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView23);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=tvON", true, "MiHabitacionActivity");

            }
        });

        FrameLayout apagarTV =(FrameLayout)findViewById(R.id.Frameview24);
        apagarTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView24);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=tvOFF", true, "MiHabitacionActivity");

            }
        });

        FrameLayout encenderPLUS =(FrameLayout)findViewById(R.id.Frameview25);
        encenderPLUS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView25);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=plusON", true, "MiHabitacionActivity");

            }
        });

        FrameLayout apagarPLUS =(FrameLayout)findViewById(R.id.Frameview26);
        apagarPLUS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView26);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=plusOFF", true, "MiHabitacionActivity");

            }
        });

        FrameLayout encenderTODO =(FrameLayout)findViewById(R.id.Frameview27);
        encenderTODO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView27);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=todoON", true, "MiHabitacionActivity");

            }
        });

        FrameLayout apagarTODO =(FrameLayout)findViewById(R.id.Frameview28);
        apagarTODO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView28);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                new Funciones(MiHabitacionActivity.this, rutaParseo+"?act=newReg&cmd=todoOFF", true, "MiHabitacionActivity");

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


}//Fin de la clase

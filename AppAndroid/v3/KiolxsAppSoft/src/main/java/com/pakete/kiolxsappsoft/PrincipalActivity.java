package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PrincipalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal_fragmento);

        //Metodos OnSetListener de los views, para que accion hacer cuando se pulse sobre ellos //
        //////////////////////////////////////////////////////////////////////////////////////////
        FrameLayout logPanel =(FrameLayout)findViewById(R.id.Frameview5);
        logPanel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView5);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                Intent LogActiviyStart = new Intent(PrincipalActivity.this, LogActivity.class);
                startActivity(LogActiviyStart);

            }
        });

        FrameLayout MiHabitacionPanel =(FrameLayout)findViewById(R.id.Frameview1);
        MiHabitacionPanel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView1);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                Intent MiHabitacionActiviyStart = new Intent(PrincipalActivity.this, MiHabitacionActivity.class);
                startActivity(MiHabitacionActiviyStart);

            }
        });

        FrameLayout IntranetPanel =(FrameLayout)findViewById(R.id.Frameview3);
        IntranetPanel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView3);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                Intent IntranetActiviyStart = new Intent(PrincipalActivity.this, IntranetActivity.class);
                startActivity(IntranetActiviyStart);

            }
        });

        FrameLayout OpcionesPanel =(FrameLayout)findViewById(R.id.Frameview6);
        OpcionesPanel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView cambioFondo =(TextView)findViewById(R.id.textView6);
                cambioFondo.setBackgroundResource(R.drawable.gradient_bg_hover);

                Intent OpcionesActiviyStart = new Intent(PrincipalActivity.this, OpcionesActivity.class);
                startActivity(OpcionesActiviyStart);

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

package com.pakete.kiolxsappsoft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class IntranetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intranet);

        //Metodo Onlistener para el boton RefrescarLogEventos
        final Button button = (Button) findViewById(R.id.buttonWolPortatil);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

             new WOLPowerManager().sendWOL(IntranetActivity.this, "BC-AE-C5-83-E7-F2", 4);

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

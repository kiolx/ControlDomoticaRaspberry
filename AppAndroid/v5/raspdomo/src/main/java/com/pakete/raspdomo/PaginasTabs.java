package com.pakete.raspdomo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by GlobalMarduk on 24/01/14.
 */
public class PaginasTabs{

    public static String estado = "checked";
    public static Boolean estadoBool = false;

    public static class  TabFragment0 extends Fragment {

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;
        public ViewGroup container;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_fragment_log, container, false);
            new Funciones(ctx,"",false,"").LogBD(rootView,ctx ,dataBaseName);

            return rootView;
        }

        public TabFragment0(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado){
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;
        }

        @Override
        public void onResume() {
            new Funciones(ctx,"",false,"").LogBD(getView(),ctx ,dataBaseName);
            super.onResume();
        }
    }

    public static class TabFragment1 extends Fragment {
        public static final String ARG_OBJECT = "object";

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;
        public String rutaParseo;
        private Switch mySwitch;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_fragment_servidor, container, false);

            mySwitch = (Switch)rootView.findViewById(R.id.switchTestingLed);
            estadoBool = mySwitch.isChecked();
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Do Something
                   if(estadoBool != isChecked){
                    if(isChecked){
                        new Funciones(ctx, rutaParseo+"?act=newReg&cmd=ledON", false, ActivityName);
                        estado = "checked";
                        estadoBool = true;
                    }else{
                        new Funciones(ctx, rutaParseo+"?act=newReg&cmd=ledOFF", false, ActivityName);
                        estado = "unchecked";
                        estadoBool = false;
                    }
                   }
                 }
            });
            return rootView;
        }

        public TabFragment1(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado, String rutaParseollegado){
            rutaParseo = rutaParseollegado;
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;
        }

    }


    public static class TabFragment2 extends Fragment {
        public static final String ARG_OBJECT = "object";

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_fragment_servidor2, container, false);
            return rootView;
        }

        public TabFragment2(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado){
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;
        }
    }

    public static class TabFragment3 extends Fragment {
        public static final String ARG_OBJECT = "object";

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_fragment_opciones, container, false);
            new Funciones(ctx,"",false, ActivityName).OpcionesBD(rootView, "leerOpcionesBD", ctx, dataBaseName);

            return rootView;

        }

        public TabFragment3(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado){
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;

        }

      }

}

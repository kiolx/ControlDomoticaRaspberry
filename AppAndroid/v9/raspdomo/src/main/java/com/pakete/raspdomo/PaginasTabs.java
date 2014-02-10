package com.pakete.raspdomo;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

/**
 * Created by GlobalMarduk on 24/01/14.
 */
public class PaginasTabs{

    public static class  TabFragment0 extends Fragment {

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;
        public ViewGroup container;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.layout_fragment_log, container, false);
            rootView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            new Funciones(ctx,"",false,"").LogBD(rootView, ctx, dataBaseName);

            return rootView;
        }

        public TabFragment0(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado){
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;
        }

        @Override
        public void onResume() {
           //new Funciones(ctx,"",false,"").LogBD(getView(),ctx ,dataBaseName);
            super.onResume();
        }
    }

    public static class TabFragment1 extends Fragment {
        public static final String ARG_OBJECT = "object";

        public Context ctx;
        public String ActivityName;
        public String dataBaseName;
        public String rutaParseo;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.layout_fragment_servidor, container, false);
            //rootView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            Button ButtonLedON = (Button)rootView.findViewById(R.id.ButtonTestingLEDon);
            ButtonLedON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=ledON", false, ActivityName);
                }
            });

            Button ButtonLedOFF = (Button)rootView.findViewById(R.id.ButtonTestingLEDoff);
            ButtonLedOFF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=ledOFF", false, ActivityName);
                }
            });

            Button ButtonTvON = (Button)rootView.findViewById(R.id.ButtonTestingTVLG);
            ButtonTvON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=tvON", false, ActivityName);
                }
            });

            Button ButtonAireON = (Button)rootView.findViewById(R.id.ButtonTestingAire);
            ButtonAireON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=aireON", false, ActivityName);
                }
            });

            Button ButtonCanalPlusON = (Button)rootView.findViewById(R.id.ButtonTestingCanalPLUS);
            ButtonCanalPlusON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=canalPlusON", false, ActivityName);
                }
            });

            Button ButtonConfigurarPines = (Button)rootView.findViewById(R.id.ButtonTestingConfigurarPines);
            ButtonConfigurarPines.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=configurarPINES", false, ActivityName);
                }
            });

            Button ButtonApagarRaspi = (Button)rootView.findViewById(R.id.ButtonTestingApagarRaspi);
            ButtonApagarRaspi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=MORIR", false, ActivityName);
                }
            });

            ////////////////////////////////////////////////////////////////////////////////////////////////////////

            ImageView TVvolUP = (ImageView)rootView.findViewById(R.id.imageViewTVvolUP);
            TVvolUP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumeup, 100, 100));
            TVvolUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo + "?act=newReg&cmd=tvLGvolUP", false, ActivityName);
                    new Funciones(ctx, "", false, ActivityName).cambiarColor(rootView, R.id.imageViewTVvolUP);
                }
            });

            ImageView TVvolDown = (ImageView)rootView.findViewById(R.id.imageViewTVvolDOWN);
            TVvolDown.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumedown, 100, 100));
            TVvolDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=tvLGvolDOWN", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewTVvolDOWN);
                }
            });

            ImageView TVchUP= (ImageView)rootView.findViewById(R.id.imageViewTVChUP);
            TVchUP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channelup, 100, 100));
            TVchUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=tvLGchUP", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewTVChUP);
                }
            });

            ImageView TVchDown= (ImageView)rootView.findViewById(R.id.imageViewTVChDown);
            TVchDown.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channeldown, 100, 100));
            TVchDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=tvLGchDOWN", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewTVChDown);
                }
            });

            ImageView TVvolMute= (ImageView)rootView.findViewById(R.id.imageViewTVvolMute);
            TVvolMute.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumemute, 100, 100));
            TVvolMute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=tvLGvolMUTE", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewTVvolMute);
                }
            });

            /////////////////////////////////////////////////////////////////////////////////////////////////////

            ImageView aireTimer= (ImageView)rootView.findViewById(R.id.imageViewAireTimer);
            aireTimer.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.timer, 100, 100));
            aireTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=aireTimer", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewAireTimer);
                }
            });

            ImageView aireCancel= (ImageView)rootView.findViewById(R.id.imageViewAireCancel);
            aireCancel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.cancel, 100, 100));
            aireCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=aireCancel", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewAireCancel);
                }
            });

            ImageView aireTempUP= (ImageView)rootView.findViewById(R.id.imageViewAireTempUP);
            aireTempUP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channelup, 100, 100));
            aireTempUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=aireTempUP", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewAireTempUP);
                }
            });

            ImageView aireTempDOWN= (ImageView)rootView.findViewById(R.id.imageViewAireTempDown);
            aireTempDOWN.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channeldown, 100, 100));
            aireTempDOWN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=aireTempDOWN", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewAireTempDown);
                }
            });

            ImageView airePowerful= (ImageView)rootView.findViewById(R.id.imageViewAirePowerful);
            airePowerful.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.powerful, 100, 100));
            airePowerful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=airePowerful", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewAirePowerful);
                }
            });

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            ImageView CanalPlusUP = (ImageView)rootView.findViewById(R.id.imageViewCanalPlusUP);
            CanalPlusUP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumeup, 100, 100));
            CanalPlusUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo + "?act=newReg&cmd=canalPlusUP", false, ActivityName);
                    new Funciones(ctx, "", false, ActivityName).cambiarColor(rootView, R.id.imageViewCanalPlusUP);
                }
            });

            ImageView CanalPlusDown = (ImageView)rootView.findViewById(R.id.imageViewCanalPlusDOWN);
            CanalPlusDown.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumedown, 100, 100));
            CanalPlusDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=canalPlusDOWN", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewCanalPlusDOWN);
                }
            });

            ImageView CanalPlusChUP= (ImageView)rootView.findViewById(R.id.imageViewCanalPlusChUP);
            CanalPlusChUP.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channelup, 100, 100));
            CanalPlusChUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=canalPlusChUP", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewCanalPlusChUP);
                }
            });

            ImageView CanalPlusChDown= (ImageView)rootView.findViewById(R.id.imageViewCanalPlusChDown);
            CanalPlusChDown.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.channeldown, 100, 100));
            CanalPlusChDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=canalPlusChDOWN", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewCanalPlusChDown);
                }
            });

            ImageView CanalPlusMute= (ImageView)rootView.findViewById(R.id.imageViewCanalPlusMute);
            CanalPlusMute.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.volumemute, 100, 100));
            CanalPlusMute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=canalPlusMUTE", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewCanalPlusMute);
                }
            });

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            final Switch EtherSwitch = (Switch)rootView.findViewById(R.id.switchTestingLedWol);

            ImageView EtherGlobalMarduk = (ImageView)rootView.findViewById(R.id.imageViewEthernetGlobalMarduk);
            EtherGlobalMarduk.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.globalmarduk, 100, 100));
            EtherGlobalMarduk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(EtherSwitch.isChecked()){
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=wolMARDUK", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewEthernetGlobalMarduk);
                    }else{
                        new Funciones(ctx, rutaParseo+"?act=newReg&cmd=shutdownMARDUK", false, ActivityName);
                        new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewEthernetGlobalMarduk);
                    }
                }
            });

            ImageView EtherGlobalParcela = (ImageView)rootView.findViewById(R.id.imageViewEthernetGlobalParcela);
            EtherGlobalParcela.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.globalparcela, 100, 100));
            EtherGlobalParcela.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(EtherSwitch.isChecked()){
                    new Funciones(ctx, rutaParseo+"?act=newReg&cmd=wolPARCELA", false, ActivityName);
                    new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewEthernetGlobalParcela);
                    }else{
                        new Funciones(ctx, rutaParseo+"?act=newReg&cmd=shutdownPARCELA", false, ActivityName);
                        new Funciones(ctx, "",false,ActivityName).cambiarColor(rootView, R.id.imageViewEthernetGlobalParcela);
                    }
                }
            });

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //Cargar las imagenes Efficiently de lso elementos restantes
            ImageView ledPanel = (ImageView)rootView.findViewById(R.id.imageViewTestingLED);
            ledPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.ledpanel, 100, 100));
            ImageView tvPanel = (ImageView)rootView.findViewById(R.id.imageViewTV);
            tvPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tvpanel, 100, 100));
            ImageView airePanel = (ImageView)rootView.findViewById(R.id.imageViewAire);
            airePanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.airepanel, 100, 100));
            ImageView plusPanel = (ImageView)rootView.findViewById(R.id.imageViewCanalPlus);
            plusPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.canalpluspanel, 100, 100));
            ImageView wolPanel = (ImageView)rootView.findViewById(R.id.imageViewEthernet);
            wolPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.wolpanel, 100, 100));
            ImageView pinesPanel = (ImageView)rootView.findViewById(R.id.imageViewPines);
            pinesPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.configurarpines, 100, 100));
            ImageView shutdownPanel = (ImageView)rootView.findViewById(R.id.imageViewMorir);
            shutdownPanel.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.poweroffraspi, 100, 100));




            return rootView;
        }


        public TabFragment1(Context ctxllegado, String dataBaseNamellegado, String ActivityNamellegado, String rutaParseollegado){
            rutaParseo = rutaParseollegado;
            ctx = ctxllegado;
            dataBaseName = dataBaseNamellegado;
            ActivityName = ActivityNamellegado;
        }

        public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        public static int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
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
            rootView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
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
            rootView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
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

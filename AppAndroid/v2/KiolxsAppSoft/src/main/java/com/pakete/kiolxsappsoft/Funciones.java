package com.pakete.kiolxsappsoft;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Funciones
{

    public String carpetaDestinoFichero = "";
    public String dataBaseName = "";
    public Boolean OpmostrarDialogo;
    public String OpzonaActivity;
    public Context ctx;

    public Funciones(Context context,String rutaParseo, boolean mostrarDialogo, String zonaActivity) {
        ctx = context;
        OpmostrarDialogo = mostrarDialogo;
        OpzonaActivity = zonaActivity;
        carpetaDestinoFichero = ctx.getResources().getString(R.string.carpetaDestinoFichero);
        dataBaseName = ctx.getResources().getString(R.string.dataBaseName);

        if(rutaParseo.equals("")){}else{
        new DownloadFileFromURL().execute(rutaParseo);
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... sUrl) {
            int count;
            try {
                URL url = new URL(sUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),8192);
                OutputStream output = new FileOutputStream(carpetaDestinoFichero);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {  }

        @Override
        public void onPostExecute(String file_url) {

            //Borramos BD antigua, parseamos el fichero, y lo cargamos en BD
            leerYparsear(ctx);

            if(OpmostrarDialogo == true){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
            alertDialogBuilder.setTitle("Estado");
            alertDialogBuilder
                    .setMessage("Se ha enviado el comando correctamente!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            }


        }
    }


    public void leerYparsear(Context ctx2) {
		/*http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/*/

        ctx2.deleteDatabase(dataBaseName);

        DBHelper dbHelper = new DBHelper(ctx2, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Insertamos las opciones por defecto
        ContentValues valuesLogEventos = new ContentValues();
        valuesLogEventos.put("evento", "- Base de datos actualizada");
        valuesLogEventos.put("fecha", getDatePhone());
        valuesLogEventos.put("status", "Ok");
        db.insert(DBHelper.TABLALogEventos, null, valuesLogEventos);

        try {
            File fXmlFile = new File(carpetaDestinoFichero);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

    		/*Inicio inserccion de partidos*/
            NodeList nList = doc.getElementsByTagName("Command");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String evento = getTagValue("cmd", eElement);
                    String fecha = getTagValue("fecha", eElement);
                    String status = getTagValue("status", eElement);
                    db.execSQL("INSERT INTO logeventos (evento,fecha,status) VALUES ('"+evento+"','"+fecha+"','"+status+"')");
                }}
			 /*FIN inserccion de partidos*/

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("errorparseo:", e.toString());
        }
        //Cerramos base de datos
        db.close();
        dbHelper.close();
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

    //Funcion que nos genera la hora actual para meterla en la BD
    private String getDatePhone(){
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        return today.format("%k:%M:%S");
    }


}
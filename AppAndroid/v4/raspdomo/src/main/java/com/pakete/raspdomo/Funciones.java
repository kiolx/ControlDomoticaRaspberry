package com.pakete.raspdomo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        //ctx2.deleteDatabase(dataBaseName); //Sustituido por mejra de TRUNCATE

        DBHelper dbHelper = new DBHelper(ctx2, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Mejora. Truncamos la tabla unicamente que queremos
        db.execSQL("delete from "+ DBHelper.TABLALogEventos);
        db.execSQL("delete from sqlite_sequence where name='"+ DBHelper.TABLALogEventos +"'");

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

    //Funcion para mostrar ventana de Información
    public void AcercaDe(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setTitle("Acerca de");
        alertDialogBuilder
                .setMessage("Desarrollador:\nAlejandro Lucas Rodriguez\n\nVersion: 1.6\n\nNombre: Octopus\n\nBuild date: 24/01/2014\n\nEmail: soporte@cavesquimo.es")
                .setCancelable(false)
                .setPositiveButton("Cerrar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void OpcionesBD(View inflatedV, String Opcion, Context ctx, String dataBaseName){

        EditText Server1NameEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer1Name);
        EditText Server2NameEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer2Name);
        EditText Server1IpEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer1Ip);
        EditText Server2IpEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer2Ip);
        EditText Server1PuertoEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer1Puerto);
        EditText Server2PuertoEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer2Puerto);
        EditText Server1ParametrosEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer1Parametros);
        EditText Server2ParametrosEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer2Parametros);
        EditText Server1PassphraseEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer1Passphrase);
        EditText Server2PassphraseEditText   = (EditText)inflatedV.findViewById(R.id.editTextServer2Passphrase);

        String nameServer1 = Server1NameEditText.getText().toString();
        String nameServer2 = Server2NameEditText.getText().toString();
        String ipServer1 = Server1IpEditText.getText().toString();
        String ipServer2 = Server2IpEditText.getText().toString();
        String puertoServer1 = Server1PuertoEditText.getText().toString();
        String puertoServer2 = Server2PuertoEditText.getText().toString();
        String parametroServer1 = Server1ParametrosEditText.getText().toString();
        String parametroServer2 = Server2ParametrosEditText.getText().toString();
        String Server1Passphrase = Server1PassphraseEditText.getText().toString();
        String Server2Passphrase = Server2PassphraseEditText.getText().toString();

        DBHelper dbHelper = new DBHelper(ctx, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(Opcion.equals("guardarOpcionesBD")){
            Cursor cursor = db.rawQuery("SELECT id_opcion FROM opciones ORDER BY id_opcion DESC LIMIT 1", null);

            if (cursor == null) {
                db.execSQL("UPDATE opciones SET nameServer1='"+nameServer1+"' , nameServer2'"+nameServer2+"', ipServer1='"+ipServer1+"' , "
                        + "ipServer2='"+ipServer2+"', puertoServer1='"+puertoServer1+"', puertoServer2='"+puertoServer2+"', parametroServer1='"+parametroServer1+"', "
                        + "parametroServer2='"+parametroServer2+"', Server1Passphrase='"+Server1Passphrase+"', Server2Passphrase='"+Server2Passphrase+"' WHERE id_opcion='1'");
            } else {
                //Insertamos las opciones por defecto
                ContentValues valuesOpciones = new ContentValues();
                valuesOpciones.put("nameServer1", nameServer1);
                valuesOpciones.put("nameServer2", nameServer2);
                valuesOpciones.put("ipServer1", ipServer1);
                valuesOpciones.put("ipServer2", ipServer2);
                valuesOpciones.put("puertoServer1", puertoServer1);
                valuesOpciones.put("puertoServer2", puertoServer2);
                valuesOpciones.put("parametroServer1", parametroServer1);
                valuesOpciones.put("parametroServer2", parametroServer2);
                valuesOpciones.put("Server1Passphrase", Server1Passphrase);
                valuesOpciones.put("Server2Passphrase", Server2Passphrase);
                db.insert(DBHelper.TABLAOpciones, null, valuesOpciones);
            }
        }else if(Opcion.equals("leerOpcionesBD")){
            Cursor fila = db.rawQuery("SELECT * FROM opciones ORDER BY id_opcion DESC LIMIT 1",null);
            //Nos aseguramos de que existe al menos un registro
            if (fila.moveToFirst()) {
                //Recorremos el cursor hasta que no haya m�s registros
                do {
                    // displaying all data in textview
                    Server1NameEditText.setText(fila.getString(1));
                    Server2NameEditText.setText(fila.getString(2));
                    Server1IpEditText.setText(fila.getString(3));
                    Server2IpEditText.setText(fila.getString(4));
                    Server1PuertoEditText.setText(fila.getString(5));
                    Server2PuertoEditText.setText(fila.getString(6));
                    Server1ParametrosEditText.setText(fila.getString(7));
                    Server2ParametrosEditText.setText(fila.getString(8));
                    Server1PassphraseEditText .setText(fila.getString(9));
                    Server2PassphraseEditText .setText(fila.getString(10));
                } while(fila.moveToNext()); }
            fila.close();
        }
        db.close();
        dbHelper.close();
    }

    public void LogBD(View inflatedV, String Opcion, Context ctx, String dataBaseName){

        EditText LogEventosditText   = (EditText)inflatedV.findViewById(R.id.editTextLogEventos);

        DBHelper dbHelper = new DBHelper(ctx, dataBaseName);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        LogEventosditText.setText("");//Limpiamos el texto

        Cursor fila = db.rawQuery("SELECT id_evento,evento,fecha,status FROM logeventos ORDER BY id_evento ASC",null);
        //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                // displaying all data in textview
                LogEventosditText.append(" " + fila.getString(0) + " - " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3) + "\n");
            } while(fila.moveToNext()); }
        fila.close();

        //Close the Database and the Helper
        db.close();
        dbHelper.close();

    }


}
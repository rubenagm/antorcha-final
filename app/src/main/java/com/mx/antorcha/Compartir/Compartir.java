package com.mx.antorcha.Compartir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 */
public class Compartir {

    public Compartir (Context activity) {
        this.activity = activity;
        sendIntent = new Intent(Intent.ACTION_SEND);

        File file = new File (Environment.getExternalStorageDirectory().toString()
                + "/antorcha/compartir.png");

        if (file.exists ()) {
            file.delete ();
        }
    }

    private final String URL = "antorcha.com.mx";
    private Intent sendIntent;
    private Context activity;

    public void agregarUrl () {
        sendIntent.putExtra(Intent.EXTRA_TEXT, URL);
    }

    public void agregarTexto (String texto) {
        sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
    }

    public void agregarView (View view) {
        Bitmap bitmap = viewABitmap(view);
        File fileSend = null;

        try {
            fileSend = new File(Environment.getExternalStorageDirectory().toString()
                    + "/antorcha/compartir.png");
            FileOutputStream out = new FileOutputStream(fileSend);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendIntent.setType(getMimeType(fileSend.getPath()));
        sendIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(fileSend));
    }

    public void agregarImagen (Bitmap bitmap) {

        //se guarda la imagen
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/antorcha");
        myDir.mkdirs();
        String fname = "compartir.png";
        File file = new File (myDir, fname);

        if (file.exists ()) {
            file.delete ();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageUri = Uri.parse(Environment.getExternalStorageDirectory().toString()
                + "/antorcha/compartir.png");

        sendIntent.setType(getMimeType(file.getPath()));
        sendIntent.putExtra("android.intent.extra.STREAM", imageUri);
    }

    public boolean compartir(){
        activity.startActivity(Intent.createChooser(sendIntent, "Selecciona el medio"));

        return true;
    }

    public static Bitmap viewABitmap(View v) {

        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        final Bitmap bm = v.getDrawingCache();
        //v.destroyDrawingCache();
        return bm;
    }

    public String getMimeType(String filePath) {
        String type = null;
        String extension = getFileExtensionFromUrl(filePath);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public String getFileExtensionFromUrl(String url) {
        int dotPos = url.lastIndexOf('.');
        if (0 <= dotPos) {
            return (url.substring(dotPos + 1)).toLowerCase();
        }
        return "";
    }
}

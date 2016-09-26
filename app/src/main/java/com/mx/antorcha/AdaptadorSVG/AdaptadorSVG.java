package com.mx.antorcha.AdaptadorSVG;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.widget.ImageView;

import com.mx.antorcha.LibreriaSVG.SVG;
import com.mx.antorcha.LibreriaSVG.SVGParseException;
import com.mx.antorcha.R;

import java.io.IOException;


/**
 * Created by Ruben on 07/12/2015.
 */
public class AdaptadorSVG {

    static public void mostrarImagen (ImageView imageView, Context context, int imagen) {

        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        try {
            SVG svg = SVG.getFromResource(context, imagen);
            Drawable drawable = new PictureDrawable(svg.renderToPicture());
            imageView.setImageDrawable(drawable);
        } catch(SVGParseException e){

        }
    }
}

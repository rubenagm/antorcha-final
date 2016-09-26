package com.mx.antorcha.Dialogos;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mx.antorcha.Conexion.ConexionValoracion;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import org.w3c.dom.Text;

/**
 *
 */
public class DialogoValoracion extends DialogFragment{

    private double valoracion;
    private Activity activity;
    private int idEspacio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogo_valoracion, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.dialogo_valoracion_rating);
        final Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#E27D2E"), PorterDuff.Mode.SRC_ATOP);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                drawable.setColorFilter(Color.parseColor("#E27D2E"), PorterDuff.Mode.SRC_ATOP);
                valoracion = rating;
            }
        });

        TextView textViewEnviar = (TextView) view.findViewById(R.id.dialogo_valoracion_enviar);
        textViewEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionValoracion conexionValoracion = new ConexionValoracion(activity,
                        idEspacio + "",
                        valoracion + "");
                conexionValoracion.setDialog(getDialog());
                conexionValoracion.enviar();
            }
        });

        return view;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }
}

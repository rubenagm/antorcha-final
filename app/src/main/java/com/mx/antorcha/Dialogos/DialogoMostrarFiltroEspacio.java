package com.mx.antorcha.Dialogos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.FiltroSharedPreferences;

/**
 *
 */
public class DialogoMostrarFiltroEspacio extends DialogFragment {

    private int rango;
    private Context context;
    private FiltroSharedPreferences filtroSharedPreferences;

    public DialogoMostrarFiltroEspacio () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filtro_busqueda_espacio, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        filtroSharedPreferences = new FiltroSharedPreferences(context);
        TextView textViewCancenlar = (TextView) view.findViewById(R.id.dialogo_filtro_espacio_cancelar);
        TextView textViewAceptar = (TextView) view.findViewById(R.id.dialogo_filtro_espacio_aceptar);

        final SeekBar seekBarRango = (SeekBar) view.findViewById(R.id.filtro_busqueda_espacio_seekbar);
        final Switch aSwitch= (Switch) view.findViewById(R.id.filtro_busqueda_actividades_preferencia);

        textViewCancenlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filtroSharedPreferences.setRango(rango);

                if (aSwitch.isChecked()) {
                    filtroSharedPreferences.setActividadesPreferencia(true);
                } else {
                    filtroSharedPreferences.setActividadesPreferencia(false);
                }
                getDialog().dismiss();
            }
        });

        //Se programa la acción del seek bar
        seekBarRango.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rango = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarRango.setProgress(filtroSharedPreferences.getRango());
        aSwitch.setChecked(filtroSharedPreferences.getActividadesPreferencia());

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

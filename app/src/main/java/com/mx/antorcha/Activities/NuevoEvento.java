package com.mx.antorcha.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorSpinner;
import com.mx.antorcha.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


public class NuevoEvento extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextDomicilio;
    public TextView textViewFecha;
    private Spinner spinnerCiudad;
    private EditText editTextTelefono;
    private String datos[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);

        datos = new String[12];

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.nuevo_evento_toolbar);
        setSupportActionBar(toolbar);

        //Se inicializan los controles
        editTextDescripcion = (EditText) findViewById(R.id.nuevo_evento_descripcion);
        editTextNombre = (EditText) findViewById(R.id.nuevo_evento_nombre);
        editTextDomicilio = (EditText) findViewById(R.id.nuevo_evento_domicilio);
        textViewFecha = (TextView) findViewById(R.id.nuevo_evento_fecha);
        editTextTelefono = (EditText) findViewById(R.id.nuevo_evento_telefono);
        spinnerCiudad = (Spinner) findViewById(R.id.nuevo_evento_ciudad);

        ArrayList<String> ciudades = new ArrayList<>();
        ciudades.add("Guadalajara");
        ciudades.add("Tlajomulco");
        ciudades.add("Zapopan");
        ciudades.add("Tonala");

        AdaptadorSpinner adaptadorSpinner = new AdaptadorSpinner(this, ciudades);
        spinnerCiudad.setAdapter(adaptadorSpinner);

        ImageView imageViewRegresar = (ImageView) findViewById(R.id.nuevo_evento_regresar);
        AdaptadorSVG.mostrarImagen(imageViewRegresar, this, R.raw.icono_regresar);

        imageViewRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Para la fecha del evento
        final TextView textViewFecha = (TextView) findViewById(R.id.nuevo_evento_fecha);
        textViewFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevoEvento.this,
                        R.style.datePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int anio,
                                                  int mes, int dia) {

                                String mesAxu = String.valueOf(mes);
                                String diaAux = String.valueOf(dia);

                                if (mesAxu.length() == 1) {
                                    mesAxu = "0" + mesAxu;
                                }

                                if (diaAux.length() == 1) {
                                    diaAux = "0" + diaAux;
                                }

                                textViewFecha.setText(anio + "-"
                                        + (mesAxu) + "-" + diaAux);

                            }
                        }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        TextView textViewSiguiente = (TextView) findViewById(R.id.nuevo_evento_siguiente);
        textViewSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuevoEvento.this, SeleccionaUbicacion.class);

                switch (spinnerCiudad.getSelectedItem().toString()) {
                    case "Guadalajara" : {
                        datos[4] = "1";
                    }
                    case "Tlajomulco" : {
                        datos[4] = "2";
                    }
                    case "Tonala" : {
                        datos[4] = "3";
                    }
                }

                //Se colocan los datos del formulario
                datos[0] = editTextNombre.getText().toString();
                datos[1] = editTextDomicilio.getText().toString();
                datos[5] = editTextDescripcion.getText().toString();
                datos[6] = textViewFecha.getText().toString();
                datos[9] = editTextTelefono.getText().toString();

                intent.putExtra("datos", datos);
                startActivity(intent);
            }
        });
    }
}

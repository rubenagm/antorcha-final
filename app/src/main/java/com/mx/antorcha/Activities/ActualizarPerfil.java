package com.mx.antorcha.Activities;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.MiembroSharedPreferences;

import java.util.Calendar;

public class ActualizarPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final MiembroSharedPreferences miembroSharedPreferences = new MiembroSharedPreferences(this);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.actualizar_perfil_toolbar);
        setSupportActionBar(toolbar);

        //click en regresar
        ImageView imageViewRegresar = (ImageView) findViewById(R.id.actualizar_perfil_atras);
        AdaptadorSVG.mostrarImagen(imageViewRegresar, this, R.raw.icono_regresar);
        imageViewRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Se obtienn todos los objetos
        final EditText editTextNombre = (EditText) findViewById(R.id.actualizar_perfil_nombre);
        final EditText editTextDescripcion = (EditText) findViewById(R.id.actualizar_perfil_descripcion);
        final EditText editTextIntereses = (EditText) findViewById(R.id.actualizar_perfil_intereses);
        final TextView textViewFechaNacimiento = (TextView) findViewById(R.id.actualizar_perfil_fecha_nacimiento);

        //El click en la fecha de nacimiento
        textViewFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActualizarPerfil.this,
                        R.style.datePickerDialogStyle,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int anio,
                                                  int mes, int dia) {
                                textViewFechaNacimiento.setText(anio + "-"
                                        + (mes + 1) + "-" + dia);

                            }
                        }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        //Se cargan los datos para mostrarlos en los campos
        editTextNombre.setText(miembroSharedPreferences.getNombre());
        textViewFechaNacimiento.setText(miembroSharedPreferences.getFechaNacimiento());

        if (miembroSharedPreferences.getDescripcion() != "" && miembroSharedPreferences.getDescripcion() != "null") {
            editTextDescripcion.setText(miembroSharedPreferences.getDescripcion());
        }

        if (miembroSharedPreferences.getIntereses() != "" && miembroSharedPreferences.getDescripcion() != "null") {
            editTextIntereses.setText(miembroSharedPreferences.getIntereses());
        }

        //El botón para guardar el perfil
        ImageView imageViewGuardarPerfil =
                (ImageView) findViewById(R.id.actualizar_perfil_boton_guardar);

        //Se carga la imagen de guardar perfil
        AdaptadorSVG.mostrarImagen(imageViewGuardarPerfil, this, R.raw.boton_guardar_meta); // Temporal

        //El click para guardar los cambios
        imageViewGuardarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Condicion para ver si hay errores
                if (true) {
                    Toast.makeText(ActualizarPerfil.this,
                            "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
                    miembroSharedPreferences.setNombre(editTextNombre.getText().toString());
                    miembroSharedPreferences.setFechaNacimiento(
                            textViewFechaNacimiento.getText().toString());
                    miembroSharedPreferences.setDescripcion(
                            editTextDescripcion.getText().toString());
                    miembroSharedPreferences.setIntereses(editTextIntereses.getText().toString());
                    miembroSharedPreferences.setActualizar(1);
                    finish();
                } else {
                    Toast.makeText(ActualizarPerfil.this,
                            "Debes llenar todos los campos correctamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

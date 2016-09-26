package com.mx.antorcha.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Conexion.ConexionRegistro;
import com.mx.antorcha.R;

public class Registro extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.registro_toolbar);
        setSupportActionBar(toolbar);

        //se cargan las imagenes de la barra
        ImageView imageViewAtras = (ImageView) findViewById(R.id.registro_barra_regresar);
        ImageView imageViewRegistrarse = (ImageView) findViewById(R.id.registro_boton_regitrarse);

        //se cargan las imagenes de las imagenes en svg
        AdaptadorSVG.mostrarImagen(imageViewAtras, this, R.raw.icono_regresar);
        AdaptadorSVG.mostrarImagen(imageViewRegistrarse, this, R.raw.boton_registrate);

        //Se inicializan todos los elementos
        final EditText editTextNombre = (EditText) findViewById(R.id.registro_nombre);
        final EditText editTextApellido = (EditText) findViewById(R.id.registro_apellidos);
        final EditText editTextCorreo = (EditText) findViewById(R.id.registro_correo);
        final EditText editTextPassword = (EditText) findViewById(R.id.registro_password);

        imageViewAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Se agregan los datos a los spinner de fecha de nacimiento
        ArrayAdapter adapterDias = ArrayAdapter.createFromResource(this, R.array.dias, android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapterDias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        final Spinner spinnerDias = (Spinner) findViewById(R.id.login_fecha_dia);
        spinnerDias.setAdapter(adapterDias);

        //Se agregan los datos a los spinner de fecha de nacimiento
        ArrayAdapter adapterMeses = ArrayAdapter.createFromResource(this, R.array.meses, android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapterMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        final Spinner spinnerMeses = (Spinner) findViewById(R.id.login_fecha_mes);
        spinnerMeses.setAdapter(adapterMeses);

        //Se agregan los datos a los spinner de fecha de nacimiento
        ArrayAdapter adapterAnios = ArrayAdapter.createFromResource(this, R.array.anios, android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapterAnios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        final Spinner spinnerAnios = (Spinner) findViewById(R.id.login_fecha_anio);
        spinnerAnios.setAdapter(adapterAnios);

        //Se agregan los datos a los spinner de fecha de nacimiento
        ArrayAdapter adapterSexo = ArrayAdapter.createFromResource(this, R.array.sexo, android.R.layout.simple_spinner_item);
        //Añadimos el layout para el menú
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Le indicamos al spinner el adaptador a usar
        final Spinner spinnerSexo = (Spinner) findViewById(R.id.login_sexo);
        spinnerSexo.setAdapter(adapterSexo);

        //Se coloca el click
        imageViewRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mensaje = "";
                boolean bandera = true;

                if (editTextNombre.getText().toString().length() < 3) {
                    mensaje += "Te hace falta llenar el campo de nombre, ";
                    bandera = false;
                }

                if (editTextApellido.getText().toString().length() < 3) {
                    mensaje += "Te hace falta llenar el campo de apellidos, ";
                    bandera = false;
                }

                if (editTextPassword.getText().toString().length() < 4) {
                    mensaje += "La contraseña debe ser de al menos 4 caracteres, ";
                    bandera = false;
                }

                if (!editTextCorreo.getText().toString()
                        .matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$")) {
                    mensaje += "El formato del correo es incorrecto (con este correo te vamos a identificar)";
                    bandera = false;
                }

                if (bandera) {
                    //Correcto
                    ConexionRegistro conexionRegistro = new ConexionRegistro(
                            editTextNombre.getText().toString() + " " + editTextApellido.getText().toString(),
                            spinnerSexo.getSelectedItem().toString().substring(0,1).toUpperCase(),
                            editTextCorreo.getText().toString(),
                            editTextPassword.getText().toString(),
                            spinnerAnios.getSelectedItem().toString() + "-"
                                    + spinnerMeses.getSelectedItem().toString() + "-"
                                    + spinnerDias.getSelectedItem().toString(),
                            Registro.this
                    );

                    conexionRegistro.execute();
                } else {
                    Toast.makeText(Registro.this, mensaje, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}

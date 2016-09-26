package com.mx.antorcha.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaNuevosDeportes;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionNuevoEvento;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;

import java.util.ArrayList;

public class SeleccionaDeportesEvento extends AppCompatActivity {

    private ArrayList<String> stringDisciplinas;
    private ArrayList<String> stringDeportes;
    private String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_deportes_evento);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.selecciona_deportes_evento_toolbar);
        setSupportActionBar(toolbar);

        datos = getIntent().getStringArrayExtra("datos");

        //click en regresar
        ImageView imageViewRegresar = (ImageView) findViewById(R.id.selecciona_deportes_evento_regresar);
        AdaptadorSVG.mostrarImagen(imageViewRegresar, this, R.raw.icono_regresar);

        imageViewRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ConexionBaseDatosObtener conexionBaseDatosObtener = new ConexionBaseDatosObtener(this);
        //Se cargan las preferencias del arrayList
        final DisciplinasDeportesSharedPreferences disciplinasDeportesSharedPreferences
                = new DisciplinasDeportesSharedPreferences(this);

        //Se crean los dos arrays que guardarán los deportes favoritos seleccionados y las disciplinas
        stringDeportes = disciplinasDeportesSharedPreferences.getDeportes();
        stringDisciplinas = disciplinasDeportesSharedPreferences.getDisciplinas();

        ImageView imageViewVerificar = (ImageView) findViewById(R.id.selecciona_deportes_evento_verificar);

        imageViewVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SeleccionaDeportesEvento.this).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Se cargan los datos del formulario

                        //Cuando se responde sí se agrega al calendario
                        Intent intent = new Intent(SeleccionaDeportesEvento.this, BuscarActividad.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("idEvento", "0");
                        ConexionNuevoEvento conexionNuevoEvento = new ConexionNuevoEvento(SeleccionaDeportesEvento.this, datos);
                        conexionNuevoEvento.enviar();
                        startActivity(intent);
                    }
                }).setNegativeButton("No", null)
                        .setIcon(R.drawable.logo_antorcha)
                        .setMessage("¿Seguro que toda la información es correcta?")
                        .setTitle("Guardar evento")
                        .show();
            }
        });

        //Se obitnene los datos guardados
        ArrayList<Disciplina> disciplinas = conexionBaseDatosObtener.obtenerDisciplinas();

        ListView listViewDisciplinas = (ListView) findViewById(R.id.selecciona_deportes_evento_lista);
        AdaptadorListaNuevosDeportes adaptadorListaNuevosDeportes = new AdaptadorListaNuevosDeportes(this, disciplinas);
        adaptadorListaNuevosDeportes.setStringDeportes(stringDeportes);
        adaptadorListaNuevosDeportes.setStringDisciplinas(stringDisciplinas);
        listViewDisciplinas.setAdapter(adaptadorListaNuevosDeportes);
    }
}

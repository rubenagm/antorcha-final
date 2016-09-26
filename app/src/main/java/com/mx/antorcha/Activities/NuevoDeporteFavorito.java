package com.mx.antorcha.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorListaNuevosDeportes;
import com.mx.antorcha.BaseDatos.ConexionBaseDatosObtener;
import com.mx.antorcha.Conexion.ConexionInsertarDeporte;
import com.mx.antorcha.Conexion.ConexionInsertarDisciplina;
import com.mx.antorcha.Modelos.Disciplina;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;

import java.util.ArrayList;

public class NuevoDeporteFavorito extends AppCompatActivity {
    private ArrayList<String> stringDisciplinas;
    private ArrayList<String> stringDeportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_deporte_favorito);

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.nuevo_deporte_toolbar);
        setSupportActionBar(toolbar);

        //click en regresar
        ImageView imageViewRegresar = (ImageView) findViewById(R.id.nuevo_deporte_barra_regresar);
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

        //Se obitnene los datos guardados
        ArrayList<Disciplina> disciplinas = conexionBaseDatosObtener.obtenerDisciplinas();

        //Se Carga el listView
        ListView listViewDisciplinas = (ListView) findViewById(R.id.nuevo_deporte_lista_disciplina);
        AdaptadorListaNuevosDeportes adaptadorListaNuevosDeportes = new AdaptadorListaNuevosDeportes(this, disciplinas);
        adaptadorListaNuevosDeportes.setStringDeportes(stringDeportes);
        adaptadorListaNuevosDeportes.setStringDisciplinas(stringDisciplinas);
        listViewDisciplinas.setAdapter(adaptadorListaNuevosDeportes);

        //el click en guardar
        ImageView imageViewGuardar = (ImageView) findViewById(R.id.nuevo_deporte_guardar);
        AdaptadorSVG.mostrarImagen(imageViewGuardar, this, R.raw.icono_guardar_palomita);

        //En el click se guarda el arreglo
        imageViewGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disciplinasDeportesSharedPreferences.setDeportes(stringDeportes);
                disciplinasDeportesSharedPreferences.setDisciplinas(stringDisciplinas);
                Toast.makeText(NuevoDeporteFavorito.this, "Se han guardado los cambios", Toast.LENGTH_SHORT).show();

                //Se guardan los deportes y las disciplinas
                for (String deporte : stringDeportes) {
                    ConexionInsertarDeporte conexionInsertarDeporte = new ConexionInsertarDeporte(NuevoDeporteFavorito.this, Integer.parseInt(deporte), 0);
                    conexionInsertarDeporte.insertarDeporte();
                }

                for (String disciplina : stringDisciplinas) {
                    ConexionInsertarDisciplina conexionInsertarDisciplina = new ConexionInsertarDisciplina(NuevoDeporteFavorito.this, Integer.parseInt(disciplina), 0);
                    conexionInsertarDisciplina.insertarDisciplina();
                }

                finish();
            }
        });

    }
}

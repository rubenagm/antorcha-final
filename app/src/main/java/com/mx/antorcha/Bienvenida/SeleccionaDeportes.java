package com.mx.antorcha.Bienvenida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.mx.antorcha.Conexion.ConexionInsertarDeporte;
import com.mx.antorcha.Conexion.ConexionInsertarDisciplina;
import com.mx.antorcha.R;
import com.mx.antorcha.SharedPreferences.DisciplinasDeportesSharedPreferences;

import java.util.ArrayList;

public class SeleccionaDeportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_deportes);

        findViewById(R.id.selecciona_deportes_siguiente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se insertan los deportes
                ArrayList<String> deportes = new ArrayList<String>();
                ArrayList<String> disciplinas = new ArrayList<String>();

                //Futbol
                if (((CheckBox) findViewById(R.id.selecciona_deportes_1)).isChecked()) {
                    deportes.add("173");
                    disciplinas.add("12");
                }

                //Basketball
                if (((CheckBox) findViewById(R.id.selecciona_deportes_2)).isChecked()) {
                    deportes.add("110");
                    disciplinas.add("19");
                }

                //Ciclismo
                if (((CheckBox) findViewById(R.id.selecciona_deportes_3)).isChecked()) {
                    deportes.add("83");
                    deportes.add("84");
                    deportes.add("85");
                    disciplinas.add("9");
                }

                //Deportes de combate
                if (((CheckBox) findViewById(R.id.selecciona_deportes_4)).isChecked()) {
                    deportes.add("86");
                    deportes.add("87");
                    deportes.add("91");
                    deportes.add("93");
                    deportes.add("92");
                    deportes.add("94");
                    deportes.add("90");
                    disciplinas.add("10");
                }

                //Danza
                if (((CheckBox) findViewById(R.id.selecciona_deportes_5)).isChecked()) {
                    deportes.add("156");
                    deportes.add("157");
                    deportes.add("158");
                    deportes.add("159");
                    deportes.add("160");
                    deportes.add("161");
                    deportes.add("162");
                    deportes.add("163");
                    disciplinas.add("28");
                }

                //Voleibol
                if (((CheckBox) findViewById(R.id.selecciona_deportes_6)).isChecked()) {
                    deportes.add("111");
                    disciplinas.add("19");
                }

                //Futbol
                if (((CheckBox) findViewById(R.id.selecciona_deportes_7)).isChecked()) {
                    disciplinas.add("1");
                    disciplinas.add("2");
                    disciplinas.add("9");
                }

                new DisciplinasDeportesSharedPreferences(SeleccionaDeportes.this).setDeportes(deportes);
                new DisciplinasDeportesSharedPreferences(SeleccionaDeportes.this).setDisciplinas(disciplinas);

                //Se guardan los deportes y las disciplinas
                for (String deporte : deportes) {
                    ConexionInsertarDeporte conexionInsertarDeporte = new ConexionInsertarDeporte(SeleccionaDeportes.this, Integer.parseInt(deporte), 0);
                    conexionInsertarDeporte.insertarDeporte();
                }

                for (String disciplina : disciplinas) {
                    ConexionInsertarDisciplina conexionInsertarDisciplina = new ConexionInsertarDisciplina(SeleccionaDeportes.this, Integer.parseInt(disciplina), 0);
                    conexionInsertarDisciplina.insertarDisciplina();
                }

                Intent intent = new Intent(SeleccionaDeportes.this, BienvenidaNotificaciones.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

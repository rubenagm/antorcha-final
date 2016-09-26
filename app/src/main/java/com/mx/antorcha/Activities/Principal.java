package com.mx.antorcha.Activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mx.antorcha.MenuDrawer.AdapterDrawer;
import com.mx.antorcha.R;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Se agrega el drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.lista_drawer);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        listView.setAdapter(new AdapterDrawer(this, R.layout.drawer, arrayList, ""));

        //se carga la barra de android por el xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.principal_toolbar);
        setSupportActionBar(toolbar);
    }
}

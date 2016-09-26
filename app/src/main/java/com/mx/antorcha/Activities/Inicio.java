package com.mx.antorcha.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.mx.antorcha.AdaptadorSVG.AdaptadorSVG;
import com.mx.antorcha.Adaptadores.AdaptadorInicioTabs;
import com.mx.antorcha.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Inicio extends AppCompatActivity {
    private ImageView imageViewRegistrarse;
    private ImageView imageViewIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FacebookSdk.sdkInitialize(getApplicationContext());
        //Se inicializan los objetos del layout
        imageViewRegistrarse = (ImageView) findViewById(R.id.inicio_image_registrarse);
        imageViewIniciarSesion = (ImageView) findViewById(R.id.inicio_image_iniciar_sesion);

        //Cuando se presiona iniciar sesion
        imageViewIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Login.class);
                startActivity(intent);
            }
        });

        //Cuando se presiona registrar
        imageViewRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Registro.class);
                startActivity(intent);
            }
        });

        AdaptadorSVG.mostrarImagen(imageViewIniciarSesion, this, R.raw.iniciar_sesion);
        AdaptadorSVG.mostrarImagen(imageViewRegistrarse, this, R.raw.registrarse);

        final ImageView imageViewIconoCirculo1 = (ImageView) findViewById(R.id.inicio_icono_circulo_1);
        final ImageView imageViewIconoCirculo2 = (ImageView) findViewById(R.id.inicio_icono_circulo_2);
        final ImageView imageViewIconoCirculo3 = (ImageView) findViewById(R.id.inicio_icono_circulo_3);
        final ImageView imageViewIconoCirculo4 = (ImageView) findViewById(R.id.inicio_icono_circulo_4);

        AdaptadorInicioTabs adaptadorInicioTabs = new AdaptadorInicioTabs(getSupportFragmentManager(), imageViewIconoCirculo1, imageViewIconoCirculo2, imageViewIconoCirculo3, imageViewIconoCirculo4);



        ViewPager viewPager = (ViewPager) findViewById(R.id.inicio_pager);
        viewPager.setAdapter(adaptadorInicioTabs);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                imageViewIconoCirculo1.setAlpha(.4f);
                imageViewIconoCirculo2.setAlpha(.4f);
                imageViewIconoCirculo3.setAlpha(.4f);
                imageViewIconoCirculo4.setAlpha(.4f);

                switch (position) {
                    case 0 : {
                        imageViewIconoCirculo1.setAlpha(1f);
                        break;
                    }
                    case 1 : {
                        imageViewIconoCirculo2.setAlpha(1f);
                        break;
                    }
                    case 2 : {
                        imageViewIconoCirculo3.setAlpha(1f);
                        break;
                    }
                    case 3 : {
                        imageViewIconoCirculo4.setAlpha(1f);
                        break;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getUserInfo();
    }

    public void getUserInfo()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}

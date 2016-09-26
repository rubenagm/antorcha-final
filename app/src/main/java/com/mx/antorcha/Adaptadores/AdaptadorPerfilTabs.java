package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.mx.antorcha.Activities.Perfil;
import com.mx.antorcha.Fragment.FragmentPerfilDeportes;
import com.mx.antorcha.Fragment.FragmentPerfilPerfil;

/**
 * Created by Ruben on 09/12/2015.
 */
public class AdaptadorPerfilTabs extends FragmentStatePagerAdapter {

    private Activity activity;
    private ImageView imageViewAgregar;
    private FragmentManager fragmentManager;
    private Perfil perfil;

    public AdaptadorPerfilTabs(FragmentManager fm, Activity activity, ImageView imageViewAgregar) {
        super(fm);
        this.activity = activity;
        this.imageViewAgregar = imageViewAgregar;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :
                imageViewAgregar.setVisibility(View.GONE);
                FragmentPerfilPerfil fragmentPerfilPerfil = new FragmentPerfilPerfil();
                fragmentPerfilPerfil.setActivity(activity);
                fragmentPerfilPerfil.setPerfil(perfil);
                fragmentPerfilPerfil.setFragmentManager(fragmentManager);
                return fragmentPerfilPerfil;
            case 1 :
                imageViewAgregar.setVisibility(View.VISIBLE);
                FragmentPerfilDeportes fragmentPerfilDeportes = new FragmentPerfilDeportes();
                fragmentPerfilDeportes.setActivity(activity);
                return fragmentPerfilDeportes;
            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Personal";
            case 1:
                return "Deportes";
            default:
                return "Tab";
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}

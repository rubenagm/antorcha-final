package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mx.antorcha.Fragment.FragmentBuscarEspacio;
import com.mx.antorcha.Fragment.FragmentBuscarEventos;

/**
 *
 */
public class AdaptadorBuscarActividadTabs extends FragmentStatePagerAdapter {

    private Activity activity;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    public AdaptadorBuscarActividadTabs(FragmentManager fm, Activity activity, Bundle bundle) {
        super(fm);
        this.activity = activity;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :
                FragmentBuscarEspacio fragmentBuscarEspacio = new FragmentBuscarEspacio();
                fragmentBuscarEspacio.setActivity(activity);
                fragmentBuscarEspacio.setBundle(bundle);
                fragmentBuscarEspacio.setFragmentManager(fragmentManager);
                return fragmentBuscarEspacio;
            case 1 :
                FragmentBuscarEventos fragmentBuscarEventos = new FragmentBuscarEventos();
                fragmentBuscarEventos.setActivity(activity);
                fragmentBuscarEventos.setBundle(bundle);
                //fragmentBuscarEventos.setFragmentManager(fragmentManager);
                return fragmentBuscarEventos;
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
                return "Espacios deportivos";
            case 1:
                return "Eventos";
            default:
                return "Tab";
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}

package com.mx.antorcha.Adaptadores;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mx.antorcha.Fragment.FragmentActividadesEspacio;
import com.mx.antorcha.Fragment.FragmentActividadesEvento;

public class AdaptadorActividadesTabs extends FragmentStatePagerAdapter{

    private Activity activity;
    private FragmentManager fragmentManager;

    public AdaptadorActividadesTabs(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :
                FragmentActividadesEspacio fragmentActividadesEspacio = new FragmentActividadesEspacio();
                fragmentActividadesEspacio.setActivity(activity);
                fragmentActividadesEspacio.setFragmentManager(fragmentManager);
                return fragmentActividadesEspacio;
            case 1 :
                FragmentActividadesEvento fragmentActividadesEvento = new FragmentActividadesEvento();
                fragmentActividadesEvento.setActivity(activity);
                return fragmentActividadesEvento;
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

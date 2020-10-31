package com.raysk.linearprogramming.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.raysk.linearprogramming.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;



    private int paginas;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int paginas) {
        super(fm);
        mContext = context;
        this.paginas = paginas;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //paginas = position + 1;
        return PlaceholderFragment.newInstance(position+1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Tabla inicial";
        }else {
            return "tabla" + (position);
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return paginas;
    }


}
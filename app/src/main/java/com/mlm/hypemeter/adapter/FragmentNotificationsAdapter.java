package com.mlm.hypemeter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.mlm.hypemeter.NearbyFragment;
import com.mlm.hypemeter.NotificationsFragment;

/**
 * Created by Martin on 3/15/2016.
 */

public class FragmentNotificationsAdapter extends FragmentPagerAdapter {
    public FragmentNotificationsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
    /*final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "You", "Nearby" };
    private Context context;

    public FragmentNotificationsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new NotificationsFragment();
            case 1:
                return new NearbyFragment();
        }
        return NotificationsFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }*/
}

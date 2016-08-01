package com.mlm.hypemeter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.mlm.hypemeter.adapter.FragmentNotificationsAdapter;

/**
 * Created by Martin on 7/13/2016.
 */
public class NotificationsNavFragment extends Fragment {

    private View NotificationsView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NotificationsView = inflater.inflate(R.layout.notifications_activity, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notifications");

        ViewPager viewPager = (ViewPager) NotificationsView.findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) NotificationsView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("You");
        tabLayout.getTabAt(1).setText("Nearby");

        viewPager.setAdapter(new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount()));

        return NotificationsView;
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new NotificationsFragment();
                case 1:
                    return new NearbyFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

        /*// Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) NotificationsView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentNotificationsAdapter(getFragmentManager(),
                NotificationsNavFragment.this.getActivity()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) NotificationsView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/

        //return NotificationsView;
    //}
}

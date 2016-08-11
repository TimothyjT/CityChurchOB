package com.tubbs.citychurchob.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tubbs.citychurchob.fragments.EventsFragment;
import com.tubbs.citychurchob.fragments.MoreFragment;
import com.tubbs.citychurchob.fragments.MusicFragment;
import com.tubbs.citychurchob.fragments.SeriesFragment;
import com.tubbs.citychurchob.models.SeriesObjectModel;

import java.util.ArrayList;

/**
 * Created by Tubbster on 5/25/15.
 */
public class TabsFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<SeriesObjectModel> models;
    public TabsFragmentPagerAdapter(FragmentManager fm,ArrayList<SeriesObjectModel> models) {
        super(fm);
        this.models = models;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      switch (position){
          case 0:
              return "Podcast";
          case 1:
              return "Music";
          case 2:
              return "Events";
          case 3:
              return "More";
          default:
              return null;
      }
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return SeriesFragment.newInstance(models);

            case 1:
                return new MusicFragment();

            case 2:
                return new EventsFragment();

            case 3:
                return new MoreFragment();

        }

        return null;

    }

    @Override
    public int getCount() {
        return 4;
    }
}

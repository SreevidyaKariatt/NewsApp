package robosoft.com.jsonparser.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import robosoft.com.jsonparser.common.constants.AppConstants;

/**
 * Created by sree on 23/1/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mTabTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return AppConstants.integerConstants.NO_OF_TABS;
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mTabTitleList.add(title);
    }

    @Override
    public int getItemPosition(Object object) {
       // return super.getItemPosition(object);
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTabTitleList.get(position);
    }
}




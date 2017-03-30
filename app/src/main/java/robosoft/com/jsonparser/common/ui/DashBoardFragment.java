package robosoft.com.jsonparser.common.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import robosoft.com.jsonparser.common.adapter.ViewPagerAdapter;
import robosoft.com.jsonparser.favorite.FavoriteFragment;
import robosoft.com.jsonparser.images.ui.ImageFragment;
import robosoft.com.jsonparser.news.ui.NewsFragment;
import robosoft.com.jsonparser.videos.ui.VideoFragment;
import robosoft.com.newsapp.R;


public class DashBoardFragment extends BaseFragment {

    private ViewPager mDetailsViewPager;
    private TabLayout mHeaderTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dash_board_layout, container, false);
        initViews(view);
        setContent(view);
        return parent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideProgress();
        setUpViewPager();
        setUpTabLayout();

    }

    private void initViews(View view) {
        mDetailsViewPager = (ViewPager) view.findViewById(R.id.details_viewPager);
        mHeaderTabLayout = (TabLayout) view.findViewById(R.id.header_tabLayout);

    }

    private void setUpViewPager() {
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new NewsFragment(), getString(R.string.news_string));
        viewPagerAdapter.addFragment(new ImageFragment(), getString(R.string.image_string));
        viewPagerAdapter.addFragment(new VideoFragment(), getString(R.string.video_string));
        viewPagerAdapter.addFragment(new FavoriteFragment(), getString(R.string.favorite_string));
        mDetailsViewPager.setAdapter(viewPagerAdapter);
        mDetailsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //refresh the pager each time it is accessed
            @Override
            public void onPageSelected(int position) {
                // ...anything you may need to do to handle pager state...
                viewPagerAdapter.notifyDataSetChanged(); //this line will force all pages to be loaded fresh when changing between fragments
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setUpTabLayout() {

        mHeaderTabLayout.setupWithViewPager(mDetailsViewPager);
        mHeaderTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mDetailsViewPager.setCurrentItem(tab.getPosition());
                setTitle(tab.getText().toString());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}

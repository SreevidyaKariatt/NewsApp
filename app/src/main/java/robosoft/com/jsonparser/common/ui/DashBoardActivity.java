package robosoft.com.jsonparser.common.ui;


import android.content.Intent;
import android.os.Bundle;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.helper.FragmentOperations;
import robosoft.com.jsonparser.images.ui.ImageDescriptionActivity;
import robosoft.com.jsonparser.images.ui.ImageFragment.onImageItemClick;
import robosoft.com.jsonparser.news.ui.NewsDescriptionActivity;
import robosoft.com.jsonparser.news.ui.NewsFragment.onNewsItemClick;
import robosoft.com.jsonparser.videos.ui.VideoFragment.onVideoItemClick;
import robosoft.com.jsonparser.videos.ui.VideoPlayActivity;
import robosoft.com.newsapp.R;

//TODO create a base activity and fragment,fragment inside activity
public class DashBoardActivity extends BaseActivity implements onNewsItemClick,onImageItemClick,onVideoItemClick {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentOperations.addFragment(this, new DashBoardFragment(), R.id.content_activity, AppConstants.stringConstants.STR_DASHBOARD_FRAGMENT);

    }


    @Override
    public void displayNewsInDetail(int position, String webUrl, String imageUrl, String headLine) {
        Intent intent = new Intent(this, NewsDescriptionActivity.class);
        intent.putExtra(AppConstants.stringConstants.STR_URL, webUrl);
        intent.putExtra(AppConstants.stringConstants.STR_POSITION, position);
        intent.putExtra(AppConstants.stringConstants.STR_IMAGE_URL,imageUrl);
        intent.putExtra(AppConstants.stringConstants.STR_HEADLINE, headLine);
        startActivity(intent);

    }


    @Override
    public void displayImageInDetail(String imageDescription, String imageUrl) {
        Intent intent = new Intent(this, ImageDescriptionActivity.class);
        intent.putExtra(AppConstants.stringConstants.STR_IMAGE_DESCRIPTION, imageDescription);
        intent.putExtra(AppConstants.stringConstants.STR_IMAGE_URL, imageUrl);
        startActivity(intent);

    }

    @Override
    public void displayVideoInDetail(String videoUrl) {
        Intent intent = new Intent(this, VideoPlayActivity.class);
        intent.putExtra(AppConstants.stringConstants.STR_VIDEO_URL, videoUrl);
        startActivity(intent);
    }
}
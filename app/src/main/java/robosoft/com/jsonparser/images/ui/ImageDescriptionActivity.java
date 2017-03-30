package robosoft.com.jsonparser.images.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.net.MySingleton;
import robosoft.com.jsonparser.common.ui.BaseActivity;
import robosoft.com.newsapp.R;

//Activity for showing image description
public class ImageDescriptionActivity extends BaseActivity {

    private Toolbar mToolBar;
    ImageLoader mImageLoader;
    NetworkImageView mImageView;
    TextView mDescriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_description_activity_layout);
        initViews();
        setUpToolbar();
        setContents();
    }

    private void setContents() {

        String imageDescription = getIntent().getStringExtra(AppConstants.stringConstants.STR_IMAGE_DESCRIPTION);
        mDescriptionText.setText(imageDescription);
        mImageView.setImageUrl(getIntent().getStringExtra(AppConstants.stringConstants.STR_IMAGE_URL), mImageLoader);

    }

    private void setUpToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(AppConstants.stringConstants.STR_INFO);

        mToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        mImageView = (NetworkImageView)findViewById(R.id.image_description_networkimageview);
        mDescriptionText = (TextView)findViewById(R.id.image_text);
    }

}

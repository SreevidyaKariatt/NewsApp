package robosoft.com.jsonparser.news.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.newsapp.R;
import robosoft.com.jsonparser.common.helper.DatabaseHelper;

//Class for displaying the news in detail using web view
public class NewsDescriptionActivity extends AppCompatActivity {

    private DatabaseHelper mAdapter;
    private final String TAG = "News";
    private WebView newsDetailsWebView;
    private FloatingActionButton fab;
    private String mUrl;
    private String mHeadLine;
    private int mPosition;
    private byte[] mNewsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_description_activity_layout);
        setTitle(AppConstants.stringConstants.STR_NEWS_DETAIL);
        mAdapter = new DatabaseHelper(this);
        initViews();
        getDataFromIntent();
        setupWebView();
    }

    private void setupWebView() {
        newsDetailsWebView.setWebViewClient(new Callback());
        newsDetailsWebView.loadUrl(mUrl);
    }

    private void getDataFromIntent() {
        mUrl = getIntent().getStringExtra(AppConstants.stringConstants.STR_URL);
        mHeadLine = getIntent().getStringExtra(AppConstants.stringConstants.STR_HEADLINE);
        fab.setVisibility(View.GONE);
        if (mHeadLine != null) {
            getDataFromNewsIntent();
        }

    }

    private void getDataFromNewsIntent() {

        String imageUrl = getIntent().getStringExtra(AppConstants.stringConstants.STR_IMAGE_URL);
        mPosition = getIntent().getIntExtra(AppConstants.stringConstants.STR_POSITION, 0);
        new DownloadImage().execute(imageUrl);
        displayNewsInDetail();

    }

    private void displayNewsInDetail() {
        fab.setVisibility(View.VISIBLE);
        boolean find = mAdapter.checkForPositionInDb(mHeadLine);
        if (find == true) {
            fab.setImageResource(R.drawable.favoritestar);
        } else
            fab.setImageResource(R.drawable.favunfilled);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabButtonClick(view);
            }
        });
    }

    private void onFabButtonClick(View view) {
        boolean find = mAdapter.checkForPositionInDb( mHeadLine);
        if (find == false) {
            long value = mAdapter.insertData(mPosition, mNewsImage, AppConstants.stringConstants.STR_NEWS, mHeadLine, null,null, mUrl);
            if (value > 0) {
                fab.setImageResource(R.drawable.favoritestar);
                Snackbar.make(view, getString(R.string.msg_news_addedAsFavorite), Snackbar.LENGTH_LONG).show();

            }
        } else {
            fab.setImageResource(R.drawable.favunfilled);
            int count = mAdapter.deleteRow(mHeadLine);
            if (count > 0)
                Snackbar.make(view, getString(R.string.msg_news_deletedAsFavorite), Snackbar.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        newsDetailsWebView = (WebView) findViewById(R.id.news_webview);
        newsDetailsWebView.setVisibility(View.VISIBLE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        WebSettings webSettings = newsDetailsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
    }

    private class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

    }

    class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            Bitmap newsImageBitmap = null;
            try {
                url = new URL(params[0]);
                newsImageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }

            catch (MalformedURLException e) {
                Log.i(TAG,e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, e.getMessage());
            }
            return newsImageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmapImage) {
            super.onPostExecute(bitmapImage);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
            mNewsImage = bos.toByteArray();

        }
    }



}

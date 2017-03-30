package robosoft.com.jsonparser.videos.ui;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.newsapp.R;

//Activity for playing video
public class VideoPlayActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_activity_layout);
        initViews();
    }

    private void initViews() {
        final String url = getIntent().getStringExtra(AppConstants.stringConstants.STR_VIDEO_URL);
        mView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        initYouTubePlayer(url);

    }

    private void initYouTubePlayer(final String url) {
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(url);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        mView.initialize(AppConstants.stringConstants.STR_YOUTUBE_API_KEY, onInitializedListener);
    }

}

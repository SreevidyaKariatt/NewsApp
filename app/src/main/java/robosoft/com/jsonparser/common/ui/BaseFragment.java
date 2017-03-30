package robosoft.com.jsonparser.common.ui;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import robosoft.com.newsapp.R;


public class BaseFragment extends Fragment implements View.OnClickListener {

    private FrameLayout mContainerFrame;
    private TextView mTitleText;
    private ProgressBar mDownloadProgressBar;
    private LinearLayout mHeaderLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ImageButton backButton = (ImageButton) view.findViewById(R.id.back_imageButton);
        mContainerFrame = (FrameLayout) view.findViewById(R.id.frame_container);
        mDownloadProgressBar = (ProgressBar)view.findViewById(R.id.download_progress);
        mTitleText = (TextView) view.findViewById(R.id.title_text);
        mHeaderLayout = (LinearLayout) view.findViewById(R.id.header_layout);
        backButton.setOnClickListener(this);
    }

    protected void setContent(View childFragmentView) {
        mContainerFrame.addView(childFragmentView);
    }

    protected void setTitle(String title) {
        mTitleText.setText(title);
    }

    @Override
    public void onClick(View v) {

    }

    protected void hideProgress() {
        mDownloadProgressBar.setVisibility(View.GONE);
    }

    protected void showHeaderBar() {
        mHeaderLayout.setVisibility(View.VISIBLE);
    }

    protected void hideHeaderBar() {
        mHeaderLayout.setVisibility(View.GONE);
    }

    protected void showProgress() {
        mDownloadProgressBar.setVisibility(View.VISIBLE);
    }

}

package robosoft.com.jsonparser.videos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import robosoft.com.jsonparser.common.ui.BaseFragment;
import robosoft.com.jsonparser.videos.VideoAdapter;
import robosoft.com.newsapp.R;

//Fragment to display videos
public class VideoFragment extends BaseFragment {


    private RecyclerView mVideoRecycler;
    private VideoAdapter mVideoAdapter;
    private onVideoItemClick mVideoItemClickReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.video_fragment_layout, container, false);
        setContent(view);
        initViews(view);
        setUpVideoRecyclerView();
        return parent;

    }

    private void setUpVideoRecyclerView() {
        LinearLayoutManager mManager = new LinearLayoutManager(getActivity());
        mVideoRecycler.setLayoutManager(mManager);
        mVideoRecycler.setAdapter(mVideoAdapter);
    }

    private void initViews(View view) {
        mVideoRecycler = (RecyclerView)view.findViewById(R.id.video_recycler);
        mVideoAdapter = new VideoAdapter(getActivity(),mVideoItemClickReference);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mVideoItemClickReference = (onVideoItemClick)context;
    }

    public interface onVideoItemClick {
        void displayVideoInDetail(String videoUrl);
    }

}

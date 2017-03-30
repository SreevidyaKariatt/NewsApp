package robosoft.com.jsonparser.images.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.ui.BaseFragment;
import robosoft.com.newsapp.R;
import robosoft.com.jsonparser.common.net.DownloadManager;
import robosoft.com.jsonparser.common.ui.views.SpacesItemDecoration;
import robosoft.com.jsonparser.images.dto.ImageResponse;

//Fragment for displaying images
public class ImageFragment extends BaseFragment {

    private RecyclerView mImageRecyclerView;
    private onImageItemClick mImageItemClickReference;
    private ImageAdapter mImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.image_fragment_layout, container, false);
        setContent(view);
        initViews(view);
        return parent;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideHeaderBar();
        showProgress();
        downloadData(AppConstants.urlConstants.IMAGE_URL);
    }

    private void initViews(View view) {
        mImageRecyclerView = (RecyclerView)view.findViewById(R.id.image_recyclerview);
        mImageRecyclerView.addItemDecoration(new SpacesItemDecoration(AppConstants.integerConstants.SPAN_COUNT, AppConstants.integerConstants.SPACING, AppConstants.booleanConstants.IMCLUDE_EDGE));
        mImageAdapter = new ImageAdapter(getActivity(),null,mImageItemClickReference);
        GridLayoutManager mManager = new GridLayoutManager(getActivity(),2);
        mImageRecyclerView.setLayoutManager(mManager);
        mImageRecyclerView.setAdapter(mImageAdapter);

    }

    private void downloadData(String url)
    {
        DownloadManager.downloadDataForImage(getActivity(), url, new Response.Listener<ImageResponse>() {
            @Override
            public void onResponse(ImageResponse response) {
                    hideProgress();
                    mImageAdapter.addItems(response.images);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.msg_download_error), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mImageItemClickReference = (onImageItemClick)context;
    }

    public interface onImageItemClick {
        void displayImageInDetail(String imageDescription,String imageUrl);
    }

}

package robosoft.com.jsonparser.news.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.net.DownloadManager;
import robosoft.com.jsonparser.common.ui.BaseFragment;
import robosoft.com.jsonparser.news.dto.NewsListResponse;
import robosoft.com.newsapp.R;


//Fragment for displaying news
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private TextView mNewsInMarqueeText;
    private String TAG = "News";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mNewsRecyclerView;
    private NewsAdapter mNewsAdapter;
    private onNewsItemClick newsItemClickReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_fragmet_layout, container, false);
        initViews(view);
        setContent(view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideHeaderBar();
        showProgress();
        downloadData(AppConstants.urlConstants.NEWS_URL);
    }

    private void downloadData(String url) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            hideProgress();
        }
        downloadJsonNews(url);
    }

    private void downloadJsonNews(String newsUrl) {

        DownloadManager.downloadData(getActivity(), newsUrl, new Response.Listener<NewsListResponse>() {

            @Override
            public void onResponse(NewsListResponse response) {
                hideProgress();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                mNewsAdapter.addItems(response.newsItems);
                displayHeadlineAsMarquee(response.newsItems);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.msg_download_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayHeadlineAsMarquee(List<NewsListResponse.NewsItem> newsList) {
        final StringBuffer marqueeText = new StringBuffer();
        for (NewsListResponse.NewsItem newsItemObject : newsList) {
            marqueeText.append(newsItemObject.headLine);
            marqueeText.append("  ***  ");
        }
        mNewsInMarqueeText.setVisibility(View.VISIBLE);
        mNewsInMarqueeText.setText(marqueeText);
        mNewsInMarqueeText.setSelected(true);

    }

    @Override
    public void onRefresh() {
        downloadData(AppConstants.urlConstants.NEWS_URL);
    }

    private void initViews(View view) {
        LinearLayoutManager managerForRecycler = new LinearLayoutManager(getActivity());
        mNewsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        mNewsRecyclerView.setLayoutManager(managerForRecycler);
        mNewsAdapter = new NewsAdapter(null,newsItemClickReference);
        mNewsRecyclerView.setAdapter(mNewsAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_swipe_refresh);
        mNewsInMarqueeText = (TextView) view.findViewById(R.id.news_marquee_text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsItemClickReference = (onNewsItemClick)context;

    }
    public interface onNewsItemClick {
        void displayNewsInDetail(int position,String webUrl,String imaeUrl,String headLine);
    }


}

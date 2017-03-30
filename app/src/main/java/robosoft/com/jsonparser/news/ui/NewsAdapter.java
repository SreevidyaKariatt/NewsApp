package robosoft.com.jsonparser.news.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.net.MySingleton;
import robosoft.com.jsonparser.common.utils.DateUtils;
import robosoft.com.jsonparser.news.dto.NewsListResponse;
import robosoft.com.newsapp.R;
import robosoft.com.jsonparser.news.ui.NewsFragment.onNewsItemClick;


/**
 * Created by sree on 13/1/16.
 */

//Adapter for news data
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private ArrayList<NewsListResponse.NewsItem> mNewsList;
    private Picasso mPicasso;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    private final String TAG = "NEWS";
    private onNewsItemClick mNewsItemClickReference;

    public NewsAdapter(ArrayList<NewsListResponse.NewsItem> object, onNewsItemClick newsItemClickReference) {
        mNewsList = object;
        this.mNewsItemClickReference = newsItemClickReference;

    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mImageLoader = MySingleton.getInstance(parent.getContext()).getImageLoader();
        mPicasso = Picasso.with(parent.getContext());
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.news_child_layout, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.newsImage.setDefaultImageResId(R.drawable.newsdeafult);
        if (mNewsList.get(position).imageData != null) {
            Log.i(TAG, "image present in url");
            Log.i(TAG, mNewsList.get(position).imageData.photo);
            // mPicasso.load(mNewsList.get(position).imageData.photo).into(holder.newsImage);
            //
            // holder.newsImage.setImageUrl(mNewsList.get(position).imageData.photo, mImageLoader);
        }
        holder.headLineText.setText(mNewsList.get(position).headLine);
        holder.dateText.setText(DateUtils.parseDate(mNewsList.get(position).dateLine));
    }

    public void addItems(List<NewsListResponse.NewsItem> newsList) {
        if (mNewsList == null) {
            mNewsList = new ArrayList<>();
        }
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNewsList != null ? mNewsList.size() : 0;
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView newsImage;
        TextView headLineText;
        TextView dateText;

        public NewsViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick();
                }
            });
        }

        private void itemClick() {
            Bitmap bitmap = ((BitmapDrawable) newsImage.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
            byte[] bytes = bos.toByteArray();
            if (mNewsList.get(getAdapterPosition()).imageData != null) {
                mNewsItemClickReference.displayNewsInDetail(getAdapterPosition(), mNewsList.get(getAdapterPosition()).webURL, mNewsList.get(getAdapterPosition()).imageData.photo, dateText.getText().toString());
            } else {
                mNewsItemClickReference.displayNewsInDetail(getAdapterPosition(), mNewsList.get(getAdapterPosition()).webURL, AppConstants.urlConstants.DEFAULT_IMAGE_URL, dateText.getText().toString());
            }
        }

        private void initViews(View itemView) {
            newsImage = (NetworkImageView) itemView.findViewById(R.id.news_network_imageview);
            headLineText = (TextView) itemView.findViewById(R.id.news_title_text);
            dateText = (TextView) itemView.findViewById(R.id.news_date_text);
        }

    }


}

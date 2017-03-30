package robosoft.com.jsonparser.favorite;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.helper.DatabaseHelper;
import robosoft.com.jsonparser.images.ui.ImageFragment.onImageItemClick;
import robosoft.com.jsonparser.news.ui.NewsFragment.onNewsItemClick;
import robosoft.com.jsonparser.videos.ui.VideoFragment.onVideoItemClick;
import robosoft.com.newsapp.R;

/**
 * Created by sree on 21/1/16.
 */

//Adapter for displaying the favorite items clicked
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<FavoriteDb> mFavoriteList;
    private DatabaseHelper mAdapter;
    private LayoutInflater mInflater;
    private onNewsItemClick mNewsItemClickReference;
    private onImageItemClick mImageItemClickRefernce;
    private onVideoItemClick mVideoItemClickRefernce;

    private final String TAG = "News";

    public FavoriteAdapter(ArrayList<FavoriteDb> imageInfoList, onNewsItemClick newsItemClickReference, onImageItemClick imageItemClickReference, onVideoItemClick videoItemClickReference) {
        mFavoriteList = imageInfoList;
        mNewsItemClickReference = newsItemClickReference;
        mImageItemClickRefernce = imageItemClickReference;
        mVideoItemClickRefernce = videoItemClickReference;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.favorite_child_layout, parent, false);
        FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);
        mAdapter = new DatabaseHelper(parent.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        holder.imageView.setImageBitmap(mFavoriteList.get(position).getImage());
        if (mFavoriteList.get(position).getType().equals(AppConstants.stringConstants.STR_IMAGE)) {
            holder.textType.setImageResource(R.drawable.imageicon);
        } else if (mFavoriteList.get(position).getType().equals(AppConstants.stringConstants.STR_VIDEO)) {
            holder.textType.setImageResource(R.drawable.videoicon);
        } else {
            holder.textType.setImageResource(R.drawable.newsicon);
        }
        holder.textDescription.setText(mFavoriteList.get(position).getData());

    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public ImageView imageView;
        public ImageView textType;
        public TextView textDescription;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            initViews();
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mFavoriteList.get(getAdapterPosition()).getType().equals(AppConstants.stringConstants.STR_IMAGE)) {
                        mImageItemClickRefernce.displayImageInDetail(mFavoriteList.get(getAdapterPosition()).getDescription(), mFavoriteList.get(getAdapterPosition()).getImageUrl());

                    } else if (mFavoriteList.get(getAdapterPosition()).getType().equals(AppConstants.stringConstants.STR_VIDEO)) {
                        mVideoItemClickRefernce.displayVideoInDetail(mFavoriteList.get(getAdapterPosition()).getUrl());

                    } else if (mFavoriteList.get(getAdapterPosition()).getType().equals(AppConstants.stringConstants.STR_NEWS)) {
                        mNewsItemClickReference.displayNewsInDetail(getAdapterPosition(), mFavoriteList.get(getAdapterPosition()).getUrl(), null, null);


                    }

                }
            });
        }

        private void initViews() {
            imageView = (ImageView) itemView.findViewById(R.id.favorite_imageview);
            textType = (ImageView) itemView.findViewById(R.id.contenttype_imageview);
            textDescription = (TextView) itemView.findViewById(R.id.favorite_description_text);
        }

        @Override
        public boolean onLongClick(View v) {
            // final FavoriteDb imageObject = mObject.get(getAdapterPosition());
            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(v.getContext().getString(R.string.msg_warning))
                    .setMessage(v.getContext().getString(R.string.msg_dialogmessage) + String.valueOf(getAdapterPosition()))
                    .setPositiveButton(v.getContext().getString(R.string.msg_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onDialoguePositiveButtonClick();


                        }


                    }).setNegativeButton(v.getContext().getString(R.string.msg_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            builder.create().show();
            return true;
        }

        private void onDialoguePositiveButtonClick() {
            int count = mAdapter.deleteRow(textDescription.getText().toString());
            if (count > 0) {

                mFavoriteList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }
        }
    }

    public void onItemRemove(final int adapterPosition, final RecyclerView mRecycler, final String data) {
        final FavoriteDb imageObject = mFavoriteList.get(adapterPosition);

        Snackbar snackbar = Snackbar
                .make(mRecycler, mRecycler.getContext().getString(R.string.snackbar_title), Snackbar.LENGTH_LONG)
                .setAction(mRecycler.getContext().getString(R.string.snackbar_message), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mFavoriteList.add(adapterPosition, imageObject);
                        notifyItemInserted(adapterPosition);
                        mRecycler.scrollToPosition(adapterPosition);

                    }
                });
        snackbar.show();
        int count = mAdapter.deleteRow(data);
        if (count > 0) {

            mFavoriteList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);

        }

    }

    public void addItems(ArrayList<FavoriteDb> favoriteList) {
        if (favoriteList == null) {
            mFavoriteList = new ArrayList<>();
        }
        mFavoriteList.addAll(favoriteList);
        notifyDataSetChanged();
    }

}

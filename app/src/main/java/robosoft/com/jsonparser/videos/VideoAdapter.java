package robosoft.com.jsonparser.videos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.helper.DatabaseHelper;
import robosoft.com.jsonparser.videos.ui.VideoFragment.onVideoItemClick;
import robosoft.com.newsapp.R;


/**
 * Created by sree on 20/1/16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private ArrayList<String> mMovieTitleList;
    private ArrayList<String> mUrlList;
    private ArrayList<Integer> mImageList;
    private DatabaseHelper mDataBaseHelperObject;
    private onVideoItemClick mVideoItemClickRefrence;
    private static final String TAG = "NEWS";
    Context mContext;

    public VideoAdapter(Context context, onVideoItemClick videoItemClickReference) {
        mVideoItemClickRefrence = videoItemClickReference;
        initVideoList(context);
        initVideoImageList();
        initVideoUrlList(context);
    }

    private void initVideoUrlList(Context context) {
        mUrlList = new ArrayList<>();
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
        mUrlList.add(context.getString(R.string.video_url));
    }

    private void initVideoImageList() {
        mImageList = new ArrayList<Integer>();
        mImageList.add(R.drawable.imagesadikapyare);
        mImageList.add(R.drawable.ennnintemoideen);
        mImageList.add(R.drawable.anarkali);
        mImageList.add(R.drawable.rani);
        mImageList.add(R.drawable.charlie);
        mImageList.add(R.drawable.amarakbar);
        mImageList.add(R.drawable.lifeofjosutty);
        mImageList.add(R.drawable.kunjiramayanam);
        mImageList.add(R.drawable.ayalnjanalla);
        mImageList.add(R.drawable.bhaskar);
    }

    private void initVideoList(Context context) {
        mMovieTitleList = new ArrayList<>();
        mMovieTitleList.add(context.getString(R.string.video_title_adikapyare));
        mMovieTitleList.add(context.getString(R.string.video_title_moideen));
        mMovieTitleList.add(context.getString(R.string.video_title_anarkali));
        mMovieTitleList.add(context.getString(R.string.video_title_rani));
        mMovieTitleList.add(context.getString(R.string.video_title_charlie));
        mMovieTitleList.add(context.getString(R.string.video_title_amar));
        mMovieTitleList.add(context.getString(R.string.video_title_josutty));
        mMovieTitleList.add(context.getString(R.string.video_title_kunjiramayanam));
        mMovieTitleList.add(context.getString(R.string.video_title_njanalla));
        mMovieTitleList.add(context.getString(R.string.video_title_bhaskar));
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mDataBaseHelperObject = new DatabaseHelper(mContext);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_child_layout, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.videoimageView.setImageResource(mImageList.get(position));
        holder.titleText.setText(mMovieTitleList.get(position));
        boolean value = mDataBaseHelperObject.checkForPositionInDb(holder.titleText.getText().toString());
        holder.favoriteImage.setChecked((value) ? true : false);


    }

    @Override
    public int getItemCount() {
        return mMovieTitleList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView videoimageView;
        TextView titleText;
        ToggleButton favoriteImage;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoimageView = (ImageView) itemView.findViewById(R.id.imageForVideo);
            titleText = (TextView) itemView.findViewById(R.id.textForVideo);
            favoriteImage = (ToggleButton) itemView.findViewById(R.id.favoriteForVideo);
            itemView.setOnClickListener(this);
            favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onFavoriteClick(v);

                }
            });


        }

        private void onFavoriteClick(View view) {

            boolean value = mDataBaseHelperObject.checkForPositionInDb(titleText.getText().toString());
            if (value == false) {
                insertFavoriteItemToDatabase(view);

            } else {
                deleteFavoriteItemFromDatabase(view);

            }
        }

        private void deleteFavoriteItemFromDatabase(View view) {
            int count = mDataBaseHelperObject.deleteRow(titleText.getText().toString());
            if (count > 0)
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.msg_item_deletedFromFavorite), Toast.LENGTH_LONG).show();
            else
                Log.i(TAG, view.getContext().getString(R.string.msg_error_delete));
        }

        private void insertFavoriteItemToDatabase(View view) {
            Bitmap bitmap = ((BitmapDrawable) videoimageView.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] imgInBytes = bos.toByteArray();
            long count = mDataBaseHelperObject.insertData(getAdapterPosition(), imgInBytes, AppConstants.stringConstants.STR_VIDEO, titleText.getText().toString(), null, null, mUrlList.get(getAdapterPosition()));
            if (count > 0)
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.msg_item_addedAsFavorite), Toast.LENGTH_LONG).show();
            else
                Log.i(TAG, view.getContext().getString(R.string.msg_error_insert));
        }

        @Override
        public void onClick(View v) {
            mVideoItemClickRefrence.displayVideoInDetail(mUrlList.get(getAdapterPosition()));

        }


    }


}

package robosoft.com.jsonparser.images.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import robosoft.com.jsonparser.common.constants.AppConstants;
import robosoft.com.jsonparser.common.helper.DatabaseHelper;
import robosoft.com.jsonparser.common.net.MySingleton;
import robosoft.com.jsonparser.images.dto.ImageResponse;
import robosoft.com.jsonparser.images.ui.ImageFragment.onImageItemClick;
import robosoft.com.newsapp.R;


/**
 * Created by sree on 19/1/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {


    private DatabaseHelper mAdapter;
    private ArrayList<ImageResponse.ImageDescription> mImageList;
    private byte[] img = null;
    private ImageLoader mImageLoader;
    private static final String TAG = "NEWS";
    private onImageItemClick mImageItemClickReference;

    public ImageAdapter(Context context, ArrayList<ImageResponse.ImageDescription> imageList, onImageItemClick imageItemClickReference) {
        mImageList = imageList;
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        mAdapter = new DatabaseHelper(context);
        mImageItemClickReference = imageItemClickReference;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_childview_layout, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        holder.textView.setText(mImageList.get(position).title);
        holder.imageView.setImageUrl(mImageList.get(position).imageUrl, mImageLoader);
        holder.textDescription.setText(mImageList.get(position).description);
        boolean value = mAdapter.checkForPositionInDb(holder.textView.getText().toString());
        if (value) {
            holder.favoriteImage.setChecked(true);

        } else {
            holder.favoriteImage.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mImageList != null ? mImageList.size() : 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        NetworkImageView imageView;
        TextView textView;
        TextView textDescription;
        ToggleButton favoriteImage;

        public ImageViewHolder(final View itemView) {
            super(itemView);
            initView();
            favoriteImage.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemViewClick();
                }
            });
        }

        private void onItemViewClick() {
            mImageItemClickReference.displayImageInDetail(mImageList.get(getAdapterPosition()).description, mImageList.get(getAdapterPosition()).imageUrl);

        }

        private void initView() {
            imageView = (NetworkImageView) itemView.findViewById(R.id.furniture_networkimageview);
            textView = (TextView) itemView.findViewById(R.id.furniture_text);
            textDescription = (TextView) itemView.findViewById(R.id.furniture_description_text);
            favoriteImage = (ToggleButton) itemView.findViewById(R.id.favorite_togglebutton);
        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            onClickFavorite(position,v);

        }

        private void onClickFavorite(int position,View view) {
            boolean value = mAdapter.checkForPositionInDb(textView.getText().toString());
            if (value == false) {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
                long count = mAdapter.insertData(position, img, AppConstants.stringConstants.STR_IMAGE, textView.getText().toString(), mImageList.get(getAdapterPosition()).description, mImageList.get(getAdapterPosition()).imageUrl,null);
                if (count > 0)
                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.msg_item_addedAsFavorite), Toast.LENGTH_LONG).show();
                else
                    Log.i(TAG, view.getContext().getString(R.string.msg_error_insert));
            } else {
                int count = mAdapter.deleteRow(textView.getText().toString());
                if (count > 0)
                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.msg_item_deletedFromFavorite), Toast.LENGTH_LONG).show();
                else
                    Log.i(TAG, view.getContext().getString(R.string.msg_error_delete));
            }
        }


    }

    public void addItems(List<ImageResponse.ImageDescription> imageList) {
        if (mImageList == null) {
            mImageList = new ArrayList<>();
        }
        mImageList.addAll(imageList);
        notifyDataSetChanged();
    }

}

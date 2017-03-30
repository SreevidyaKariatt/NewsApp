package robosoft.com.jsonparser.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import robosoft.com.jsonparser.common.helper.DatabaseHelper;
import robosoft.com.jsonparser.common.ui.BaseFragment;
import robosoft.com.jsonparser.images.ui.ImageFragment.onImageItemClick;
import robosoft.com.jsonparser.news.ui.NewsFragment.onNewsItemClick;
import robosoft.com.jsonparser.videos.ui.VideoFragment.onVideoItemClick;
import robosoft.com.newsapp.R;


//Fragment for displaying the favorite items
public class FavoriteFragment extends BaseFragment {


    private final String TAG = "News";
    private DatabaseHelper mAdapter;
    private RecyclerView mFavoriteRecycler;
    private TextView mTextForEmptyMessage;
    private LinearLayoutManager mManager;
    private FavoriteAdapter mFavoriteAdapter;
    private onNewsItemClick mNewsItemClickReference;
    private onImageItemClick mImageItemClickReference;
    private onVideoItemClick mVideoItemClickReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment_layout, container, false);
        initViews(view);

        populateRecyclerView(view);

        return view;

    }

    private void initViews(View view) {
        mFavoriteRecycler = (RecyclerView) view.findViewById(R.id.favorite_recyclerview);
        mTextForEmptyMessage = (TextView) view.findViewById(R.id.empty_message_text);
        mAdapter = new DatabaseHelper(getActivity());
        mFavoriteAdapter = new FavoriteAdapter(null, mNewsItemClickReference, mImageItemClickReference, mVideoItemClickReference);
        mFavoriteRecycler.setAdapter(mFavoriteAdapter);
        mManager = new LinearLayoutManager(getActivity());
        mFavoriteRecycler.setLayoutManager(mManager);
        mTextForEmptyMessage.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsItemClickReference = (onNewsItemClick) context;
        mImageItemClickReference = (onImageItemClick) context;
        mVideoItemClickReference = (onVideoItemClick) context;
    }

    public void populateRecyclerView(View view) {

        mFavoriteAdapter.addItems(mAdapter.getAllRows());
        if (mAdapter.getAllRows().size() > 0) {
            onSwipeToDelete();
        } else {
            setEmptyTextMessage(view);

        }
    }

    private void setEmptyTextMessage(View view) {
        mTextForEmptyMessage.setVisibility(View.VISIBLE);
        mTextForEmptyMessage.setText(view.getContext().getString(R.string.msg_empty_text));
    }

    private void onSwipeToDelete() {
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                FavoriteAdapter.FavoriteViewHolder favoriteViewHolder = (FavoriteAdapter.FavoriteViewHolder) viewHolder;
                mFavoriteAdapter.onItemRemove(viewHolder.getAdapterPosition(), mFavoriteRecycler, favoriteViewHolder.textDescription.getText().toString());

            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mFavoriteRecycler);
    }


}

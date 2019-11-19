package com.example.nearbyrestaurants.restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nearbyrestaurants.R;
import com.example.nearbyrestaurants.common.util.AppSharedPreference;
import com.example.nearbyrestaurants.common.util.AppUtil;
import com.example.nearbyrestaurants.common.util.ImageDownloader;
import com.example.nearbyrestaurants.restaurant.model.Result;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Result> mRestaurantsList;
    private onRestaurantItemClickListener mListener;

    public RestaurantListAdapter(List<Result> results, onRestaurantItemClickListener listener) {
        mRestaurantsList = results;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.restaurant_list_item_layout, parent, false);
            return new RestaurantViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading_progress_bar, parent, false);
            return new RestaurantListAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RestaurantViewHolder) {
            setRestaurantView((RestaurantViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            setLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurantsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mRestaurantsList.size() > 0) {
            return position == mRestaurantsList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        } else {
            return 0;
        }
    }

    private class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private View mRestaurantLyt;
        private TextView mTitle;
        private TextView mLocation;
        private TextView mDistance;
        private ImageView mHotelImage;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestaurantLyt = itemView.findViewById(R.id.lyt_restaurant_item);
            mTitle = itemView.findViewById(R.id.title);
            mLocation = itemView.findViewById(R.id.location);
            mHotelImage = itemView.findViewById(R.id.hotel_image);
            mDistance = itemView.findViewById(R.id.distance);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar mProgressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void setRestaurantView(RestaurantViewHolder restaurantViewHolder, final int position) {
        if (mRestaurantsList.size() > 0) {
            restaurantViewHolder.mTitle.setText(mRestaurantsList.get(position).getName());
            restaurantViewHolder.mLocation.setText(mRestaurantsList.get(position).getVicinity());
            double restaurantLat = mRestaurantsList.get(position).getGeometry().getLocation().getLat();
            double restaurantLan = mRestaurantsList.get(position).getGeometry().getLocation().getLng();
            Context context = restaurantViewHolder.mDistance.getContext();
            restaurantViewHolder.mDistance.setText(String.format("%s m", String.valueOf(
                    AppUtil.distance(Double.parseDouble(AppSharedPreference.getInstance().getDeviceLat(context)),
                            Double.parseDouble(AppSharedPreference.getInstance().getDeviceLan(context)),
                            restaurantLat, restaurantLan, "m"))));
            new ImageDownloader(restaurantViewHolder.mHotelImage, null).
                    execute(mRestaurantsList.get(position).getIcon());
            restaurantViewHolder.mRestaurantLyt.setOnClickListener(v ->
            {
                if (null != mRestaurantsList.get(position).getPhotos() &&
                        mRestaurantsList.get(position).getPhotos().size() > 0) {
                    mListener.onItemClicked(mRestaurantsList.get(position).getPhotos().get(0).
                            getPhotoReference());
                } else {
                    Toast.makeText(context, R.string.restaurant_image_not_available_error_msg,
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setLoadingView(LoadingViewHolder viewHolder, int position) {
        viewHolder.mProgressBar.setVisibility(View.VISIBLE);
    }

    public interface onRestaurantItemClickListener {
        void onItemClicked(String photoReference);
    }
}

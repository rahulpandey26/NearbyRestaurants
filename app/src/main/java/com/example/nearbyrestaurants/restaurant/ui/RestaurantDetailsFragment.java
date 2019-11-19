package com.example.nearbyrestaurants.restaurant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.nearbyrestaurants.R;
import com.example.nearbyrestaurants.common.ui.fragment.BaseFragment;
import com.example.nearbyrestaurants.common.util.Constant;
import com.example.nearbyrestaurants.common.util.DialogUtil;
import com.example.nearbyrestaurants.common.util.ImageDownloader;
import com.example.nearbyrestaurants.common.util.NetworkUtil;

import java.util.Objects;

public class RestaurantDetailsFragment extends BaseFragment implements
        ImageDownloader.OnImageDownloadedListener, View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener {

    private static final String KEY_PHOTO_REFERENCE = "longitude";
    private String mPhotoReference;
    private ImageView mPlaceImage;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

    static RestaurantDetailsFragment newInstance(String photoReference) {
        RestaurantDetailsFragment homeFragment = new RestaurantDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHOTO_REFERENCE, photoReference);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout(R.layout.fragment_restaurant_details);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        showToolbar();
        hideMapIcon();
        getPlacePhoto();
    }

    private void initializeViews(View view) {
        mPlaceImage = view.findViewById(R.id.place_image);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(),this);
    }

    private void getBundleData() {
        if (getArguments() != null) {
            mPhotoReference = getArguments().getString(KEY_PHOTO_REFERENCE);
        }
    }

    private void getPlacePhoto() {
        if(!NetworkUtil.isAvailable(getContext())) {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            return;
        }
        if(null != mPhotoReference) {
            setProgressBarVisible();
            String query = "key=" + Constant.GOOGLE_PLACE_KEY +
                    "&maxwidth=800&photoreference=" + mPhotoReference;
            String finalUrl = Constant.BASE_URL + Constant.PLACE_PHOTO_END_POIND + query;
            new ImageDownloader(mPlaceImage, this).execute(finalUrl);
        } else {
            Toast.makeText(getContext(), R.string.image_not_available_error_msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnImageDownloaded() {
        hideProgressBar();
    }

    @Override
    public void OnImageDownloadingError() {
        hideProgressBar();
        Toast.makeText(getContext(), R.string.image_not_available_error_msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScaleFactor *= detector.getScaleFactor();
        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
        mPlaceImage.setScaleX(mScaleFactor);
        mPlaceImage.setScaleY(mScaleFactor);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}

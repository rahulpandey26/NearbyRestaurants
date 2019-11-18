package com.example.nearbyrestaurants.common.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.nearbyrestaurants.R;


public class BaseFragment extends Fragment {

    private int mLayoutId;
    private Toolbar mToolbar;
    private ImageView mMapIcon;
    private FrameLayout mProgressBarLyt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        FrameLayout fragmentLayoutContainer = view.findViewById(R.id.layout_container);
        inflater.inflate(mLayoutId, fragmentLayoutContainer);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mMapIcon = view.findViewById(R.id.map_icon);
        mProgressBarLyt = view.findViewById(R.id.progress_bar_container);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(mToolbar);
            if (appCompatActivity.getSupportActionBar() != null) {
                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                appCompatActivity.getSupportActionBar().setHomeButtonEnabled(false);
                appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    protected void setLayout(int layoutId) {
        mLayoutId = layoutId;
    }

    protected void showToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    public void hideToolbar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    protected void showMapIcon(){
        mMapIcon.setVisibility(View.VISIBLE);
    }

    protected void hideMapIcon(){
        mMapIcon.setVisibility(View.GONE);
    }

    public void setToolbarTitle(String toolbarTitle) {
        ((TextView) mToolbar.findViewById(R.id.toolbar_title)).setText(toolbarTitle);
    }

    protected void setProgressBarVisible() {
        mProgressBarLyt.setVisibility(View.VISIBLE);
    }

    protected void hideProgressBar() {
        mProgressBarLyt.setVisibility(View.GONE);
    }
}

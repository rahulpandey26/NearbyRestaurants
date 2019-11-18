package com.example.nearbyrestaurants.restaurant.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.nearbyrestaurants.R;
import com.example.nearbyrestaurants.common.ui.fragment.BaseFragment;
import com.example.nearbyrestaurants.common.util.Constant;
import com.example.nearbyrestaurants.common.util.WebService;
import com.example.nearbyrestaurants.restaurant.adapter.RestaurantListAdapter;
import com.example.nearbyrestaurants.restaurant.model.RestaurantsResponse;
import com.example.nearbyrestaurants.restaurant.model.Result;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends BaseFragment implements WebService.OnParsingCompletedListener,
        RestaurantListAdapter.onRestaurantItemClickListener {

    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private double mLatitude, mLongitude;
    private RestaurantsResponse mRestaurantResults;
    private ArrayList<Result> mRestaurantList = new ArrayList<>();
    private boolean mIsLoading = false;
    private HomeScreenListener mListener;

    static HomeFragment newInstance(double lat, double lan) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(KEY_LATITUDE, lat);
        bundle.putDouble(KEY_LONGITUDE, lan);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (HomeScreenListener) context;
        } catch (ClassCastException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout(R.layout.fragment_home);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setAdapter();
        getRestaurantList("");
        initScrollListener();
    }

    private void initializeViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mRecyclerView = view.findViewById(R.id.restaurant_list);
        showMapIcon();
        view.findViewById(R.id.map_icon).setOnClickListener(v ->
                mListener.launchRestaurantMarkerScreen(mRestaurantList));

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            getRestaurantList("");
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private void getBundleData() {
        if (getArguments() != null) {
            mLatitude = getArguments().getDouble(KEY_LATITUDE);
            mLongitude = getArguments().getDouble(KEY_LONGITUDE);
        }
    }

    private void getRestaurantList(String nextPageToken) {
        setProgressBarVisible();
        String query = "type=restaurant&key=" + Constant.GOOGLE_PLACE_KEY +
                "&rankby=distance&location=" + mLatitude + ", " + mLongitude;
        if (!TextUtils.isEmpty(nextPageToken)) {
            query = query + "&next_page_token=" + nextPageToken;
        }
        String finalUrl = Constant.BASE_URL + Constant.PLACE_END_POINT + query;
        WebService webService = new WebService(finalUrl, this);
        webService.execute();
    }

    @Override
    public void onParsingCompleted(RestaurantsResponse restaurantsResponse) {
        if (null != restaurantsResponse) {
            mRestaurantResults = restaurantsResponse;
            mRestaurantList.addAll(restaurantsResponse.getResults());
            Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
            hideProgressBar();
        }
    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(new RestaurantListAdapter(mRestaurantList, this));
    }

    private void initScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!mIsLoading && null != linearLayoutManager) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if ((totalItemCount - visibleItemCount)
                            <= (firstVisibleItemPosition + 5)) {
                        //bottom of list, so load more data.
                        loadMoreRestaurants();
                        mIsLoading = true;
                    }
                }
            }
        });
    }

    private void loadMoreRestaurants() {
        getRestaurantList(mRestaurantResults.getNextPageToken());
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), R.string.fetch_restaurant_list_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(String photoReference) {
        mListener.launchRestaurantDetailsScreen(photoReference);
    }

    public interface HomeScreenListener{
        void launchRestaurantDetailsScreen(String photoReference);
        void launchRestaurantMarkerScreen(ArrayList<Result> restaurantList);
    }
}

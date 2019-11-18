package com.example.nearbyrestaurants.restaurant.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.example.nearbyrestaurants.R;
import com.example.nearbyrestaurants.common.util.Constant;
import com.example.nearbyrestaurants.restaurant.model.Result;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class RestaurantMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private List<Result> mRestaurantList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_marker);
        getBundleData();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void getBundleData() {
        if(null != getIntent()){
            mRestaurantList = getIntent().getParcelableArrayListExtra(Constant.KEY_RESTAURANT_LIST);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        for(int pos = 0; pos < mRestaurantList.size(); pos++){
            if(null != mRestaurantList.get(pos).getGeometry()) {
                LatLng latLng = new LatLng(mRestaurantList.get(pos).getGeometry().getLocation().getLat(),
                        mRestaurantList.get(pos).getGeometry().getLocation().getLng());
                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title(mRestaurantList.get(pos).getName()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }
}

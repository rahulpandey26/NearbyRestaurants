package com.example.nearbyrestaurants.restaurant.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nearbyrestaurants.R;
import com.example.nearbyrestaurants.common.util.Constant;
import com.example.nearbyrestaurants.common.util.DialogUtil;
import com.example.nearbyrestaurants.common.util.FragmentHelper;
import com.example.nearbyrestaurants.common.util.TrackLocation;
import com.example.nearbyrestaurants.restaurant.model.Result;
import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeActivity extends AppCompatActivity implements HomeFragment.HomeScreenListener {

    private ArrayList<String> mPermissionsToRequest;
    private ArrayList<String> mPermissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private TrackLocation mTrackLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermission();
    }

    private void setPermission() {
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        mPermissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionsToRequest.size() > 0)
                requestPermissions(mPermissionsToRequest.toArray(new String[mPermissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private void getDeviceLocation() {
        mTrackLocation = new TrackLocation(HomeActivity.this);
        if (mTrackLocation.canGetLocation()) {
            double longitude = mTrackLocation.getLongitude();
            double latitude = mTrackLocation.getLatitude();
            loadHomeFragment(latitude, longitude);
            Toast.makeText(getApplicationContext(), "Longitude:" + longitude + "\nLatitude:" +
                            latitude, Toast.LENGTH_SHORT).show();
        } else {
            mTrackLocation.showSettingsAlert();
        }
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
            }
        }
        return false;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (String perms : mPermissionsToRequest) {
                if (hasPermission(perms)) {
                    mPermissionsRejected.add(perms);
                } else {
                    getDeviceLocation();
                }
            }

            if (mPermissionsRejected.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(mPermissionsRejected.get(0))) {
                        DialogUtil.showAlertMessage(this,
                                "These permissions are mandatory for the application. Please allow access.",
                                (dialog, which) -> requestPermissions(mPermissionsRejected.toArray(new
                                        String[mPermissionsRejected.size()]), ALL_PERMISSIONS_RESULT));
                    }
                }

            }
        }

    }

    private void loadHomeFragment(double latitude, double longitude) {
        FragmentHelper.replaceFragment(this, HomeFragment.newInstance(latitude,
                longitude), R.id.fragment_container, "Home_fragment");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTrackLocation.stopListener();
    }

    @Override
    public void launchRestaurantDetailsScreen(String photoReference) {
        FragmentHelper.addFragmentWithoutAnimation(this, RestaurantDetailsFragment.newInstance(
                photoReference), R.id.fragment_container, "Restaurant_details_fragment");
    }

    @Override
    public void launchRestaurantMarkerScreen(ArrayList<Result> restaurantList) {
        Intent intent = new Intent(this, RestaurantMarkerActivity.class);
        intent.putParcelableArrayListExtra(Constant.KEY_RESTAURANT_LIST, restaurantList);
        startActivity(intent);
    }
}


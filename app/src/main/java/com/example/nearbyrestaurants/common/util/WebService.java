package com.example.nearbyrestaurants.common.util;

import android.os.AsyncTask;
import com.example.nearbyrestaurants.restaurant.model.RestaurantsResponse;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService extends AsyncTask<String, Void, RestaurantsResponse> {

    private OnParsingCompletedListener mOnParsingCompletedListener;
    private String mUrl = "";

    public WebService(String url, OnParsingCompletedListener onParsingCompletedListener) {
        mUrl = url;
        mOnParsingCompletedListener = onParsingCompletedListener;
    }

    @Override
    protected RestaurantsResponse doInBackground(String... params) {
        HttpURLConnection httpConnection = null;
        try {
            URL mUrl = new URL(this.mUrl);
            httpConnection = (HttpURLConnection) mUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-length", "0");
            httpConnection.setUseCaches(false);
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setConnectTimeout(100000);
            httpConnection.setReadTimeout(100000);

            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String result=  stringBuilder.toString();
                // convert to json
                return new Gson().fromJson(result, RestaurantsResponse.class);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
            return null;
    }

    @Override
    protected void onPostExecute(RestaurantsResponse restaurantsResponse) {
        super.onPostExecute(restaurantsResponse);
        if(null != mOnParsingCompletedListener) {
            if (null != restaurantsResponse) {
                mOnParsingCompletedListener.onParsingCompleted(restaurantsResponse);
            } else
                mOnParsingCompletedListener.onError();
        }
    }

    public interface OnParsingCompletedListener {
        void onParsingCompleted(RestaurantsResponse restaurantsResponse);

        void onError();
    }
}

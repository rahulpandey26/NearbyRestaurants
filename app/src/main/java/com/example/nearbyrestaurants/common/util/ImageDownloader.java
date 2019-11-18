package com.example.nearbyrestaurants.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nearbyrestaurants.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private OnImageDownloadedListener mListener;

    public ImageDownloader(ImageView imageView, OnImageDownloadedListener onImageDownloadedListener) {
        imageViewReference = new WeakReference<>(imageView);
        mListener = onImageDownloadedListener;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        ImageView imageView = imageViewReference.get();
        if (imageView != null && result != null) {
            imageView.setImageBitmap(result);
            if(null != mListener) {
                mListener.OnImageDownloaded();
            }
        } else {
           mListener.OnImageDownloadingError();
        }
    }

    public interface OnImageDownloadedListener {
        void OnImageDownloaded();
        void OnImageDownloadingError();
    }
}

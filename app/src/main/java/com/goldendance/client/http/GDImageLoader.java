package com.goldendance.client.http;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.goldendance.client.R;
import com.goldendance.client.view.GlideCircleTransform;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/13.
 */

public class GDImageLoader {
    private static final String TAG = GDImageLoader.class.getSimpleName();

    /**
     * 设置图片
     *
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void setImageView(Context context, String imgUrl, ImageView imageView) {
        load(context, imgUrl, imageView, null);
    }

    //    设置图片
    public static void setImageView(Context context, String imgUrl, ImageView imageView, final GDImageLoaderListener listener) {
        load(context, imgUrl, imageView, listener);
    }

    public static void setCircleView(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl).transform(new GlideCircleTransform(context)).error(R.mipmap.i_avatar).into(imageView);
    }

    private static void load(Context context, String imgUrl, ImageView imageView, final GDImageLoaderListener listener) {
        Glide.with(context).load(imgUrl)

                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onLoadFailed(e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (listener != null) {
                            listener.onLoadSuccess(target, resource);
                        }
                        return false;
                    }
                })
                .error(R.mipmap.logo_square)
                .into(imageView);

    }

    public static void load2(Context context, Uri uri, ImageView imageView, final GDImageLoaderListener listener) {
        Glide.with(context).load(uri)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        if (listener != null) {
//                            listener.onLoadFailed(e);
//                        }
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (listener != null) {
//                            listener.onLoadSuccess(target, resource);
//                        }
//                        return false;
//                    }
//                })
                .into(imageView);

    }

    public static void clearCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}

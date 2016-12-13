package com.goldendance.client.http;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Hemingway1014@gmail.com on 2016/12/13.
 */

public interface GDImageLoaderListener {
    void onLoadSuccess(Target<GlideDrawable> target, GlideDrawable resource);

    void onLoadFailed(Exception e);
}

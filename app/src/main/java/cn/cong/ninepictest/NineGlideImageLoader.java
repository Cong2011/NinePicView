package cn.cong.ninepictest;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import cn.cong.ninepic.INinePicImageLoader;

public class NineGlideImageLoader implements INinePicImageLoader {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).transform(new RoundedCorners(32)).into(imageView);
    }

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height) {
        GlideApp.with(context).load(url).transform(new RoundedCorners(32)).override(width, height).into(imageView);
    }

}
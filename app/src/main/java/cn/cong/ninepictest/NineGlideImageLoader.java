package cn.cong.ninepictest;

import android.content.Context;
import android.widget.ImageView;

import cn.cong.ninepic.INinePicImageLoader;

public class NineGlideImageLoader implements INinePicImageLoader {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).circleCrop().into(imageView);
    }

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height) {
        GlideApp.with(context).load(url).override(width, height).circleCrop().into(imageView);
    }

}
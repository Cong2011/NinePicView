package cn.cong.ninepic;

import android.content.Context;
import android.widget.ImageView;

public interface INinePicImageLoader {

    void displayNineGridImage(Context context, String url, ImageView imageView);

    void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height);
}
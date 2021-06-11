package cn.cong.ninepictest;

import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class InitGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // 缓存位置为APP对应的内部缓存文件夹
        String cachePath = context.getApplicationContext().getCacheDir().getPath() + "/img";
        // 磁盘缓存大小，最大100M
        long diskCacheSize = 1024 * 1024 * 100;
        // 图片内存占用大小，最大20M
        long memoryCacheSize = 1024 * 1024 * 20;

        builder
                // 配置磁盘缓存 disk硬盘 cache缓存 Lru最大最少管理规则
                .setDiskCache(new DiskLruCacheFactory(cachePath, diskCacheSize))
                // 配置内存缓存
                .setMemoryCache(new LruResourceCache(memoryCacheSize))
                .setDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_launcher_round)
                        .placeholder(R.mipmap.ic_launcher));
    }
}

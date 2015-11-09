package com.example.com.imageloaderdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class MainActivity extends ActionBarActivity {
    //    String url = "http://down.tutu001.com/d/file/20101129/2f5ca0f1c9b6d02ea87df74fcc_560.jpg";
    String url = "http://img2.imgtn.bdimg.com/it/u=4270894670,2181503254&fm=21&gp=0.jpg";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview);
        // ImageLoader是单例对象
        //显示图片的设置
//        cacheInMemory()     启动内存缓存
//        cacheOnDisk(true)     启用磁盘缓存
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
                        //设置图片为圆形
                        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        //声明画布
                        Canvas canvas = new Canvas(bitmap1);
                        BitmapShader bitmapShader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                        Paint paint = new Paint();
                        paint.setShader(bitmapShader);
                        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2, paint);
                        imageAware.setImageBitmap(bitmap1);
                    }
                })     //设置图片展示的样式
                .build();
        //进行配置二级缓存的问题
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSizePercentage(20)   //配置二级缓存内存的缓存大小
                .diskCacheFileCount(100)       //设置磁盘缓存，缓存的数量是多少个
                .diskCacheSize(5 * 1024 * 1024)        //缓存的图片的大小
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        //build一下表示配置完成
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        //展示
        ImageLoader.getInstance().displayImage(url, imageView);
        //下载的监听
        ImageLoader.getInstance().displayImage(url, imageView, displayImageOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Toast.makeText(getApplicationContext(),"下载完成",Toast.LENGTH_LONG).show();
                Log.i("下载完成","完成测试");
            }


            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                Log.i("下载", String.valueOf(current));
            }
        });

    }


}

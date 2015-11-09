# Universal-Image-Loader
核心代码：
 String url = "http://img2.imgtn.bdimg.com/it/u=4270894670,2181503254&fm=21&gp=0.jpg";
    ImageView imageView;
 //展示
        ImageLoader.getInstance().displayImage(url, imageView);
        //可以显示图片了
//布局文件
<ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/imageview"
        />
//设置圆形的方式自带的不能用，兼用性不高
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
  //监听的设置
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
                Log.i("下载完成", "完成测试");
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

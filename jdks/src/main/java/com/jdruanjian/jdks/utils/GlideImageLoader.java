package com.jdruanjian.jdks.utils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Longlong on 2018/1/2.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */

        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
          //获取图片真正的宽高
    /*    Glide.with(context).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                int width = bitmap.getWidth();
                int height = bitmap.getWidth();
            }
        });

        //获取图片显示在ImageView后的宽高
        Glide.with(context)
                .load(path)
                .asBitmap()
                .listener(new RequestListener<Object, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, Object o, Target<Bitmap> target, boolean b) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, boolean b, boolean b1) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getWidth()/3;
                        return false;
                    }
                }).into(imageView);*/
    }
        //Picasso 加载图片简单用法
   //     Picasso.with(context).load(path).into(imageView);
        //用fresco加载图片简单用法，记得要写下面的createImageView方法


    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
    //    SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return null;
    }
}

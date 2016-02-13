package com.samaranavi.egorpetruchcho.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.samaranavi.egorpetruchcho.samaranavi.R;

import org.osmdroid.DefaultResourceProxyImpl;

public class CustomResourceProxy extends DefaultResourceProxyImpl {

    private final Context context;

    public CustomResourceProxy(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bitmap getBitmap(bitmap resId) {
//        switch (resId) {
//            case person:
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_person_pin_circle_black_48dp);
//        }
        return super.getBitmap(resId);
    }

    @Override
    public Drawable getDrawable(bitmap resId) {
//        switch (resId) {
//            case person:
//                return ContextCompat.getDrawable(context, R.drawable.ic_person_pin_circle_black_48dp);
//        }
        return super.getDrawable(resId);
    }
}
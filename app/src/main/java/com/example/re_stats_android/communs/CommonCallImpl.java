package com.example.re_stats_android.communs;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

public class CommonCallImpl implements ICommonCall {
    @Override
    public Drawable loadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}

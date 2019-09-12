package com.zhixiao.wanandroid.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: NamePictureCreator
 * @Description: 根据名字生成头像
 * @Author: zhixiao
 * @CreateDate: 2019/9/10
 */
public class NamePictureCreator {
    public static Bitmap generateNameDrawable(String name){
        if(name == null || name.isEmpty()) return null;
        String showText = name.substring(0, 1).toUpperCase();
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int c;
        paint.setColor(c = generateColor(name));
        canvas.drawCircle(100, 100, 100, paint);
        paint.setColor(reverseColor(c));
        paint.setTextSize(120);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float dy=(fontMetrics.descent-fontMetrics.ascent)/2-fontMetrics.descent;
        float Texty=100+dy;
        canvas.drawText(showText, 100, Texty, paint);
        return bitmap;
    }

    /**
     * 判断字符串是否为中文
     * @param str
     * @return
     */
    private static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find())
            return true;
        else
            return false;
    }

    /**
     * 根据字符串生成底色
     * @param str
     * @return
     */
    private static int generateColor(@NonNull String str){
        return (str.hashCode() * 198465623 % 0x1000000) + 0xFF000000;
    }

    public static int reverseInt(int c){
        int cc = 255 - c;
        if(cc>64 && cc<128)
            cc-=64;
        else if(cc>=128 && cc<192)
            cc+=64;
        return cc;
    }
    public static int reverseColor(int color) {
        int newColor = 0;
        for (int i = 0; i < 3; i++) {
            newColor += reverseInt(color & 0xff) << (i * 8);
            color >>= 8;
        }
        newColor += 0xFF000000;
        return newColor;
    }
}

package com.jacmobile.magicprices.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class DeviceUtils
{
    public static final String TAG = DeviceUtils.class.getSimpleName();

    public static boolean isLollipop()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isExternalStorageAvailable()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) &&
                !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static float getScreenDensity(Context context)
    {
        return context.getResources().getDisplayMetrics().density;
    }

    /** @return  int[ width, height ] */
    public static int[] getScreenDimens(Context context)
    {
        int[] result = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        result[0] = metrics.widthPixels;
        result[1] = metrics.heightPixels;
        return result;
    }


    public static int getScreenWidth(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay(context).getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay(context).getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static float getScreenWidthDp(Context context)
    {
        return pixelToDp(context, getScreenWidth(context));
    }

    /** dp = px / (dpi / 160) */
    public static float pixelToDp(Context context, float pixels)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay(context).getMetrics(metrics);
        return pixels / (metrics.densityDpi / 160f);
    }

    public static float dpToPixels(float dp)
    {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Display getDisplay(Context context)
    {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    /** Displays the soft keyboard */
    public static void showKeyboard(Context context)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /** Hides the soft keyboard */
    public static void hideKeyboard(Activity context)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static boolean isProductionEnvironment(Context context)
    {
        return context.getApplicationContext().getPackageName().contains("pro");
    }

    public static boolean isDevEnvironment(Context context)
    {
        return context.getApplicationContext().getPackageName().contains("dev");
    }

    public static boolean isStageEnvironment(Context context)
    {
        return context.getApplicationContext().getPackageName().contains("stage");
    }

    public static boolean hasCamera(Context context)
    {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static boolean isOnWifi(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static float getScreenHeightDp(Context context)
    {
        return pixelToDp(context, getScreenHeight(context));
    }
}
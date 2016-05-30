package com.waterchen.android_photosignapp.extra.tool;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by seasonyuu on 16-5-9.
 */
public class ScreenUtils {
	public static int getScreenHeight(Context context) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.heightPixels;
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.widthPixels;

	}
}

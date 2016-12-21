package menu.menudemo.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;


public class DrawableUtil {
	
	public static Drawable getColoredDrawable(Context context, int id, int color) {
		return getColoredDrawable(getDrawable(context, id), color);
	}
	
	public static Drawable getColoredDrawable(Drawable raw, int color) {
		if (raw != null) {
			DrawableCompat.setTint(raw, color);
		}
		return raw;
	}
	
	
	
	
	
	
	
//	public static Drawable newSelector(Drawable raw, int colorNormal, int colorPressed) {
//		Drawable selector = null;
//		if (raw != null) {
//			selector = new PressedEffectStateListDrawable(raw, colorNormal, colorPressed);
//		}
//		return selector;
//	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Drawable getDrawable(Context context, int id) {
		Drawable drawable;
		if (Build.VERSION.SDK_INT > 20) {//Build.VERSION_CODES.KITKAT_WATCH=20
			drawable = context.getResources().getDrawable(id);
		} else {
			drawable = context.getResources().getDrawable(id);
		}
		return drawable;
	}
	
}

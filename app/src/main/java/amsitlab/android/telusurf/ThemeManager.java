package amsitlab.android.telusurf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public final class ThemeManager {
    public static final String PREFS = "app_prefs";
    public static final String KEY_THEME_MODE = "theme_mode";

    public static final String SYSTEM = "system";
    public static final String DARK = "dark";
    public static final String LIGHT = "light";

    private ThemeManager() {}

    public static void applySavedTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String mode = sp.getString(KEY_THEME_MODE, SYSTEM);
        AppCompatDelegate.setDefaultNightMode(toNightMode(mode));
    }

    public static String getSavedTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sp.getString(KEY_THEME_MODE, SYSTEM);
    }

    public static void saveTheme(Context context, String mode) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_THEME_MODE, mode)
                .apply();
        AppCompatDelegate.setDefaultNightMode(toNightMode(mode));
    }

    private static int toNightMode(String mode) {
        if (DARK.equals(mode)) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        }
        if (LIGHT.equals(mode)) {
            return AppCompatDelegate.MODE_NIGHT_NO;
        }
        return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
    }
}

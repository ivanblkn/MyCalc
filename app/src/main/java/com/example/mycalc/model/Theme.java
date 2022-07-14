package com.example.mycalc.model;

import android.annotation.SuppressLint;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.example.mycalc.R;

public enum Theme {
    SIMPLE(R.style.Theme_Simple, R.string.themeSimple, "themeSimple"),
    FRAME(R.style.Theme_Frame, R.string.themeFrame, "themeFrame"),
    SHADOW(R.style.Theme_Shadow, R.string.themeShadow, "themeShadow");

    public int getThemeRes() {
        return themeRes;
    }

    public int getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public static String getDefaultThemeKey() {
        return Theme.SIMPLE.getKey();
    }

    public static Theme getDefaultTheme() {
        return Theme.SIMPLE;
    }

    @StyleRes
    private final int themeRes;
    @SuppressLint("SupportAnnotationUsage")
    @StringRes
    private final int title;

    private final String key;

    Theme(int themeRes, int title, String key) {
        this.themeRes = themeRes;
        this.title = title;
        this.key = key;
    }
}

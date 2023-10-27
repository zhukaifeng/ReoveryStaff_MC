package com.dclee.recovery.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast(Context context, String toastContent) {
        Toast.makeText(context, toastContent, Toast.LENGTH_SHORT).show();
    }
}

package com.dclee.recovery.util;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class KeyboardUtil {
    public KeyboardUtil() {
    }

    public static void showKeyBoard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        inputMethodManager.showSoftInput(editText, 2);
        inputMethodManager.toggleSoftInput(2, 1);
    }

    public static void hideKeyBoard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 2);
        }

    }

    public static boolean isShownKeyBoard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view == null) {
            return false;
        } else {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
            return inputMethodManager.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
    }

    public static void setOnEnterClickListener(final Activity activity, final EditText editText, final OnKeyBoardEnterClickListener onKeyBoardEnterClickListener) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                //submit status???
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    onKeyBoardEnterClickListener.onKeyboardEnterClick(editText.getText().toString());
                }
                return false;
            }
        });
    }

    public interface OnKeyBoardEnterClickListener {
        void onKeyboardEnterClick(String content);
    }
}

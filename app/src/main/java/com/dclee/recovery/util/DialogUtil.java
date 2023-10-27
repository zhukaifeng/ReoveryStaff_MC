package com.dclee.recovery.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dclee.recovery.R;

public class DialogUtil {

    public static void showComfirmDialog(final Activity activity, String alertContent, final OnComfirmClickListener onComfirmClickListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("提示");
//        builder.setMessage(alertContent);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                onComfirmClickListener.onComfirmClick();
//            }
//        });
//
//        builder.setNegativeButton("取消", null);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.layout_comfirm_dialog, null, false);//引入弹窗布局
        final PopupWindow popupWindow = new PopupWindow(vPopupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        vPopupWindow.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        TextView content = vPopupWindow.findViewById(R.id.content);
        content.setText(alertContent);
        vPopupWindow.findViewById(R.id.comfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                onComfirmClickListener.onComfirmClick();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(activity, 1);
            }
        });
        //引入依附的布局
        View parentView = activity.getWindow().getDecorView();
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(activity, 0.5f);
    }

    private static void setBackgroundAlpha(Activity activity, float bgAlpha) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public interface OnComfirmClickListener {
        void onComfirmClick();
    }
}

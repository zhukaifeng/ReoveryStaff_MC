package com.dclee.recovery.wedget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dclee.recovery.R;


/**
 * 作者: GIndoc
 * 日期: 2017/2/14 15:53
 * 作用:
 */

public class TitleBar extends FrameLayout {
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvRight;
    private OnRightClickListener onRightClickListener;

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);


        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        tvRight.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_rightTextVisibility, false) ? View.VISIBLE : View.GONE);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });

        CharSequence titleText = typedArray.getText(R.styleable.TitleBar_titleText);
        tvTitle.setText(titleText);
        tvRight.setText(typedArray.getText(R.styleable.TitleBar_rightText));
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRightClickListener != null) {
                    onRightClickListener.onRightClick();
                }
            }
        });
    }

    public interface OnRightClickListener {
        void onRightClick();
    }

    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }
}

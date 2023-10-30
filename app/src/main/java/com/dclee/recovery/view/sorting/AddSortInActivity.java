package com.dclee.recovery.view.sorting;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;


public class AddSortInActivity extends BaseActivity {

    private TextView tv_back;
    private TextView tv_add_continue;
    private TextView tv_add;
    private TextView tv_netweight;
    private EditText edt_weight;
    private Spinner spinner_type;
    private Spinner spinner_operate;
    private TextView tv_weight;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sortin;
    }

    @Override
    public void initView() {
        tv_back = findViewById(R.id.tv_back);
        tv_add_continue = findViewById(R.id.tv_add_continue);
        tv_add = findViewById(R.id.tv_add);
        tv_netweight = findViewById(R.id.tv_netweight);
        edt_weight = findViewById(R.id.edt_weight);
        spinner_type = findViewById(R.id.spinner_type);
        spinner_operate = findViewById(R.id.spinner_operate);
        tv_weight = findViewById(R.id.tv_weight);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}

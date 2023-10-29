package com.dclee.recovery.view.sorting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.create_order.UploadImageAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

public class AddSortingReqActivity extends BaseActivity {

    private RecyclerView selectedImgRecycler;
    private TypeImageAdapter typeImageAdapter;
    private RequestUtil requestUtil;
    private EditText edt_order_id;
    private EditText edt_count;
    private EditText edt_remark;
    private Spinner spinner_department;
    private Spinner spinner_category;
    private Spinner spinner_product;
    private Spinner spinner_position;
    private Spinner spinner_inventory;
    private Spinner spinner_type;
    private CheckBox cb_yes;
    private CheckBox cb_no;
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sorting_req;
    }

    @Override
    public void initView() {
        edt_order_id = findViewById(R.id.edt_order_id);
        edt_count = findViewById(R.id.edt_count);
        edt_remark = findViewById(R.id.edt_remark);
        cb_yes = findViewById(R.id.cb_yes);
        cb_no = findViewById(R.id.cb_no);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_category = findViewById(R.id.spinner_category);
        spinner_product = findViewById(R.id.spinner_product);
        spinner_position = findViewById(R.id.spinner_position);
        spinner_inventory = findViewById(R.id.spinner_inventory);
        spinner_type = findViewById(R.id.spinner_type);


        requestUtil = new RequestUtil(this);
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, requestUtil);
       // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }


        typeImageAdapter.onActivityResult(requestCode, resultCode, data);
    }

}

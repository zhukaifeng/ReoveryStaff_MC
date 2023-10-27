package com.dclee.recovery.view;

import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dclee.recovery.R;
import com.dclee.recovery.adapter.SelectedProdAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.pojo.Product;
import com.dclee.recovery.pojo.RecyclableProduct;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.wedget.TitleBar;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectOrderProdActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private SelectedProdAdapter adapter;
    private RequestUtil requestUtil;
    private TitleBar title;
    private int currentLevel;
    private OrderProd[] types = {new OrderProd().setSimpleDatas("可回收垃圾")
            , new OrderProd().setSimpleDatas("大件垃圾")};

    private List<OrderProd> recycleableProds;
    private List<OrderProd> bigProds;

    private void initProds() {
        if (recycleableProds == null) {
            recycleableProds = new ArrayList<>();
            queryProds(0);
        }
        if (bigProds == null) {
            bigProds = new ArrayList<>();
            queryProds(1);
        }
    }

    private void queryProds(int type) {
        RequestParams requestParams = new RequestParams();
        if (type != 1) {
            requestUtil.postList("s1/products", requestParams, RecyclableProduct.class, new RequestUtil.OnRequestFinishListener<List<RecyclableProduct>>() {
                @Override
                public void onRequestSuccess(List<RecyclableProduct> result) {
                    for (RecyclableProduct item :
                            result) {
                        for (Product prod :
                                item.getItem()) {
                            prod.setType(type);
                            recycleableProds.add(revertProduct(prod));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onRequestFail(int errorCode, String desc) {

                }
            });
        } else {
            requestParams.addParameter("type", 1);
            requestUtil.postList("s1/products", requestParams, Product.class, new RequestUtil.OnRequestFinishListener<List<Product>>() {
                @Override
                public void onRequestSuccess(List<Product> result) {
                    for (Product prod :
                            result) {
                        prod.setType(type);
                        bigProds.add(revertProduct(prod));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onRequestFail(int errorCode, String desc) {

                }
            });
        }
    }

    private OrderProd revertProduct(Product prod) {
        OrderProd orderProd = new OrderProd();
        orderProd.setId(prod.getId());
        orderProd.setProduct_name(prod.getName());
        orderProd.setMax_price(Double.parseDouble(prod.getMax()));
        orderProd.setMin_price(Double.parseDouble(prod.getMin()));
        orderProd.setProduct_id(prod.getId());
        orderProd.setType(prod.getType());
        return orderProd;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_orderprod;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.title);
        title.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                List<OrderProd> prods = new ArrayList<>();
                for (int i = 0; i < recycleableProds.size(); i++) {
                    if (recycleableProds.get(i).isSelected()) {
                        recycleableProds.get(i).setSelected(false);
                        prods.add(recycleableProds.get(i));
                    }
                }
                for (int i = 0; i < bigProds.size(); i++) {
                    if (bigProds.get(i).isSelected()) {
                        bigProds.get(i).setSelected(false);
                        prods.add(bigProds.get(i));
                    }
                }
                if (prods.size() == 0) {
                    ToastUtil.showToast(getActivity(), "请选择商品");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("datas", JSON.toJSONString(prods));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        requestUtil = new RequestUtil(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectedProdAdapter(this);
        adapter.setDatas(types);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (currentLevel == 0) {
                    if (position == 0) {
                        adapter.setDatas(recycleableProds);
                    } else {
                        adapter.setDatas(bigProds);
                    }
                    currentLevel = 1;
                } else {
                    Log.e("xzw",adapter.getDatas().size()+"");
                    for (int i = 0; i < adapter.getDatas().size(); i++) {
                        adapter.getDatas().get(i).setSelected(false);
                    }
                    adapter.getDatas().get(position).setSelected(true);
                    adapter.notifyDataSetChanged();
                    boolean hasSelected = false;
                    for (int i = 0; i < adapter.getDatas().size(); i++) {
                        if (adapter.getDatas().get(i).isSelected()) {
                            hasSelected = true;
                            break;
                        }
                    }
                    if (adapter.getDatas() == recycleableProds) {
                        types[0].setSelected(hasSelected);
                    } else {
                        types[1].setSelected(hasSelected);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (currentLevel > 0) {
            currentLevel = 0;
            adapter.setDatas(types);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initData() {
        initProds();
    }
}

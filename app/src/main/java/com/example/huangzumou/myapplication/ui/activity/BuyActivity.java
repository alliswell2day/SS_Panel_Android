package com.example.huangzumou.myapplication.ui.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.model.bean.GoodsBean;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.presenter.BuyPresenter;
import com.example.huangzumou.myapplication.presenter.contract.BuyContract;
import com.example.huangzumou.myapplication.base.BaseActivity;
import com.example.huangzumou.myapplication.ui.adapter.GoodsAdapter;
import com.example.huangzumou.myapplication.util.LogUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huangzumou on 2017/4/29.
 */

public class BuyActivity extends BaseActivity<BuyPresenter> implements BuyContract.View{

    @BindView(R.id.refresh)
    public SwipeRefreshLayout refresh;
    @BindView(R.id.item_content)
    public RecyclerView item_content;
    @BindView(R.id.buy)
    public FloatingActionButton buy;

    private ProgressDialog dialog;

    GoodsAdapter goodsAdapter;
    List<GoodsBean> mList = new ArrayList<>();
    public static BuyActivity instance = null;

    @Override
    public void showError(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_buy;
    }

    @Override
    protected void initEventAndData() {

        if(LoginActivity.instance != null){
            LoginActivity.instance.finish();
        }
        instance = this;
        //mPresenter.initBP();
        mPresenter.getGoodsInfo();
        goodsAdapter = new GoodsAdapter(mContext,mList);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGoodsInfo();
            }
        });
        item_content.setLayoutManager(new LinearLayoutManager(mContext));
        item_content.setAdapter(goodsAdapter);


    }

    @Override
    public void updateNodeInfo(List<GoodsBean> mList) {
        if(refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }

        item_content.setVisibility(View.VISIBLE);
        this.mList.clear();
        this.mList.addAll(mList);
        goodsAdapter.notifyDataSetChanged();
    }

    @Override
    public GoodsBean getSelectedNode() {
        int child_count = item_content.getChildCount();

        for(int i = 0 ;i< child_count;i++){
            GoodsAdapter.ViewHolder viewHolder = (GoodsAdapter.ViewHolder)item_content.getChildViewHolder(item_content.getChildAt(i));
            if(viewHolder.selected.isChecked()){
                return mList.get(i);
            }
        }

        return null;
    }

    @Override
    public void showDialog(String msg) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(mContext);
                dialog.setCancelable(true);
            }
            dialog.setMessage(msg);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    @Override
    public void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    @Override
    public String getName() {
        GoodsBean goodsBean = this.getSelectedNode();
        if(goodsBean != null){
            return goodsBean.getName();
        }
        return "";
    }

    @Override
    public double getPrice() {
        GoodsBean goodsBean = this.getSelectedNode();
        if(goodsBean != null){
            return Double.valueOf(goodsBean.getPrice());
        }
        return 0;
    }

    @Override
    public String getBody() {
        return "";
    }

    @Override
    public String getShopID() {
        GoodsBean goodsBean = this.getSelectedNode();
        if(goodsBean != null){
            return goodsBean.getId();
        }
        return "";
    }

    @OnClick(R.id.buy)
    public void Buy(){
        //mPresenter.initBP();
        mPresenter.pay(true);
    }

    @Override
    public void startPayActivity() {
        try {
            Intent intent = new Intent("android.intent.action.PAY");
            intent.addCategory("android.intent.category.PAY");
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buySucceed(String msg) {
        this.showError(msg);
        this.onBackPressedSupport();
    }

    @Override
    public void returnToLogin() {
        //LogUtil.i(App.getAppComponent().getContext().getUser().toString());
        mPresenter.deleteUser();
        //LogUtil.i(App.getAppComponent().getContext().getUser().toString());
        if(LoginActivity.instance != null){
            LoginActivity.instance.finish();
        }
        if(InfoActivity.instance != null){
            InfoActivity.instance.finish();
        }
        this.showError("Token即将过期，请重新登录");
        this.finish();
        Intent intent = new Intent(mContext,LoginActivity.class);
        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();
        if(realmUserBean != null){
            intent.putExtra("email", App.getAppComponent().getContext().getUser().getEmail());
        }else {
            intent.putExtra("email", "");
        }

        startActivity(intent);
    }

    @Override
    public boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(mContext,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        LogUtil.i("BuyActivity Destory");
        super.onDestroy();
    }
}

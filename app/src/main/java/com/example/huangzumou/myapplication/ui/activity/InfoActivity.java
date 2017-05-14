package com.example.huangzumou.myapplication.ui.activity;


import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.BaseActivity;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.model.bean.UserInfoBean;
import com.example.huangzumou.myapplication.presenter.InfoPresenter;
import com.example.huangzumou.myapplication.presenter.contract.InfoContract;
import com.example.huangzumou.myapplication.socks.core.ProxyConfig;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.huangzumou.myapplication.socks.core.LocalVpnService;
import com.example.huangzumou.myapplication.ui.adapter.NodeAdapter;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnCheckedChanged;



/**
 * Created by huangzumou on 2017/4/22.
 */

public class InfoActivity extends BaseActivity<InfoPresenter> implements InfoContract.View{


    @BindView(R.id.refresh)
    public SwipeRefreshLayout refresh;
    @BindView(R.id.info_content)
    public RecyclerView info_content;
    @BindView(R.id.connect)
    public FloatingActionButton connect;
    @BindView(R.id.name)
    public AppCompatTextView name;
    @BindView(R.id.data)
    public AppCompatTextView data;
    @BindView(R.id.expire_date)
    public AppCompatTextView expire_date;
    @BindView(R.id.recharge)
    public AppCompatButton recharge;
    @BindView(R.id.logout)
    public AppCompatButton logout;
    @BindView(R.id.global)
    public AppCompatCheckBox global;

    private AlertDialog.Builder builder;


    public static InfoActivity instance = null;

    NodeAdapter nodeAdapter;
    List<NodeBean> mList = new ArrayList<>();
    private static final int START_VPN_SERVICE_REQUEST_CODE = 1985;

    @Override
    public void showError(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserInfo(UserInfoBean userInfoBean) {

        if(userInfoBean != null){

            DecimalFormat df = new DecimalFormat("0.00");

            double tran_enable = (double)userInfoBean.getTransfer_enable()/(1024*1024*1024);
            double used = ((double)userInfoBean.getU() + (double)userInfoBean.getD())/(1024*1024*1024);
            //LogUtil.i(String.valueOf(used));

            String used_data = df.format(used);
            String tot_data;
            if(tran_enable > 998){
                tot_data = "未限制流量";
            }else {
                tot_data = df.format(tran_enable) + "G";
            }

            String user_name = userInfoBean.getUser_name();
            String user_data = "已使用 : " + used_data + "G / " + tot_data ;
            String user_expire_date = "到期日 : " + userInfoBean.getExpire_in().substring(0,10);

            this.name.setText(user_name);
            this.data.setText(user_data);
            this.expire_date.setText(user_expire_date);

        }

        if(refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }

    }

    @Override
    public void updateNodeInfo(List<NodeBean> mList) {

        //LogUtil.i(mList.get(0).getRemarks());
        if(refresh.isRefreshing()){
            refresh.setRefreshing(false);
        }

        info_content.setVisibility(View.VISIBLE);
        this.mList.clear();
        this.mList.addAll(mList);
        nodeAdapter.notifyDataSetChanged();


    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info;
    }

    @Override
    protected void initEventAndData() {
        instance = this;

        if(LoginActivity.instance != null){
            LoginActivity.instance.finish();
        }

        mPresenter.getUserInfo();
        mPresenter.getNodeInfo();
        nodeAdapter = new NodeAdapter(mContext,mList);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getUserInfo();
                mPresenter.getNodeInfo();
            }
        });

        info_content.setLayoutManager(new LinearLayoutManager(mContext));
        info_content.setAdapter(nodeAdapter);
        LocalVpnService.addOnStatusChangedListener(mPresenter);
        connect.setSelected(App.getAppComponent().getContext().isRunning());
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定退出及切换用户");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 无动作
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jumpToLogin();
            }
        });

    }


    @Override
    public NodeBean getSelectedNode() {

        int child_count = info_content.getChildCount();

        for(int i = 0 ;i< child_count;i++){
            NodeAdapter.ViewHolder viewHolder = (NodeAdapter.ViewHolder)info_content.getChildViewHolder(info_content.getChildAt(i));
            if(viewHolder.selected.isChecked()){
                return mList.get(i);
            }
        }

        return null;

    }

    private String getProxyUrl(){
        NodeBean nodeBean = this.getSelectedNode();
        if(nodeBean != null){
            String method = nodeBean.getMethod();
            String password = nodeBean.getPassword();
            String server = nodeBean.getServer();
            int server_port = nodeBean.getServer_port();
            String proxy_url = method + ":" + password + "@" + server + ":" + String.valueOf(server_port);
            String base64String = "";
            try {
                base64String = new String(Base64.encode(proxy_url.getBytes("ASCII"), Base64.DEFAULT));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //LogUtil.i(base64String);
            return "ss://" + base64String;
        }else {
            showError("未找到相关连接信息");
            return null;
        }

    }


    @OnClick(R.id.connect)
    public void clickConnect(){
        //LogUtil.i(String.valueOf(this.getSelectedIndex()));
        if(this.getSelectedNode() != null && !this.getSelectedNode().is_online() && !connect.isSelected()){
            this.showError("此节点不在线，可能连接后无法上网");
        }
        if(LocalVpnService.IsRunning == connect.isSelected()){
            if(connect.isSelected()){
                this.disconnect();
            }else{
                this.connect();
            }
        }else {
            LocalVpnService.IsRunning = false;
        }

    }

    @OnCheckedChanged(R.id.global)
    public void clickGlobal(boolean global){

        ProxyConfig.Instance.globalMode = !ProxyConfig.Instance.globalMode;
        if (ProxyConfig.Instance.globalMode) {
            this.showError("全局模式打开");
        } else {
            this.showError("全局模式关闭");
        }

        //this.showError(String.valueOf(global));
    }

    @OnClick(R.id.logout)
    public void logout(){
        //mPresenter.logout();
        this.builder.create().show();
    }




    @OnClick(R.id.recharge)
    public void recharge(){
        this.buyGoods();
    }

    @Override
    public void connect() {

        Intent intent = LocalVpnService.prepare(this);
        if (intent == null) {
            //LogUtil.i("intent -> null");
            String proxyUrl = this.getProxyUrl();

            if(proxyUrl != null){
                LocalVpnService.ProxyUrl =  this.getProxyUrl();
                startService(new Intent(this,LocalVpnService.class));
            }else {
                this.showError("请重新刷新列表");
            }
        } else {
            //LogUtil.i("intent -> not null");
            startActivityForResult(intent, START_VPN_SERVICE_REQUEST_CODE);
        }
    }

    @Override
    public void disconnect() {
        LocalVpnService.IsRunning = false;
    }

    @Override
    public void setConnectButtonState(boolean isRunning) {
        //LogUtil.i("isRunning : "+ isRunning);
        //LogUtil.i(connect.toString());
        connect.setSelected(isRunning);
        App.getAppComponent().getContext().setRunning(isRunning);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == START_VPN_SERVICE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                this.connect();
            } else {
                this.setConnectButtonState(false);
            }
            return;
        }
/*
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String ProxyUrl = scanResult.getContents();
            if (isValidUrl(ProxyUrl)) {
                setProxyUrl(ProxyUrl);
                textViewProxyUrl.setText(ProxyUrl);
            } else {
                Toast.makeText(mContext, R.string.err_invalid_url, Toast.LENGTH_SHORT).show();
            }
            return;
        }
*/
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void returnToLogin() {
        mPresenter.deleteUser();
        this.showError("Token即将过期，请重新登录");
        this.finish();
        Intent intent = new Intent(mContext,LoginActivity.class);
        RealmUserBean realmUserBean = App.getAppComponent().getContext().getUser();
        if(realmUserBean != null){
            intent.putExtra("email",App.getAppComponent().getContext().getUser().getEmail());
        }else{
            intent.putExtra("email","");
        }

        startActivity(intent);
    }

    @Override
    public void jumpToLogin() {
        mPresenter.deleteUser();
        this.finish();
        Intent intent = new Intent(mContext,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void buyGoods() {
        Intent intent = new Intent(mContext,BuyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressedSupport() {
        //super.onBackPressedSupport();

        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);

    }

    @Override
    protected void onDestroy() {
        LogUtil.i("InfoActivity Destory");
        super.onDestroy();
    }

}

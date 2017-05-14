package com.example.huangzumou.myapplication.ui.activity;


/**
 * Created by huangzumou on 2017/4/22.
 */


import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.BaseActivity;
import com.example.huangzumou.myapplication.model.bean.RealmUserBean;
import com.example.huangzumou.myapplication.presenter.LoginPresenter;
import com.example.huangzumou.myapplication.presenter.contract.InfoContract;
import com.example.huangzumou.myapplication.presenter.contract.LoginContract;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.ToastUtil;
import android.content.Intent;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.avatar)
    public AppCompatImageView avatar;
    @BindView(R.id.email)
    public AppCompatEditText email;
    @BindView(R.id.password)
    public AppCompatEditText password;
    @BindView(R.id.login)
    public AppCompatButton login;
    @BindView(R.id.register)
    public AppCompatTextView register;
    public static LoginActivity instance = null;

    //private String email_input = "";
    //private String passwd_input = "";


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        instance = this;
        //setContentView(R.layout.activity_login);
        //ButterKnife.bind(this);
        register.setClickable(true);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.getPaint().setAntiAlias(true);
        register.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,RegisterActivity.class);
                startActivity(intent);
            }
        });

        RealmUserBean realmUserBean = mPresenter.checkLogin();
        if(realmUserBean != null){
            Toast.makeText(mContext,realmUserBean.getEmail()+"已经登录",Toast.LENGTH_SHORT).show();
            this.jumpToInfo();
        }else {
            Toast.makeText(mContext,"未登录",Toast.LENGTH_SHORT).show();
        }
        if(this.getIntent().getStringExtra("passwd") != null){
            mPresenter.login(this.getIntent().getStringExtra("email"),
                    this.getIntent().getStringExtra("passwd"));
        }else {
            if(this.getIntent().getStringExtra("email") != null){
                this.email.setText(this.getIntent().getStringExtra("email"));
            }

        }
    }

    @Override
    public void jumpToInfo() {
        this.finish();
        Intent intent=new Intent(mContext,InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    /*
    @Override
    public void showToken(String token) {
        //LogUtil.i(token);
        //ToastUtil.show(token);
        Toast.makeText(mContext,token,Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public void returnToLogin() {

    }

    @OnClick(R.id.login)
    public void clickLogin(){
        String email_input = email.getText().toString().trim();
        String passwd_input = password.getText().toString().trim();
        //Log.i("Login_activity_email",email_input);
        //Log.i("Login_activity_pass",passwd_input);
        if(email_input.isEmpty() || passwd_input.isEmpty() ){
            this.showError("请输入电子邮箱及密码");
        }else{
            mPresenter.login(email_input,passwd_input);
        }

    }

    @Override
    protected void onDestroy() {
        LogUtil.i("LoginActivity Destory");
        super.onDestroy();
    }
}

package com.example.huangzumou.myapplication.ui.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.example.huangzumou.myapplication.base.BaseActivity;
import com.example.huangzumou.myapplication.presenter.RegisterPresneter;
import com.example.huangzumou.myapplication.presenter.contract.RegisterContract;
import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huangzumou on 2017/4/22.
 */

public class RegisterActivity extends BaseActivity<RegisterPresneter> implements RegisterContract.View {

    @BindView(R.id.name)
    public AppCompatEditText name;
    @BindView(R.id.email)
    public AppCompatEditText email;
    @BindView(R.id.passwd)
    public AppCompatEditText passwd;
    @BindView(R.id.repasswd)
    public AppCompatEditText repasswd;
    @BindView(R.id.register)
    public AppCompatButton register;
    public static RegisterActivity instance = null;

    @Override
    public void showError(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(int ret, String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void returnToLogin() {
        this.finish();
        Intent intent = new Intent(mContext,LoginActivity.class);
        intent.putExtra("email",this.email.getText().toString().trim());
        intent.putExtra("passwd",this.passwd.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        instance = this;
    }

    @OnClick(R.id.register)
    public void clickRegister(){

        String name = this.name.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String passwd = this.passwd.getText().toString().trim();
        String repasswd = this.repasswd.getText().toString().trim();

        if(name.isEmpty() || email.isEmpty() || passwd.isEmpty() || repasswd.isEmpty()){
            this.showError("以上均为必输项");
        }else{
            if(passwd.contentEquals(repasswd)){
                mPresenter.register(name,email,passwd,repasswd);
            }else{
                this.showError("确认密码不一致,请重新输入");
            }

        }

    }

    @Override
    protected void onDestroy() {
        LogUtil.i("RegisterActivity Destory");
        super.onDestroy();
    }
}

package com.example.huangzumou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;


import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.app.App;
import com.example.huangzumou.myapplication.base.BaseAdapter;
import com.example.huangzumou.myapplication.model.bean.GoodsBean;
import com.example.huangzumou.myapplication.util.DateUtil;
import com.example.huangzumou.myapplication.util.LogUtil;

import java.util.List;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by huangzumou on 2017/4/29.
 */

public class GoodsAdapter extends BaseAdapter<GoodsBean> {

    public GoodsAdapter(Context mContext, List<GoodsBean> mList) {
        super(mContext, mList);
    }

    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoodsAdapter.ViewHolder(getInflater().inflate(R.layout.item_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {

        GoodsAdapter.ViewHolder gAViewHolder = (GoodsAdapter.ViewHolder)holder;
        gAViewHolder.node_name.setText(getmList().get(position).getName().trim());
        gAViewHolder.price.setText(getmList().get(position).getPrice()+"元");
        Date expire_time = DateUtil.String2Date(DateUtil.FORMAT_1,
                App.getAppComponent().getContext().getUserInfo().getExpire_in());
        String new_expire = DateUtil.Date2String(DateUtil.FORMAT_2,
                DateUtil.dateCal(expire_time,
                        Integer.valueOf(getmList().get(position).getExpire())));
        gAViewHolder.expire_date.setText("延长到期日至 "+ new_expire);

        super.onBindViewHolder(holder,position);

    }

    public static class ViewHolder extends BaseAdapter.ViewHolder {

        @BindView(R.id.expire_date)
        public AppCompatTextView expire_date;
        @BindView(R.id.price)
        public AppCompatTextView price;
        public ViewHolder(View itemView) {
            super(itemView);
        }

    }
}

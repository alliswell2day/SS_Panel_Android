package com.example.huangzumou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.graphics.Color;


import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.model.bean.NodeBean;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.util.StringUtil;
import com.example.huangzumou.myapplication.base.BaseAdapter;

import android.support.v7.widget.CardView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * Created by huangzumou on 2017/4/23.
 */

public class NodeAdapter extends BaseAdapter<NodeBean> {



    public NodeAdapter(Context mContext, List<NodeBean> mList) {
        super(mContext, mList);
    }

    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NodeAdapter.ViewHolder(getInflater().inflate(R.layout.item_node, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseAdapter.ViewHolder holder, final int position) {

        NodeAdapter.ViewHolder nAViewHolder = (NodeAdapter.ViewHolder)holder;
        nAViewHolder.node_name.setText(getmList().get(position).getRemarks());
        nAViewHolder.user_count.setText(String.valueOf(getmList().get(position).getUser_count())+"人在线");
        // 速度信息
        if(getmList().get(position).is_online()){
            holder.card.setBackgroundColor(Color.parseColor("#a8dbd4"));
        }

        if(getmList().get(position).getSpeed_test() != null ){
            //(tele)
            nAViewHolder.speed_test.setVisibility(View.VISIBLE);
            nAViewHolder.telecomping.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getTelecomping()));
            nAViewHolder.telecomeupload.setText(StringUtil.getDoubleString(getmList()
                    .get(position).getSpeed_test().getTelecomeupload()));
            nAViewHolder.telecomedownload.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getTelecomedownload()));
            //(cmcc)
            nAViewHolder.cmccping.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getCmccping()));
            nAViewHolder.cmccupload.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getCmccupload()));
            nAViewHolder.cmccdownload.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getCmccdownload()));
            //(unicom)
            nAViewHolder.unicomping.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getUnicomping()));
            nAViewHolder.unicomupload.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getUnicomupload()));
            nAViewHolder.unicomdownload.setText(StringUtil.getDoubleString(getmList().get(position).getSpeed_test().getUnicomdownload()));
        }else {
            nAViewHolder.speed_test.setVisibility(View.GONE);
        }

        super.onBindViewHolder(holder,position);


    }



    public static class ViewHolder extends BaseAdapter.ViewHolder {


        @BindView(R.id.user_count)
        public AppCompatTextView user_count;
        @BindView(R.id.telecomping)
        public AppCompatTextView telecomping;
        @BindView(R.id.telecomeupload)
        public AppCompatTextView telecomeupload;
        @BindView(R.id.telecomedownload)
        public AppCompatTextView telecomedownload;
        @BindView(R.id.unicomping)
        public AppCompatTextView unicomping;
        @BindView(R.id.unicomupload)
        public AppCompatTextView unicomupload;
        @BindView(R.id.unicomdownload)
        public AppCompatTextView unicomdownload;
        @BindView(R.id.cmccping)
        public AppCompatTextView cmccping;
        @BindView(R.id.cmccupload)
        public AppCompatTextView cmccupload;
        @BindView(R.id.cmccdownload)
        public AppCompatTextView cmccdownload;
        @BindView(R.id.speed_test)
        public LinearLayout speed_test;

        public ViewHolder(View itemView) {
            super(itemView);

        }

    }
}

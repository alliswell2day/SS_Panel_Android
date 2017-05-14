package com.example.huangzumou.myapplication.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v7.widget.AppCompatRadioButton;
import android.content.Context;
import android.widget.CompoundButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import com.example.huangzumou.myapplication.R;
import com.example.huangzumou.myapplication.ui.adapter.GoodsAdapter;

/**
 * Created by huangzumou on 2017/4/29.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private List<T> mList;
    private LayoutInflater inflater;
    private Context mContext;

    private AppCompatRadioButton lastChoose = null;
    private int lastPos = 0;


    public BaseAdapter(Context mContext, List<T> mList) {
        inflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.mContext = mContext;
    }

    public List<T> getmList() {
        return mList;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.setSelectSingle(holder.selected,position);
    }

    private void setSelectSingle(final AppCompatRadioButton selected, final int position){

        if(position == 0){
            selected.setChecked(true);
            lastChoose = selected;
            lastPos = position;
        }else {
            selected.setChecked(false);
            lastPos = 0;
        }


        selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //holder.selected.setChecked(true);
                if(lastChoose != null&&lastPos!=position){
                    lastChoose.setChecked(false);
                    //Log.d("TAG","点击了"+position+1);
                }
                lastChoose = selected;
                lastPos = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.selected)
        public AppCompatRadioButton selected;
        @BindView(R.id.node_name)
        public AppCompatTextView node_name;
        @BindView(R.id.card)
        public CardView card;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!selected.isChecked()) {
                        selected.setChecked(true);
                    }
                }
            });

        }
    }


}

package com.brickman.app.adapter;

import android.content.Context;

import com.brickman.app.R;
import com.brickman.app.model.Bean.FlowerBean;
import com.brickman.app.ui.widget.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mayu on 16/7/20,下午2:36.
 */
public class FlowerListAdapter extends BaseQuickAdapter<FlowerBean> {
    private Context mCtx;
    public FlowerListAdapter(Context ctx, int layoutResId, List<FlowerBean> data) {
        super(layoutResId, data);
        this.mCtx = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, FlowerBean item) {
        Glide.with(mCtx).load(item.avator).centerCrop().crossFade().into((CircleImageView)helper.getView(R.id.avator));
        helper.setText(R.id.place, item.place);
        helper.setText(R.id.nickName, item.nickName);
        helper.setText(R.id.level, item.level);
        helper.setText(R.id.flowerNum, item.flowerNum);
    }
}
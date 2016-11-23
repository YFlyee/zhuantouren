package com.brickman.app.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.brickman.app.MApplication;
import com.brickman.app.R;
import com.brickman.app.common.base.BaseActivity;
import com.brickman.app.common.utils.DateUtil;
import com.brickman.app.model.Bean.BrickBean;
import com.brickman.app.module.brick.BrickDetailActivity;
import com.brickman.app.module.brick.PublishListActivity;
import com.brickman.app.module.widget.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by mayu on 16/7/20,下午2:36.
 */
public class BrickListAdapter extends BaseQuickAdapter<BrickBean> {
    private BaseActivity mCtx;
    private ImagesAdapter mImagesAdapter;
    public BrickListAdapter(BaseActivity ctx, int layoutResId, List<BrickBean> data) {
        super(layoutResId, data);
        this.mCtx = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, final BrickBean item) {
        if(mCtx instanceof PublishListActivity){
            Glide.with(mCtx).load(((PublishListActivity)mCtx).userHead)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into((CircleImageView)helper.getView(R.id.avator));
            helper.setText(R.id.name, TextUtils.isEmpty(((PublishListActivity)mCtx).userAliar) ? ((PublishListActivity)mCtx).userName :((PublishListActivity)mCtx).userAliar);

        } else {
            Glide.with(mCtx).load(item.users.userHead)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop().into((CircleImageView)helper.getView(R.id.avator));
            helper.setText(R.id.name, TextUtils.isEmpty(item.users.userName) ? item.users.userAlias : item.users.userName);
        }
        helper.setOnClickListener(R.id.avator, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mCtx instanceof PublishListActivity)) {

                    Intent intent = new Intent(mCtx, PublishListActivity.class);
                    intent.putExtra("title", item.users.userAlias + "的砖集");
                    intent.putExtra("userId", item.userId);
                    intent.putExtra("userName", item.users.userName);
                    intent.putExtra("userHeader", item.users.userHead);
                    intent.putExtra("desc", item.users.motto);
                    intent.putExtra("isfromdetail", true);
                    mCtx.startActivityWithAnim(intent);
                }
            }
        });
        helper.setOnClickListener(R.id.layout_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, BrickDetailActivity.class);
                intent.putExtra("item", item);
                mCtx.startActivityForResultWithAnim(intent, 1001);
            }
        });

        helper.setText(R.id.date, DateUtil.getMillon(item.createdTime));
        helper.setText(R.id.address, item.contentPlace);
        if (item.users!=null) {
            if (item.users.getUserSex()!=null) {
                helper.setImageResource(R.id.report, item.users.getUserSex().equals("男") ? R.mipmap.man : R.mipmap.woman);
            }
        }
        helper.setText(R.id.content, item.contentTitle);
        helper.setImageResource(R.id.iconComment, item.commentCount > 0 ? R.mipmap.bm_comment_sel : R.mipmap.bm_comment_nor);
        helper.setText(R.id.commentNum, item.commentCount + "");

        helper.setImageResource(R.id.iconFlower, item.contentFlowors > 0 ? R.mipmap.bm_flower_sel : R.mipmap.bm_flower_nor);
        helper.setText(R.id.flowerNum, item.contentFlowors + "");

        helper.setImageResource(R.id.iconBrick, item.contentBricks > 0 ? R.mipmap.bm_brick2 : R.mipmap.bm_brick4);
        helper.setText(R.id.brickNum, item.contentBricks + "");

        helper.setImageResource(R.id.iconShare, item.contentShares > 0 ? R.mipmap.bm_share_sel : R.mipmap.bm_share_nor);
        helper.setText(R.id.shareNum, item.contentShares + "");
//        helper.setVisible(R.id.layout_meature,false);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.imageList);
        linearLayout.setTag(helper.getLayoutPosition());
        mImagesAdapter = new ImagesAdapter(mCtx, linearLayout, false, item.brickContentAttachmentList);
        mImagesAdapter.init();
    }
}

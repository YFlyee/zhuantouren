package com.brickman.app.module.mine;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.brickman.app.R;
import com.brickman.app.adapter.BricksListAdapter;
import com.brickman.app.adapter.MessageListAdapter;
import com.brickman.app.common.base.BaseActivity;
import com.brickman.app.contract.MessageContract;
import com.brickman.app.model.MessageListModel;
import com.brickman.app.module.main.MainActivity;
import com.brickman.app.presenter.MessagePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MessageActivity extends BaseActivity <MessagePresenter,MessageListModel>implements MessageContract.View{

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.ptr)
    PtrClassicFrameLayout mPtr;
    MessageListAdapter mAdapter;
    List<String> mData=new ArrayList<>();
    private boolean hasMore = true;
    private int mPageNo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        mAdapter = new MessageListAdapter(this, R.layout.item_message, mData);
        View loadingView = this.getLayoutInflater().inflate(R.layout.loading_more_view, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setLoadingView(loadingView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mPageNo++;
                        if (!hasMore) {
                            mAdapter.notifyDataChangedAfterLoadMore(false);
                            View not_loadingview = getLayoutInflater().inflate(R.layout.loading_no_more_view, (ViewGroup) mRecyclerView.getParent(), false);
                            mAdapter.addFooterView(not_loadingview);
                            showToast("没有更多内容了");
                        } else {
                            mPresenter.loadMessagekList(mPageNo);
                        }
                    }
                });
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.GRAY)
                .sizeResId(R.dimen.dp_02)
                .marginResId(R.dimen.dp_00, R.dimen.dp_00)
                .build());
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPageNo=1;
              mPresenter.loadMessagekList(mPageNo);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }
        });
        mPtr.setLastUpdateTimeRelateObject(this);
        mPresenter.loadMessagekList(mPageNo);
        mAdapter.openLoadMore(0,false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void loadMessageSuccess(List<String> dataist, boolean hasMor) {
        this.hasMore=hasMor;
        if (mPageNo==1) {
            mPtr.refreshComplete();
            mData = dataist;
            mAdapter.setNewData(mData);
        }else {
            mAdapter.notifyDataChangedAfterLoadMore(dataist,true);
        }
    }

    @Override
    public void loadFailed() {
        mPtr.refreshComplete();
    }

    @Override
    public void showMsg(String msg) {
           showToast(msg);
    }
}

package com.cn.reading.ui.base;

import library.base.BaseAppCompatActivity;

/**
 * Project Name:com.cn.reading.ui.base
 * File Name: Reading
 * Date:15/8/28下午6:2208
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 */
public class BaseActivity extends BaseAppCompatActivity {
    /**
     * get bundle data
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(android.os.Bundle extras) {

    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    /**
     * when event comming
     *
     * @param eventCenter
     */
    @Override
    protected void onEventComming(library.eventbus.EventCenter eventCenter) {

    }

    /**
     * get loading target view
     */
    @Override
    protected android.view.View getLoadingTargetView() {
        return null;
    }

    /**
     * init all views and add events
     */
    @Override
    protected void initViewsAndEvents() {

    }

    /**
     * network connected
     *
     * @param type
     */
    @Override
    protected void onNetworkConnected(library.netstatus.NetUtils.NetType type) {

    }

    /**
     * network disconnected
     */
    @Override
    protected void onNetworkDisConnected() {

    }

    /**
     * is applyStatusBarTranslucency
     *
     * @return
     */
    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    /**
     * is bind eventBus
     *
     * @return
     */
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    /**
     * get the overridePendingTransition mode
     */
    @Override
    protected library.base.BaseAppCompatActivity.TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}

package com.github.microkibaco.bearlive;

import android.app.Application;
import android.content.Context;

import com.github.microkibaco.bearlive.editprofile.CustomProfile;
import com.github.microkibaco.bearlive.utils.QnUploadHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.TIMManager;
import com.tencent.TIMUserProfile;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 小熊直播
 */

public class BearApplication extends Application {
    private static BearApplication app;
    private static Context appContext;
    private static final int SDK_APP_ID = 1400089766;
    private static final int ACCOUNT_TYPE = 26415;
    private static final String QI_NIU_ACCESS_KEY = "fRdRAADM9NVW5JwvGpteEBcSGLuI9C4u_1mnJC0x";
    private static final String QI_NIU_SECRET_KEY = "YGzO0mElgA-9_45RTWvE81VLMiddxMAEW9WAohv5";
    private static final String QI_NIU_DOMAIN = "http://or4hblbwr.bkt.clouddn.com";
    private static final String QI_NIU_BUCKET_NAME = "yangzy";

    private ILVLiveConfig mLiveConfig;

    private TIMUserProfile mSelfProfile;

    public void setSelfProfile(TIMUserProfile selfProfile) {
        mSelfProfile = selfProfile;
    }

    public ILVLiveConfig getLiveConfig() {
        return mLiveConfig;
    }

    public TIMUserProfile getSelfProfile() {
        return mSelfProfile;
    }

    public static BearApplication getApplication() {
        return app;
    }

    public static Context getContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appContext = getApplicationContext();
        ILiveSDK.getInstance().initSdk(getApplicationContext(), SDK_APP_ID, ACCOUNT_TYPE);
        List<String> customInfos = new ArrayList<String>();
        customInfos.add(CustomProfile.CUSTOM_GET);
        customInfos.add(CustomProfile.CUSTOM_SEND);
        customInfos.add(CustomProfile.CUSTOM_LEVEL);
        customInfos.add(CustomProfile.CUSTOM_RENZHENG);
        TIMManager.getInstance().initFriendshipSettings(CustomProfile.allBaseInfo, customInfos);

        // 初始化直播场景
        mLiveConfig = new ILVLiveConfig();
        ILVLiveManager.getInstance().init(mLiveConfig);
        QnUploadHelper.init(QI_NIU_ACCESS_KEY,
                QI_NIU_SECRET_KEY,
                QI_NIU_DOMAIN,
                QI_NIU_BUCKET_NAME);
        LeakCanary.install(this);

    }
}

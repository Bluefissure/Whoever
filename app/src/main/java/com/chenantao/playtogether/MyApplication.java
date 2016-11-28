package com.chenantao.playtogether;

import android.app.Application;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.chenantao.playtogether.chat.handler.DefaultHandler;
import com.chenantao.playtogether.chat.handler.MyConversationEventHandler;
import com.chenantao.playtogether.injector.component.ApplicationComponent;
import com.chenantao.playtogether.injector.component.DaggerApplicationComponent;
import com.chenantao.playtogether.injector.modules.ApiModule;
import com.chenantao.playtogether.injector.modules.ApplicationModule;
import com.chenantao.playtogether.injector.modules.BllModule;
import com.chenantao.playtogether.mvc.model.bean.Invitation;
import com.chenantao.playtogether.mvc.model.bean.User;
import com.chenantao.playtogether.mvc.view.activity.user.LoginActivity;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Chenantao_gg on 2016/1/17.
 */
public class MyApplication extends Application {
    private ApplicationComponent mApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        setTheme(R.style.SplashTheme);
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        initLeanCloud();
        initChat();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule())
                .bllModule(new BllModule())
                .applicationModule(new ApplicationModule(this))
                .build();
        //leancloud所需要的参数
        initBean();
        initLeanCloud();
        //初始化log
        Logger.init("cat")
                .methodCount(2)
                .hideThreadInfo();
        initPicasso();

    }

    /**
     * 初始化聊天系统所必须的
     */
    private void initChat() {
        AVIMMessageManager.registerDefaultMessageHandler(new DefaultHandler(this));
        //开启推送
        AVInstallation.getCurrentInstallation().saveInBackground();
        // 设置通知默认打开的 Activity
        PushService.setDefaultPushCallback(this, LoginActivity.class);
        AVIMMessageManager.setConversationEventHandler(new MyConversationEventHandler(this));
        AVIMClient.setOfflineMessagePush(true);


    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, "Y16sd0KaVf7Lsw6aWoTFSGOg-gzGzoHsz",
                "ASphDiRHs5K7EPQCX2NadEdE");
//		AVOSCloud.setDebugLogEnabled(true);
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
//        built.setIndicatorsEnabled(true);
//        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    private void initBean() {
        AVUser.alwaysUseSubUserClass(User.class);
        AVObject.registerSubclass(Invitation.class);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}

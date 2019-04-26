package shlm.lmcs.com.lazycat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyShopAct.MainAct;


public class lazyCatLogAct extends LazyCatAct {
    private Handler handler;
    private WaitDialog.RefreshDialog refreshDialog;

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //refreshDialog.dismiss();
                //进入主界面
                LazyCatActStartActivity(MainAct.class, true);
                super.handleMessage(msg);
            }
        };
        setContentView(R.layout.activity_lazy_cat_log);
        /*设置导航栏透明*/
        setHideNav();
        //初始化稍等的表格  判断网络是否要更新文件
        WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
        wait_itme_dialogpage.setView(R.layout.item_wait);
        wait_itme_dialogpage.setImg(R.id.item_wait_img);
        //refreshDialog = WaitDialog.instanceRefreshDialog(lazyCatLogAct
        //        .this);
        //refreshDialog.Init(wait_itme_dialogpage);
        //refreshDialog.showRefreshDialog("", false);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(new Message());
            }
        }, 3000);
        setStatusBar("#f30d88");
    }
}

package shlm.lmcs.com.lazycat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyShopAct.MainAct;
import shlm.lmcs.com.lazycat.LazyShopAct.PromotionAct;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;


public class lazyCatLogAct extends LazyCatAct {
    private Handler handler;
    private final String MSG = "lazyCatLogAct.java[+]";
    private WaitDialog.RefreshDialog refreshDialog;
    private TextView log_title;
    private TextView log_propaganda;
    private TextView log_propagandaB;
    private TextView log_context;
    //private ImageView img;

    @SuppressLint({"HandlerLeak", "StaticFieldLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //refreshDialog.dismiss();
                //进入主界面
                if (true) {
                    LazyCatActStartActivity(PromotionAct.class, true);
                } else {
                    LazyCatActStartActivity(MainAct.class, true);

                }

                super.handleMessage(msg);
            }
        };
        setContentView(R.layout.activity_lazy_cat_log);

        /**
         * 测试代码区
         */
        LocalProgramTools.UserToolsInstance userToolsInstance  = LocalProgramTools.getUserToolsInstance();
        userToolsInstance.setToken("TOKEN15206036936");
        userToolsInstance.setVipstatus(LocalValues.VALUES_USERCENTER.IS_NOT_VIP);
        userToolsInstance.setStatus(LocalValues.VALUES_USERCENTER.ACCOUNT_IS_OK);
        userToolsInstance.setBlance("10000");
        userToolsInstance.setNiackName("资本家");
        userToolsInstance.setAccount("15206036936");
        userToolsInstance.SaveingUserPageXml();
        /*找到Ico控件*/
        log_title = findViewById(R.id.activity_lazy_log_title);/*控件图标*/
        log_context = findViewById(R.id.activity_lazy_log_context);/*控件的内容CangKu Service*/
        TextUnt.with(log_title).setTextColor("#08c299").setFontFile(getApplicationContext(),
                "canLogo");
        log_propaganda = findViewById(R.id.activity_lazy_cat_log_Propaganda);
        log_propagandaB = findViewById(R.id.activity_lazy_cat_log_Propaganda_b);
        TextUnt.with(log_propaganda).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_propagandaB).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_context).setFontFile(getApplicationContext(), "mvboli");
        /*ICO*/
//        img = findViewById(R.id.activity_lazy_log_image);
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


    }

}
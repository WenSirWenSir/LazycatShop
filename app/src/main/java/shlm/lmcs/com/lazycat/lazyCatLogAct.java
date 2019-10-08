package shlm.lmcs.com.lazycat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyShopAct.MainAct;
import shlm.lmcs.com.lazycat.LazyShopAct.PromotionAct;


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
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder();
        xmlInstance.initDom();
        xmlInstance.setXmlTree("action", "0");
        xmlInstance.setXmlTree("phone", "15206036936");
        xmlInstance.overDom();
        Log.e(MSG, "行动开始");
        Log.i(MSG, "xml数据信息" + xmlInstance.getXmlTree());
        Net.doPostXml(getApplicationContext(), "http://120.79.63.36/CK_SERVICE/Login/login.php",
                new ProgramInterface() {


            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {
                Log.i(MSG, "手机号码：" + data.trim());
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        }, xmlInstance.getXmlTree());

        /**
         * 测试代码区域结束
         */
        /*找到Ico控件*/
        log_title = findViewById(R.id.activity_lazy_log_title);/*控件图标*/
        log_context = findViewById(R.id.activity_lazy_log_context);/*控件的内容CangKu Service*/
        TextUnt.with(log_title).setTextColor("#000000").setFontFile(getApplicationContext(),
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
package shlm.lmcs.com.lazycat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.LOAD_IMAGEPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageCache;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ImageMonitor;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopAct.MainAct;
import shlm.lmcs.com.lazycat.LazyShopAct.PromotionAct;


public class lazyCatLogAct extends LazyCatAct {
    private Handler handler;
    private final String MSG = "lazyCatLogAct.java[+]";
    private WaitDialog.RefreshDialog refreshDialog;
    private TextView log_title;
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
        Log.e(MSG,"行动开始");
        Log.i(MSG,"xml数据信息" + xmlInstance.getXmlTree());
        Net.doPostXml(getApplicationContext(), "http://120.79.63.36/CK_SERVICE/Login/login.php", new ProgramInterface() {



            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {
                Log.i(MSG,"手机号码：" + data.trim());
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        },xmlInstance.getXmlTree());

        /**
         * 测试代码区域结束
         */
        /*找到Ico控件*/
        log_title = findViewById(R.id.activity_lazy_log_title);/*控件图标*/
        TextUnt.with(log_title).setTextColor("#ffffff").setBackground(Tools.CreateDrawable(1,
                "#f30d88", "#f30d88", 20));
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
        }, 2000);
        setStatusBar("#f30d88");
        /*尝试加载图片管理者*/
        ImageMonitor imageMonitor = new ImageMonitor(getApplicationContext());
        LOAD_IMAGEPAGE load_imagepage = new LOAD_IMAGEPAGE();
        load_imagepage.setSaveName("mylove.png");
        load_imagepage.setImg_url("mylove.png");
        load_imagepage.setDefaultFile(this.getFilesDir());
        load_imagepage.setLruchTag("9832745");
        imageMonitor.toGetBitmap(load_imagepage, new ProgramInterface.ImageMonitoron() {
            @Override
            public void onSucess(Bitmap bm, LOAD_IMAGEPAGE load_imagepage) {
                ImageMonitor.saveLocalBitmap(load_imagepage, bm);/*保存到本地*/
                ImageCache imageCache = new ImageCache(getApplicationContext());
                imageCache.saveImage(load_imagepage.getLruchTag(), bm);
                bm = null;


                final Bitmap bitmap = imageCache.getImage(load_imagepage.getLruchTag());
                //img.setImageBitmap(bitmap);
            }

            @Override
            public void onFaile(String msg) {
            }
        });


    }

}
package shlm.lmcs.com.lazycat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopAct.MainAct;
import shlm.lmcs.com.lazycat.LazyShopAct.PromotionAct;
import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemGuidepage;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.TerminalSystemMO.Record.SystemVs;

import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools.isPermission;


public class lazyCatLogAct extends LazyCatAct {
    private Handler handler;
    private final String MSG = "lazyCatLogAct.java[+]";
    private WaitDialog.RefreshDialog refreshDialog;
    private TextView log_title;
    private TextView log_propaganda;
    private TextView log_propagandaB;
    private TextView log_context;
    private String St_cityCode;/*城市编码*/
    //private ImageView img;

    /**
     * 测试定位
     */
    public AMapLocationClient mapLocationClient = null;
    public AMapLocationClientOption aMapLocationClientOption = null;
    private AlertDialog alertDialog = null;

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
                    /*判断是否已经引导过了 */
                    if (Tools.getGuidetoken(getApplicationContext()).equals("1")) {
                        //没有引导过
                        LazyCatActStartActivity(SystemGuidepage.class, true);
                    } else {
                        //引导过了  进入广告界面
                        LazyCatActStartActivity(PromotionAct.class, true);
                    }
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

        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(getApplicationContext());
        XmlBuilder.XmlInstance xmlInitPage = XmlBuilder.getXmlinstanceBuilder(true);
        Net.doPostXml(http_addrs.HTTP_ADDR_ABOUTTO_AS, new ProgramInterface() {
            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {

            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        }, "");


/*
        OS._saveOrder(lazyCatLogAct.this, "1", "onlyid", "122", new OS.onSaveorder() {
            @Override
            public void onSaveOk() {

            }

            @Override
            public void onSaveError() {

            }

            @Override
            public void onSaveNologin() {

            }
        });
*/
        /*保存系统的访问记录*/
        SystemVs._start(lazyCatLogAct.this);
        /*找到Ico控件*/
        log_title = findViewById(R.id.activity_lazy_log_title);/*控件图标*/
        log_context = findViewById(R.id.activity_lazy_log_context);/*控件的内容CangKu Service*/
        TextUnt.with(log_title).setTextColor("#08c299").setFontFile(getApplicationContext(), "canLogo");
        log_propaganda = findViewById(R.id.activity_lazy_cat_log_Propaganda);
        log_propagandaB = findViewById(R.id.activity_lazy_cat_log_Propaganda_b);
        TextUnt.with(log_propaganda).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_propagandaB).setFontFile(getApplicationContext(), "hyxjtj");
        TextUnt.with(log_context).setFontFile(getApplicationContext(), "mvboli");
        /*ICO*/
//        img = findViewById(R.id.activity_lazy_log_image);
        /*设置导航栏透明*/
        setHideNav();
        init();
        //refreshDialog = WaitDialog.instanceRefreshDialog(lazyCatLogAct
        //        .this);
        //refreshDialog.Init(wait_itme_dialogpage);
        //refreshDialog.showRefreshDialog("", false);


    }

    private void init() {
        LocalProgramTools.ProgramServiceTools serviceTools = LocalProgramTools.getServiceToolsInstatnce();
        serviceTools.set_Service("127.0.0.1");
        serviceTools.SaveService(getApplicationContext());
        /*判断三个主要权限是否获取*/
        if (isPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) && isPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) && isPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendMessage(new Message());
                }
            }, 1600);

            /**
             * 展示引导界面  引导用户了解云仓库的运营模式
             */

        } else {
            Tools.showConfirm(getApplicationContext(), "请求权限", "检测到您没有开启定位权限,请您开启定位权限用来获取您的店铺位置",
                    new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ActivityCompat.requestPermissions(lazyCatLogAct.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            });
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            Log.i(MSG, "地址权限获取状态中");
            if (Tools.isPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.i(MSG, "获取权限成功");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendMessage(new Message());
                    }
                }, 1600);
            } else {
                /*地址获取失败 直接告知 并且退出程序*/
                Tools.showError(getApplicationContext(), "错误信息", "没有获取到您的地址");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1) hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
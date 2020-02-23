package shlm.lmcs.com.lazycat.LazyShopAct;import android.animation.Animator;import android.annotation.SuppressLint;import android.app.FragmentTransaction;import android.content.Intent;import android.graphics.BitmapFactory;import android.graphics.Color;import android.net.Uri;import android.os.Build;import android.os.Bundle;import android.os.Environment;import android.support.graphics.drawable.VectorDrawableCompat;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.RelativeLayout;import android.widget.TextView;import com.itsnows.upgrade.UpgradeManager;import com.itsnows.upgrade.model.bean.UpgradeOptions;import java.io.File;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.FragmentLR;import shlm.lmcs.com.lazycat.LazyShopFrg.Classifyfrg;import shlm.lmcs.com.lazycat.LazyShopFrg.Recomdfrg;import shlm.lmcs.com.lazycat.LazyShopFrg.UserCenterfrg;import shlm.lmcs.com.lazycat.LazyShopFrg.Vipexclusivefrg;import shlm.lmcs.com.lazycat.LazyShopSystemUtils.SystemUpdate;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;/** * 主界面 */public class MainAct extends LazyCatAct {    private FragmentTransaction ft;    private RelativeLayout btn_main, btn_vip, btn_usercenter, btn_recomd;    private Classifyfrg classifyfrg;/*分类界面就是主界面*/    private UserCenterfrg usercneterfrg;/*用户中心界面*/    private Vipexclusivefrg vipexclusivefrg;/*vip的展示界面*/    private Recomdfrg recomfrg;/*促销的界面*/    private FragmentLR frameLayout;    private int lr_downX;/*滑动定点*/    private boolean is_main, is_cart, is_user;/*判断是哪个导航被点击*/    private int lr_countX;/*滑动距离*/    private String MSG = "MainAct.java[+]";    private final static int ICO_FRAGMENT_MAIN = 0;    private final static int ICO_FRAGMENT_VIP = 1;    private final static int ICO_FRAGMENT_RECOMD = 2;    private final static int ICO_FRAGMENT_ME = 3;    private int now_fragment;/*判断是哪一个FRAGMENT*/    private LocalValues.HTTP_ADDRS http_addrs;    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_main);        setStatusBar("#efefef");        http_addrs = new LocalValues.HTTP_ADDRS(getApplicationContext());        /**         * 更新APP         */        SystemUpdate._check(MainAct.this, new SystemUpdate.onCheck() {            @Override            public void onNeedupdate() {                Log.i(MSG, "需要更新");                String title = "";                //判断是否是SDK28  android 9.0之后的系统 如果是  就需要客户手动进行更新                if (Build.VERSION.SDK_INT >= 28) {                    title = "您的手机是Android 9.0(P),可能会由于手机系统原因导致更新失败。如果失败安装,您可以选择点击手动更新打开浏览器下载更新文件,手动安装即可更新哦。";                } else {                    title = "如果您使用程序自动更新失败,可以选择点击手动更新打开浏览器下载更新文件,手动安装即可更新哦";                    //弹出一个对话框 要求客户自己选择是手动打开浏览器下载更新还是                }                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MainAct.this);                sweetAlertDialog.setTitle("更新受限");                sweetAlertDialog.setContentText(title);                sweetAlertDialog.setConfirmText("手动更新");                sweetAlertDialog.setCancelText("自动更新");                sweetAlertDialog.show();                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {                    @Override                    public void onClick(SweetAlertDialog sweetAlertDialog) {                        Uri uri = Uri.parse("http://120.79.63.36/yuncangku.apk");                        Intent i = new Intent(Intent.ACTION_VIEW, uri);                        startActivity(i);                    }                });                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {                    @Override                    public void onClick(SweetAlertDialog sweetAlertDialog) {                        /*自动更新*/                        UpgradeManager manager = new UpgradeManager(MainAct.this);                        UpgradeOptions.Builder updateOptions = new UpgradeOptions.Builder();                        updateOptions.setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.program_ico));                        updateOptions.setTitle("云仓库更新提示");                        updateOptions.setDescription("更新通知栏");                        updateOptions.setStorage(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Com.upload.apk"));                        updateOptions.setMultithreadEnabled(true);                        updateOptions.setMultithreadPools(10);                        updateOptions.setMd5(null);                        updateOptions.setUrl(http_addrs.HTTP_ADDR_UPDATE_APK);                        manager.checkForUpdates(updateOptions.build(), false);                    }                });            }            @Override            public void onNotupdate() {                Log.i(MSG, "不更新");                init();            }        });        super.onCreate(savedInstanceState);    }    @SuppressLint({"ResourceType", "NewApi", "ClickableViewAccessibility"})    private void init() {        setBackStatic(true);        //尝试加载Fragment        ft = this.getFragmentManager().beginTransaction();        classifyfrg = new Classifyfrg();        frameLayout = findViewById(R.id.activity_main_Framelayout);/*管理控件*/        /*设置监听器*/        ft.add(R.id.activity_main_Framelayout, classifyfrg);        ft.commitAllowingStateLoss();        /*获取定位的DIALOG*/        //找寻对于的ID号码        btn_main = findViewById(R.id.activity_main_btn_IcoMain);//主界面        btn_vip = findViewById(R.id.activity_main_btnVip);//vip界面        btn_recomd = findViewById(R.id.activity_main_btnRecomd);//活动节目        btn_usercenter = findViewById(R.id.activity_main_btn_IcoUserCenter);//个人中心        /**         * 初始化  更新用户的信息  怕vip过期         */        SystemVip systemVip = new SystemVip(MainAct.this);        systemVip.Start(new SystemVip.OnVipcheck() {            @Override            public void onIsvip() {            }            @Override            public void onIsnovip() {            }            @Override            public void onIsnologin() {            }            @Override            public void onIslogin() {            }        });        /**         * 测试 设置MESSAGE的数量         */        TextUnt.with(this, R.id.activity_main_messageNumber).setVisibility(false);/*        findViewById(R.id.activity_main_messageNumber).setBackground(Tools.CreateDrawable(1,                "#08c299", "#08c299", 50));*/        /**         * 设置初始化的界面         */        ImageView InitImg = (ImageView) btn_main.getChildAt(0);        InitImg.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main, "#08c299"));        TextView InitTitle = (TextView) btn_main.getChildAt(1);        InitTitle.setTextColor(Color.parseColor("#08c299"));        now_fragment = ICO_FRAGMENT_MAIN;        btn_main.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                hideFragment();                showFragment(ICO_FRAGMENT_MAIN);                now_fragment = ICO_FRAGMENT_MAIN;                ClearallIcoBackground();                final RelativeLayout rl = (RelativeLayout) v;                rl.post(new Runnable() {                    @Override                    public void run() {                        Animator an = Tools.createRoundAnimation(rl, 600);                        an.start();                    }                });                /*修改导航*/                ImageView img = (ImageView) btn_main.getChildAt(0);                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main, "#08c299"));                TextView title = (TextView) rl.getChildAt(1);                title.setTextColor(Color.parseColor("#08c299"));                /*开始隐藏其它的导航*/                ImageView cart = (ImageView) btn_vip.getChildAt(0);                cart.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_vip, "#a9a9a9"));                TextView cart_title = (TextView) btn_vip.getChildAt(1);                cart_title.setTextColor(Color.parseColor("#a9a9a9"));                /*用户自己的界面*/                ImageView user = (ImageView) btn_usercenter.getChildAt(0);                TextView user_title = (TextView) btn_usercenter.getChildAt(1);                user_title.setTextColor(Color.parseColor("#a9a9a9"));                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_user, "#a9a9a9"));/*                LazyCatStartActivityWithBundler(ShowshopOffice.class, false, Config.Windows                        .GET_WINDOW_VALUE_SHOP_MESSAGE, "红牛维他命饮料250毫升搭赠装", Config.Windows                        .GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH                        .VALUES_TO_SEARCH_SHOPKEYWORD);*/                TextView classify_title = (TextView) btn_recomd.getChildAt(1);                TextUnt.with(classify_title).setTextColor("#a9a9a9");                ImageView classify_img = (ImageView) btn_recomd.getChildAt(0);                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_recomd,                        "#a9a9a9"));                //LazyCatActStartActivity(ShowshopOffice.class, false);                //LazyCatActStartActivity(UploadApp.class, false);            }        });        btn_vip.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                ClearallIcoBackground();                hideFragment();                showFragment(ICO_FRAGMENT_VIP);                now_fragment = ICO_FRAGMENT_VIP;                final RelativeLayout rl = (RelativeLayout) v;                rl.post(new Runnable() {                    @Override                    public void run() {                        Animator an = Tools.createRoundAnimation(rl, 600);                        an.start();                    }                });                ImageView img = (ImageView) rl.getChildAt(0);                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_vip, "#08c299"));                /*隐藏其它的界面*/                TextView title = (TextView) rl.getChildAt(1);                title.setTextColor(Color.parseColor("#08c299"));                ImageView main = (ImageView) btn_main.getChildAt(0);                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main, "#666666"));                TextView main_title = (TextView) btn_main.getChildAt(1);                main_title.setTextColor(Color.parseColor("#a9a9a9"));                ImageView user = (ImageView) btn_usercenter.getChildAt(0);                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_user, "#666666"));                TextView user_title = (TextView) btn_usercenter.getChildAt(1);                user_title.setTextColor(Color.parseColor("#a9a9a9"));                TextView classify_title = (TextView) btn_recomd.getChildAt(1);                TextUnt.with(classify_title).setTextColor("#a9a9a9");                ImageView classify_img = (ImageView) btn_recomd.getChildAt(0);                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_recomd,                        "#666666"));            }        });        btn_recomd.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                ClearallIcoBackground();                hideFragment();                showFragment(ICO_FRAGMENT_RECOMD);                now_fragment = ICO_FRAGMENT_RECOMD;                final RelativeLayout rl = (RelativeLayout) v;                rl.post(new Runnable() {                    @Override                    public void run() {                        Animator an = Tools.createRoundAnimation(rl, 600);                        an.start();                    }                });                ImageView img = (ImageView) rl.getChildAt(0);                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_recomd, "#08c299"));                /*隐藏其它的界面*/                TextView title = (TextView) rl.getChildAt(1);                title.setTextColor(Color.parseColor("#08c299"));                /**                 * 处理其他的导航键                 */                ImageView main = (ImageView) btn_main.getChildAt(0);                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main, "#666666"));                TextView main_title = (TextView) btn_main.getChildAt(1);                main_title.setTextColor(Color.parseColor("#666666"));                ImageView user = (ImageView) btn_usercenter.getChildAt(0);                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_user, "#666666"));                TextView user_title = (TextView) btn_usercenter.getChildAt(1);                user_title.setTextColor(Color.parseColor("#666666"));                TextView delivery_title = (TextView) btn_vip.getChildAt(1);                TextUnt.with(delivery_title).setTextColor("#666666");                ImageView delivery_img = (ImageView) btn_vip.getChildAt(0);                delivery_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_vip, "#666666"                ));            }        });        btn_usercenter.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                ClearallIcoBackground();                final RelativeLayout rl = (RelativeLayout) v;                hideFragment();                showFragment(ICO_FRAGMENT_ME);                now_fragment = ICO_FRAGMENT_ME;                rl.post(new Runnable() {                    @Override                    public void run() {                        Animator an = Tools.createRoundAnimation(rl, 600);                        an.start();                    }                });                ImageView img = (ImageView) rl.getChildAt(0);                TextView title = (TextView) rl.getChildAt(1);                title.setTextColor(Color.parseColor("#08c299"));                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_user, "#08c299"));                ImageView main = (ImageView) btn_main.getChildAt(0);                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main, "#666666"));                TextView main_title = (TextView) btn_main.getChildAt(1);                main_title.setTextColor(Color.parseColor("#666666"));                ImageView cart = (ImageView) btn_recomd.getChildAt(0);                cart.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_recomd, "#666666"));                TextView cart_title = (TextView) btn_recomd.getChildAt(1);                cart_title.setTextColor(Color.parseColor("#666666"));                TextView classify_title = (TextView) btn_vip.getChildAt(1);                TextUnt.with(classify_title).setTextColor("#666666");                ImageView classify_img = (ImageView) btn_vip.getChildAt(0);                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_vip, "#666666"                ));            }        });    }    /**     * 清空所有的导航的颜色值     */    @SuppressLint("ResourceType")    private void ClearallIcoBackground() {//        ImageView btn_mainImg = (ImageView) btn_main.getChildAt(0);//第0号的位置就是Image        //TextView btn_mainTv = (TextView) btn_main.getChildAt(1);        // btn_mainTv.setTextColor(Color.parseColor("#bfbfbf"));        ImageView btn_deliveryImg = (ImageView) btn_vip.getChildAt(0);        ImageView btn_usercenterImg = (ImageView) btn_usercenter.getChildAt(0);        //设置默认的第一个导航的颜色        VectorDrawableCompat btn_mainImgVD = VectorDrawableCompat.create(getResources(), R.drawable.ico_btn_main,                getTheme());        btn_mainImgVD.setTint(Color.parseColor("#bfbfbf"));        //btn_mainImg.setImageDrawable(btn_mainImgVD);        //设置默认的第二个导航的颜色        //设置默认的第三个导航的颜色    }    @SuppressLint("ResourceType")    private void setIcoNavColor(RelativeLayout rl, int image, final android.animation.Animator animation) {        ImageView iv = (ImageView) rl.getChildAt(0);//第一个就是图标        rl.post(new Runnable() {            @Override            public void run() {                if (animation == null) {                    Log.e(MSG, "导航没有动画");                } else {                    animation.start();                }            }        });        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), image, getTheme());        vectorDrawableCompat.setTint(Color.parseColor(getResources().getString(R.color.ThemeColor)));        iv.setImageDrawable(vectorDrawableCompat);        TextView tv = (TextView) rl.getChildAt(1);        tv.setTextColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));    }    private void showFragment(int position) {        FragmentTransaction ft = getFragmentManager().beginTransaction();        switch (position) {            case ICO_FRAGMENT_MAIN:                if (classifyfrg != null) {                    ft.remove(classifyfrg);/*先移除*/                    classifyfrg = null;                    classifyfrg = new Classifyfrg();                    ft.add(R.id.activity_main_Framelayout, classifyfrg);                } else {                    classifyfrg = new Classifyfrg();                    ft.add(R.id.activity_main_Framelayout, classifyfrg);                }                break;            /**             * 展示VIP界面             */            case ICO_FRAGMENT_VIP:                if (vipexclusivefrg != null) {                    ft.remove(vipexclusivefrg);/*先移除messagefrg*/                    vipexclusivefrg = null;                    vipexclusivefrg = new Vipexclusivefrg();                    ft.add(R.id.activity_main_Framelayout, vipexclusivefrg);                } else {                    vipexclusivefrg = new Vipexclusivefrg();                    ft.add(R.id.activity_main_Framelayout, vipexclusivefrg);                }                break;            case ICO_FRAGMENT_RECOMD:                if (recomfrg != null) {                    ft.remove(recomfrg);                    recomfrg = null;                    recomfrg = new Recomdfrg();                    ft.add(R.id.activity_main_Framelayout, recomfrg);                } else {                    recomfrg = new Recomdfrg();                    ft.add(R.id.activity_main_Framelayout, recomfrg);                }                break;            case ICO_FRAGMENT_ME:                if (usercneterfrg != null) {                    ft.remove(usercneterfrg);                    usercneterfrg = null;                    usercneterfrg = new UserCenterfrg();                    ft.add(R.id.activity_main_Framelayout, usercneterfrg);                } else {                    usercneterfrg = new UserCenterfrg();                    ft.add(R.id.activity_main_Framelayout, usercneterfrg);                }                break;        }        ft.commit();    }    private void hideFragment() {        FragmentTransaction ft = getFragmentManager().beginTransaction();        if (classifyfrg != null) {            ft.hide(classifyfrg);        }        if (vipexclusivefrg != null) {            ft.hide(vipexclusivefrg);        }        if (usercneterfrg != null) {            ft.hide(usercneterfrg);        }        if (classifyfrg != null) {            ft.hide(classifyfrg);        }        ft.commit();    }    @Override    public void startActivityForResult(Intent intent, int requestCode) {        super.startActivityForResult(intent, requestCode);    }}
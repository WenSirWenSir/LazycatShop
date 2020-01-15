package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.FragmentLR;
import shlm.lmcs.com.lazycat.LazyShopFrg.Classifyfrg;
import shlm.lmcs.com.lazycat.LazyShopFrg.Mainfrg;
import shlm.lmcs.com.lazycat.LazyShopFrg.UserCenterfrg;
import shlm.lmcs.com.lazycat.LazyShopFrg.Vipexclusivefrg;
import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;
import shlm.lmcs.com.lazycat.R;


/**
 * 主界面
 */
public class MainAct extends LazyCatAct {
    private FragmentTransaction ft;
    private RelativeLayout btn_main, btn_vip, btn_usercenter, btn_recomd;
    private Mainfrg mainfrg;
    private Classifyfrg classifyfrg;
    private UserCenterfrg usercneterfrg;
    private Vipexclusivefrg vipexclusivefrg;
    private FragmentLR frameLayout;
    private int lr_downX;/*滑动定点*/
    private boolean is_main, is_cart, is_user;/*判断是哪个导航被点击*/
    private int lr_countX;/*滑动距离*/
    private String MSG = "MainAct.java[+]";
    private final static int ICO_FRAGMENT_MAIN = 0;
    private final static int ICO_FRAGMENT_DELIVERY = 1;
    private final static int ICO_FRAGMENT_USERCENTER = 2;
    private final static int ICO_FRAGMENT_MESSAGE = 3;
    private int now_fragment;/*判断是哪一个FRAGMENT*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        setTransparentBar();
        init();
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"ResourceType", "NewApi", "ClickableViewAccessibility"})
    private void init() {
        setBackStatic(true);
        //尝试加载Fragment
        ft = this.getFragmentManager().beginTransaction();
        classifyfrg = new Classifyfrg();
        frameLayout = findViewById(R.id.activity_main_Framelayout);/*管理控件*/
        /*设置监听器*/
        ft.add(R.id.activity_main_Framelayout, classifyfrg);
        ft.commitAllowingStateLoss();
        /*获取定位的DIALOG*/
        //找寻对于的ID号码
        btn_main = findViewById(R.id.activity_main_btn_IcoMain);//主界面
        btn_vip = findViewById(R.id.activity_main_btnVip);//vip界面
        btn_recomd = findViewById(R.id.activity_main_btnRecomd);//活动节目
        btn_usercenter = findViewById(R.id.activity_main_btn_IcoUserCenter);//个人中心


        /**
         * 初始化  更新用户的信息  怕vip过期
         */
        SystemVip systemVip = new SystemVip(MainAct.this);
        systemVip.Start(new SystemVip.OnVipcheck() {
            @Override
            public void onCheckdone(int _vip) {

            }
        });
        /**
         * 测试 设置MESSAGE的数量
         */

        TextUnt.with(this, R.id.activity_main_messageNumber).setVisibility(false);
/*
        findViewById(R.id.activity_main_messageNumber).setBackground(Tools.CreateDrawable(1,
                "#08c299", "#08c299", 50));
*/
        /**
         * 设置初始化的界面
         */
        ImageView InitImg = (ImageView) btn_main.getChildAt(0);
        InitImg.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable.btn_main,
                "#08c299"));
        TextView InitTitle = (TextView) btn_main.getChildAt(1);
        InitTitle.setTextColor(Color.parseColor("#08c299"));
        now_fragment = ICO_FRAGMENT_MAIN;
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
                showFragment(ICO_FRAGMENT_MAIN);
                now_fragment = ICO_FRAGMENT_MAIN;
                ClearallIcoBackground();
                final RelativeLayout rl = (RelativeLayout) v;
                rl.post(new Runnable() {
                    @Override
                    public void run() {
                        Animator an = Tools.createRoundAnimation(rl, 600);
                        an.start();
                    }
                });
                /*修改导航*/
                ImageView img = (ImageView) btn_main.getChildAt(0);
                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_main, "#08c299"));
                TextView title = (TextView) rl.getChildAt(1);
                title.setTextColor(Color.parseColor("#08c299"));
                /*开始隐藏其它的导航*/
                ImageView cart = (ImageView) btn_vip.getChildAt(0);
                cart.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_vip, "#a9a9a9"));
                TextView cart_title = (TextView) btn_vip.getChildAt(1);
                cart_title.setTextColor(Color.parseColor("#a9a9a9"));
                /*用户自己的界面*/
                ImageView user = (ImageView) btn_usercenter.getChildAt(0);
                TextView user_title = (TextView) btn_usercenter.getChildAt(1);
                user_title.setTextColor(Color.parseColor("#a9a9a9"));
                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_user, "#a9a9a9"));
/*
                LazyCatStartActivityWithBundler(ShowshopOffice.class, false, Config.Windows
                        .GET_WINDOW_VALUE_SHOP_MESSAGE, "红牛维他命饮料250毫升搭赠装", Config.Windows
                        .GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH
                        .VALUES_TO_SEARCH_SHOPKEYWORD);
*/
                TextView classify_title = (TextView) btn_recomd.getChildAt(1);
                TextUnt.with(classify_title).setTextColor("#a9a9a9");
                ImageView classify_img = (ImageView) btn_recomd.getChildAt(0);
                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                        .drawable.btn_recomd, "#a9a9a9"));
                //LazyCatActStartActivity(ShowshopOffice.class, false);
                //LazyCatActStartActivity(UploadApp.class, false);

            }
        });

        btn_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearallIcoBackground();
                hideFragment();
                showFragment(ICO_FRAGMENT_DELIVERY);
                now_fragment = ICO_FRAGMENT_DELIVERY;
                final RelativeLayout rl = (RelativeLayout) v;
                rl.post(new Runnable() {
                    @Override
                    public void run() {
                        Animator an = Tools.createRoundAnimation(rl, 600);
                        an.start();
                    }
                });
                ImageView img = (ImageView) rl.getChildAt(0);
                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_vip, "#08c299"));
                /*隐藏其它的界面*/
                TextView title = (TextView) rl.getChildAt(1);
                title.setTextColor(Color.parseColor("#08c299"));
                ImageView main = (ImageView) btn_main.getChildAt(0);
                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_main, "#666666"));
                TextView main_title = (TextView) btn_main.getChildAt(1);
                main_title.setTextColor(Color.parseColor("#a9a9a9"));
                ImageView user = (ImageView) btn_usercenter.getChildAt(0);
                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_user, "#666666"));
                TextView user_title = (TextView) btn_usercenter.getChildAt(1);
                user_title.setTextColor(Color.parseColor("#a9a9a9"));

                TextView classify_title = (TextView) btn_recomd.getChildAt(1);
                TextUnt.with(classify_title).setTextColor("#a9a9a9");
                ImageView classify_img = (ImageView) btn_recomd.getChildAt(0);
                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                        .drawable.btn_recomd, "#666666"));
            }
        });
        btn_recomd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearallIcoBackground();
                hideFragment();
                showFragment(ICO_FRAGMENT_MESSAGE);
                now_fragment = ICO_FRAGMENT_MESSAGE;
                final RelativeLayout rl = (RelativeLayout) v;
                rl.post(new Runnable() {
                    @Override
                    public void run() {
                        Animator an = Tools.createRoundAnimation(rl, 600);
                        an.start();
                    }
                });
                ImageView img = (ImageView) rl.getChildAt(0);
                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_recomd, "#08c299"));
                /*隐藏其它的界面*/
                TextView title = (TextView) rl.getChildAt(1);
                title.setTextColor(Color.parseColor("#08c299"));
                /**
                 * 处理其他的导航键
                 */
                ImageView main = (ImageView) btn_main.getChildAt(0);
                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_main, "#666666"));
                TextView main_title = (TextView) btn_main.getChildAt(1);
                main_title.setTextColor(Color.parseColor("#666666"));
                ImageView user = (ImageView) btn_usercenter.getChildAt(0);
                user.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_user, "#666666"));
                TextView user_title = (TextView) btn_usercenter.getChildAt(1);
                user_title.setTextColor(Color.parseColor("#666666"));
                TextView delivery_title = (TextView) btn_vip.getChildAt(1);
                TextUnt.with(delivery_title).setTextColor("#666666");
                ImageView delivery_img = (ImageView) btn_vip.getChildAt(0);
                delivery_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                        .drawable.btn_vip, "#666666"));

            }
        });
        btn_usercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearallIcoBackground();
                final RelativeLayout rl = (RelativeLayout) v;
                hideFragment();
                showFragment(ICO_FRAGMENT_USERCENTER);
                now_fragment = ICO_FRAGMENT_USERCENTER;
                rl.post(new Runnable() {
                    @Override
                    public void run() {
                        Animator an = Tools.createRoundAnimation(rl, 600);
                        an.start();
                    }
                });
                ImageView img = (ImageView) rl.getChildAt(0);
                TextView title = (TextView) rl.getChildAt(1);
                title.setTextColor(Color.parseColor("#08c299"));
                img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_user, "#08c299"));
                ImageView main = (ImageView) btn_main.getChildAt(0);
                main.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_main, "#666666"));
                TextView main_title = (TextView) btn_main.getChildAt(1);
                main_title.setTextColor(Color.parseColor("#666666"));
                ImageView cart = (ImageView) btn_recomd.getChildAt(0);
                cart.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R.drawable
                        .btn_recomd, "#666666"));
                TextView cart_title = (TextView) btn_recomd.getChildAt(1);
                cart_title.setTextColor(Color.parseColor("#666666"));

                TextView classify_title = (TextView) btn_vip.getChildAt(1);
                TextUnt.with(classify_title).setTextColor("#666666");
                ImageView classify_img = (ImageView) btn_vip.getChildAt(0);
                classify_img.setImageDrawable(Tools.setSvgColor(getApplicationContext(), R
                        .drawable.btn_vip, "#666666"));
            }
        });
    }


    /**
     * 清空所有的导航的颜色值
     */
    @SuppressLint("ResourceType")
    private void ClearallIcoBackground() {
//        ImageView btn_mainImg = (ImageView) btn_main.getChildAt(0);//第0号的位置就是Image
        //TextView btn_mainTv = (TextView) btn_main.getChildAt(1);
        // btn_mainTv.setTextColor(Color.parseColor("#bfbfbf"));
        ImageView btn_deliveryImg = (ImageView) btn_vip.getChildAt(0);
        ImageView btn_usercenterImg = (ImageView) btn_usercenter.getChildAt(0);
        //设置默认的第一个导航的颜色
        VectorDrawableCompat btn_mainImgVD = VectorDrawableCompat.create(getResources(), R
                .drawable.ico_btn_main, getTheme());
        btn_mainImgVD.setTint(Color.parseColor("#bfbfbf"));
        //btn_mainImg.setImageDrawable(btn_mainImgVD);
        //设置默认的第二个导航的颜色
        //设置默认的第三个导航的颜色
    }

    @SuppressLint("ResourceType")
    private void setIcoNavColor(RelativeLayout rl, int image, final android.animation.Animator
            animation) {
        ImageView iv = (ImageView) rl.getChildAt(0);//第一个就是图标
        rl.post(new Runnable() {
            @Override
            public void run() {
                if (animation == null) {
                    Log.e(MSG, "导航没有动画");
                } else {
                    animation.start();
                }
            }
        });
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(),
                image, getTheme());
        vectorDrawableCompat.setTint(Color.parseColor(getResources().getString(R.color
                .ThemeColor)));
        iv.setImageDrawable(vectorDrawableCompat);
        TextView tv = (TextView) rl.getChildAt(1);
        tv.setTextColor(Color.parseColor(getResources().getString(R.color.ThemeColor)));
    }

    private void showFragment(int position) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (position) {
            case ICO_FRAGMENT_MAIN:
                if (classifyfrg != null) {
                    ft.remove(classifyfrg);/*先移除*/
                    classifyfrg = null;
                    classifyfrg = new Classifyfrg();
                    ft.add(R.id.activity_main_Framelayout, classifyfrg);
                } else {
                    mainfrg = new Mainfrg();
                    ft.add(R.id.activity_main_Framelayout, mainfrg);
                }
                break;
            case ICO_FRAGMENT_DELIVERY:
                if (vipexclusivefrg != null) {
                    ft.remove(vipexclusivefrg);/*先移除messagefrg*/
                    vipexclusivefrg = null;
                    vipexclusivefrg = new Vipexclusivefrg();
                    ft.add(R.id.activity_main_Framelayout, vipexclusivefrg);

                } else {
                    vipexclusivefrg = new Vipexclusivefrg();
                    ft.add(R.id.activity_main_Framelayout, vipexclusivefrg);
                }
                break;
            case ICO_FRAGMENT_MESSAGE:
                if (classifyfrg != null) {
                    ft.show(classifyfrg);
                } else {
                    classifyfrg = new Classifyfrg();
                    ft.add(R.id.activity_main_Framelayout, classifyfrg);
                }
                break;
            case ICO_FRAGMENT_USERCENTER:
                if (usercneterfrg != null) {
                    ft.remove(usercneterfrg);
                    usercneterfrg = null;
                    usercneterfrg = new UserCenterfrg();
                    ft.add(R.id.activity_main_Framelayout, usercneterfrg);

                } else {
                    usercneterfrg = new UserCenterfrg();
                    ft.add(R.id.activity_main_Framelayout, usercneterfrg);
                }

                break;
        }
        ft.commit();
    }

    private void hideFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mainfrg != null) {
            ft.hide(mainfrg);
        }
        if (vipexclusivefrg != null) {
            ft.hide(vipexclusivefrg);
        }
        if (usercneterfrg != null) {
            ft.hide(usercneterfrg);
        }
        if (classifyfrg != null) {
            ft.hide(classifyfrg);
        }
        ft.commit();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

}


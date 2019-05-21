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
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopFrg.Deliveryfrg;
import shlm.lmcs.com.lazycat.LazyShopFrg.Mainfrg;
import shlm.lmcs.com.lazycat.LazyShopFrg.UserCenterfrg;
import shlm.lmcs.com.lazycat.R;

public class MainAct extends LazyCatAct {
    private FragmentTransaction ft;
    private RelativeLayout btn_main, btn_delivery, btn_usercenter;
    private Mainfrg mainfrg;
    private Deliveryfrg deliveryfrg;
    private UserCenterfrg usercneterfrg;
    private String MSG = "MainAct.java[+]";
    private final static int ICO_FRAGMENT_MAIN = 0;
    private final static int ICO_FRAGMENT_DELIVERY = 1;
    private final static int ICO_FRAGMENT_USERCENTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        init();
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"ResourceType", "NewApi"})
    private void init() {
        setBackStatic(true);
        setStatusBar("#ffffff");
        //尝试加载Fragment
        ft = this.getFragmentManager().beginTransaction();
        mainfrg = new Mainfrg();
        ft.add(R.id.activity_main_Framelayout, mainfrg);
        ft.commit();
        //找寻对于的ID号码
        btn_main = findViewById(R.id.activity_main_btn_IcoMain);//主界面
        btn_delivery = findViewById(R.id.activity_main_btn_IcoDelivery);//配送界面
        btn_usercenter = findViewById(R.id.activity_main_btn_IcoUserCenter);//个人中心
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
                showFragment(ICO_FRAGMENT_MAIN);
                ClearallIcoBackground();
                RelativeLayout rl = (RelativeLayout) v;
                Animator animation = Tools.createRoundAnimation(btn_main, 200);
                setIcoNavColor(rl, R.drawable.ico_btn_main, animation);
                LazyCatActStartActivity(ShowshopOffice.class, false);
                //LazyCatActStartActivity(UploadApp.class, false);

            }
        });

        btn_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearallIcoBackground();
                hideFragment();
                showFragment(ICO_FRAGMENT_DELIVERY);
                RelativeLayout rl = (RelativeLayout) v;
                Animator animation = Tools.createRoundAnimation(btn_delivery, 200);
                setIcoNavColor(rl, R.drawable.ico_freight, animation);
            }
        });
        btn_usercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearallIcoBackground();
                RelativeLayout rl = (RelativeLayout) v;
                hideFragment();
                showFragment(ICO_FRAGMENT_USERCENTER);
                setStatusBar("#f30d88");
                Animator animation = Tools.createRoundAnimation(btn_usercenter, 200);
                setIcoNavColor(rl, R.drawable.ico_usercontent, animation);
                LazyCatActStartActivity(LoginAct.class, false);
            }
        });
    }


    /**
     * 清空所有的导航的颜色值
     */
    @SuppressLint("ResourceType")
    private void ClearallIcoBackground() {
        ImageView btn_mainImg = (ImageView) btn_main.getChildAt(0);//第0号的位置就是Image
        TextView btn_mainTv = (TextView) btn_main.getChildAt(1);
        btn_mainTv.setTextColor(Color.parseColor("#bfbfbf"));
        ImageView btn_deliveryImg = (ImageView) btn_delivery.getChildAt(0);
        TextView btn_deliveryTv = (TextView) btn_delivery.getChildAt(1);
        btn_deliveryTv.setTextColor(Color.parseColor("#bfbfbf"));
        ImageView btn_usercenterImg = (ImageView) btn_usercenter.getChildAt(0);
        TextView btn_usercenterTv = (TextView) btn_usercenter.getChildAt(1);
        btn_usercenterTv.setTextColor(Color.parseColor("#bfbfbf"));
        //设置默认的第一个导航的颜色
        VectorDrawableCompat btn_mainImgVD = VectorDrawableCompat.create(getResources(), R
                .drawable.ico_btn_main, getTheme());
        btn_mainImgVD.setTint(Color.parseColor("#bfbfbf"));
        btn_mainImg.setImageDrawable(btn_mainImgVD);
        //设置默认的第二个导航的颜色
        VectorDrawableCompat btn_deilveryImgVD = VectorDrawableCompat.create(getResources(), R
                .drawable.ico_freight, getTheme());
        btn_deilveryImgVD.setTint(Color.parseColor("#bfbfbf"));
        btn_deliveryImg.setImageDrawable(btn_deilveryImgVD);
        //设置默认的第三个导航的颜色
        VectorDrawableCompat btn_usercenterImgVD = VectorDrawableCompat.create(getResources(), R
                .drawable.ico_usercontent, getTheme());
        btn_usercenterImgVD.setTint(Color.parseColor("#bfbfbf"));
        btn_usercenterImg.setImageDrawable(btn_usercenterImgVD);
    }

    @SuppressLint("ResourceType")
    private void setIcoNavColor(RelativeLayout rl, int image, final android.animation.Animator
            animation) {
        ImageView iv = (ImageView) rl.getChildAt(0);//第一个就是图标
        rl.post(new Runnable() {
            @Override
            public void run() {
                if (animation == null) {
                    Log.e(MSG,"导航没有动画");
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
                if (mainfrg != null) {
                    ft.show(mainfrg);
                } else {
                    mainfrg = new Mainfrg();
                    ft.add(R.id.activity_main_Framelayout, mainfrg);
                }
                break;
            case ICO_FRAGMENT_DELIVERY:
                if (deliveryfrg != null) {
                    ft.show(deliveryfrg);
                } else {
                    deliveryfrg = new Deliveryfrg();
                    ft.add(R.id.activity_main_Framelayout, deliveryfrg);
                }
                break;
            case ICO_FRAGMENT_USERCENTER:
                if (usercneterfrg != null) {
                    ft.show(usercneterfrg);
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
        if (deliveryfrg != null) {
            ft.hide(deliveryfrg);
        }
        if (usercneterfrg != null) {
            ft.hide(usercneterfrg);
        }
        ft.commit();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}

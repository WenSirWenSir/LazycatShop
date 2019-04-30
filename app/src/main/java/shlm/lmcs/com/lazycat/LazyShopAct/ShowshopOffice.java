package shlm.lmcs.com.lazycat.LazyShopAct;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.ScrollViewBiggerPhoto;
import shlm.lmcs.com.lazycat.R;

@SuppressLint({"ResourceType", "NewApi", "HandlerLeak"})
public class ShowshopOffice extends LazyCatAct {
    private LinearLayout btn_addshopcart;
    private ScrollViewBiggerPhoto scrollViewBiggerPhoto;
    private ImageView photo;
    private TextView btn_select_del;
    private TextView btn_select_add;
    private LinearLayout body_addordel;
    private TextView historicePrice;
    private ImageView countDownadavert;
    private LinearLayout select_numberBody;
    private RelativeLayout shopCartnumberBody;
    private TextView btnAccount;
    private TextView select_number;
    private TextView shopCartNumber;
    private TextView ReplayPrice;//还需要多少金额开始送
    private final static int MSG_COUNT_DOWN_ADAVERT = 0;
    private final static int MSG_CLEAR_COUNT_DOWN_ADAVERT = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COUNT_DOWN_ADAVERT:
                    countDownadavert.setVisibility(View.VISIBLE);
                    AlphaAnimation ap = Tools.createOnalpha(2000, true);
                    countDownadavert.setAnimation(ap);
                    /*开启线程 用来关闭广告*/
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = MSG_CLEAR_COUNT_DOWN_ADAVERT;
                            handler.sendMessage(msg);
                        }
                    }, Tools.getRandom(7, 11) * 1000);
                    break;
                case MSG_CLEAR_COUNT_DOWN_ADAVERT:
                    /*清空倒计时广告*/
                    final ValueAnimator anim = ValueAnimator.ofInt(countDownadavert.getHeight(), 0);
                    anim.setDuration(Tools.getRandom(1, 2) * 1000);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Log.e(Config.DEBUG, "ShowshopOffice.java[+]属性动画值: " + animation
                                    .getAnimatedValue() + "广告图片的高度:" + countDownadavert.getHeight
                                    ());
                            ViewGroup.LayoutParams params = countDownadavert.getLayoutParams();
                            params.height = (int) animation.getAnimatedValue();
                            countDownadavert.setLayoutParams(params);

                        }
                    });
                    anim.start();//属性改变动画
                    //AlphaAnimation clearap = Tools.clearOnalpha(Tools.getRandom(3, 6) * 1000,
                    // true);
                    //countDownadavert.startAnimation(clearap);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showshopoffice);
        setTransparentBar();
        /**
         * 设置促销的标题
         */
        findViewById(R.id.activity_showshopoffice_progrm_title).setBackground(Tools
                .CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources()
                        .getString(R.color.ThemeColor), 5));
        //找出ViewID
        btn_addshopcart = findViewById(R.id.activity_showshopoffice_btnAddshopcartBody);
        /*设置圆角*/
        findViewById(R.id.activity_showshopoffice_btnAddshopcartBody).setBackground(Tools
                .CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources()
                        .getString(R.color.ThemeColor), 50));
        /*滑动增大图片控件*/
        scrollViewBiggerPhoto = findViewById(R.id.activity_showshopoffice_scrollview);
        /*增加或者减少按钮布局*/
        body_addordel = findViewById(R.id.activity_showshopoffice_SeletNumberBody);
        /*显示商品图片*/
        photo = findViewById(R.id.activity_showshopoffice_photo);
        /*倒计时的广告*/
        countDownadavert = findViewById(R.id.activity_showshopoffice_countDownadavert);
        /*选择数量*/
        select_numberBody = findViewById(R.id.activity_showshopoffice_SeletNumberBody);
        /*选择数量减少*/
        btn_select_del = findViewById(R.id.activity_showshopoffice_SeletNumberBtndel);
        /*选择数量增加*/
        btn_select_add = findViewById(R.id.activity_showshopoffice_SeletNumberBtnadd);
        /*获取选择数量*/
        select_number = findViewById(R.id.activity_showshopoffice_SeletNumber);
        /*历史的价格*/
        historicePrice = findViewById(R.id.activity_showshopoffice_historicalPrice);
        /*购物车数量标志*/
        shopCartNumber = findViewById(R.id.activity_showshopoffice_shopCartnumber);
        /*购物车数量边框标志*/
        shopCartnumberBody = findViewById(R.id.activity_showshopoffice_shopCartnumberBody);
        /*还需多少金额开始起送*/
        ReplayPrice = findViewById(R.id.activity_showshopoffice_Repayprice);
        /*订购按钮*/
        btnAccount = findViewById(R.id.activity_showshopoffice_btnAccount);
        /*图片包裹*/
        Tools.setMidcourtLine(historicePrice);
        init();
        /*count down advert*/
    }

    private void init() {
        /*设置边框的圆角*/
        ReplayPrice.setBackground(Tools.CreateDrawable(1, getResources().getString(R.color
                .ThemeColor), getResources().getString(R.color.ThemeColor), 5));
        /*设置订购边框的圆角 订单未满*/
        // btnAccount.setBackground(Tools.CreateDrawable(1, getResources().getString(R.color
        //         .ThemeColor), getResources().getString(R.color.ThemeColor), 5));
        btnAccount.setBackground(Tools.CreateDrawable(1, "#666666", "#666666", 5));
        btnAccount.setTextColor(Color.parseColor("#ffffff"));
        /*首先隐藏购物车的显示数量*/
        shopCartnumberBody.setVisibility(View.GONE);
        DisplayMetrics metrics = new DisplayMetrics();/*获取屏幕矩阵*/
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        /*重新计算图片的高度实现开场动画*/
        final ViewGroup.LayoutParams photoParams = photo.getLayoutParams();
        photoParams.height = metrics.heightPixels;
        photoParams.width = metrics.widthPixels;
        photo.setLayoutParams(photoParams);

        /*图片边框动画*/
        ValueAnimator anim = ValueAnimator.ofInt(metrics.heightPixels, metrics.heightPixels * 8
                / 16);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = photo.getLayoutParams();
                Log.e(Config.DEBUG,"ShowshopOffice.java[+]改变的高度值:" + animation.getAnimatedValue());
                params.height = (int) animation.getAnimatedValue();
                photo.setLayoutParams(params);
            }
        });
        anim.start();
        /*重新计算高度和宽度*/
        ViewGroup.LayoutParams params = countDownadavert.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = Tools.getCount_downAdvert(metrics);
        countDownadavert.setLayoutParams(params);
        countDownadavert.setVisibility(View.GONE);
        /*线程开启计算倒计时广告*/
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MSG_COUNT_DOWN_ADAVERT;
                handler.sendMessage(msg);

            }
        }, Tools.getRandom(10, 20) * 1000);
        scrollViewBiggerPhoto.setImageHead(photo, metrics);
        /*加入购物车的布局*/
        btn_addshopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_addshopcart.setVisibility(View.GONE);
                select_numberBody.setVisibility(View.VISIBLE);
                select_number.setText(String.valueOf(1));
                int i = Integer.parseInt(select_number.getText().toString());
                if (i >= 1) {
                    if (shopCartNumber.getVisibility() != View.VISIBLE) {
                        shopCartNumber.setVisibility(View.VISIBLE);
                    }
                    shopCartNumber.setText(String.valueOf(1));
                    shopCartnumberBody.setVisibility(View.VISIBLE);
                } else {
                    shopCartnumberBody.setVisibility(View.GONE);
                }
            }
        });
        /*数量减少*/
        btn_select_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(select_number.getText().toString());
                i -= 1;
                if (i <= 0) {
                    select_numberBody.setVisibility(View.GONE);
                    btn_addshopcart.setVisibility(View.VISIBLE);
                    shopCartnumberBody.setVisibility(View.GONE);
                } else {
                    select_number.setText(String.valueOf(i));
                    shopCartNumber.setText(String.valueOf(i));
                }

            }
        });
        /*数量增加*/
        btn_select_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(select_number.getText().toString());
                i += 1;
                if (shopCartNumber.getVisibility() != View.VISIBLE) {
                    /*正在显示*/
                    shopCartNumber.setVisibility(View.VISIBLE);
                }
                select_number.setText(String.valueOf(i));
                shopCartNumber.setText(String.valueOf(i));
            }
        });


    }
}

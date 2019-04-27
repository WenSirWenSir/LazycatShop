package shlm.lmcs.com.lazycat.LazyShopAct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.ScrollViewBiggerPhoto;
import shlm.lmcs.com.lazycat.R;

@SuppressLint({"ResourceType", "NewApi"})
public class ShowshopOffice extends LazyCatAct {
    private LinearLayout btn_addshopcart;
    private ScrollViewBiggerPhoto scrollViewBiggerPhoto;
    private ImageView photo;
    private LinearLayout body_addordel;

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
        init();
    }

    private void init() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        scrollViewBiggerPhoto.setImageHead(photo, metrics);
        btn_addshopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body_addordel.setVisibility(View.VISIBLE);
                btn_addshopcart.setVisibility(View.GONE);
            }
        });


    }
}

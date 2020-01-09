package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ToOpenWindowtools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopAct.ScanQRCodeAct;
import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;
import shlm.lmcs.com.lazycat.LazyShopAct.ShowshopOffice;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalPage;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.R;

import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment {
    /**
     * -------------------------------------------
     * 显示商品的参数
     */
    private ShowshopList showshopList = null;
    ArrayList<ShowshopList> showList = new ArrayList<ShowshopList>();
    /**
     * -------------------------------------------
     */


    /**
     * 显示首页的下拉加载的图片的参数值
     */
    private String LoadingImgurl;/*图片的地址*/
    private String LoadingOnclick;/*图片的点击的地址*/
    /**
     * -------------------------------------------
     */
    /**
     * 腾讯定位模块
     */
    private AlertDialog alertDialog = null;
    private View item;
    private static final String MSG = "Mainfrg.java[+]";
    private String StCitycode;/*获取城市的CODE用来获取地区服务器地址*/
    /**
     * DIALOG
     */
    private WaitDialog.RefreshDialog refreshDialog = null;

    /**
     * 存在服务器参数表格
     */

    private XmlTagValuesFactory.Init_btnValues init_btnValues;
    private XmlTagValuesFactory.Init_filletValues init_filletValues;


    /**
     * 本类用的参数
     */
    private TextView _headTitle;/*顶部标题*/
    private LinearLayout refreshBody;/*滑动控件的Body*/
    private LinearLayout _Refreshhead;/*滑动控件的头部的广告*/
    private LocalPage.SecondSmallNavAPage secondSmallNavAPage;
    private LocalPage.BigCenterHeadpageInstance bigCenterHeadpageInstance;
    private LocalPage.ThreeNavPageInstance threeNavPageInstance;
    private ImageView secondNavAimg;/*第二个导航中的第一个子导航的NAV*/
    private ImageView secondNavBimg;/*第二个导航中的第二个子导航的NAV*/
    private ImageView secondNavCimg;/*第二个导航中的第三个子导航的图片*/
    private ImageView secondNavDimg;/*第四个导航中的第四个子导航的图片*/
    private ImageView threeNavAimg;/*第三排的第一个竖向图片*/
    private ImageView threeNavBimg;/*第三排的第二个竖向的图片*/
    private ImageView threeNavCimg;/*第三排的第四个竖向的图片*/
    private ImageView _RefreshheadImg;/*滑动的控件的图片的控件*/

    private ConvenientBanner banner;
    private String ProgramVersion;/*应用程序版本号*/
    private String ProgramNewSize;/*更新的文件的大小*/
    private String ProgramVersionText;/*更新之后的一些简介*/
    private ImageView CenterHeadpageImg;
    private static final int REFRESH_STOP_MESSAGELOAD = 0;/*停止刷新 隐藏广告*/
    private AlertDialog UpdateDialog;
    private Boolean isLoadEnd = false;/*判断是否加载完毕*/
    private Boolean isLoadShopdone = false;/*判断是否加载过首页推荐的商品了*/
    private int Position = 0;/*设置position用来底部加载*/
    private String prepay_id;
    private ArrayList<String> big_headimgs = new ArrayList<>();

    /**
     * TAG值
     */
    private String LOCALACTION_ONEBIGTITLE = "oneBigtitle";
    private String LOCALACTION_ONEBIGTITLE_COLOR = "oneBigtitleColor";
    private String LOCALACTION_ONESMALLTITLE = "oneSmalltitle";
    private String LOCALACTION_ONESMALLTITLE_COLOR = "oneSmalltitleColor";
    private String LOCALACTION_TWOBIGTITLE = "twoBigtitle";
    private String LOCALACTION_TWOBIGTITLE_COLOR = "twoBigtitleColor";
    private String LOCALACTION_TWOSMALLTITLE = "twoSmalltitle";
    private String LOCALACTION_TWOSMALLTITLE_COLOR = "twoSmalltitleColor";
    private String LOCALACTION_THREEBIGTITLE = "threeBigtitle";
    private String LOCALACTION_THREEBIGTITLE_COLOR = "threeBigtitleColor";
    private String LOCALACTION_THREESMALLTITLE = "threeSmalltitle";
    private String LOCALACTION_THREESMALLTITLE_COLOR = "threeSmalltitleColor";
    private String LOCALACTION_FOURBIGTITLE = "fourBigtitle";
    private String LOCALACTION_FOURBIGTITLE_COLOR = "fourBigtitleColor";
    private String LOCALACTION_FOURSMALLTITLE = "fourSmalltitle";
    private String LOCALACTION_FOURSMALLTITLE_COLOR = "fourSmalltitleColor";
    /**
     * 对应上面的VALUES值
     */
    private String oneBigtitle;
    private String oneBigtitleColor;
    private String oneSmalltitle;
    private String oneSmalltitleColor;
    private String twoBigtitle;
    private String twoBigtitleColor;
    private String twoSmalltitle;
    private String twoSmalltitleColor;
    private String threeBigtitle;
    private String threeBigtitleColor;
    private String threeSmalltitle;
    private String threeSmalltitleColor;
    private String fourBigtitle;
    private String fourBigtitleColor;
    private String fourSmalltitle;
    private String fourSmalltitleColor;
    /**
     * 模块数据存储
     */
    private RefreshScrollView _RefreshScrollView;
    LocalValues.HTTP_ADDRS http_addrs;


    /**
     * 类的实现
     */
    private BigheadImg bigheadImg = new BigheadImg();/*存储首页BigHeadImg中的参数*/
    private LocalProgramTools.UserToolsInstance userToolsInstance;/*用户类的Instance*/

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STOP_MESSAGELOAD:
                    _RefreshScrollView.stopRefresh();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        /*设置是否加载完毕*/
        isLoadEnd = false;
        /*设置状态栏颜色*/
        item = inflater.inflate(R.layout.fragment_main, null);
        /*顶部标题*/
        _headTitle = item.findViewById(R.id.fragment_main_headTitle);
        /*处理顶部标题的字体*/
        TextUnt.with(_headTitle).setFontFile(getContext(), "canLogo").setTextColor("#08c299");
        /*滑动控件的Body*/
        refreshBody = item.findViewById(R.id.fragment_main_body);
        /*滑动控件*/
        _RefreshScrollView = item.findViewById(R.id.fragment_main_refreshScrollview);
        /*滑动控件的头部广告*/
        _Refreshhead = item.findViewById(R.id.fragment_main_refreshHead);
        /*滑动控件的头部的图片控件*/
        _RefreshheadImg = item.findViewById(R.id.fragment_main_refreshHeadImg);
        /*第二个导航的第一个子导航*/
        secondNavAimg = item.findViewById(R.id.fragment_main_secondSmallNavAimg);
        /*第二个导航的第二个子导航*/
        secondNavBimg = item.findViewById(R.id.fragment_main_secondSmallNavBimg);
        /*第二个导航的第三个子导航*/
        secondNavCimg = item.findViewById(R.id.fragment_main_secondSmallNavCimg);
        /*第四个导航的子导航*/
        secondNavDimg = item.findViewById(R.id.fragment_main_secondSmallNavDimg);
        /*中间的宣传图片*/
        CenterHeadpageImg = item.findViewById(R.id.fragment_main_CenterHeadimg);
        /*第三排的第一个竖向的图片*/
        threeNavAimg = item.findViewById(R.id.fragment_main_threeNavAimg);
        /*第三排的第二个竖向的图片*/
        threeNavBimg = item.findViewById(R.id.fragment_main_threeNavBimg);
        /*第三排的第三个竖向的图片*/
        threeNavCimg = item.findViewById(R.id.fragment_main_threeNavCimg);
        /*banner控件*/
        banner = item.findViewById(R.id.fragment_main_banner);
        /*判断是否有定位权限 没有定位权限就去申请定位权限*/
        onStartMain();
        listener(item);
        return item;

    }


    /**
     * 最先开始
     */
    @SuppressLint("NewApi")
    private void onStartMain() {
        /*获取地址*/
        http_addrs = LocalValues.getHttpaddrs(getContext());
        getConfigXml();
    }


    /**
     * 控件的监听处理事件
     *
     * @param item
     */
    @SuppressLint({"NewApi", "ResourceType"})
    private void listener(View item) {

        /**
         * 第二排导航的第一个横向的图片的点击事件
         */
        secondNavAimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(v.getTag().toString().trim());
                web_values_act.set_StaticColor("#ffffff");
                LazyCatFragmentStartWevact(web_values_act);
            }
        });

        /**
         * 搜索ICO点击
         */
        item.findViewById(R.id.fragment_main_btnSearchIco).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                LazyCatFragmetStartAct(SearchAct.class);
            }
        });


        /**
         * 第二个NAV的第一个图片的点击事件
         */
        secondNavAimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(secondSmallNavAPage
                        .getSecondSmallAClickUrl());
                web_values_act.set_StaticColor("#ffffff");
                web_values_act.set_TitleBackColor("#ffffff");
                web_values_act.set_TitleColor(getResources().getString(R.color.ThemeColor));
                LazyCatFragmentStartWevact(web_values_act);
            }
        });


        _RefreshScrollView.SetLinstener(new RefreshScrollView.RefreshScrollViewListener() {
            @Override
            public void onRefresh() {
                Log.i(MSG, "onRefresh");

            }

            @Override
            public void onRefreshDone() {
                Log.i(MSG, "onRefreshDone");

            }

            @Override
            public void onStopRefresh() {
                Log.i(MSG, "onStopRefresh");

            }

            @Override
            public void onState(int _static) {
                Log.i(MSG, "onState");

            }

            @Override
            public void onLoadMore() {
                Log.i(MSG, "onLoadMore");
            }

            @Override
            public void onLoadBottom() {
                Log.e(MSG, "在onLoadBottom");
                if (!isLoadShopdone) {
                    Net.doPostXml(getContext(), http_addrs.HTT_ADDR_GETMAINPAGE_SHOWSHOP, new
                            ProgramInterface() {
                        @Override
                        public void onSucess(String data, int code, WaitDialog.RefreshDialog
                                _refreshDialog) {
                            _refreshDialog.dismiss();
                            Log.i(MSG, "获取到商品的数据信息" + data.trim());
                            XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data
                                    .trim());
                            xmlanalysisFactory.Startanalysis(new XmlanalysisFactory
                                    .XmlanalysisInterface() {

                                @Override
                                public void onFaile() {

                                }

                                @Override
                                public void onStartDocument(String tag) {

                                }

                                @Override
                                public void onStartTag(String tag, XmlPullParser pullParser,
                                                       Integer id) {
                                    isLoadShopdone = true;
                                    try {
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_START)) {
                                            showshopList = new ShowshopList();

                                        }
                                        /*标题*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_TITLE)) {
                                            showshopList.set_title(pullParser.nextText().trim());
                                        }
                                        /*设置图片地址*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_IMG)) {
                                            showshopList.set_img(pullParser.nextText().trim());
                                        }
                                        /*商品的批发价格*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_TP)) {
                                            showshopList.set_tp(pullParser.nextText().trim());
                                        }
                                        /*商品唯一的ID*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_ONLYID)) {
                                            showshopList.set_onlyid(pullParser.nextText().trim());

                                        }
                                        /*商品批发价格对应的单位*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_COMPANY)) {
                                            showshopList.set_company(pullParser.nextText().trim());
                                        }
                                        /*商品的生产日期*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_PD)) {
                                            showshopList.set_pd(pullParser.nextText().trim());

                                        }
                                        /*商品的保质期*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_EXP)) {
                                            showshopList.set_exp(pullParser.nextText().trim());
                                        }
                                        /*商品的对接商家*/
                                        if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                                .ACTION_SHOPVALUES_BUSINSS)) {
                                            showshopList.set_business(pullParser.nextText().trim());

                                        }
                                    } catch (Exception e) {

                                    }


                                }

                                @Override
                                public void onEndTag(String tag, XmlPullParser pullParser,
                                                     Integer id) {
                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES
                                            .ACTION_SHOPVALUES_START)) {
                                        showList.add(showshopList);
                                        showshopList = null;
                                    }

                                }

                                @Override
                                public void onEndDocument() {
                                    /**
                                     * 处理首页的商品展示信息 每次加载3个数据
                                     */
                                    toHandlerShoplist();
                                    _RefreshScrollView.stopRefresh();/*下拉刷新停止*/
                                }
                            });
                        }

                        @Override
                        public WaitDialog.RefreshDialog onStartLoad() {
                            /*初始化一个DIALOG*/
                            final WaitDialog.RefreshDialog refreshDialog = new WaitDialog
                                    .RefreshDialog(getContext());
                            WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
                            wait_itme_dialogpage.setImg(R.id.item_wait_img);
                            wait_itme_dialogpage.setView(R.layout.item_wait);
                            wait_itme_dialogpage.setCanClose(false);
                            wait_itme_dialogpage.setTitle(R.id.item_wait_title);
                            refreshDialog.Init(wait_itme_dialogpage);
                            refreshDialog.showRefreshDialog("获取中...", false);
                            return refreshDialog;
                        }

                        @Override
                        public void onFaile(String data, int code) {

                        }
                    }, "");


                } else {
                    /**
                     * 处理首页的商品展示信息 每次加载3个数据
                     */
                    toHandlerShoplist();
                }
            }


            @Override
            public void onScrollStop() {
                Log.e(MSG, "onScrollStop");
            }

            @Override
            public void onloadMessage() {
                onStartMain();/*重新尝试加载*/
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = REFRESH_STOP_MESSAGELOAD;
                        handler.sendMessage(msg);
                    }
                }, 3000);
            }

            @Override
            public void onScrollDistance(int distance) {
                Log.e(MSG, "onScrollDistance");

            }

            @Override
            public void onScrollToleft(int _moveCount) {
                Log.e(MSG, "onScrollToleft");

            }

            @Override
            public void onScrollToRight(int _moveCount) {
                Log.e(MSG, "onScrollToRight");

            }
        });

        /*加载扫一扫*/
        item.findViewById(R.id.fragment_main_btnScan).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加载扫一扫
                        LazyCatFragmetStartAct(ScanQRCodeAct.class);
                    }
                });

    }


    /**
     * 整理界面
     */
    @SuppressLint({"NewApi", "ResourceType"})
    private void toHandlerShoplist() {
        for (int i = 0; i < 3; i++) {
            try {
                if (showList.get(Position) != null) {
                    View shopItem = LayoutInflater.from(getContext()).inflate(R.layout
                            .item_mainshoplist, null);
                    TextUnt.with(shopItem, R.id.item_mainshoplist_Title).setText(showList.get
                            (Position).get_title());
                    shopItem.setTag(showList.get(Position));
                    shopItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShowshopList _list = (ShowshopList) v.getTag();
                            LazyCatFragmentStartActivityWithBundler(ShowshopOffice.class, Config
                                    .Windows.GET_WINDOW_VALUE_SHOP_MESSAGE, _list.get_title()
                                    .trim(), GET_WINDOW_VALUE_SHOP_ACTION, LocalValues
                                    .VALUES_SEARCH.VALUES_TO_SEARCH_SHOPKEYWORD);
                        }
                    });
                    //shopItem.setTag(showList.get(Position));
                    //*进行Item处理监听*//*
                    //*是否喜欢*//*
/*
                                TextView Itemtitle = shopItem.findViewById(R.id
                                        .item_mainshoplist_Title);
*/
                    //*标题*//*
                    TextView ItemTp = shopItem.findViewById(R.id.item_mainshoplist_Tp);
                    //*批发价格*//*
                    TextView ItemCompany = shopItem.findViewById(R.id.item_mainshoplist_Company);
                    //*批发规格单位*//*
                    //*生产日期和保质期*//*
                    TextView ItemExpAndPd = shopItem.findViewById(R.id.item_mainshoplist_ExpAndPd);
                    ImageView ItemShopimg = shopItem.findViewById(R.id.item_mainshoplist_Shopimg);
                    //*图片地址*//*

                    //*设置生产日期和保质期*//*
                    //*设置单位*//*
                    TextUnt.with(ItemCompany).setText("/" + showList.get(Position).get_company());
                    //*设置批发价格*//*
                    if (userToolsInstance.isLogin()) {
                        TextUnt.with(ItemTp).setText(showList.get(Position).get_tp());
                    } else {
                        TextUnt.with(ItemTp).setText("*.*");
                    }
                    //*设置生产日期和保质期*//*
                    TextUnt.with(ItemExpAndPd).setText(showList.get(Position).get_exp() + "生产·" +
                            showList.get(Position).get_pd() + "天保质");
                    //*加载图片*//*

                    Glide.with(getContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +
                            showList.get(Position).get_img().trim()).skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(ItemShopimg);
                    /*设置加盟的赠送的内容*/
                    shopItem.findViewById(R.id.item_mainshoplist_vipTpBody).setBackground(Tools
                            .CreateDrawable(1, getResources().getString(R.color.colorVip),
                                    getResources().getString(R.color.colorVip), 20));
                    //*进行数据判断 用户是否收藏过该商品 如果没有就设置为灰色*//*

                    refreshBody.addView(shopItem);
                } else {

                }
                Position++;
            } catch (Exception e) {
                Log.e(MSG, "整理商品界面错误,错误原因:" + e.getMessage());

            }
        }


    }

    /**
     * 获取首页的配置信息
     */
    @SuppressLint("NewApi")
    private void getConfigXml() {
        /**
         * 获取地区服务器关于该地址的首页配置信息
         * fragment_main_bigHeadMsg
         */
        Net.doGet(getContext(), http_addrs.HTTP_ADDR_GET_MAINCONFIGPAGE, new Net
                .onVisitInterServiceListener() {
            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (getContext());
                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
                wait_itme_dialogpage.setImg(R.id.item_wait_img);
                wait_itme_dialogpage.setView(R.layout.item_wait);
                wait_itme_dialogpage.setCanClose(false);
                wait_itme_dialogpage.setTitle(R.id.item_wait_title);
                refreshDialog.Init(wait_itme_dialogpage);
                refreshDialog.showRefreshDialog("拉取信息...", false);
                return refreshDialog;
            }

            @Override
            public void onSucess(String tOrgin, final WaitDialog.RefreshDialog _rfreshdialog) {
                Log.i(MSG, "返回的首页整理的XML信息: " + tOrgin);
                _rfreshdialog.dismiss();
                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(tOrgin.trim());
                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                    @Override
                    public void onFaile() {

                    }

                    @Override
                    public void onStartDocument(String tag) {

                    }

                    @Override
                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                        try {


                            /**
                             * 版本号
                             */
                            if (tag.equals("ProgramVersion")) {
                                ProgramVersion = pullParser.nextText().trim();
                            }
                            /**
                             * 版本号大小
                             */
                            if (tag.equals("ProgramNewSize")) {
                                ProgramNewSize = pullParser.nextText().trim();
                            }

                            /**
                             * 对于该版本的介绍信息
                             */
                            if (tag.equals("ProgramVersionText")) {
                                ProgramVersionText = pullParser.nextText().trim();
                            }

                            /*设置下拉的图片*/
                            if (tag.equals(LocalAction.ACTION_MAINPAGE
                                    .ACTION_MAINFRGPAG_LOADINGIMG)) {
                                LoadingImgurl = pullParser.nextText().trim();
                            }
                            /*设置下拉图片加载的点击地址*/
                            if (tag.equals(LocalAction.ACTION_MAINPAGE
                                    .ACTION_MAINFRGPAG_LOADINGONCLICK)) {
                                LoadingOnclick = pullParser.nextText().trim();/*获取点击的地址*/
                            }
                            /*设置点击之后的地址*/
                            if (tag.equals(bigheadImg.getTAG_ONCLICK_URL())) {
                                bigheadImg.setOnClick_url(pullParser.nextText().trim());
                            }
                            /*设置图片*/
                            if (tag.equals(bigheadImg.getTAG_SHOW_IMG())) {
                                String addrs = pullParser.nextText().trim();
                                big_headimgs.clear();
                                big_headimgs.add(addrs);
                                big_headimgs.add(addrs);
                                big_headimgs.add(addrs);
                                big_headimgs.add(addrs);
                            }


                            /**
                             * 获取首页的大标题和小标题
                             */
                            /*第一个大标题*/
                            if (tag.equals(LOCALACTION_ONEBIGTITLE)) {
                                oneBigtitle = pullParser.nextText().trim();
                            }
                            /*第一个大标题的颜色*/
                            if (tag.equals(LOCALACTION_ONEBIGTITLE_COLOR)) {
                                oneBigtitleColor = pullParser.nextText().trim();
                            }
                            /*第一个大标题对应的小标题的文字*/
                            if (tag.equals(LOCALACTION_ONESMALLTITLE)) {
                                oneSmalltitle = pullParser.nextText().trim();
                            }
                            /*第一个大标题对应的小标题的颜色*/
                            if (tag.equals(LOCALACTION_ONESMALLTITLE_COLOR)) {
                                oneSmalltitleColor = pullParser.nextText().trim();
                            }
                            /*第二个大标题*/
                            if (tag.equals(LOCALACTION_TWOBIGTITLE)) {
                                twoBigtitle = pullParser.nextText().trim();
                            }
                            /*第二个大标题的颜色*/
                            if (tag.equals(LOCALACTION_TWOBIGTITLE_COLOR)) {
                                twoBigtitleColor = pullParser.nextText().trim();
                            }
                            /*第二个大标题对应的小标题*/
                            if (tag.equals(LOCALACTION_TWOSMALLTITLE)) {
                                twoSmalltitle = pullParser.nextText().trim();
                            }
                            /*第二个大标题对应的小标题的颜色*/
                            if (tag.equals(LOCALACTION_TWOSMALLTITLE_COLOR)) {
                                twoSmalltitleColor = pullParser.nextText().trim();
                            }
                            /*第三个大标题*/
                            if (tag.equals(LOCALACTION_THREEBIGTITLE)) {
                                threeBigtitle = pullParser.nextText().trim();
                            }
                            /*第三个标题的颜色*/
                            if (tag.equals(LOCALACTION_THREEBIGTITLE_COLOR)) {
                                threeBigtitleColor = pullParser.nextText().trim();
                            }
                            /*第三个标题对应的小标题的*/
                            if (tag.equals(LOCALACTION_THREESMALLTITLE)) {
                                threeSmalltitle = pullParser.nextText().trim();
                            }
                            /*第三个标题对应的小标题的颜色*/
                            if (tag.equals(LOCALACTION_THREESMALLTITLE_COLOR)) {
                                threeSmalltitleColor = pullParser.nextText().trim();
                            }
                            /*第四个大标题*/
                            if (tag.equals(LOCALACTION_FOURBIGTITLE)) {
                                fourBigtitle = pullParser.nextText().trim();
                            }
                            /*第四个大标题的颜色*/
                            if (tag.equals(LOCALACTION_FOURBIGTITLE_COLOR)) {
                                fourBigtitleColor = pullParser.nextText().trim();
                            }
                            /*第四个标题对应的小标题*/
                            if (tag.equals(LOCALACTION_FOURSMALLTITLE)) {
                                fourSmalltitle = pullParser.nextText().trim();
                            }
                            /*第四个标题对应的小标题的颜色*/
                            if (tag.equals(LOCALACTION_FOURSMALLTITLE_COLOR)) {
                                fourSmalltitleColor = pullParser.nextText().trim();
                            }
                            /**
                             * 判断是不是SecondSmallNavA
                             */
                            if (tag.equals(LocalPage.SecondSmallNavAPage.BEGIN_XML)) {
                                secondSmallNavAPage = LocalPage.getSecondSmallNavAPageInstance();
                            }
                            /*获取第一个NAV的标题和提示*/
                            if (tag.equals("Big")) {

                            }
                            /*设置第一个NAV的图片*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVA_IMG)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAimgUrl(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /*设置第一个Nav的图片点击URL*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVA_URL)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAClickUrl(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /*第一个Nav的图片标题*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVA_TITLE)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAtitle(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*第一个Nav的图片介绍*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVA_CONTEXT)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAcontext(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /*第一个Nav的标题的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVA_TITLECOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAtitleColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*第一个Nav的介绍的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage
                                    .XML_TAG_NAVA_CONTEXTCOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallAcontextColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /**
                             * 横向第二个图片的取值
                             */
                            /*获取标题*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVB_TITLE)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBtitle(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /*获取标题的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVB_TITLECOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBtitleColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*获取介绍的信息*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVB_CONTEXT)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBcontext(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*获取介绍信息的颜色值*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage
                                    .XML_TAG_NAVB_CONTEXTCOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBcontextColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*获取点击的跳转介绍URL*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVB_URL)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBClickUrl(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*获取图片地址*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVB_IMG)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallBimgUrl(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /**
                             * 竖向的第一个图片的地址
                             */

                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVC_IMG)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCimgUrl(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*竖向的第一个图片的Title*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVC_TITLE)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCtitle(pullParser.nextText
                                            ().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*竖向的第一个图片的Title的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVC_TITLECOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCtitleColor(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }
                            /*竖向的第一个图片的Context*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVC_CONTEXT)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCcontext(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }
                            /*竖向的第一个个图片的Context的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage
                                    .XML_TAG_NAVC_CONTEXTCOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCcontextColor(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*竖向的第一个个图片的点击URL*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVC_URL)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallCClickUrl(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }


                            /**
                             * 竖向的第二个图片的地址
                             */
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVD_IMG)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDimgUrl(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }

                            /*竖向的第二个图片的Title*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVD_TITLE)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDtitle(pullParser.nextText
                                            ().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*竖向的第二个图片的Title的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVD_TITLECOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDtitleColor(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }
                            /*竖向的第二个图片的Context*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVD_CONTEXT)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDcontext(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }
                            /*竖向的第二个图片的Context的颜色*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage
                                    .XML_TAG_NAVD_CONTEXTCOLOR)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDcontextColor(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }
                            }
                            /*竖向的第二个图片的点击URL*/
                            if (tag.equals(LocalPage.SecondSmallNavAPage.XML_TAG_NAVD_URL)) {
                                if (secondSmallNavAPage != null) {
                                    secondSmallNavAPage.setSecondSmallDClickUrl(pullParser
                                            .nextText().trim());

                                } else {
                                    Log.e(MSG, "SecondSmallA为空");
                                }

                            }
                            /**
                             * 中间的横向图片的地址
                             */
                            if (tag.equals(LocalPage.BigCenterHeadpageInstance
                                    .XML_TAG_CENTER_HEAD_START)) {
                                bigCenterHeadpageInstance = LocalPage
                                        .getBigCenterHeadpageInstance();
                            }
                            /*获取中间横向图片的地址*/
                            if (tag.equals(LocalPage.BigCenterHeadpageInstance
                                    .XML_TAG_CENTER_HEAD_IMG)) {
                                if (bigCenterHeadpageInstance != null) {
                                    bigCenterHeadpageInstance.setHeadimg(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "bigCenterHeadpageInstantce为NULL没有初始化");
                                }
                            }
                            /*获取中间横向图片的点击地址*/
                            if (tag.equals(LocalPage.BigCenterHeadpageInstance
                                    .XML_TAG_CENTER_HEAD_URL)) {
                                if (bigCenterHeadpageInstance != null) {
                                    bigCenterHeadpageInstance.setHeadurl(pullParser.nextText()
                                            .trim());
                                }
                            }


                            /**
                             * 第三行的导航的参数取值
                             */
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVA_START)) {
                                if (threeNavPageInstance == null) {
                                    threeNavPageInstance = LocalPage.getThreeNavPageInstance();
                                }
                            }

                            /*获取竖向的第一个图片地址*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVA_IMG)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavAimg(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的点击事件的跳转URL*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVA_URL)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavAurl(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字信息*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVA_BIGTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavAbigTitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVA_BIGTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavAbigTitlecolor(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的小标题的文字标题*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVA_SMALLTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavASmalltitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*设置竖向小标题的颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVA_SMALLTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavASmalltitleColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }

                            /**
                             * 设置第二个竖向的参数
                             */
                            /*获取竖向的第一个图片地址*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVB_IMG)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBimg(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的点击事件的跳转URL*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVB_URL)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBurl(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字信息*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVB_BIGTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBbigTitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVB_BIGTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBbigTitlecolor(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的小标题的文字标题*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVB_SMALLTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBSmalltitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*设置竖向小标题的颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVB_SMALLTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavBSmalltitleColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }


                            /**
                             * 设置第三个竖向的参数
                             */

                            /*获取竖向的第一个图片地址*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVC_IMG)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCimg(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的点击事件的跳转URL*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance.XML_TAG_THREE_NAVC_URL)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCurl(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字信息*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVC_BIGTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCbigTitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的大标题的文字颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVC_BIGTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCbigTitlecolor(pullParser.nextText
                                            ().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*获取竖向的小标题的文字标题*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVC_SMALLTITLE)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCSmalltitle(pullParser.nextText()
                                            .trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }
                            /*设置竖向小标题的颜色*/
                            if (tag.equals(LocalPage.ThreeNavPageInstance
                                    .XML_TAG_THREE_NAVC_SMALLTITLE_COLOR)) {
                                if (threeNavPageInstance != null) {
                                    threeNavPageInstance.setNavCSmalltitleColor(pullParser
                                            .nextText().trim());
                                } else {
                                    Log.e(MSG, "ThreeNavPageInstance为空");
                                }
                            }


                        } catch (Exception e) {
                            Log.e(MSG, "解析首页整理的XML数据中出错:" + e.getMessage());
                        }
                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {


                    }

                    @Override
                    public void onEndDocument() {
                        _rfreshdialog.dismiss();

                        /**
                         * 判断是否需要跳出更新框
                         */

                        if (Tools.getProgramVersion(getContext()) != null) {
                            Log.i(MSG, "本地版本号:" + Tools.getProgramVersion(getContext()) +
                                    "服务器版本号:" + ProgramVersion);
                            if (Tools.getProgramVersion(getContext()).equals(ProgramVersion)) {
                                //版本号相同
                                /**
                                 * 结束完成  开始整理界面
                                 */
                                InitMainPage();
                            } else {
                                //版本号不相同
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                View item = LayoutInflater.from(getContext()).inflate(R.layout
                                        .item_uploadapp, null);
                                builder.setView(item);
                                /*更改更新LOGO的图标信息*/
                                TextView upadLog = item.findViewById(R.id.uploadapp_logo);
                                TextUnt.with(upadLog).setFontFile(getContext(), "canLogo");
                                TextView upadText = item.findViewById(R.id.item_uploadappText);
                                TextUnt.with(upadText).setHtmlText(ProgramVersionText.trim());
                                /*新的大小*/
                                TextView upadNewsize = item.findViewById(R.id
                                        .item_uploadappNewsize);
                                TextUnt.with(upadNewsize).setText("更新版本大小:" + ProgramNewSize.trim
                                        ());
                                TextView upadVersion = item.findViewById(R.id
                                        .item_uploadappNewversion);
                                TextUnt.with(upadVersion).setText("最新版本号:" + ProgramVersion.trim());
                                builder.setCancelable(false);//不能返回
                                TextUnt.with(item, R.id.uploadapp_btnupload).setOnClick(new View
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Net.doDownloadApk(http_addrs.HTTP_ADDR_UPDATE_APK,
                                                getContext());
                                    }
                                });
                                UpdateDialog = builder.show();
                                UpdateDialog.getWindow().setBackgroundDrawableResource(android.R
                                        .color.transparent);
                            }

                        } else {
                            Toast.makeText(getContext(), "没有找到应用程序的Version您可能在使用过程中出现意外,请联系管理人员",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onNotConnect() {

            }

            @Override
            public void onFail(String tOrgin) {

            }
        });
    }


    /**
     * 处理好XML界面之后 开始整理信息
     */
    @SuppressLint("NewApi")
    private void InitMainPage() {
        /**
         * 整理首页左右切换的Banner
         */
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public bannerHolder createHolder() {
                return new bannerHolder();
            }
        }, big_headimgs).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign
                .ALIGN_PARENT_RIGHT).startTurning(3000);
        /**
         * 设置加载的下拉加载的图片地址和点击事件
         */
        LinearLayout headView = item.findViewById(R.id.fragment_main_refreshHead);
        _RefreshScrollView.SetHeadView(LoadingImgurl, LoadingOnclick, headView, 100, R.id
                .fragment_main_Headprogressbar, R.id.fragment_main_refreshHeadImg);
        userToolsInstance = LocalProgramTools.getUserToolsInstance();
        /*设置第一个大标题*/
        TextUnt.with(item, R.id.fragment_main_oneBigtitle).setText(oneBigtitle).setTextColor
                (oneBigtitleColor);
        /*设置第一个小标题*/
        TextUnt.with(item, R.id.fragment_main_oneSmalltitle).setText(oneSmalltitle).setTextColor
                (oneSmalltitleColor);
        /*设置第二个大标题*/
        TextUnt.with(item, R.id.fragment_main_twoBigtitle).setText(twoBigtitle).setTextColor
                (twoBigtitleColor);
        /*设置第二个小标题*/
        TextUnt.with(item, R.id.fragment_main_twoSmalltitle).setText(twoSmalltitle).setTextColor
                (twoSmalltitleColor);
        /*设置第三个大标题*/
        TextUnt.with(item, R.id.fragment_main_threeBigtitle).setText(threeBigtitle).setTextColor
                (threeBigtitleColor);
        /*设置第三个小标题*/
        TextUnt.with(item, R.id.fragment_main_threeSmalltitle).setText(threeSmalltitle)
                .setTextColor(threeSmalltitleColor);
        /*设置第四个大标题*/
        TextUnt.with(item, R.id.fragment_main_fourBigtitle).setText(fourBigtitle).setTextColor
                (fourBigtitleColor);
        /*设置第四个小标题*/
        TextUnt.with(item, R.id.fragment_main_fourSmalltitle).setText(fourSmalltitle)
                .setTextColor(fourSmalltitleColor);

        /**
         * 整理第二个图片导航
         */
        /*加载横向的第一个图片*/
        Glide.with(getContext()).load(secondSmallNavAPage.getSecondSmallAimgUrl().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (secondNavAimg);
        /*加载横向的第二个图片*/
        Glide.with(getContext()).load(secondSmallNavAPage.getSecondSmallBimgUrl().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (secondNavBimg);
        /*加载竖向的第一个图片*/
        Glide.with(getContext()).load(secondSmallNavAPage.getSecondSmallCimgUrl().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (secondNavCimg);
        /*加载竖向的第二个图片*/
        Glide.with(getContext()).load(secondSmallNavAPage.getSecondSmallDimgUrl().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (secondNavDimg);
        /*加载中间的导航的图片*/
        Glide.with(getContext()).load(bigCenterHeadpageInstance.getHeadimg().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (CenterHeadpageImg);

        /*第一个图片的标题*/
        TextView secondNavAtitle = item.findViewById(R.id.fragment_main_secondSmallNavAtitle);
        TextUnt.with(secondNavAtitle).setText(secondSmallNavAPage.getSecondSmallAtitle())
                .setTextColor(secondSmallNavAPage.getSecondSmallAtitleColor());
        /*第一个图片的介绍*/
        TextView secondNavAcontext = item.findViewById(R.id.fragment_main_secondSmallNavAcontext);
        TextUnt.with(secondNavAcontext).setText(secondSmallNavAPage.getSecondSmallAcontext())
                .setTextColor(secondSmallNavAPage.getSecondSmallAcontextColor());
        /*横向的第二个图片的标题*/
        TextView secondNavBtitle = item.findViewById(R.id.fragment_main_secondSmallNavBtitle);
        TextUnt.with(secondNavBtitle).setText(secondSmallNavAPage.getSecondSmallBtitle())
                .setTextColor(secondSmallNavAPage.getSecondSmallBtitleColor());
        /*横向的第二个图片的介绍*/
        TextView secondNavBcontext = item.findViewById(R.id.fragment_main_secondSmallNavBcontext);
        TextUnt.with(secondNavBcontext).setText(secondSmallNavAPage.getSecondSmallBcontext())
                .setTextColor(secondSmallNavAPage.getSecondSmallBcontextColor());
        /*第一个NAV导航的竖向地址的标题*/
        TextView secondNavCtitle = item.findViewById(R.id.fragment_main_secondSmallNavCtitle);
        TextUnt.with(secondNavCtitle).setText(secondSmallNavAPage.getSecondSmallCtitle())
                .setTextColor(secondSmallNavAPage.getSecondSmallAtitleColor().trim());
        /*第一个NAV导航的竖向地址的内容*/
        TextView secondNavCcontext = item.findViewById(R.id.fragment_main_secondSmallNavCcontext);
        TextUnt.with(secondNavCcontext).setText(secondSmallNavAPage.getSecondSmallCcontext())
                .setTextColor(secondSmallNavAPage.getSecondSmallCcontextColor().trim());
        /*第二个NAV导航的竖向地址的的标题*/
        TextView secondNavDtitle = item.findViewById(R.id.fragment_main_secondSmallNavDtitle);
        TextUnt.with(secondNavDtitle).setText(secondSmallNavAPage.getSecondSmallDtitle().trim())
                .setTextColor(secondSmallNavAPage.getSecondSmallDtitleColor().trim());
        /*第二个NAV导航的竖向地址的第内容*/
        TextView secondNavDcontext = item.findViewById(R.id.fragment_main_secondSmallNavDcontext);
        TextUnt.with(secondNavDcontext).setText(secondSmallNavAPage.getSecondSmallDcontext().trim
                ()).setTextColor(secondSmallNavAPage.getSecondSmallDcontextColor().trim());


        /**
         * 整理第三个NAV的导航内容和图片
         */

        /*整理大标题和小标题*/
        Log.i(MSG, "第一个控件图片地址：" + threeNavPageInstance.getNavAimg());
        TextUnt.with(item, R.id.fragment_main_threeABigtitle).setText(threeNavPageInstance
                .getNavAbigTitle()).setTextColor(threeNavPageInstance.getNavAbigTitlecolor().trim
                ());
        TextUnt.with(item, R.id.fragment_main_threeASmalltitle).setText(threeNavPageInstance
                .getNavASmalltitle()).setTextColor(threeNavPageInstance.getNavASmalltitleColor()
                .trim());
        TextUnt.with(item, R.id.fragment_main_threeBBigtitle).setText(threeNavPageInstance
                .getNavBbigTitle()).setTextColor(threeNavPageInstance.getNavBbigTitlecolor().trim
                ());
        TextUnt.with(item, R.id.fragment_main_threeBSmalltitle).setText(threeNavPageInstance
                .getNavBSmalltitle()).setTextColor(threeNavPageInstance.getNavBSmalltitleColor()
                .trim());
        TextUnt.with(item, R.id.fragment_main_threeCBigtitle).setText(threeNavPageInstance
                .getNavCbigTitle()).setTextColor(threeNavPageInstance.getNavCbigTitlecolor().trim
                ());
        TextUnt.with(item, R.id.fragment_main_threeCSmalltitle).setText(threeNavPageInstance
                .getNavCSmalltitle()).setTextColor(threeNavPageInstance.getNavCSmalltitleColor()
                .trim());


        /**
         * 为所有的ITEM的图片设置监听事件
         */
        /*设置中间的Item的点击事件*/
        /*设置第二行第二个图片的点击跳转地址*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_secondSmallNavBBody).setOnclick(new View
                .OnClickListener() {


            @Override
            public void onClick(View v) {
                String _url = (String) v.getTag();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();
            }
        }).setTag(secondSmallNavAPage.getSecondSmallBClickUrl().trim());
        /*设置第二行第3处竖向的图片的点击跳转事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_secondSmallNavCBody).setOnclick(new View
                .OnClickListener() {


            @Override
            public void onClick(View v) {
                String _url = (String) v.getTag();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();
            }
        }).setTag(secondSmallNavAPage.getSecondSmallCClickUrl().trim());
        /*设置第二行4处的竖向图片的点击事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_secondSmallNavDBody).setOnclick(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String _url = (String) v.getTag();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();
            }
        }).setTag(secondSmallNavAPage.getSecondSmallDClickUrl().trim());
        /*第三行第一个BODY设置点击事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_threeAbody).setOnclick(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String _url = v.getTag().toString().trim();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();

            }
        }).setTag(threeNavPageInstance.getNavAurl().trim());
        /*第三行第二个BODY设置点击事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_threeBbody).setOnclick(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String _url = v.getTag().toString().trim();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();

            }
        }).setTag(threeNavPageInstance.getNavBurl().trim());
        /*第三行的第三个BODY设置点击事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_threeCbody).setOnclick(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String _url = v.getTag().toString().trim();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();
            }
        }).setTag(threeNavPageInstance.getNavCurl().trim());
        /*设置中间的Item的加载事件*/
        RelativeLayoutUnt.with(item, R.id.fragment_main_CenterHeadBody).setOnclick(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String _url = v.getTag().toString().trim();
                ToOpenWindowtools toOpenWindowtools = new ToOpenWindowtools(getContext(), _url);
                toOpenWindowtools.toStart();
            }
        }).setTag(bigCenterHeadpageInstance.getHeadurl().trim());
        /*加载第三个导航的第一个竖向的图片*/
        Glide.with(getContext()).load(threeNavPageInstance.getNavAimg()).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(threeNavAimg);
        /*加载第三个导航的第二个竖向图片*/
        Glide.with(getContext()).load(threeNavPageInstance.getNavBimg()).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(threeNavBimg);
        /*加载第三个导航的第二个竖向图片*/
        Glide.with(getContext()).load(threeNavPageInstance.getNavCimg()).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(threeNavCimg);


    }

    /**
     * 定义一个类 用来存储首页中Big_headImg的参数信息
     */
    class BigheadImg {
        public String onClick_url;
        public String showImg;
        public String TAG_ONCLICK_URL = "onClick_url";
        public String TAG_SHOW_IMG = "showImg";

        public void setOnClick_url(String onClick_url) {
            this.onClick_url = onClick_url;
        }

        public void setShowImg(String showImg) {
            this.showImg = showImg;
        }

        public String getOnClick_url() {
            return onClick_url;
        }

        public String getShowImg() {
            return showImg;
        }

        public String getTAG_ONCLICK_URL() {
            return TAG_ONCLICK_URL;
        }

        public String getTAG_SHOW_IMG() {
            return TAG_SHOW_IMG;
        }
    }


    /**
     * 检查权限
     */
    @SuppressLint("NewApi")
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 1);
            Log.i(MSG, "没有授权权限!");
        } else {
            Log.i(MSG, "已经授权读写权限成功!");
        }
    }

    /**
     * 显示商品的参数集合
     */
    class ShowshopList {
        String _title;/*标题*/
        String _tp;/*批发价格*/
        String _company;/*配送单位*/
        String _exp;/*保质期*/
        String _pd;/*生产日期*/
        String _business;/*对接商家*/
        String _businessimg;/*对接商家的图片地址*/
        String _img;/*商品的图片地址*/
        String _onlyid;/*商品的唯一ID*/
        String _like;/*判断用户是否喜欢*/

        public String get_like() {
            return _like;
        }

        public void set_like(String _like) {
            this._like = _like;
        }

        public String get_onlyid() {
            return _onlyid;
        }

        public void set_onlyid(String _onlyid) {
            this._onlyid = _onlyid;
        }

        public String get_img() {
            return _img;
        }

        public void set_img(String _img) {
            this._img = _img;
        }

        public String get_title() {
            return _title;
        }

        public void set_title(String _title) {
            this._title = _title;
        }

        public String get_tp() {
            return _tp;
        }

        public void set_tp(String _su) {
            this._tp = _su;
        }

        public String get_company() {
            return _company;
        }

        public void set_company(String _company) {
            this._company = _company;
        }

        public String get_exp() {
            return _exp;
        }

        public void set_exp(String _exp) {
            this._exp = _exp;
        }

        public String get_pd() {
            return _pd;
        }

        public void set_pd(String _pd) {
            this._pd = _pd;
        }

        public String get_business() {
            return _business;
        }

        public void set_business(String _business) {
            this._business = _business;
        }

        public String get_businessimg() {
            return _businessimg;
        }

        public void set_businessimg(String _businessimg) {
            this._businessimg = _businessimg;
        }

    }

    public class bannerHolder implements Holder<String> {
        private ImageView img;

        @SuppressLint("NewApi")
        @Override
        public View createView(Context context) {
            img = new ImageView(context);
            img.setAdjustViewBounds(true);
            img.setBackground(Tools.CreateDrawable(1,"#ffffff","#ffffff",30));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            return img;
        }

        @SuppressLint("NewApi")
        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(getContext()).load(data.trim()).into(img);

        }
    }
}

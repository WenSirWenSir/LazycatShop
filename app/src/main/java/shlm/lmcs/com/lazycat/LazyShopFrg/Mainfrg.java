package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
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
import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools.isPermission;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment implements TencentLocationListener {
    /**
     * -------------------------------------------
     * 显示商品的参数
     */
    private ShowshopList showshopList = null;
    ArrayList<ShowshopList> showList = new ArrayList<ShowshopList>();
    private final static String SHOW_SHOPLIST_BEGIN = "Showshoplist";
    private final static String SHOW_SHOPLIST_TITLE = "shopTitle";
    private final static String SHOW_SHOPLIST_TP = "shopTp";
    private final static String SHOW_SHOPLIST_IMG = "shopImg";
    private final static String SHOW_SHOPLIST_COMPANY = "shopCompany";
    private final static String SHOW_SHOPLIST_EXP = "shopExp";
    private final static String SHOW_SHOPLIST_PD = "shopPd";
    private final static String SHOW_SHOPLIST_BUSINESS = "business";
    private final static String SHOW_SHOPLIST_BUSINESSIMG = "businessImg";

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
    private TencentLocationRequest LocationRequest;
    private TencentLocationListener locationListener;
    private TencentLocationManager locationManager;
    private View item;
    private static final String MSG = "Mainfrg.java[+]";
    private Double StLong;/*经度*/
    private Double StLat;/*维度*/
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
    private ImageView bigHead_img;/*头部第一个Img的控件*/
    private LocalPage.SecondSmallNavAPage secondSmallNavAPage;
    private LocalPage.BigCenterHeadpageInstance bigCenterHeadpageInstance;
    private LocalPage.ThreeNavPageInstance threeNavPageInstance;
    private ImageView secondNavAimg;/*第二个导航中的第一个子导航的NAV*/
    private ImageView secondNavBimg;/*第二个导航中的第二个子导航的NAV*/
    private ImageView secondNavCimg;/*第二个导航中的第三个子导航的图片*/
    private ImageView secondNavDimg;/*第四个导航中的第四个子导航的图片*/
    private ImageView threeNavAimg;/*第三排的第一个竖向图片*/
    private ImageView _RefreshheadImg;/*滑动的控件的图片的控件*/
    private String ProgramVersion;/*应用程序版本号*/
    private String ProgramNewSize;/*更新的文件的大小*/
    private String ProgramVersionText;/*更新之后的一些简介*/
    private ImageView CenterHeadpageImg;
    private static final int REFRESH_STOP_MESSAGELOAD = 0;/*停止刷新 隐藏广告*/
    private AlertDialog UpdateDialog;
    private Boolean isLogin;/*判断本地是否有登录数据*/
    private Boolean isLoadEnd;/*判断是否加载完毕*/
    private int Position;/*设置position用来底部加载*/
    private AlertDialog alertDialog = null;

    /**
     * 模块数据存储
     */
    private RefreshScrollView _RefreshScrollView;

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
        /*判断是否有定位权限 没有定位权限就去申请定位权限*/
        onStartMain();
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
        /*头部第一个Image的广告控件*/
        bigHead_img = item.findViewById(R.id.fragment_main_bigHeadMsg);
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


        /**
         * 初始化 DIALOG
         */
       /* refreshDialog = new WaitDialog.RefreshDialog(getActivity());
        WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
        wait_itme_dialogpage.setImg(R.id.item_wait_img);
        wait_itme_dialogpage.setView(R.layout.item_wait);
        wait_itme_dialogpage.setTitle(R.id.item_wait_title);
        refreshDialog.Init(wait_itme_dialogpage);
        refreshDialog.showRefreshDialog("定位中...", false);*/
        /*设置监听*/
/*        locationListener.setOnReceiveLocationListener(new LocationMapListener
                .onReceiveLocationListener() {
            @Override
            public void onHasAddr(BDLocation bdLocation) {
                if (bdLocation.hasAddr()) {
                    *//*存在地址信息*//*
                    init(item);
                    *//*设置地址标题*//*
                    TextView addr_title = SearchViewBody.findViewById(R.id.assembly_head_addrTitle);
                    String City = bdLocation.getCity();*//*获取城市信息*//*
                    String District = bdLocation.getDistrict();*//*获取地区信息  用县区作为搜索条件*//*
                    addr_title.setText(District + bdLocation.getStreet().trim());
                    *//**
         * 判断该地址是否在配送范围之内
         *//*
                    getServiceAddr(District);
                    refreshDialog.dismiss();
                }
            }

            @Override
            public void onNotAddr(BDLocation bdLocation) {
                *//*没有地址信息*//*
                init(item);
                getServiceAddr("上杭县");
                refreshDialog.dismiss();
                //body.setVisibility(View.VISIBLE);
                //body.removeAllViews();
                //View item_noservice = inflater.inflate(R.layout.item_noservice, null);
                //body.addView(item_noservice);
                //Log.e(MSG, "没有地址信息");
                TextView addr_title = SearchViewBody.findViewById(R.id.assembly_head_addrTitle);
                addr_title.setText("上杭县上杭大道");

            }
        });*/
/*
        locationClient.start();
*/

        return item;

    }


    /**
     * 最先开始
     */
    @SuppressLint("NewApi")
    private void onStartMain() {
        if (isPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            StartLocaling();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View item = LayoutInflater.from(getContext()).inflate(R.layout.alert_message, null);
            builder.setView(item);
            TextView Tv_context = item.findViewById(R.id.alert_messageText);
            TextUnt.with(Tv_context).setText(getResources().getString(R.string.quitMsg));
            TextView Tv_btnconfirm = item.findViewById(R.id.alert_messageBtnConfirm);
            TextUnt.with(Tv_btnconfirm).setTextColor("#ffffff").setBackColor("#f30d65");
            builder.setCancelable(false);
            Tv_btnconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            alertDialog = builder.show();
        }
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
                /*显示一个加载中的DIALOG*/
                /*初始化一个DIALOG*/
                WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog(getContext());
                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
                wait_itme_dialogpage.setImg(R.id.item_wait_img);
                wait_itme_dialogpage.setView(R.layout.item_wait);
                wait_itme_dialogpage.setCanClose(false);
                wait_itme_dialogpage.setTitle(R.id.item_wait_title);
                refreshDialog.Init(wait_itme_dialogpage);
                refreshDialog.showRefreshDialog("加载中...", false);
                refreshDialog.show();
                /*向尾部更新5个数据信息*/

                /**
                 * 处理首页的商品展示信息
                 */

                for (int i = 0; i < 2; i++) {
                    Position += 1;
                    if (Position >= showList.size() && isLoadEnd == false) {
                        isLoadEnd = true;
                        /*没有更多的信息 那就加载一个ITEM*/
                        TextView endTitle = new TextView(refreshBody.getContext());
                        endTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                                .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                                .WRAP_CONTENT));
                        endTitle.setPadding(0, 10, 0, 10);
                        endTitle.setGravity(Gravity.CENTER);
                        TextUnt.with(endTitle).setText("暂时没有推荐的商品啦,您可以去分类中心看看哦").setTextSize(13)
                                .setTextColor("#666666");
                        refreshBody.addView(endTitle);
                        refreshDialog.dismiss();
                    } else {
                        try {

                            View shopItem = LayoutInflater.from(getContext()).inflate(R.layout
                                    .item_mainshoplist, null);
                            shopItem.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShowshopList _list = (ShowshopList) v.getTag();
                                    LazyCatFragmentStartActivityWithBundler(ShowshopOffice.class,
                                            Config.Windows.GET_WINDOW_VALUE_SHOP_MESSAGE, _list
                                                    .get_title().trim(),
                                            GET_WINDOW_VALUE_SHOP_ACTION, LocalValues
                                                    .VALUES_SEARCH.VALUES_TO_SEARCH_SHOPKEYWORD);
                                }
                            });
                            //shopItem.setTag(showList.get(Position));
                            /*进行Item处理监听*/
                            ImageView btnLike = shopItem.findViewById(R.id
                                    .item_mainshoplist_btnLike);
                            /*是否喜欢*/
                            TextView Itemtitle = shopItem.findViewById(R.id
                                    .item_mainshoplist_Title);
                            /*标题*/
                            TextView ItemTp = shopItem.findViewById(R.id.item_mainshoplist_Tp);
                            /*批发价格*/
                            TextView ItemCompany = shopItem.findViewById(R.id
                                    .item_mainshoplist_Company);
                            /*批发规格单位*/
                            /*生产日期和保质期*/
                            TextView ItemExpAndPd = shopItem.findViewById(R.id
                                    .item_mainshoplist_ExpAndPd);
                            ImageView ItemShopimg = shopItem.findViewById(R.id
                                    .item_mainshoplist_Shopimg);/*图片地址*/

                            /*商户名称*/
                            TextView ItemBusiness = shopItem.findViewById(R.id
                                    .item_mainshoplist_business);
                            /*设置生产日期和保质期*/
                            /*设置单位*/
                            TextUnt.with(ItemCompany).setText("/" + showList.get(Position)
                                    .get_company());
                            /*设置批发价格*/
                            if (isLogin) {
                                TextUnt.with(ItemTp).setText(showList.get(Position).get_tp());
                            } else {
                                TextUnt.with(ItemTp).setText("*.*");
                            }
                            /*设置供货商*/
                            TextUnt.with(ItemBusiness).setText(showList.get(Position)
                                    .get_business());
                            /*设置生产日期和保质期*/
                            TextUnt.with(ItemExpAndPd).setText(showList.get(Position).get_exp() +
                                    "生产·" + showList.get(Position).get_pd() + "天保质");
                            /*加载图片*/
                            Glide.with(getContext()).load("http://i.caigoubao" + "" + "" + "" +
                                    "" + ".cc/583105/SHOP_DATABASE/" + showList.get(Position)
                                    .get_img()).skipMemoryCache(false).diskCacheStrategy
                                    (DiskCacheStrategy.NONE).into(ItemShopimg);

                            Itemtitle.setText(showList.get(Position).get_title());
                            /*进行数据判断 用户是否收藏过该商品 如果没有就设置为灰色*/
                            btnLike.setImageResource(R.drawable.ico_nolike);
                            btnLike.setTag(LocalValues.VALUES_SHOPLIKES.SHOP_NO_LIKE);
                            btnLike.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int tag = (int) v.getTag();
                                    if (tag == LocalValues.VALUES_SHOPLIKES.SHOP_NO_LIKE) {
                                        /*设置为喜欢的图标 并且发送服务器*/
                                        ImageView img = (ImageView) v;
                                        img.setImageResource(R.drawable.ico_like);
                                    } else {
                                        /*设置为不喜欢的图标 并且发送服务器*/
                                        ImageView img = (ImageView) v;
                                        img.setImageResource(R.drawable.ico_nolike);
                                    }
                                }
                            });
                            refreshBody.addView(shopItem);
                        } catch (Exception e) {
                            Log.e(MSG, "显示展示商品的信息失败信息:" + e.getMessage());
                        }
                        refreshDialog.dismiss();
                    }

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
        item.findViewById(R.id.fragment_main_btnScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载扫一扫
                LazyCatFragmetStartAct(ScanQRCodeAct.class);
            }
        });

    }


    /**
     * 判断该地址是否开通服务
     */
    @SuppressLint("NewApi")
    private void getServiceAddr(String CityCode) {
        Net.doGet(getContext(), Config.HTTP_ADDR.getIsServiceIn(), new Net
                .onVisitInterServiceListener() {
            @Override
            public WaitDialog.RefreshDialog onStartLoad() {/*初始化一个DIALOG*/
                /*初始化一个DIALOG*/
                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog
                        (getContext());
                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
                wait_itme_dialogpage.setImg(R.id.item_wait_img);
                wait_itme_dialogpage.setView(R.layout.item_wait);
                wait_itme_dialogpage.setCanClose(false);
                wait_itme_dialogpage.setTitle(R.id.item_wait_title);
                refreshDialog.Init(wait_itme_dialogpage);
                refreshDialog.showRefreshDialog("加载中...", false);
                return refreshDialog;
            }

            @Override
            public void onSucess(String tOrgin, final WaitDialog.RefreshDialog _RefreshDialog) {
                _RefreshDialog.dismiss();
                if (!TextUtils.isEmpty(tOrgin)) {
                    /*调试输出*/
                    LocalProgramTools.ProgramServiceTools programServiceTools = LocalProgramTools
                            .getServiceToolsInstatnce();
                    programServiceTools.set_Service(tOrgin.trim());
                    programServiceTools.SaveService();/*保存服务器*/
                    /*不是为空的话 就去访问网络*/
                    getConfigXml();/*获取首页的配置文件*/
                    checkPermission();
                } else {
                    /*没有地址  没有开放*/
                    Log.i(MSG, "该地区服务器地址没开放");

                }
                Log.i(MSG, tOrgin.trim());
            }

            @Override
            public void onNotConnect() {

            }

            @Override
            public void onFail(String tOrgin) {

            }
        }, Config.HttpAction.ACTION_CITYCODE, CityCode);
    }


    @SuppressLint("NewApi")
    private void getConfigXml() {

        Net.doPostXml(getContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GET_MAINCONFIGPAGE, new ProgramInterface() {



            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {
                Log.i(MSG,"doPost:" + data.trim());
            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        },"");
        /**
         * 获取地区服务器关于该地址的首页配置信息
         * fragment_main_bigHeadMsg
         */
        Net.doGet(getContext(), LocalValues.HTTP_ADDRS.HTTP_ADDR_GET_MAINCONFIGPAGE, new Net
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
                refreshDialog.showRefreshDialog("加载中...", false);
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
                                bigheadImg.setShowImg(pullParser.nextText().trim());
                            }


                            /**
                             * 判断是不是SecondSmallNavA
                             */
                            if (tag.equals(LocalPage.SecondSmallNavAPage.BEGIN_XML)) {
                                secondSmallNavAPage = LocalPage.getSecondSmallNavAPageInstance();
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
                            /**
                             * 获取首页显示商品的数据
                             */
                            if (tag.equals(SHOW_SHOPLIST_BEGIN)) {
                                /*重新申请一个ShowshopList*/
                                showshopList = new ShowshopList();
                            }
                            /*获取标题*/
                            if (tag.equals(SHOW_SHOPLIST_TITLE)) {
                                if (showshopList != null) {
                                    showshopList.set_title(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }

                            /*获取批发价格*/
                            if (tag.equals(SHOW_SHOPLIST_TP)) {
                                if (showshopList != null) {
                                    showshopList.set_tp(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }
                            /*获取商品图片地址*/
                            if (tag.equals(SHOW_SHOPLIST_IMG)) {
                                if (showshopList != null) {
                                    showshopList.set_img(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }

                            /*获取商品单位*/
                            if (tag.equals(SHOW_SHOPLIST_COMPANY)) {
                                if (showshopList != null) {
                                    showshopList.set_company(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }
                            /*获取商品保质期*/
                            if (tag.equals(SHOW_SHOPLIST_EXP)) {
                                if (showshopList != null) {
                                    showshopList.set_exp(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }
                            /*获取商品单位*/
                            if (tag.equals(SHOW_SHOPLIST_PD)) {
                                if (showshopList != null) {
                                    showshopList.set_pd(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }
                            /*获取商品单位*/
                            if (tag.equals(SHOW_SHOPLIST_BUSINESS)) {
                                if (showshopList != null) {
                                    showshopList.set_business(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }
                            /*获取商品单位*/
                            if (tag.equals(SHOW_SHOPLIST_BUSINESSIMG)) {
                                if (showshopList != null) {
                                    showshopList.set_businessimg(pullParser.nextText().trim());
                                } else {
                                    Log.e(MSG, "XML数据错误显示首页商品数据的数组为空");
                                }
                            }


                        } catch (Exception e) {
                            Log.e(MSG, "解析首页整理的XML数据中出错:" + e.getMessage());
                        }
                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

                        /*在堆栈中 存入一个数据之后制空*/
                        if (tag.equals(SHOW_SHOPLIST_BEGIN)) {
                            showList.add(showshopList);
                            showshopList = null;
                        }

                    }

                    @Override
                    public void onEndDocument() {
                        _rfreshdialog.dismiss();
                        /**
                         * 结束完成  开始整理界面
                         */
                        InitMainPage();
                        /**
                         * 判断是否需要跳出更新框
                         */

                        if (Tools.getProgramVersion(getContext()) != null) {
                            Log.i(MSG, "本地版本号:" + Tools.getProgramVersion(getContext()) +
                                    "服务器版本号:" + ProgramVersion);
                            if (Tools.getProgramVersion(getContext()).equals(ProgramVersion)) {
                                //版本号相同
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
         * 设置加载的下拉加载的图片地址和点击事件
         */
        LinearLayout headView = item.findViewById(R.id.fragment_main_refreshHead);
        _RefreshScrollView.SetHeadView(LoadingImgurl, LoadingOnclick, headView, 100, R.id
                .fragment_main_Headprogressbar, R.id.fragment_main_refreshHeadImg);
        /**
         * 判断本地是否登录账户
         */
        userToolsInstance = LocalProgramTools.getUserToolsInstance();

        /*加载顶部的第一个Big_headimg*/
        Glide.with(getContext()).load(bigheadImg.getShowImg()).into(bigHead_img);

        /**
         * 整理第二个图片导航
         */
        /*加载横向的第一个图片*/
        Glide.with(getContext()).load(secondSmallNavAPage.getSecondSmallAimgUrl().trim())
                .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.NONE).into
                (secondNavAimg);
        /*设置图片的点击跳转地址*/
        secondNavAimg.setTag(secondSmallNavAPage.getSecondSmallAClickUrl());
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
        /*加载第三个导航的第一个竖向的图片*/
        Glide.with(getContext()).load(threeNavPageInstance.getNavAimg()).skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(threeNavAimg);
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

    }


    /**
     * 腾讯地图
     *
     * @param tencentLocation
     * @param i
     * @param s
     */
    @SuppressLint("NewApi")
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        try {
            if (TencentLocation.ERROR_OK == 0) {
                //定位成功  就整理界面
                Log.i(MSG, "获取的地址为:" + tencentLocation.getCityCode());
                StCitycode = tencentLocation.getCityCode();
                /*莫名的定位失败就重新打开应用*/
                if (tencentLocation.getCityCode() == null) {
                    locationManager.removeUpdates(this);
                    LocationRequest = null;
                    /*确定用户的物理地址*/
                    LocationRequest = TencentLocationRequest.create();
                    LocationRequest.setInterval(1000);
                    LocationRequest.setAllowCache(false);
                    //包含经纬度位置所处的中国大陆行政划区
                    LocationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);//
                    locationManager = TencentLocationManager.getInstance(getContext());
                    locationManager.requestLocationUpdates(LocationRequest, this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View item = LayoutInflater.from(getContext()).inflate(R.layout.alert_message,
                            null);
                    TextView Tv_title = item.findViewById(R.id.alert_messageTitle);
                    TextUnt.with(Tv_title).setText("错误提示").setTextColor("#f30d65");
                    TextView Tv_context = item.findViewById(R.id.alert_messageText);
                    TextUnt.with(Tv_context).setText(getResources().getString(R.string
                            .ErrorNoCityCode)).setTextColor("#f03d65");
                    TextView Tv_btnconfirm = item.findViewById(R.id.alert_messageBtnConfirm);
                    TextUnt.with(Tv_btnconfirm).setText("重新启动").setTextColor("#ffffff")
                            .setBackColor("#f30d65");
                    Tv_btnconfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });
                    builder.setView(item);
                    //alertDialog = builder.show();
                } else {
                    getServiceAddr(StCitycode);
                    StLong = tencentLocation.getLongitude();/*获取经度*/
                    StLat = tencentLocation.getLatitude();/*获取维度*/
                    locationManager.removeUpdates(this);
                    /**
                     * 定位成功  获取地区服务器地址
                     */
                    listener(item);

                }

            } else {
                /*定位失败  直接弹出对话框 禁止整理界面*/
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View item = LayoutInflater.from(getContext()).inflate(R.layout.alert_message, null);
                TextView Tv_title = item.findViewById(R.id.alert_messageTitle);
                TextUnt.with(Tv_title).setText("错误提示").setTextColor("#f30d65");
                /*错误的内容*/
                TextView Tv_context = item.findViewById(R.id.alert_messageText);
                Tv_context.setText(getResources().getString(R.string.ErrorLocal));
                /*知晓的按钮*/
                TextView Tv_btnConfirm = item.findViewById(R.id.alert_messageBtnConfirm);
                TextUnt.with(Tv_btnConfirm).setText("我已知晓").setTextColor("#ffffff").setBackColor
                        ("#f30d65");
                builder.setView(item);
                builder.show();
                Toast.makeText(getContext(), "定位失败,错误原因:" + i, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(MSG, "处理定位的时候发生失败:" + e.getMessage());
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

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

    /**
     * 获取用户的地理位置
     */
    @SuppressLint("NewApi")
    private void StartLocaling() {
        /*确定用户的物理地址*/
        LocationRequest = TencentLocationRequest.create();
        LocationRequest.setInterval(1000);
        LocationRequest.setAllowCache(false);
        //包含经纬度位置所处的中国大陆行政划区
        LocationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);//
        locationManager = TencentLocationManager.getInstance(getContext());
        int error = locationManager.requestLocationUpdates(LocationRequest, this);
        Log.i(MSG, "腾讯地图调用:" + error);
    }
}

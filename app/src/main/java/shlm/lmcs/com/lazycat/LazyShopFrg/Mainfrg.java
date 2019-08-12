package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;

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
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.ArcView;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;
import shlm.lmcs.com.lazycat.LazyShopInterface.LocationMapListener;
import shlm.lmcs.com.lazycat.LazyShopMonitor.BrandSingMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.MerchantMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.Monitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.MonitorStatic;
import shlm.lmcs.com.lazycat.LazyShopMonitor.NewShopinMonitor;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalMonitorPage;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalShopModuleValuepage;
import shlm.lmcs.com.lazycat.LazyShopTools.LocalMonitorHandler;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment {

    /**
     * 百度定位模块
     */
    private LocationClient locationClient;/*接口*/
    private LocationClientOption locationClientOption;/*参数*/
    private LocationMapListener locationListener;/*回调接口*/
    private View item;

    /**
     * DIALOG
     */
    private WaitDialog.RefreshDialog refreshDialog = null;

    /**
     * 存在服务器参数表格
     */

    private XmlTagValuesFactory.Init_btnValues init_btnValues;
    private XmlTagValuesFactory.Init_filletValues init_filletValues;


    /*首页首先展示的模块的数量 不要浪费了CPU*/
    private int showModue = 3;/*显示3个*/
    /**
     * 模块数据存储
     */
    private LocalShopModuleValuepage.BusinessPromotionValues businessPromotionValues;
    private LinearLayout horizontaladv_body;
    private LayoutInflater inflater;
    private RelativeLayout big_body;/*最外层的布局 用来切换外卖使用*/
    private RefreshScrollView _RefreshScrollView;
    private static final int GONE_HEADE_IMG = 0;
    private LinearLayout body;
    private String MSG = "Mainfrg.java[+]";
    private String LOCAL_MSG = "LocalServiceMainFrg.java[+]";
    private Timer _GoneHeadimg;
    private String Search_input_line_color;/*输入框线条颜色*/
    private String Search_input_back_color;/*输入框背景颜色*/
    private ArcView arcView;/*主页的拱形*/
    private XmlTagValuesFactory.XMLTagMainNavValues navPage = new XmlTagValuesFactory
            .XMLTagMainNavValues();
    private ArrayList<XmlTagValuesFactory.XMLTagMainNavValues> nav_list = new
            ArrayList<XmlTagValuesFactory.XMLTagMainNavValues>();
    private View _navaBody;
    private View SearchViewBody;/*搜索框的父窗口布局*/
    private int window_width;/*屏幕的宽度*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GONE_HEADE_IMG:
                    _GoneHeadimg.cancel();
                    if (_RefreshScrollView != null) {
                        _RefreshScrollView.stopRefresh();//停止刷新
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        /*确定用户的物理地址*/
        SDKInitializer.initialize(getActivity().getApplicationContext());
        item = inflater.inflate(R.layout.fragment_main, null);
        body = item.findViewById(R.id.fragment_main_body);/*主要的布局*/
        body.setVisibility(View.GONE);/*先隐藏起来*/
        SearchViewBody = item.findViewById(R.id.fragment_main_head);/*搜索框的父布局*/
        //初始化一个背景样式
        item.findViewById(R.id.assembly_head_editText).setFocusable(false);
        big_body = item.findViewById(R.id.fragment_main_bigBody);/*最外层布局*/
        arcView = item.findViewById(R.id.fragment_main_arcView);
        arcView.setBackGroundColor("#e9e9e9");
        locationClientOption = new LocationClientOption();
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationListener = new LocationMapListener();
        locationClientOption.setOpenGps(true);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setOpenAutoNotifyMode();/*设置自动回调*/
        locationClient.setLocOption(locationClientOption);
        locationClient.registerLocationListener(locationListener);

        /**
         * 初始化 DIALOG
         */
        refreshDialog = new WaitDialog.RefreshDialog(getActivity());
        WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();
        wait_itme_dialogpage.setImg(R.id.item_wait_img);
        wait_itme_dialogpage.setView(R.layout.item_wait);
        wait_itme_dialogpage.setTitle(R.id.item_wait_title);
        refreshDialog.Init(wait_itme_dialogpage);
        refreshDialog.showRefreshDialog("定位中...", false);
        InitPageXml();
        /*设置监听*/
        locationListener.setOnReceiveLocationListener(new LocationMapListener
                .onReceiveLocationListener() {
            @Override
            public void onHasAddr(BDLocation bdLocation) {
                if (bdLocation.hasAddr()) {
                    /*存在地址信息*/
                    init(item);
                    /*设置地址标题*/
                    TextView addr_title = SearchViewBody.findViewById(R.id.assembly_head_addrTitle);

                    String City = bdLocation.getCity();/*获取城市信息*/
                    String District = bdLocation.getDistrict();/*获取地区信息  用县区作为搜索条件*/
                    addr_title.setText(District + bdLocation.getStreet().trim());
                    /**
                     * 判断该地址是否在配送范围之内
                     */
                    getServiceAddr(District);
                    refreshDialog.dismiss();
                }
            }

            @Override
            public void onNotAddr(BDLocation bdLocation) {
                /*没有地址信息*/
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
        });
        locationClient.start();
        return item;

    }


    /**
     * 判断该地址是否开通服务
     */
    @SuppressLint("NewApi")
    private void getServiceAddr(String Districe) {
        Net.doGet(getContext(), Config.HTTP_ADDR.getIsServiceIn(), new Net
                .onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                if (!TextUtils.isEmpty(tOrgin)) {
                    /*调试输出*/
                    Log.i(MSG, "地区服务器地址:" + tOrgin.trim());
                    /*不是为空的话 就去访问网络*/
                    LocalValues.ADDR_SERVICE = tOrgin.trim();
                    InitMain();
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
        }, Config.HttpAction.ACTION_ADDR, Districe);
    }


    /**
     * 该地区服务器开放 整理界面
     */
    @SuppressLint("NewApi")
    private void InitMain() {

        /*开始整理地区服务器*/
        if (!TextUtils.isEmpty(LocalValues.ADDR_SERVICE)) {
            /*存在地址 开始访问*/
            Net.doGet(getContext(), Config.SERVICE_API.getInitMainXml(), new Net
                    .onVisitInterServiceListener() {
                @Override
                public void onSucess(String tOrgin) {
                    /*开始处理数据*/
                    Log.i(LOCAL_MSG, "首页初始化数据:" + tOrgin.trim());
                    XmlanalysisFactory xml = new XmlanalysisFactory(tOrgin.trim());
                    xml.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
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
                                 * 判断文档开头  获取对应的方法
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_XML_START)) {
                                    init_btnValues = XmlTagValuesFactory
                                            .getXmlServiceInitBtnInstance();
                                }
                                /**
                                 * 判断文档的开头 获取对应的方法
                                 */
                                if (tag.equals(XmlTagValuesFactory.Init_fillet.ACTION_XML_START)) {
                                    if (init_filletValues == null) {
                                        /*获取对象*/
                                        init_filletValues = XmlTagValuesFactory
                                                .getXmlServiceInitPageInstance();
                                    }
                                }

                                /*判断是否是拱形界面的颜色*/
                                if (tag.equals(XmlTagValuesFactory.Init_fillet.ACTION_ARC_VIEW)) {
                                    if (init_filletValues != null) {
                                        init_filletValues.setArcViewColor(pullParser.nextText()
                                                .trim());
                                    }
                                }

                                /**
                                 * 获取第一个按钮的值
                                 */
                                /*判断是否是第一个的按钮的标题*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_TITLE)) {
                                    init_btnValues.setFirst_btn_title(pullParser.nextText().trim());
                                }
                                /*判断是否为第一个按钮的图片*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_IMG)) {
                                    init_btnValues.setFirst_btn_img(pullParser.nextText().trim());
                                }
                                /*判断是否为第一个按钮的状态*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_STATUS)) {
                                    init_btnValues.setFirst_btn_status(pullParser.nextText().trim
                                            ());
                                }
                                /*判断是否为第一个按钮的链接地址*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_URL)) {
                                    init_btnValues.setFirst_btn_url(pullParser.nextText().trim());
                                }
                                /*是否为第一个按钮的标题的颜色*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_TEXTCOLOR)) {
                                    init_btnValues.setFirst_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }
                                /*判断是否为第一个按钮的状态的背景颜色*/
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setFirst_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIRST_BTN_STATUSCOLOR)) {
                                    init_btnValues.setFirst_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }

                                /***
                                 * 初始化第二个按钮的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_TITLE)) {
                                    init_btnValues.setSecond_btn_title(pullParser.nextText().trim
                                            ());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_STATUS)) {
                                    init_btnValues.setSecond_btn_status(pullParser.nextText()
                                            .trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_IMG)) {
                                    init_btnValues.setSecond_btn_img(pullParser.nextText().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_URL)) {
                                    init_btnValues.setSecond_btn_url(pullParser.nextText().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setSecond_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_TEXTCOLOR)) {
                                    init_btnValues.setSecond_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SECOND_BTN_STATUSCOLOR)) {
                                    init_btnValues.setSecond_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }

                                /**
                                 * 初始化第三个按钮的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_TITLE)) {
                                    init_btnValues.setThree_btn_title(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_STATUS)) {
                                    init_btnValues.setThree_btn_status(pullParser.nextText().trim
                                            ());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_IMG)) {
                                    init_btnValues.setThree_btn_img(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_URL)) {
                                    init_btnValues.setThree_btn_url(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setThree_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_STATUSCOLOR)) {
                                    init_btnValues.setThree_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_THREE_BTN_TEXTCOLOR)) {
                                    init_btnValues.setThree_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }
                                /**
                                 * 初始化第四个按钮的值
                                 *
                                 */

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FOUR_BTN_TITLE)) {
                                    init_btnValues.setFour_btn_title(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FOUR_BTN_STATUS)) {
                                    init_btnValues.setFour_btn_status(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_FOUR_BTN_URL)) {
                                    init_btnValues.setFour_btn_url(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_FOUR_BTN_IMG)) {
                                    init_btnValues.setFour_btn_img(pullParser.nextText().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FOUR_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setFour_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FOUR_BTN_STATUSCOLOR)) {
                                    init_btnValues.setFour_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }


                                /**
                                 * 第五个按钮的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIVE_BTN_TITLE)) {
                                    init_btnValues.setFive_btn_title(pullParser.nextText().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIVE_BTN_STATUS)) {
                                    init_btnValues.setFive_btn_status(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_FIVE_BTN_IMG)) {
                                    init_btnValues.setFive_btn_img(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_FIVE_BTN_URL)) {
                                    init_btnValues.setFive_btn_url(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIVE_BTN_STATUSCOLOR)) {
                                    init_btnValues.setFive_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIVE_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setFive_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_FIVE_BTN_TEXTCOLOR)) {
                                    init_btnValues.setFive_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }


                                /**
                                 * 第六个按钮的参数
                                 */

                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SIX_BTN_TITLE)) {
                                    init_btnValues.setSix_btn_title(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_SIX_BTN_IMG)) {
                                    init_btnValues.setSix_btn_img(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SIX_BTN_STATUS)) {
                                    init_btnValues.setSix_btn_status(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SIX_BTN_STATUSCOLOR)) {
                                    init_btnValues.setSix_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_SIX_BTN_URL)) {
                                    init_btnValues.setSix_btn_url(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SIX_BTN_STATUSBACKGROUND)) {
                                    init_btnValues.setSix_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SIX_BTN_TEXTCOLOR)) {
                                    init_btnValues.setSix_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }

                                /**
                                 * 第七个按钮的参数
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_SEVENT_TITLE)) {
                                    init_btnValues.setSeven_btn_title(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SEVENT_STATUS)) {
                                    init_btnValues.setSeven_btn_status(pullParser.nextText().trim
                                            ());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SEVENT_STATUSBACKGROUND)) {
                                    init_btnValues.setSeven_btn_status_backgrond(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SEVENT_STATUSCOLOR)) {
                                    init_btnValues.setSeven_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_SEVENT_TEXTCOLOR)) {
                                    init_btnValues.setSeven_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_SEVENT_IMG)) {
                                    init_btnValues.setSeven_btn_img(pullParser.nextText());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_SEVENT_URL)) {
                                    init_btnValues.setSeven_btn_url(pullParser.nextText().trim());
                                }
                                /**
                                 * 第八个按钮的参数
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_EIGHT_IMG)) {
                                    init_btnValues.setEight_btn_img(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_EIGHT_STATUS)) {
                                    init_btnValues.setEight_btn_status(pullParser.nextText().trim
                                            ());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_EIGHT_STATUSBACKGROUND)) {
                                    init_btnValues.setEight_btn_status_background(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_EIGHT_STATUSCOLOR)) {
                                    init_btnValues.setEight_btn_status_textColor(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns
                                        .ACTION_EIGHT_TEXTCOLOR)) {
                                    init_btnValues.setEight_btn_titleColor(pullParser.nextText()
                                            .trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_EIGHT_TITLE)) {
                                    init_btnValues.setEight_btn_title(pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_EIGHT_URL)) {
                                    init_btnValues.setEight_btn_url(pullParser.nextText().trim());
                                }

                                /**
                                 * 判断模块开始  就要初始化模块数据表
                                 *
                                 * 初始化商家促销的参数信息
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_XML_START)) {
                                    /*到达解析头部  开始获取句柄*/
                                    businessPromotionValues = LocalShopModuleValuepage
                                            .GetBusinessPromotionInstance();
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_PROMOTION_STATUS)) {
                                    if (pullParser.nextText().trim().equals("0")) {
                                        businessPromotionValues.setStatus(false);
                                    } else {
                                        businessPromotionValues.setStatus(true);
                                    }
                                }

                                /**
                                 * 初始化标题和其他的VALUE
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_TITLE)) {
                                    businessPromotionValues.setTitle(pullParser.nextText().trim()
                                    );/*标题*/
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_TITLE_COLOR)) {
                                    businessPromotionValues.setTitle_color(pullParser.nextText()
                                            .trim());
                                }
                                /**
                                 * 第一个ITEM的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_TITLE)) {
                                    businessPromotionValues.setFirst_item_title(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_IMG)) {
                                    businessPromotionValues.setFirst_item_img(pullParser.nextText
                                            ().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_STATUS)) {
                                    businessPromotionValues.setFirst_item_status(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_URL)) {
                                    businessPromotionValues.setFirst_item_url(pullParser.nextText
                                            ().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_STATUS_COLOR)) {
                                    businessPromotionValues.setFirst_item_status_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_STATUS_BACKGROUND)) {
                                    businessPromotionValues.setFirst_item_status_background
                                            (pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_TITLE_COLOR)) {
                                    businessPromotionValues.setFirst_item_title_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_TEXT)) {
                                    businessPromotionValues.setFirst_item_text(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FIRST_BUSINESSPROMOTION_TEXT_COLOR)) {
                                    businessPromotionValues.setFirst_item_text_color(pullParser
                                            .nextText().trim());
                                }


                                /**
                                 * 第二个ITEM的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_TITLE)) {
                                    businessPromotionValues.setSecond_item_title(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_IMG)) {
                                    businessPromotionValues.setSecond_item_img(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_STATUS)) {
                                    businessPromotionValues.setSecond_item_status(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_URL)) {
                                    businessPromotionValues.setSecond_item_url(pullParser
                                            .nextText().trim());
                                }

                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_STATUS_COLOR)) {
                                    businessPromotionValues.setSecond_item_status_color
                                            (pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_STATUS_BACKGROUND)) {
                                    businessPromotionValues.setSecond_item_status_background
                                            (pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_TITLE_COLOR)) {
                                    businessPromotionValues.setSecond_item_title_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_TEXT)) {
                                    businessPromotionValues.setSecond_item_text(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_SECOND_BUSINESSPROMOTION_TEXT_COLOR)) {
                                    businessPromotionValues.setSecond_item_text_color(pullParser
                                            .nextText().trim());
                                }
                                /**
                                 * 第三个ITEM的值
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_TITLE)) {
                                    businessPromotionValues.setThree_item_title(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_TITLE_COLOR)) {
                                    businessPromotionValues.setThree_item_title_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_IMG)) {
                                    businessPromotionValues.setThree_item_img(pullParser.nextText
                                            ().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_STATUS)) {
                                    businessPromotionValues.setThree_item_status(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_STATUS_BACKGROUND)) {
                                    businessPromotionValues.setThree_item_status_background
                                            (pullParser.nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_STATUS_COLOR)) {
                                    businessPromotionValues.setThree_item_status_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_TEXT)) {
                                    businessPromotionValues.setThree_item_text(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_TEXT_COLOR)) {
                                    businessPromotionValues.setThree_item_text_color(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_THREE_BUSINESSPROMOTION_URL)) {
                                    businessPromotionValues.setThree_item_url(pullParser.nextText
                                            ());
                                }
                                /**
                                 * 第四个ITEM的值
                                 *
                                 */
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FOUR_BUSINESSPROMOTION_TITLE)) {
                                    businessPromotionValues.setFour_item_title(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FOUR_BUSINESSPROMOTION_IMG)) {
                                    businessPromotionValues.setFour_item_img(pullParser.nextText
                                            ().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FOUR_BUSINESSPROMOTION_STATUS)) {
                                    businessPromotionValues.setFour_item_status(pullParser
                                            .nextText().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                        .ACTION_FOUR_BUSINESSPROMOTION_URL)) {
                                    businessPromotionValues.setFour_item_url(pullParser.nextText
                                            ().trim());
                                }
                                if (tag.equals(XmlTagValuesFactory.init_business_promotion.ACTION_FOUR_BUSINESSPROMOTION_TITLE_COLOR)){
                                    businessPromotionValues.setFour_item_title_color(pullParser.nextText().trim());
                                }


                            } catch (Exception e) {
                                Log.i(MSG, "处理界面的参数的错误");
                            }

                        }

                        @Override
                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                            if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_XML_START)) {
                                //已经结束  调试输出
                            }

                            /*判断这个布局是否可以显示*/
                            if (tag.equals(XmlTagValuesFactory.init_business_promotion
                                    .ACTION_XML_START)) {
                                /*判断这个模块是否可以加载 如果不可以 就不用去加载该模块*/
                                if (businessPromotionValues.getStatus()) {
                                    Toast.makeText(getContext(), "不能显示", Toast.LENGTH_SHORT).show();
                                } else {
                                    /**
                                     * 商品促销管理者  
                                     */
                                    if (showModue > 1) {
                                        /**
                                         * 允许可以显示的模块中
                                         */
                                        showModue--;
                                        LinearLayout body = item.findViewById(R.id
                                                .fragment_main_moduleBody);
                                        View merchaView = inflater.inflate(R.layout
                                                .assembly_fragment_main_erpromotion, null);
                                        body.addView(merchaView);
                                        LocalMonitorHandler.HandlerBusiness handlerBusiness =
                                                LocalMonitorHandler.getHandlerBusinessAdapter();
                                        Log.i(MSG, "标题为: " + businessPromotionValues.getTitle());
                                        handlerBusiness.Instance(merchaView,
                                                businessPromotionValues);/*设置基本参数*/
                                        handlerBusiness.Start();
                                    } else {
                                        /**
                                         * 已经显示完毕 就不要加载  放入LIST中 由ScrollView的监听调用
                                         */
                                    }
                                }
                            }

                            if (tag.equals(XmlTagValuesFactory.init_btns.ACTION_XML_START)) {

                                /**
                                 * 刷新第一个按钮的参数
                                 */

                                HandlerNav(init_btnValues, _navaBody);

                            }
                        }

                        @Override
                        public void onEndDocument() {


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
        } else {

        }


    }


    /**
     * 初始化设置边框等界面
     */
    @SuppressLint("NewApi")
    private void InitPageXml() {
        Net.doGet(getContext(), Config.HTTP_ADDR.getInitMainXmlConfig(), new Net
                .onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                Log.i(MSG, "调试输出:" + tOrgin);
                XmlanalysisFactory xmlTools = new XmlanalysisFactory(tOrgin);
                xmlTools.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
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
                             * 获取搜索的父窗口的背景颜色
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchbackground)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchBackground(pullParser
                                        .nextText().trim());
                            }


                            /**
                             * 获取状态栏颜色
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_windowcolor)) {
                                XmlTagValuesFactory.XmlInitPage.setWindowColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取搜索栏的线条的颜色
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchlinecolor)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchLineColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取搜索栏的背景颜色
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchbodycolor)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchBodyColor(pullParser
                                        .nextText().trim());
                            }

                            /**
                             * 获取系统通知的数量
                             */

                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_howmessagenumber)) {
                                XmlTagValuesFactory.XmlInitPage.setHowMessageNumber(pullParser
                                        .nextText().trim());

                            }

                            /**
                             * 获取热搜的标题
                             */
                            if (tag.equals(XmlTagValuesFactory.XmlInitPage.key_searchkeyword)) {
                                XmlTagValuesFactory.XmlInitPage.setSearchKeyWord(pullParser
                                        .nextText().trim());
                            }
                        } catch (Exception e) {
                            Log.e(MSG, "错误信息:" + e.getMessage());
                        }

                    }

                    @Override
                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {

                    }

                    @Override
                    public void onEndDocument() {
                        setStatusBar(XmlTagValuesFactory.XmlInitPage.getWindowColor());
                        /*设置搜索框的线条和背景颜色*/
                        EditText editText = SearchViewBody.findViewById(R.id
                                .assembly_head_editText);
                        editText.setBackground(Tools.CreateDrawable(1, XmlTagValuesFactory
                                .XmlInitPage.getSearchLineColor(), XmlTagValuesFactory
                                .XmlInitPage.getSearchBodyColor(), 10));
                        /*设置父窗口背景的颜色*/
                        SearchViewBody.setBackgroundColor(Color.parseColor(XmlTagValuesFactory
                                .XmlInitPage.getSearchBackground()));
                        /*系统信息数量*/
                        TextView howMessage = SearchViewBody.findViewById(R.id
                                .assembly_head_howmessageNumber);
                        /*设置通知图标的圆圈的颜色*/
                        howMessage.setBackground(Tools.CreateDrawable(1, XmlTagValuesFactory
                                .XmlInitPage.getHowMessageNumber(), XmlTagValuesFactory
                                .XmlInitPage.getHowMessageNumber(), 50));
                        /*处理系统信息数量*/
                        /*设置热搜*/
                        EditText SearchEditView = SearchViewBody.findViewById(R.id
                                .assembly_head_editText);/*热搜输入框*/
                        SearchEditView.setText(XmlTagValuesFactory.XmlInitPage.getSearchKeyWord());


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

    @SuppressLint({"NewApi", "ResourceType", "LongLogTag"})
    private void init(final View item) {
        /*开启界面*/
        body.setVisibility(View.VISIBLE);
        Net.doGet(getContext(), Config.HTTP_ADDR.getInitMainXmlConfig(), new Net
                .onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                Log.i(MSG, "获取到的数据信息为:" + tOrgin.toString());
                HandlerXml(tOrgin, item);
            }

            @Override
            public void onNotConnect() {

            }

            @Override
            public void onFail(String tOrgin) {

            }
        });
        inflater = LayoutInflater.from(getContext());//初始化inflater

        /*横向的广告图片的布局*/
        horizontaladv_body = item.findViewById(R.id.fragment_main_horizontaladv_body);//横向广告的布局
        final View horizontaladv_view = inflater.inflate(R.layout
                .assembly_fragment_main_horizontaladv, null);
        horizontaladv_body.addView(horizontaladv_view);

        ImageView bigImg = horizontaladv_view.findViewById(R.id
                .assembly_fragment_main_horizontaladv_img);
        Glide.with(getContext()).load("http://120.79.63.36/collection/lybh/photos/1231321.png")
                .into(bigImg);
        /*横向动画开始*/
        DisplayMetrics matrix = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(matrix);
        /**
         * 尝试加载
         */
        _RefreshScrollView = item.findViewById(R.id.fragment_main_refreshScrollview);
        LinearLayout _layout = item.findViewById(R.id.fragment_main_layoutmore);
        _RefreshScrollView.SetHeadView(_layout, 200, R.id.layoutmore_progressbar, R.id
                .layoutmore_headimg);


        /**
         * 华丽分割线
         * ========================================================
         */

        /*<新品上架促销>*/

        LinearLayout newShopin = item.findViewById(R.id.fragment_main_newShopin);
        View newShopinView = inflater.inflate(R.layout.assembly_fragment_main_newshopin, null);
        newShopin.setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 10));
        newShopin.addView(newShopinView);
        NewShopinMonitor newShopinMonitor = new NewShopinMonitor(newShopinView, getContext(),
                Config.HTTP_ADDR.CONFIG_XML_SERVICE + "newshopin.xml");
        newShopinMonitor.Start();

        /*</新品上架促销>*/


        /**
         * 华丽分割线
         * ========================================================
         */
        /*<导航界面的管理>*/
        LinearLayout navA_body = item.findViewById(R.id.fragment_main_navA_body);
        _navaBody = inflater.inflate(R.layout.assembly_fragment_main_nava, null);
        navA_body.addView(_navaBody);
        /*</首页的新品上架的管理>*/
        /**
         * 华丽分割线
         * ========================================================
         */
        /*<品牌促销的界面管理>*/
        LinearLayout layout = item.findViewById(R.id.fragment_main_singlebody);
        View mainSingle = inflater.inflate(R.layout.assembly_fragment_main_single, null);
        layout.addView(mainSingle);
        /*启动品牌的事务管理器*/
        BrandSingMonitor brandSingMonitor = new BrandSingMonitor(mainSingle, getContext());
        brandSingMonitor.Start(new ProgramInterface() {
            @Override
            public void onSucess(String data, int code) {
                //判断code 如果code正确的话 要设置ScrollView的停止监听tag
            }

            @Override
            public void onFaile(String data, int code) {

            }
        }, getContext());
        brandSingMonitor.SaveTag(LocalMonitorPage.MONITOR_BRANDSING);/*标识管理者的名字*/
        layout.setTag(brandSingMonitor);/*保存管理者*/
        /*</品牌促销的界面管理>*/


        /**
         * 滑动监听器 用来处理各个界面的管理
         */
        _RefreshScrollView.setCanListenMoveLeft(true);
        _RefreshScrollView.setCanListenMoveRight(true);
        _RefreshScrollView.SetLinstener(new RefreshScrollView.RefreshScrollViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefreshDone() {

            }

            @Override
            public void onStopRefresh() {

            }

            @Override
            public void onState(int _static) {
                if (_static == RefreshScrollView.RUNNOW_REFRESH) {
                    //可以开始刷新

                }

            }

            @Override
            public void onLoadMore() {
                /**
                 * 开启一个定时的线程 用来在三秒中过后启动
                 */
                _GoneHeadimg = new Timer();
                _GoneHeadimg.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = GONE_HEADE_IMG;
                        handler.sendMessage(msg);

                    }
                }, 3000, 1000);

            }

            @Override
            public void onLoadBottom() {

            }

            @Override
            public void onScrollStop() {


                /*停止刷新*/
                LinearLayout i = (LinearLayout) _RefreshScrollView.getChildAt(0);
                Rect rect = new Rect();
                for (int y = 0; y < i.getChildCount(); y++) {
                    if (i.getChildAt(y).getLocalVisibleRect(rect)) {
                        /*正在显示*/
                        Monitor monitor = (Monitor) i.getChildAt(y).getTag();
                        if (monitor != null) {
                            switch (monitor.GetTag()) {
                                case LocalMonitorPage.MONITOR_MERCHANT:
                                    MerchantMonitor mm = (MerchantMonitor) monitor;
                                    switch (mm.getStatic()) {
                                        case MonitorStatic.CLEAR_DONE:
                                            /*已经被清空 要求重新再次加载*/
                                            mm.reStart();
                                            break;
                                        case MonitorStatic.LOAD_IN:
                                            /*正在加载 不进行重复加载*/
                                            break;
                                    }
                            }

                        }

                        /**/
                        if (monitor != null) {
                            /*获取到了数据信息*/
                            if (monitor.GetTag() == LocalMonitorPage.MONITOR_BRANDSING) {
                                /*品牌促销的界面*/
                                BrandSingMonitor bsm = (BrandSingMonitor) monitor;
                                bsm.Start(new ProgramInterface() {
                                    @Override
                                    public void onSucess(String data, int code) {

                                    }

                                    @Override
                                    public void onFaile(String data, int code) {

                                    }
                                }, getContext());

                            }
                            Toast.makeText(getContext(), "Tag:" + monitor.GetTag(), Toast
                                    .LENGTH_SHORT).show();
                        }
                    } else {
                        /*不在显示的话 就调用清空*/
                        Monitor monitor = (Monitor) i.getChildAt(y).getTag();
                        if (monitor != null) {

                            switch (monitor.GetTag()) {
                                case LocalMonitorPage.MONITOR_BRANDSING:
                                    /*品牌促销*/
                                    BrandSingMonitor bsm = (BrandSingMonitor) monitor;
                                    bsm.pause();
                                    break;
                                case LocalMonitorPage.MONITOR_MERCHANT:
                                    MerchantMonitor mm = (MerchantMonitor) monitor;
                                    switch (mm.getStatic()) {
                                        case MonitorStatic.CLEAR_DONE:
                                            /*已经被清空 要求重新再次加载*/
                                            mm.reStart();
                                            break;
                                        case MonitorStatic.LOAD_IN:
                                            /*正在加载 不进行重复加载*/
                                            break;
                                    }
                                    mm.getStatic();
                                    mm.clear();
                                    break;
                            }

                        }
                    }
                }
                _RefreshScrollView.getHitRect(rect);
                _RefreshScrollView.onStopHandle = true;/*设置不能再次回调停止事件 在按下的时候 该值重新被激活*/

            }

            @Override
            public void onloadMessage() {
                Log.i(Config.DEBUG, "现在可以开始加载广告窗口了");
                _RefreshScrollView.inLoadMessage = true;//告诉内部 已经开始加载 不能再次调用接口
                WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT
                        ("http://120.79.63.36/lazyShop/webpage/1.html");
                /*创建加载框*/

                LazyCatFragmentStartWevact(web_values_act);
            }

            @Override
            public void onScrollDistance(int distance) {

            }

            @Override
            public void onScrollToleft(int _moveCount) {
            }

            @Override
            public void onScrollToRight(int _moveCount) {
                /*右向滑动*/

            }
        });


        /**
         * 实现点击搜索框进入搜索界面
         */
        item.findViewById(R.id.assembly_head_editText).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                LazyCatFragmetStartAct(SearchAct.class);
            }
        });

    }


    /**
     * 处理XML数据信息开始整理界面
     *
     * @param tOrgin
     */
    private void HandlerXml(String tOrgin, final View item) {
        if (!TextUtils.isEmpty(tOrgin)) {
            XmlanalysisFactory xsf = new XmlanalysisFactory(tOrgin);
            xsf.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {
                @Override
                public void onFaile() {

                }

                @Override
                public void onStartDocument(String tag) {

                }

                @SuppressLint("NewApi")
                @Override
                public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                    try {


                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_arcview)) {
                            /*设置拱形的颜色*/
                            String arcColor = pullParser.nextText().trim();
                            Log.i(MSG, "拱形的颜色为:" + arcColor);
                            //arcView.setBackGroundColor(arcColor);
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_body)) {
                            /*重新创建一个表格*/
                            navPage = new XmlTagValuesFactory.XMLTagMainNavValues();
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_ico_ico)) {
                            if (navPage != null) {
                                /*标题图片*/
                                navPage.setNav_ico(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_ico_color)) {
                            if (navPage != null) {
                                /*标题颜色*/
                                navPage.setNav_color(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_ico_auto_link)) {
                            if (navPage != null) {
                                /*链接的地址*/
                                navPage.setNav_link_url(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_ico_title)) {
                            if (navPage != null) {
                                /*标题*/
                                navPage.setNav_title(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_ico_static)) {
                            if (navPage != null) {
                                /*状态*/
                                navPage.setNav_static(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_nav_ico_static_titleColor)) {
                            if (navPage != null) {
                                /*状态的颜色*/
                                navPage.setNav_static_titleColor(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_nav_ico_static_backColor)) {
                            if (navPage != null) {
                                /*状态的背景颜色*/
                                navPage.setNav_static_backColor(pullParser.nextText());
                            }
                        }

                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_nav_ico_auto_link_titleColor)) {
                            if (navPage != null) {
                                /*webview的标题文字颜色*/
                                navPage.setAuto_link_titleColor(pullParser.nextText());
                            }
                        }

                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_nav_ico_auto_link_titleBackColor)) {
                            if (navPage != null) {
                                /*webview的标题背景颜色*/
                                navPage.setAuto_link_titleBackColor(pullParser.nextText());
                            }
                        }
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_nav_ico_auto_link_staticColor)) {
                            if (navPage != null) {
                                /*webview的状态栏颜色*/
                                navPage.setAuto_link_staticColor(pullParser.nextText());
                            }
                        }


                        /*改变状态栏的颜色*/
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_main_static_color)) {
                            setStatusBar(pullParser.nextText().trim());
                        }

                        /*更换搜索框的背景颜色*/
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_main_searchBody_BackColor)) {
                            item.findViewById(R.id.fragment_main_head).setBackgroundColor(Color
                                    .parseColor(pullParser.nextText().trim()));

                        }

                        /*更换输入框背景颜色*/
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_main_searchBody_inputBackColor)) {
                            /*保存值*/
                            Search_input_back_color = pullParser.nextText();
                        }
                        /*更换输入框背景颜色*/
                        if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml
                                .key_main_searchBody_inputLineColor)) {
                            Search_input_line_color = pullParser.nextText();
                        }
                    } catch (Exception e) {
                        Log.e(MSG, "错误信息:" + e.getMessage());
                    }


                }

                @Override
                public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {
                    if (tag.equals(XmlTagValuesFactory.XMLKeyMainXml.key_nav_body)) {
                        nav_list.add(navPage);/*加入一个新的图标*/
                        navPage = null;/*清空*/
                    }

                }

                @SuppressLint("NewApi")
                @Override
                public void onEndDocument() {
                    /*处理信息的梳理的标签*/
                    item.findViewById(R.id.assembly_head_howmessageNumber).setBackground(Tools
                            .CreateDrawable(1, "#ffffff", "#ffffff", 50));
                    /*处理搜索框的背景*/
                    if (!TextUtils.isEmpty(Search_input_line_color) && !TextUtils.isEmpty
                            (Search_input_back_color)) {
                        item.findViewById(R.id.assembly_head_editText).setBackground(Tools
                                .CreateDrawable(1, Search_input_back_color.trim(),
                                        Search_input_line_color.trim(), 50));

                    } else {
                        Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < nav_list.size(); i++) {
                        Log.i(MSG, "[" + i + "]导航标题" + nav_list.get(i).getNav_title());
                    }
                    /**
                     * 开始整理nav导航图标
                     */
                }
            });
        }
    }


    /**
     * 开始整理导航界面
     *
     * @param _init_btnvalues 整理好的数据
     * @param view
     */
    @SuppressLint("NewApi")
    private void HandlerNav(XmlTagValuesFactory.Init_btnValues _init_btnvalues, View view) {
        TextView first_view = view.findViewById(R.id.assembly_fragment_main_nava_fristTitle);
        TextView first_static = view.findViewById(R.id.assembly_fragment_main_nava_fristStatic);
        ImageView first_img = view.findViewById(R.id.assembly_fragment_main_nava_fristImg);
        RelativeLayout firstBody = view.findViewById(R.id.assembly_fragment_main_nav_firstBody);
        /*第二个导航的控件信息*/

        TextView second_view = view.findViewById(R.id.assembly_fragment_main_nava_secondTitle);
        TextView second_static = view.findViewById(R.id.assembly_fragment_main_nava_secondStatic);
        ImageView second_img = view.findViewById(R.id.assembly_fragment_main_nava_secondImg);
        RelativeLayout secondBody = view.findViewById(R.id.assembly_fragment_main_nav_secondBody);

        /*第三个导航栏的控件信息*/
        TextView three_view = view.findViewById(R.id.assembly_fragment_main_nava_threeTitle);
        TextView three_static = view.findViewById(R.id.assembly_fragment_main_nava_threeStatic);
        RelativeLayout threeBody = view.findViewById(R.id.assembly_fragment_main_nava_threeBody);


        /*第四个导航的控件信息*/

        TextView four_view = view.findViewById(R.id.assembly_fragment_main_nava_fourTitle);
        TextView four_static = view.findViewById(R.id.assembly_fragment_main_nava_fourStatic);
        RelativeLayout fourBody = view.findViewById(R.id.assembly_fragment_main_nava_fourBody);
        ImageView four_img = view.findViewById(R.id.assembly_fragment_main_nava_fourImg);

        /*第五个导航的控件信息*/
        TextView five_view = view.findViewById(R.id.assembly_fragment_main_nava_fiveTitle);
        TextView five_static = view.findViewById(R.id.assembly_fragment_main_nava_fiveStatic);
        ImageView five_img = view.findViewById(R.id.assembly_fragment_main_nava_fiveImg);

        /*第六个导航控件信息*/
        TextView six_view = view.findViewById(R.id.assembly_fragment_main_nava_sixTitle);
        TextView six_static = view.findViewById(R.id.assembly_fragment_main_nava_sixStatic);
        ImageView six_img = view.findViewById(R.id.assembly_fragment_main_nava_sixImg);
        /*第七个导航控件信息*/
        TextView seven_view = view.findViewById(R.id.assembly_fragment_main_nava_eventTitle);
        TextView seven_static = view.findViewById(R.id.assembly_fragment_main_nava_eventStatic);
        ImageView seven_img = view.findViewById(R.id.assembly_fragment_main_nava_eventImg);
        /*第八个导航控件*/
        TextView eight_view = view.findViewById(R.id.assembly_fragment_main_nava_eighthTitle);
        TextView eight_static = view.findViewById(R.id.assembly_fragment_main_nava_eighthStatic);
        ImageView eight_img = view.findViewById(R.id.assembly_fragment_main_nava_eighthImg);
        /*第一个导航的body*/
        firstBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlTagValuesFactory.XMLTagMainNavValues nv = (XmlTagValuesFactory
                        .XMLTagMainNavValues) v.getTag();
                if (nv == null) {
                    Log.i(MSG, "打开WebService窗口失败,为空");
                } else {
                    WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(nv.getNav_link_url());
                    web_values_act.set_StaticColor(nv.getAuto_link_staticColor().trim());/*状态栏颜色*/
                    web_values_act.set_TitleBackColor(nv.getAuto_link_titleBackColor().trim());
                    /*标题背景颜色*/
                    web_values_act.set_TitleColor(nv.getAuto_link_titleColor().trim());/*标题文字颜色*/
                    if (nv.getNav_link_url() != null && !TextUtils.isEmpty(nv.getNav_link_url())) {
                        LazyCatFragmentStartWevact(web_values_act);
                    }
                }
            }
        });

        /*第二个导航的BODY点击事件*/
        secondBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlTagValuesFactory.XMLTagMainNavValues nv = (XmlTagValuesFactory
                        .XMLTagMainNavValues) v.getTag();
                if (nv == null) {
                    Log.i(MSG, "导航2的点击TAG为NULL");
                } else {
                    WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(nv.getNav_link_url());
                    web_values_act.set_StaticColor(nv.getAuto_link_staticColor().trim());
                    web_values_act.set_TitleBackColor(nv.getAuto_link_titleBackColor().trim());
                    web_values_act.set_TitleColor(nv.getAuto_link_titleColor().trim());

                    if (nv.getNav_link_url() != null && !TextUtils.isEmpty(nv.getNav_link_url())) {
                        LazyCatFragmentStartWevact(web_values_act);
                    }
                }
            }
        });
        /*第三个导航栏的点击事件*/
        threeBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlTagValuesFactory.XMLTagMainNavValues nv = (XmlTagValuesFactory
                        .XMLTagMainNavValues) v.getTag();
                if (nv == null) {
                    Log.i(MSG, "导航3的点击TAG为NULL");
                } else {
                    WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(nv.getNav_link_url());
                    web_values_act.set_StaticColor(nv.getAuto_link_staticColor().trim());
                    web_values_act.set_TitleBackColor(nv.getAuto_link_titleBackColor().trim());
                    web_values_act.set_TitleColor(nv.getAuto_link_titleColor().trim());

                    if (nv.getNav_link_url() != null && !TextUtils.isEmpty(nv.getNav_link_url())) {
                        LazyCatFragmentStartWevact(web_values_act);
                    }
                }
            }
        });

        /*第四个导航的点击事件*/
        fourBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XmlTagValuesFactory.XMLTagMainNavValues nv = (XmlTagValuesFactory
                        .XMLTagMainNavValues) v.getTag();
                if (nv == null) {
                    Log.i(MSG, "导航4的点击TAG为NULL");
                } else {
                    WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(nv.getNav_link_url());
                    web_values_act.set_StaticColor(nv.getAuto_link_staticColor().trim());
                    web_values_act.set_TitleBackColor(nv.getAuto_link_titleBackColor().trim());
                    web_values_act.set_TitleColor(nv.getAuto_link_titleColor().trim());

                    if (nv.getNav_link_url() != null && !TextUtils.isEmpty(nv.getNav_link_url())) {
                        LazyCatFragmentStartWevact(web_values_act);

                    }
                }
            }
        });
        /*第一个标题*/
        firstBody.setTag(_init_btnvalues);
        TextUnt.with(first_view).setText(_init_btnvalues.getFirst_btn_title()).setTextColor
                (_init_btnvalues.getFirst_btn_titleColor());
        Glide.with(getContext()).load(_init_btnvalues.getFirst_btn_img().trim()).into(first_img);
        /*设置状态颜色*/
        /*没有状态就设置成透明的*/

        if (TextUtils.isEmpty(_init_btnvalues.getFirst_btn_status().trim())) {
            first_static.setVisibility(View.GONE);

        } else {
            TextUnt.with(first_static).setText(_init_btnvalues.getFirst_btn_status())
                    .setTextColor(_init_btnvalues.getFirst_btn_status_textColor());
            first_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getFirst_btn_status_background()));/*设置状态文字的背景颜色*/
        }

        /*设置第二个标题的参数*/
        secondBody.setTag(_init_btnvalues);
        Glide.with(getContext()).load(_init_btnvalues.getSecond_btn_img()).into(second_img);
        TextUnt.with(second_view).setText(_init_btnvalues.getSecond_btn_title()).setTextColor
                (_init_btnvalues.getFirst_btn_titleColor());
        /*设置状态颜色*/
        /*没有状态就设置成透明的*/

        if (TextUtils.isEmpty(_init_btnvalues.getSecond_btn_status().trim())) {
            second_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(second_static).setText(_init_btnvalues.getSecond_btn_status())
                    .setTextColor(_init_btnvalues.getSecond_btn_status_textColor());
            second_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getSecond_btn_status_background()));
        }

        /*设置第三个标题的参数*/
        threeBody.setTag(_init_btnvalues);
        TextUnt.with(three_view).setText(_init_btnvalues.getThree_btn_title()).setTextColor
                (_init_btnvalues.getThree_btn_titleColor());

        /*没有状态就设置成透明的*/

        if (TextUtils.isEmpty(_init_btnvalues.getThree_btn_status().trim())) {
            first_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(three_static).setText(_init_btnvalues.getThree_btn_status())
                    .setTextColor(_init_btnvalues.getThree_btn_status_textColor());
            three_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getThree_btn_status_background()));
        }


        /*设置第四个导航的参数*/
        fourBody.setTag(_init_btnvalues);
        TextUnt.with(four_view).setText(_init_btnvalues.getFour_btn_title()).setTextColor
                (_init_btnvalues.getFour_btn_titleColor());
        /*判断是否有图片*/
        Glide.with(getContext()).load(_init_btnvalues.getFour_btn_img().trim()).into(four_img);

        /*没有状态就设置成透明的*/
        if (TextUtils.isEmpty(_init_btnvalues.getFour_btn_status().trim())) {
            four_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(four_static).setText(_init_btnvalues.getFour_btn_status()).setTextColor
                    (_init_btnvalues.getFour_btn_status_textColor());

            four_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getFour_btn_status_background()));
        }

        /*设置第五个导航的参数*/
        TextUnt.with(five_view).setText(_init_btnvalues.getFive_btn_title()).setTextColor
                (_init_btnvalues.getFive_btn_titleColor());
        Glide.with(getContext()).load(_init_btnvalues.getFive_btn_img().trim()).into(five_img);
        /*没有状态就设置成透明的*/
        if (TextUtils.isEmpty(_init_btnvalues.getFive_btn_status().trim())) {
            five_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(five_static).setText(_init_btnvalues.getFive_btn_status()).setTextColor
                    (_init_btnvalues.getFive_btn_status_textColor());
            five_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getFive_btn_status_background()));
        }

        /*设置第六个导航的参数*/
        TextUnt.with(six_view).setText(_init_btnvalues.getSix_btn_title()).setTextColor
                (_init_btnvalues.getSix_btn_titleColor());
        Glide.with(getContext()).load(_init_btnvalues.getSix_btn_img().trim()).into(six_img);
        /*没有状态就设置成透明的*/
        if (TextUtils.isEmpty(_init_btnvalues.getSix_btn_status().trim())) {
            six_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(six_static).setText(_init_btnvalues.getSix_btn_status()).setTextColor
                    (_init_btnvalues.getSix_btn_status_textColor());
            six_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getSix_btn_status_background()));
        }
        /*设置第七个导航的参数*/
        TextUnt.with(seven_view).setText(_init_btnvalues.getSeven_btn_title()).setTextColor
                (_init_btnvalues.getSeven_btn_titleColor());
        Glide.with(getContext()).load(_init_btnvalues.getSeven_btn_img().trim()).into(seven_img);
        /*没有状态就设置成透明的*/
        if (TextUtils.isEmpty(_init_btnvalues.getSeven_btn_status().trim())) {
            seven_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(seven_static).setText(_init_btnvalues.getSeven_btn_status())
                    .setTextColor(_init_btnvalues.getSeven_btn_status_textColor());
            seven_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getSeven_btn_status_backgrond()));
        }
        /*设置第八个导航的参数*/
        TextUnt.with(eight_view).setText(_init_btnvalues.getEight_btn_title()).setTextColor
                (_init_btnvalues.getEight_btn_titleColor());
        /*如果存在图片就获取图片*/
        if (!TextUtils.isEmpty(_init_btnvalues.getEight_btn_img().trim())) {
            Glide.with(getContext()).load(_init_btnvalues.getEight_btn_img().trim()).into
                    (eight_img);
        } else {
            /*加载默认图片*/
        }
        /*没有状态就设置成透明的*/
        if (TextUtils.isEmpty(_init_btnvalues.getEight_btn_status().trim())) {
            eight_static.setVisibility(View.GONE);
        } else {
            TextUnt.with(eight_static).setText(_init_btnvalues.getEight_btn_status())
                    .setTextColor(_init_btnvalues.getEight_btn_status_textColor());
            eight_static.setBackgroundColor(Color.parseColor(_init_btnvalues
                    .getEight_btn_status_background()));
        }
    }
}

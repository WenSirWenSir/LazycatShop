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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;
import shlm.lmcs.com.lazycat.LazyShopMonitor.BrandSingMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.MerchantMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.Monitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.MonitorStatic;
import shlm.lmcs.com.lazycat.LazyShopMonitor.NewShopinMonitor;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalMonitorPage;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment {
    private LinearLayout horizontaladv_body;
    private LayoutInflater inflater;
    private RelativeLayout big_body;/*最外层的布局 用来切换外卖使用*/
    private RefreshScrollView _RefreshScrollView;
    private static final int GONE_HEADE_IMG = 0;
    private String MSG = "Mainfrg.java[+]";
    private Timer _GoneHeadimg;
    private String Search_input_line_color;/*输入框线条颜色*/
    private String Search_input_back_color;/*输入框背景颜色*/
    private XmlTagValuesFactory.XMLTagMainNavValues navPage = new XmlTagValuesFactory
            .XMLTagMainNavValues();
    private ArrayList<XmlTagValuesFactory.XMLTagMainNavValues> nav_list = new
            ArrayList<XmlTagValuesFactory.XMLTagMainNavValues>();
    private View _navaBody;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View item = inflater.inflate(R.layout.fragment_main, null);
        //初始化一个背景样式
        item.findViewById(R.id.assembly_head_input).setFocusable(false);
        big_body = item.findViewById(R.id.fragment_main_bigBody);/*最外层布局*/
        init(item);
        return item;
    }

    @SuppressLint({"NewApi", "ResourceType", "LongLogTag"})
    private void init(final View item) {
        Net.doGet(getContext(), Config.HTTP_ADDR.getMainFragmentConfigXml(), new Net
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
        /*<首页的商家促销的界面管理>*/

        /*找到展示的界面的父布局*/
        LinearLayout merchantLayout = item.findViewById(R.id.fragment_main_merchant);
        View merchantView = inflater.inflate(R.layout.assembly_fragment_main_erpromotion, null);
        merchantLayout.addView(merchantView);/*添加View*/
        MerchantMonitor merchantMonitor = new MerchantMonitor(merchantView, getContext());
        if (merchantMonitor.getStatic() == MonitorStatic.NOT_LOAD) {
            merchantMonitor.Start();
        }
        /*创建商家促销管理者*/
        merchantMonitor.SaveTag(LocalMonitorPage.MONITOR_MERCHANT);
        merchantLayout.setTag(merchantMonitor);
        /*</首页的商家促销的界面管理>*/

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
        item.findViewById(R.id.assembly_head_input).setOnClickListener(new View.OnClickListener() {
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
                public void onStartDocument() {

                }

                @SuppressLint("NewApi")
                @Override
                public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {
                    try {
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
                        item.findViewById(R.id.assembly_head_input).setBackground(Tools
                                .CreateDrawable(1, Search_input_back_color.trim(),
                                        Search_input_line_color.trim(), 50));

                    } else {
                        Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < nav_list.size(); i++) {
                        Log.i(MSG, "[" + i + "]标题" + nav_list.get(i).getNav_link_url());
                    }
                    /**
                     * 开始整理nav导航图标
                     */
                    HandlerNav(nav_list, _navaBody);
                }
            });
        }
    }


    /**
     * 开始整理导航界面
     *
     * @param list
     * @param view
     */
    @SuppressLint("NewApi")
    private void HandlerNav(ArrayList<XmlTagValuesFactory.XMLTagMainNavValues> list, View view) {
        TextView first_view = view.findViewById(R.id.assembly_fragment_main_nava_fristTitle);
        TextView first_static = view.findViewById(R.id.assembly_fragment_main_nava_fristStatic);
        RelativeLayout firstBody = view.findViewById(R.id.assembly_fragment_main_nav_firstBody);
        /*第一个导航的body*/
        firstBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rl = (RelativeLayout) v;
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
        /*第一个标题*/
        if (list.get(0) != null) {
            first_view.setText(list.get(0).getNav_title());/*设置文字*/
            first_view.setTextColor(Color.parseColor(list.get(0).getNav_color().trim()));
            /*设置颜色*/

            first_static.setText(list.get(0).getNav_static().trim());/*设置状态*/
            first_static.setTextColor(Color.parseColor(list.get(0).getNav_static_titleColor()
                    .trim()));
            /*设置状态文字的颜色*/
            first_static.setBackgroundColor(Color.parseColor(list.get(0).getNav_static_backColor
                    ().trim()));/*设置状态文字的背景颜色*/

            firstBody.setTag(list.get(0));
            Log.e(MSG, "请求的地址:" + list.get(0).getNav_link_url());
        } else {
            Log.e(MSG, "请求地址:" + list.get(0).getNav_link_url());
        }

    }
}

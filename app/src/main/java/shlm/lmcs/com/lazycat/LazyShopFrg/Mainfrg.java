package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.WebServiceAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopAct.SearchAct;
import shlm.lmcs.com.lazycat.LazyShopMonitor.BrandSingMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.MerchantMonitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.Monitor;
import shlm.lmcs.com.lazycat.LazyShopMonitor.NewShopinMonitor;
import shlm.lmcs.com.lazycat.LazyShopPage.LocalMonitorPage;
import shlm.lmcs.com.lazycat.R;

@SuppressLint("HandlerLeak")
public class Mainfrg extends LazyCatFragment {
    private LinearLayout horizontaladv_body;
    private LayoutInflater inflater;
    private RefreshScrollView _RefreshScrollView;
    private static final int GONE_HEADE_IMG = 0;
    private Timer _GoneHeadimg;
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
        item.findViewById(R.id.assembly_head_input).setBackground(Tools.CreateDrawable(3,
                getResources().getString(R.color.ThemeColor), "#ffffff", 10));
        item.findViewById(R.id.assembly_head_input).setFocusable(false);
        init(item);
        Tools.getMemorySize(getContext(), new ProgramInterface.onMemorySize() {
            @SuppressLint("LongLogTag")
            @Override
            public void onGet(int max, float total, float free) {
                Log.i(Config.DEBUG, "最大使用内存:" + max + "合计分配内存:" + total + "剩余内存:" + free);
            }
        });
        return item;
    }

    @SuppressLint({"NewApi", "ResourceType", "LongLogTag"})
    private void init(View item) {
        inflater = LayoutInflater.from(getContext());//初始化inflater
        //加载第一个横向的广告布局
        horizontaladv_body = item.findViewById(R.id.fragment_main_horizontaladv_body);//横向广告的布局
        View horizontaladv_view = inflater.inflate(R.layout.assembly_fragment_main_horizontaladv,
                null);
        horizontaladv_body.addView(horizontaladv_view);

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
        merchantMonitor.Start();
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
        NewShopinMonitor newShopinMonitor = new NewShopinMonitor(newShopinView, getContext());
        newShopinMonitor.Start();

        /*</新品上架促销>*/


        /**
         * 华丽分割线
         * ========================================================
         */
        /*<导航界面的管理>*/
        LinearLayout navA_body = item.findViewById(R.id.fragment_main_navA_body);
        View _navaBody = inflater.inflate(R.layout.assembly_fragment_main_nava, null);
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
                            Toast.makeText(getContext(), "Tag:" + monitor.GetTag(), Toast
                                    .LENGTH_SHORT).show();
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
                Dialog _dialog = new Dialog(getContext());
                _dialog.setContentView(R.layout.item_wait);
                _dialog.show();
                Intent i = new Intent();
                i.setClass(getContext(), WebServiceAct.class);
                i.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, web_values_act);
                startActivity(i);
                //停止刷新
            }

            @Override
            public void onScrollDistance(int distance) {

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
}

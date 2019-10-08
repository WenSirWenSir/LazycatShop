package shlm.lmcs.com.lazycat.LazyShopFrg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Views.RefreshScrollView;
import shlm.lmcs.com.lazycat.LazyShopInterface.LocationMapListener;
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
    private static final String MSG = "Mainfrg.java[+]";
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

    /**
     * 模块数据存储
     */
    private RefreshScrollView _RefreshScrollView;

    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        /*确定用户的物理地址*/
        SDKInitializer.initialize(getActivity().getApplicationContext());
        /*设置状态栏颜色*/
        item = inflater.inflate(R.layout.fragment_main, null);
        /*顶部标题*/
        _headTitle = item.findViewById(R.id.fragment_main_headTitle);
        /*处理顶部标题的字体*/
        TextUnt.with(_headTitle).setFontFile(getContext(), "canLogo");
        /*滑动控件的Body*/
        refreshBody = item.findViewById(R.id.fragment_main_body);
        for (int i = 0; i < 100; i++) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.item_mainshoplist, null);
            refreshBody.addView(item);
        }
        //初始化一个背景样式
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

        init(item);
        getServiceAddr("上杭县");
        return item;

    }


    /**
     * 判断该地址是否开通服务
     */
    @SuppressLint("NewApi")
    private void getServiceAddr(String Districe) {
        locationClient.stop();/*禁止多次去访问地址*/
        Net.doGet(getContext(), Config.HTTP_ADDR.getIsServiceIn(), new Net
                .onVisitInterServiceListener() {
            @Override
            public WaitDialog.RefreshDialog onStartLoad() {/*初始化一个DIALOG*/

                return null;
            }

            @Override
            public void onSucess(String tOrgin, final WaitDialog.RefreshDialog _RefreshDialog) {
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

    private void InitMain() {
    }

    private void init(View item) {

    }


}

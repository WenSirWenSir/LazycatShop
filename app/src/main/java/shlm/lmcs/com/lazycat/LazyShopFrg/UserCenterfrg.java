package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.text.TextUtils;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ViewSet;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.WxpayinitInstance;import shlm.lmcs.com.lazycat.LazyShopAct.LoginAct;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemAboutus;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemGuessshop;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemHelpus;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemIntegralPostal;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemPayvipRecordAct;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemShakeshops;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.Systemset;import shlm.lmcs.com.lazycat.LazyShopAct.UserAct.UsertrackAct;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.SuperSystemOS.Home;import shlm.lmcs.com.lazycat.TerminalSystemOS.PagesgetUser;public class UserCenterfrg extends LazyCatFragment {    private String MSG = "UserCenterfrg.java[+]";    private XmlTagValuesFactory.XMLValuesUserpageCard userpageCard = null;    private XmlTagValuesFactory.XMLValuesUserpageBtnItem item = null;/*控件BTN*/    private XmlTagValuesFactory.Init_UserCenterValues init_userCenter = null;    private ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard> cardList =            new ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard>();    private TextView business_name;    private TextView business_vip;    private String St_long;/*维度*/    private String St_lat;/*经度*/    private String St_busniessname;/*店铺全称*/    private String St_businessaddr;/*店铺的地址*/    private String St_businessPeople;/*店铺的负责人*/    private String St_businessTel;/*店铺的电话*/    private View body;    private WxpayinitInstance wxpayinitInstance;    private AlertDialog alertDialog;/*退出*/    private String Paykey;    private String _perpayId;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LocalValues.HTTP_ADDRS http_addrs;/*地址工具类*/    /*检测用户登录 是否加盟商工具*/    private SystemVip systemVip;    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        body = inflater.inflate(R.layout.fragment_user, null);        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getContext());        /*我的功能body*/        body.findViewById(R.id.usercenterfrg_userValuesBody).setBackground(Tools.CreateDrawable(1, "#ffffff",                "#ffffff", 15));        /*设置其他功能的导航的Body*/        body.findViewById(R.id.fragment_user_otherNav).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 15));        /*设置娱乐功能导航的Body*/        body.findViewById(R.id.fragment_userEntertainmentBody).setBackground(Tools.CreateDrawable(1, "#ffffff",                "#ffffff", 15));        /*设置Logo字体*/        TextUnt.with(body, R.id.fragment_user_ico).setFontFile(getContext(), "canLogo");        TextUnt.with(body, R.id.fragment_user_icoTitle).setFontFile(getContext(), "mvboli");/*        business_balance.setValues(80.98f);*/        /*这个现实商户的名称*/        business_name = body.findViewById(R.id.fragmen_user_business_name);        /*这个现实VIP*/        business_vip = body.findViewById(R.id.fragment_user_vip);        /*初始化用户的工具类*/        userToolsInstance = new LocalProgramTools.UserToolsInstance();        init(body);        listener(body);        /*设置游戏猜商品动画*/        ArrayList<Integer> arViews = new ArrayList<>();        arViews.add(R.id.fragment_userWhatshopGametitle);        arViews.add(R.id.fragment_userShaketitle);        arViews.add(R.id.fragment_userWhattimetitle);        ViewSet.with(body, arViews).setAnimation(LocalValues.VALUES_VIEWS.SHAKE_ANIMATION_2);        return body;    }    @SuppressLint({"NewApi", "ResourceType"})    private void init(final View item) {        systemVip = new SystemVip(getContext());/*初始化检查是否登录的类*/        /**         * 获取数据信息         */        PagesgetUser._get(getContext(), new PagesgetUser.OnGetlistener() {            @Override            public void onGet(PagesgetUser.Pages _pages) {                Log.i(MSG, "商家信息:" + _pages._name);                if (_pages._isJoin) {                    TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                            getResources().getString(R.color.colorVip), getResources().getString(R.color.colorVip),                            50)).setTextColor("#ffffff");                    TextUnt.with(item, R.id.fragment_user_Hello).setTextColor(getResources().getString(R.color.colorVip)).setVisibility(true);                } else {                    TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                            getResources().getString(R.color.colornoVip),                            getResources().getString(R.color.colornoVip), 50)).setTextColor("#ffffff");                    TextUnt.with(item, R.id.fragment_user_Hello).setTextColor(getResources().getString(R.color.colornoVip)).setVisibility(true);                }                TextUnt.with(item, R.id.fragmen_user_business_name).setText(_pages._name);                TextUnt.with(item, R.id.fragment_user_TvLongLat).setText("经度:" + _pages._long + "维度:" + _pages._lat).setVisibility(true);                TextUnt.with(item, R.id.fragment_user_integral).setText("￥" + Tools.calcToAdd(_pages._integral, "0"));/*设置积分*/            }            @Override            public void onError() {                Tools.showError(getContext(), "系统错误", getResources().getString(R.string.SystemError0001));            }            @Override            public void onCanlogin() {                Log.i(MSG, "没有登录");                //没有登录系统                TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                        getResources().getString(R.color.colornoVip), getResources().getString(R.color.colornoVip),                        50)).setTextColor("#ffffff");                /*设置没有登录的显示标题*/                TextUnt.with(business_name).setText(getResources().getString(R.string.noLoginView));                TextUnt.with(item, R.id.fragment_user_TvLongLat).setVisibility(false);/*设置经纬度不显示*/                TextUnt.with(item, R.id.fragment_user_integral).setText("￥0.00");/*设置积分为0*/                TextUnt.with(item, R.id.fragment_user_Hello).setVisibility(false);            }        });    }    /**     * 控件监听     *     * @param body     */    @SuppressLint("NewApi")    private void listener(final View body) {        /**         * 猜品牌点击事件(娱乐功能)         */        body.findViewById(R.id.fragment_userBtnwhatBrand).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(SystemGuessshop.class);            }        });        /**         * 摇一摇惊喜界面         */        body.findViewById(R.id.fragment_user_btnShare).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(SystemShakeshops.class);            }        });        /**         * 系统设置界面         */        body.findViewById(R.id.fragment_userBtnSystemset).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(Systemset.class);            }        });        /**         * 点击进入VIP充值记录中心         */        RelativeLayoutUnt.with(body, R.id.fragment_user_BtnpayStatusRecord).setOnclick(new View.OnClickListener() {            @Override            public void onClick(View v) {                Tools.showError(getContext(), "错误信息", "没有加盟记录哦");            }        });        /**         * 点击帮助中心         */        RelativeLayoutUnt.with(body, R.id.fragment_userBtntoService).setOnclick(new View.OnClickListener() {            @Override            public void onClick(View v) {                /**                 * 点击帮助中心的事件                 */                LazyCatFragmetStartAct(SystemHelpus.class);            }        });        /*用户的足迹的按钮*/        body.findViewById(R.id.fragment_user_btnTrack).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(UsertrackAct.class);            }        });        /*关于我们的按钮*/        body.findViewById(R.id.fragment_user_btnAboutAs).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                /**                 * 启动一个查看版本号的界面                 */                LazyCatFragmetStartAct(SystemAboutus.class);            }        });        /*优惠卷的按钮*/        body.findViewById(R.id.fragment_user_btnInteger).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getContext(), "错误信息", "您还没有登录系统");                    }                    @Override                    public void onIslogin() {                        LazyCatFragmetStartAct(SystemIntegralPostal.class);                    }                });            }        });        /**         * 手动选择登录的         *         */        business_name.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                TextView tv = (TextView) v;                /*如果是没有登录的  就跳转去实现登录或者注册*/                if (tv.getText().toString().trim().equals(getResources().getString(R.string.noLoginView))) {                    LazyCatFragmetStartAct(LoginAct.class);                } else if (tv.getText().toString().trim().equals(getResources().getString(R.string.leftProgram))) {                    LazyCatFragmetStartAct(Home.class);                    Log.i(MSG, "请求登录管理系统");                }            }        });        /**         * 退出登录的按钮         */        body.findViewById(R.id.fragment_user_btnExit).setOnClickListener(new View.OnClickListener() {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                /*点击的按钮*/                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getContext(), "错误信息", "您还没有登录系统");                    }                    @Override                    public void onIslogin() {                        Tools.showConfirm(getContext(), "确定要退出系统吗？", "该操作可能会导致您重新登录",                                new SweetAlertDialog.OnSweetClickListener() {                            @Override                            public void onClick(SweetAlertDialog sweetAlertDialog) {                                sweetAlertDialog.dismiss();                                if (userToolsInstance.ClearLocalCach()) {                                    Tools.showConfirm(getContext(), "清空成功", "您的登录信息已经删除啦",                                            new SweetAlertDialog.OnSweetClickListener() {                                        @Override                                        public void onClick(SweetAlertDialog sweetAlertDialog) {                                            sweetAlertDialog.dismiss();                                        }                                    });                                    init(body);                                } else {                                    Tools.showError(getContext(), "清空失败", "您的登录信息清空失败");                                }                            }                        });                    }                });            }        });    }    @Override    public void onResume() {        Log.i(MSG, "onResume");        init(body);        super.onResume();    }    @Override    public void onPause() {        Log.i(MSG, "onPause");        super.onPause();    }    @Override    public void onStop() {        Log.i(MSG, "onStop");        super.onStop();    }    @Override    public void onStart() {        Log.i(MSG, "onStart");        super.onStart();    }}
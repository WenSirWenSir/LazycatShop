package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.os.Bundle;import android.text.TextUtils;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.RelativeLayoutUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.ViewSet;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay.WxpayinitInstance;import shlm.lmcs.com.lazycat.LazyShopAct.LoginAct;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemAboutus;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemGuessshop;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemHelpus;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemIntegralPostal;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.SystemPayvipRecordAct;import shlm.lmcs.com.lazycat.LazyShopAct.SystemAct.Systemset;import shlm.lmcs.com.lazycat.LazyShopAct.UserAct.UsertrackAct;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.SuperSystemOS.Home;public class UserCenterfrg extends LazyCatFragment {    private String MSG = "UserCenterfrg.java[+]";    private XmlTagValuesFactory.XMLValuesUserpageCard userpageCard = null;    private XmlTagValuesFactory.XMLValuesUserpageBtnItem item = null;/*控件BTN*/    private XmlTagValuesFactory.Init_UserCenterValues init_userCenter = null;    private ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard> cardList =            new ArrayList<XmlTagValuesFactory.XMLValuesUserpageCard>();    private TextView business_name;    private TextView business_vip;    private TextView TvBusinessLonglat;/*店铺的经纬度*/    private TextView TvIntegral;    private String St_long;/*维度*/    private String St_lat;/*经度*/    private String St_busniessname;/*店铺全称*/    private String St_businessaddr;/*店铺的地址*/    private String St_businessPeople;/*店铺的负责人*/    private String St_businessTel;/*店铺的电话*/    private View body;    private WxpayinitInstance wxpayinitInstance;    private AlertDialog alertDialog;/*退出*/    private String Paykey;    private String _perpayId;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private LocalValues.HTTP_ADDRS http_addrs;/*地址工具类*/    /*检测用户登录 是否加盟商工具*/    private SystemVip systemVip;    @SuppressLint({"NewApi", "ResourceType"})    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        body = inflater.inflate(R.layout.fragment_user, null);        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getContext());        /*我的功能body*/        body.findViewById(R.id.usercenterfrg_userValuesBody).setBackground(Tools.CreateDrawable(1, "#ffffff",                "#ffffff", 15));        /*设置其他功能的导航的Body*/        body.findViewById(R.id.fragment_user_otherNav).setBackground(Tools.CreateDrawable(1, "#ffffff", "#ffffff", 15));        /*设置娱乐功能导航的Body*/        body.findViewById(R.id.fragment_userEntertainmentBody).setBackground(Tools.CreateDrawable(1, "#ffffff",                "#ffffff", 15));        /*设置Logo字体*/        TextUnt.with(body, R.id.fragment_user_ico).setFontFile(getContext(), "canLogo");        TextUnt.with(body, R.id.fragment_user_icoTitle).setFontFile(getContext(), "mvboli");/*        business_balance.setValues(80.98f);*/        /*这个现实商户的名称*/        business_name = body.findViewById(R.id.fragmen_user_business_name);        /*这个现实VIP*/        business_vip = body.findViewById(R.id.fragment_user_vip);        /*设置商品的积分*/        TvIntegral = body.findViewById(R.id.fragment_user_integral);        /*这个显示交易的次数*/        /*店铺的经纬度*/        TvBusinessLonglat = body.findViewById(R.id.fragment_user_TvLongLat);        /*初始化用户的工具类*/        userToolsInstance = new LocalProgramTools.UserToolsInstance();        init(body);        listener(body);        /*设置游戏猜商品动画*/        ArrayList<Integer> arViews = new ArrayList<>();        arViews.add(R.id.fragment_userWhatshopGametitle);        arViews.add(R.id.fragment_userShaketitle);        arViews.add(R.id.fragment_userWhattimetitle);        ViewSet.with(body, arViews).setAnimation(LocalValues.VALUES_VIEWS.SHAKE_ANIMATION_2);        return body;    }    @SuppressLint({"NewApi", "ResourceType"})    private void init(final View item) {        /*systemVip*/        systemVip = new SystemVip(getContext());        /**         *登录检查         */        systemVip.Start(new SystemVip.OnVipcheck() {            @Override            public void onIsvip() {            }            @Override            public void onIsnovip() {            }            @Override            public void onIsnologin() {                handlerUsernologin();            }            @Override            public void onIslogin() {                toGetuserPage();            }        });    }    /**     * 控件监听     *     * @param body     */    @SuppressLint("NewApi")    private void listener(final View body) {        /**         * 猜品牌点击事件(娱乐功能)         */        body.findViewById(R.id.fragment_userBtnwhatBrand).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(SystemGuessshop.class);            }        });        /**         * 系统设置界面         */        body.findViewById(R.id.fragment_userBtnSystemset).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(Systemset.class);            }        });        /**         * 点击进入VIP充值记录中心         */        RelativeLayoutUnt.with(body, R.id.fragment_user_BtnpayStatusRecord).setOnclick(new View.OnClickListener() {            @Override            public void onClick(View v) {                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getContext(), "错误信息", "您还没有登录系统");                    }                    @Override                    public void onIslogin() {                        LazyCatFragmetStartAct(SystemPayvipRecordAct.class);                    }                });            }        });        /**         * 点击帮助中心         */        RelativeLayoutUnt.with(body, R.id.fragment_userBtntoService).setOnclick(new View.OnClickListener() {            @Override            public void onClick(View v) {                /**                 * 点击帮助中心的事件                 */                LazyCatFragmetStartAct(SystemHelpus.class);            }        });        /*用户的足迹的按钮*/        body.findViewById(R.id.fragment_user_btnTrack).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatFragmetStartAct(UsertrackAct.class);            }        });        /*关于我们的按钮*/        body.findViewById(R.id.fragment_user_btnAboutAs).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                /**                 * 启动一个查看版本号的界面                 */                LazyCatFragmetStartAct(SystemAboutus.class);            }        });        /*优惠卷的按钮*/        body.findViewById(R.id.fragment_user_btnInteger).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getContext(), "错误信息", "您还没有登录系统");                    }                    @Override                    public void onIslogin() {                        LazyCatFragmetStartAct(SystemIntegralPostal.class);                    }                });            }        });        /**         * 手动选择登录的         *         */        business_name.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                TextView tv = (TextView) v;                /*如果是没有登录的  就跳转去实现登录或者注册*/                if (tv.getText().toString().trim().equals(getResources().getString(R.string.noLoginView))) {                    LazyCatFragmetStartAct(LoginAct.class);                } else if (tv.getText().toString().trim().equals(getResources().getString(R.string.leftProgram))) {                    LazyCatFragmetStartAct(Home.class);                    Log.i(MSG, "请求登录管理系统");                }            }        });        /**         * 退出登录的按钮         */        body.findViewById(R.id.fragment_user_btnExit).setOnClickListener(new View.OnClickListener() {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                /*点击的按钮*/                systemVip.Start(new SystemVip.OnVipcheck() {                    @Override                    public void onIsvip() {                    }                    @Override                    public void onIsnovip() {                    }                    @Override                    public void onIsnologin() {                        Tools.showError(getContext(), "错误信息", "您还没有登录系统");                    }                    @Override                    public void onIslogin() {                        Tools.showConfirm(getContext(), "确定要退出系统吗？", "该操作可能会导致您重新登录",                                new SweetAlertDialog.OnSweetClickListener() {                            @Override                            public void onClick(SweetAlertDialog sweetAlertDialog) {                                sweetAlertDialog.dismiss();                                if (userToolsInstance.ClearLocalCach()) {                                    Tools.showConfirm(getContext(), "清空成功", "您的登录信息已经删除啦",                                            new SweetAlertDialog.OnSweetClickListener() {                                        @Override                                        public void onClick(SweetAlertDialog sweetAlertDialog) {                                            sweetAlertDialog.dismiss();                                        }                                    });                                    init(body);                                } else {                                    Tools.showError(getContext(), "清空失败", "您的登录信息清空失败");                                }                            }                        });                    }                });            }        });    }    /**     * 获取用户的所有信息     */    private void toGetuserPage() {        /*开始登录检查*/        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.overDom();        Net.doPostXml(http_addrs.HTTP_ADDR_GETUSER_CENTERVALUES, new ProgramInterface() {            @SuppressLint("NewApi")            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                Log.i(MSG, "获取登录的用户的信息为:" + data.trim());                /*获取数据信息进行处理*/                _refreshDialog.dismiss();                if (data.trim().equals(LocalValues.NET_ERROR)) {                    /*清空本地的数据库*/                    if (userToolsInstance.ClearLocalCach()) {                        Toast.makeText(getContext(), getResources().getString(R.string.logintotime),                                Toast.LENGTH_SHORT).show();                    } else {                        /*无法删除文件*/                        Toast.makeText(getContext(), getResources().getString(R.string.clearloginxmlerror),                                Toast.LENGTH_SHORT).show();                    }                    handlerUsernologin();                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                            if (init_userCenter != null) {                                init_userCenter = null;                                init_userCenter = XmlTagValuesFactory.getXmlServiceInitCenterInstance();                            } else {                                init_userCenter = XmlTagValuesFactory.getXmlServiceInitCenterInstance();                            }                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*用户的余额*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_BLANCE)) {                                    init_userCenter.setSt_userBalance(Float.parseFloat(pullParser.nextText().trim()));                                }                                /*用户的状态*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_STATUS)) {                                    init_userCenter.setSt_userStatus(pullParser.nextText().trim());                                }                                /*用户的VIP状态*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_VIPSTATUS)) {                                    init_userCenter.setSt_userVip(pullParser.nextText().trim());                                }                                /*VIP的过期时间*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_VIPOVERDATE)) {                                    init_userCenter.setSt_userVipoverDate(pullParser.nextText().trim());                                }                                /*抵用券的数量*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_COUPON)) {                                    init_userCenter.setSt_coupon(pullParser.nextText().trim());                                }                                /*交易记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TRANSACTION)) {                                    init_userCenter.setSt_transaction(pullParser.nextText().trim());                                }                                /*通知发货记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SENDSYSTEM)) {                                    init_userCenter.setSt_sendsystem(pullParser.nextText().trim());                                }                                /*充值记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_RECHARGERECORD)) {                                    init_userCenter.setSt_rechargerecord(pullParser.nextText().trim());                                }                                /*派送记录*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_DELIVER)) {                                    init_userCenter.setSt_deliver(pullParser.nextText().trim());                                }                                /*获取积分*/                                if (tag.equals(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_INTEGER)) {                                    init_userCenter.setSt_integral(pullParser.nextText().trim());                                }                            } catch (Exception e) {                                Log.e(MSG, "解析服务器回传的数据失败:" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                        }                        @SuppressLint("ResourceType")                        @Override                        public void onEndDocument() {                            if (init_userCenter.getSt_userVip().equals(LocalValues.VALUES_USERCENTER.IS_NOT_VIP)) {                                /*不是VIP*/                                TextUnt.with(business_vip).setText("加入Vip").setBackground(Tools.CreateDrawable(1,                                        getResources().getString(R.color.colornoVip),                                        getResources().getString(R.color.colornoVip), 50)).setTextColor("#ffffff");                            } else {                                /*是VIP*/                                TextUnt.with(business_vip).setText("Vip").setTextColor(getResources().getString(R.color.colorVip));                                TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                                        getResources().getString(R.color.colorVip),                                        getResources().getString(R.color.colorVip), 50)).setTextColor("#ffffff");                                if (init_userCenter.getSt_userVipoverDate() != null && !TextUtils.isEmpty(init_userCenter.getSt_userVipoverDate())) {                                    TextUnt.with(body, R.id.fragment_user_UservipDone).setText(Tools.StamptoDate(init_userCenter.getSt_userVipoverDate()));                                }                            }                            /*设置商户名称*/                            TextUnt.with(business_name).setText(userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPNAME));                            /*设置店铺的经纬度*/                            TextUnt.with(TvBusinessLonglat).setText("经度:" + userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLONG) + "|维度:" + userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLAT)).setTextColor("#020433");                            /*设置积分数量*/                            TextUnt.with(TvIntegral).setText(init_userCenter.getSt_integral().trim());                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog(getActivity());                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("请稍后...", false);                return refreshDialog;            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    /**     * 设置用户没有登录的界面     */    @SuppressLint("ResourceType")    private void handlerUsernologin() {        TextUnt.with(business_vip).setText("Vip").setBackground(Tools.CreateDrawable(1,                getResources().getString(R.color.colornoVip), getResources().getString(R.color.colornoVip), 50)).setTextColor("#ffffff");        /*设置没有登录的显示标题*/        TextUnt.with(business_name).setText(getResources().getString(R.string.noLoginView));        TextUnt.with(TvBusinessLonglat).setVisibility(false);/*设置经纬度不显示*/        TextUnt.with(TvIntegral).setText("0");/*设置积分为0*/    }    @Override    public void onResume() {        Log.i(MSG, "onResume");        init(body);        super.onResume();    }    @Override    public void onPause() {        Log.i(MSG, "onPause");        super.onPause();    }    @Override    public void onStop() {        Log.i(MSG, "onStop");        super.onStop();    }    @Override    public void onStart() {        Log.i(MSG, "onStart");        super.onStart();    }}
package shlm.lmcs.com.lazycat.LazyShopVip;import android.content.Context;import android.util.Log;import android.widget.Toast;import org.xmlpull.v1.XmlPullParser;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * Vip检查类 */public class SystemVip {    public static final int USER_NO_LOGIN = 2;    public static final int USER_IS_VIP = 1;    public static final int USER_NOT_VIP = 0;    private LocalProgramTools.UserToolsInstance userToolsInstance;    private OnVipcheck _onVipcheck;    private Context _context;    private XmlBuilder.XmlInstance xmlInstance;    private LocalValues.HTTP_ADDRS http_addrs;    private String vip_status;/*网络回传VIP状态*/    private String MSG = "SystemVip.java[+]";    /**     * 初始化 获取上下文对象     *     * @param _context     */    public SystemVip(Context _context) {        Log.i(MSG, "检测Vip状态正常");        this._context = _context;        this.http_addrs = new LocalValues.HTTP_ADDRS(_context);        xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);    }    public void Start(final OnVipcheck onVipcheck) {        Log.i(MSG, "开始判断是否登录");        this._onVipcheck = onVipcheck;        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);        xmlInstance.initDom();        userToolsInstance = LocalProgramTools.getUserToolsInstance();/*获取工具类*/        if (userToolsInstance.isLogin()) {            Log.i(MSG, "登录云仓库了");            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_PHONE,                    userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_ACCOUNT));            xmlInstance.setXmlTree(LocalAction.ACTION_LOGIN.ACTION_TOKEN,                    userToolsInstance.GetUserpageOnAction(LocalAction.ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN));            xmlInstance.overDom();            Net.doPostXml(this.http_addrs.HTTP_ADDR_CHECK_VIP, new ProgramInterface() {                @Override                public void onSucess(String data, int code,                                     WaitDialog.RefreshDialog _refreshDialog) {                    _refreshDialog.dismiss();                    Log.i(MSG, "VIP工具类更新VIP获取数据:" + data.trim());                    if (data.equals(LocalValues.NET_ERROR) || data.equals("") || data.equals(USER_NO_LOGIN)) {                        Toast.makeText(_context, "实在不好意思,拉取VIP数据失败", Toast.LENGTH_LONG).show();                        if (_onVipcheck != null) {                            onVipcheck.onIsnologin();                        }                    } else {                        XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser,                                                   Integer id) {                                try {                                    if (tag.equals(LocalAction.ACTION_LOGIN.ACTION_XML_VIPSTATUS)) {                                        vip_status = pullParser.nextText().trim();                                    }                                } catch (Exception e) {                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            }                            @Override                            public void onEndDocument() {                                if (vip_status.equals("") || vip_status.equals("2")) {                                    if (_onVipcheck != null) {                                        _onVipcheck.onIsnologin();                                    }                                } else if (vip_status.equals(LocalValues.VALUES_USERCENTER.IS_VIP)) {                                    if (_onVipcheck != null) {                                        _onVipcheck.onIsvip();                                        _onVipcheck.onIslogin();                                    }                                } else if (vip_status.equals(LocalValues.VALUES_USERCENTER.IS_NOT_VIP)) {                                    if (_onVipcheck != null) {                                        _onVipcheck.onIsnovip();                                        _onVipcheck.onIslogin();                                    }                                }                            }                        });                    }                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    /*初始化一个DIALOG*/                    final WaitDialog.RefreshDialog refreshDialog =                            new WaitDialog.RefreshDialog(_context);                    WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                    wait_itme_dialogpage.setImg(R.id.item_wait_img);                    wait_itme_dialogpage.setView(R.layout.item_wait);                    wait_itme_dialogpage.setCanClose(false);                    wait_itme_dialogpage.setTitle(R.id.item_wait_title);                    refreshDialog.Init(wait_itme_dialogpage);                    refreshDialog.showRefreshDialog("加载中...", false);                    return refreshDialog;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        } else {            Log.i(MSG, "没有登录");            this._onVipcheck.onIsnologin();        }    }    public interface OnVipcheck {        void onIsvip();        void onIsnovip();        void onIsnologin();        void onIslogin();    }}
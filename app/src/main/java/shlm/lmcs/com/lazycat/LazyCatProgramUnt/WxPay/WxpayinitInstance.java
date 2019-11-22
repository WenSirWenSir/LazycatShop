package shlm.lmcs.com.lazycat.LazyCatProgramUnt.WxPay;import android.content.Context;import android.util.Log;import android.widget.Toast;import com.tencent.mm.opensdk.modelpay.PayReq;import com.tencent.mm.opensdk.openapi.IWXAPI;import com.tencent.mm.opensdk.openapi.WXAPIFactory;import org.xmlpull.v1.XmlPullParser;import java.util.LinkedList;import java.util.List;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;public class WxpayinitInstance {    private String MSG = "WxpayinitInstance.java[+]";    private String ATTACH;/*外面传入的参数*/    private String BODY;/*外面传入的参数*/    private String MCH_ID = "1499072172";    private String NONCE_STR;/*外面传入的参数*/    private String NOTIFY_URL = "http://www.baidu.com";    private String OUT_TRADE_NO;/*外面传入的参数*/    private String SPBILL_CREATE_IP = "127.0.0.1";    private String TOTAL_FEE;/*外面传入的参数*/    private String TRADE_TYPE;/*外面传入的参数*/    private String APP_ID = "wxbfc4618cf0bd4b88";    private Context CONTEXT;    private String prepay_id = "";/*支付KEY*/    /**     * @param _context     * @param _attach     附加数据  备注     * @param _body       支付场景     * @param _outtradeno 商户的订单号     * @param _totalfee   金额     */    public WxpayinitInstance(Context _context, String _attach, String _body, String _outtradeno,                             String _totalfee) {        this.ATTACH = _attach;        this.BODY = _body;        this.NONCE_STR = Utils.getOutTradNo();        this.OUT_TRADE_NO = _outtradeno;        this.TOTAL_FEE = _totalfee;        this.TRADE_TYPE = "APP";        this.CONTEXT = _context;    }    public String getXmldata() {        /**         * 整理listView         */        List<SingValues> signvalues = new LinkedList<SingValues>();        /**         * 创建表格 获取SIGN         */        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_APPID, APP_ID));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_ATTACH, ATTACH));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_BODY, this.BODY));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_MCH_ID, this.MCH_ID));        signvalues.add(new SingValues("nonce_str", this.NONCE_STR));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_NOTIFY_URL, this.NOTIFY_URL));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_OUT_TRADE_NO, this                .OUT_TRADE_NO));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_SPBILL_CREATE_IP, this                .SPBILL_CREATE_IP));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_TOTAL_FEE, this.TOTAL_FEE));        signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_TRADE_TYPE, this.TRADE_TYPE));        XmlBuilder.XmlInstance xmlInstance = new XmlBuilder.XmlInstance();        xmlInstance.initDom();        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_APPID, APP_ID);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_ATTACH, ATTACH);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_BODY, this.BODY);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_MCH_ID, this.MCH_ID);/*商户号*/        xmlInstance.setXmlTree("nonce_str", this.NONCE_STR);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_NOTIFY_URL, this.NOTIFY_URL);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_OUT_TRADE_NO, this.OUT_TRADE_NO);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_SPBILL_CREATE_IP, this                .SPBILL_CREATE_IP);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_TOTAL_FEE, this.TOTAL_FEE);        xmlInstance.setXmlTree(LocalAction.ACTION_WXPAY.ACTION_TRADE_TYPE, this.TRADE_TYPE);        xmlInstance.setXmlTree("sign", Utils.getSign(signvalues));        xmlInstance.overDom();        return xmlInstance.getXmlTree().trim();    }    /**     * 微信支付开始     */    public void startWxPay() {        Net.doPostXml(this.CONTEXT, LocalValues.HTTP_ADDRS.HTTP_ADDR_WXPAY_UNIFIEDORDER, new                ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                Log.i(MSG, "微信支付调用统一订单号数据回传:" + data.trim());                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                    @Override                    public void onFaile() {                    }                    @Override                    public void onStartDocument(String tag) {                    }                    @Override                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                        try {                            if (tag.equals("prepay_id")) {                                prepay_id = pullParser.nextText().trim();                            }                        } catch (Exception e) {                        }                    }                    @Override                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                    }                    @Override                    public void onEndDocument() {                        /*开始微信注册支付*/                        Log.i(MSG, "开始微信支付");                        try {                            PayReq req = new PayReq();                            req.appId = APP_ID;                            Log.i(MSG, "APPID" + APP_ID);                            req.partnerId = MCH_ID;                            Log.i(MSG, "MCH_ID" + MCH_ID);                            req.prepayId = prepay_id;                            Log.i(MSG, "PREPAYID" + prepay_id);                            req.packageValue = "Sign=WXPay";                            req.nonceStr = NONCE_STR;                            Log.i(MSG, "NONCE_STR" + NONCE_STR);                            req.timeStamp = String.valueOf(getTimeStamp());                            Log.i(MSG, "timeStamp" + req.timeStamp);                            List<SingValues> signvalues = new LinkedList<SingValues>();                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY.ACTION_APPID,                                    req.appId));                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY                                    .ACTION_NONCE_STR, req.nonceStr));                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY                                    .ACTION_PACKAGE, req.packageValue));                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY                                    .ACTION_PARTNERID, req.partnerId));                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY                                    .ACTION_PREPAYID, req.prepayId));                            signvalues.add(new SingValues(LocalAction.ACTION_WXPAY                                    .ACTION_TIMESTAMP, req.timeStamp));                            req.sign = Utils.getSign(signvalues);                            IWXAPI api = WXAPIFactory.createWXAPI(CONTEXT, null);                            api.sendReq(req);                        } catch (Exception e) {                            Log.e(MSG, "微信调用支付API错误：" + e.getMessage());                            Toast.makeText(CONTEXT, "微信支付调用失败", Toast.LENGTH_SHORT).show();                        }                    }                });            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                /*初始化一个DIALOG*/                final WaitDialog.RefreshDialog refreshDialog = new WaitDialog.RefreshDialog                        (CONTEXT);                WAIT_ITME_DIALOGPAGE wait_itme_dialogpage = new WAIT_ITME_DIALOGPAGE();                wait_itme_dialogpage.setImg(R.id.item_wait_img);                wait_itme_dialogpage.setView(R.layout.item_wait);                wait_itme_dialogpage.setCanClose(false);                wait_itme_dialogpage.setTitle(R.id.item_wait_title);                refreshDialog.Init(wait_itme_dialogpage);                refreshDialog.showRefreshDialog("获取支付订单号中", false);                return refreshDialog;            }            @Override            public void onFaile(String data, int code) {            }        }, getXmldata());    }    /*获取系统时间瘥*/    private long getTimeStamp() {        return System.currentTimeMillis() / 1000;    }}
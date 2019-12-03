package shlm.lmcs.com.lazycat.LazyShopAct;import android.annotation.SuppressLint;import android.content.Intent;import android.os.Bundle;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import io.github.xudaojie.qrcodelib.CaptureActivity;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;/** * 人心中的成见就是一座大山,任凭你怎么努力都休想搬动 * 我们迟早会分开,我们迟早会离婚 ---翁启鑫 * 2019年11月4号 * 我以为的支持  是你会在我最需要人支持的时候给我一丝的安慰说声没关系 慢慢来 我在 * 谢谢你的支持 无尽的嘲讽 无尽的挖苦  无尽的看不起,不然或许我不会成长》 * 你所说的对于金钱的无所谓 希望得到一个有温暖的爱情,你的所作所为都是在扯蛋. * 如果我有一天把代码中的信息给你看了,说明我对你已经没有任何的依赖,也没有了任何的寄托 * 我也不想做太多无所谓的解释 你看到的就是你看到的 千万别挽留 当这信息在你眼中出现,我将永远不会再对你好 */public class ScanQRCodeAct extends LazyCatAct {    private String MSG = "ScanQRCodeAct.java[+]";    private LocalProgramTools.UserToolsInstance userToolsInstance;    private ImageView barcodeImg;    private LocalValues.HTTP_ADDRS http_addrs;    /**     * 商品的参数列表     */    private XmlTagValuesFactory.Shopvalues shopvalues = null;    @Override    protected void onCreate(Bundle savedInstanceState) {        //setContentView(R.layout.activity_scanbarcode);        userToolsInstance = LocalProgramTools.getUserToolsInstance();        /**/        /*设置标题*/        //TextUnt.with(this, R.id.assembly_act_headTitle).setText("扫一扫");        setTransparentBar();        /*找到条码图片*/        //barcodeImg = findViewById(R.id.activity_scanbarcode_ScanBarcodeImg);       // doGetBarcodeImg();/*        doGetScandata("6902265360018");*/        init();        super.onCreate(savedInstanceState);    }    /**     * 加载条码图片     * acitivy_scanbarcode_toSendsystemBody     */    private void doGetBarcodeImg() {        Log.i(MSG, "图片条码地址:" + "http://api.k780.com/?app=barcode.get&bc_text=" + "6902265360018"                + "&appkey=" + getResources().getString(R.string.nowApiKey) + "&sign=" +                getResources().getString(R.string.nowApiMd5));        Glide.with(ScanQRCodeAct.this).load("http://api.k780.com/?app=barcode.get&bc_text=" +                "6902265360018" + "&appkey=" + getResources().getString(R.string.nowApiKey) +                "&sign=" + getResources().getString(R.string.nowApiMd5)).diskCacheStrategy                (DiskCacheStrategy.NONE).skipMemoryCache(false).into(barcodeImg);    }    /**     * 初始化 调用扫描器     */    private void init() {       // Listener();        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getApplicationContext());        StartScanActivity();    }    /**     * VIEW的事件监听     */    private void Listener() {        /*退出的点击监听事件*/        findViewById(R.id.assembly_act_headBackImg).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });        /*点击之后进入商品的详情界面*/        findViewById(R.id.activity_scanbarcode_SearchresultBody).setOnClickListener(new View                .OnClickListener() {            @Override            public void onClick(View v) {                XmlTagValuesFactory.Shopvalues v_shopvalue = (XmlTagValuesFactory.Shopvalues) v                        .getTag();                LazyCatStartActivityWithBundler(ShowshopOffice.class, true, Config.Windows                        .GET_WINDOW_VALUE_SHOP_MESSAGE, v_shopvalue.getTitle().trim(),                        GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH                                .VALUES_TO_SEARCH_SHOPKEYWORD);            }        });    }    private void StartScanActivity() {        Intent i = new Intent(this, CaptureActivity.class);        startActivityForResult(i, LocalValues.VALUES_SCAN.VALUES_QR_CODE);    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if (resultCode == RESULT_OK && requestCode == LocalValues.VALUES_SCAN.VALUES_QR_CODE &&                data != null) {            String result = data.getStringExtra("result");            doGetScandata(result);        } else {            doCantScan();        }    }    /**     * 无法获取扫描数据的通知     */    private void doCantScan() {        Toast.makeText(getApplicationContext(), "无法获取扫描结果,请重试", Toast.LENGTH_SHORT).show();        finish();    }    /**     * 获取到扫描结果之后的处理事件     */    private void doGetScandata(final String _scanData) {        if (_scanData.indexOf(LocalValues.VALUES_QRCODE.VALUES_QRCODE_ACTIVITY) != -1) {            /*窗口 二维码*/        } else if (_scanData.indexOf(LocalValues.VALUES_QRCODE.VALUES_QRCODE_WEBDATA) != -1) {            /*网页 二维码*/            String url = _scanData.split("\\|")[1];            WEB_VALUES_ACT web_values_act = new WEB_VALUES_ACT(url);            web_values_act.set_TitleBackColor("#ffffff");            web_values_act.set_StaticColor("#ffffff");            LeftCompanyActStartWebView(true, web_values_act);        } else {            /*商品的条码*/            XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(false);            xmlInstance.initDom();            xmlInstance.setXmlTree(LocalAction.ACTION_QRCODE.ACTION_BARCODE, _scanData.trim());            xmlInstance.overDom();            Net.doPostXml(getApplicationContext(), http_addrs.HTTP_ADDR_TOSCANQRCODE,                    new ProgramInterface() {                @Override                public void onSucess(String data, int code, WaitDialog.RefreshDialog                        _refreshDialog) {                    Log.i(MSG, data.trim());                    if (data.trim().equals(LocalValues.NET_ERROR)) {                        Toast.makeText(getApplicationContext(), "不好意思,该商品仓库暂时没有货源哦", Toast                                .LENGTH_SHORT).show();                        finish();                    } else {                        /*开始XML解析*/                        /*设置Laout为可以显示*//*                        LinearLayoutUnt.with(ScanQRCodeAct.this, R.id                                .activity_scanbarcode_ScrollBody).setVisibility(true);*/                        final XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data                                .trim());                        xmlanalysisFactory.Startanalysis(new XmlanalysisFactory                                .XmlanalysisInterface() {                            @Override                            public void onFaile() {                            }                            @Override                            public void onStartDocument(String tag) {                                shopvalues = XmlTagValuesFactory.getShopvaluesInstance();                            }                            @Override                            public void onStartTag(String tag, XmlPullParser pullParser, Integer                                    id) {                                try {                                    /*商品标题*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TITLE)) {                                        shopvalues.setTitle(pullParser.nextText().trim());                                    }                                    /*商品条码*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BARCODE)) {                                        shopvalues.setBarcode(pullParser.nextText().trim());                                    }                                    /*商品归属*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ASCRIPTION)) {                                        shopvalues.setAscription(pullParser.nextText().trim());                                    }                                    /*商品的品牌*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BRAND)) {                                        shopvalues.setBrand(pullParser.nextText().trim());                                    }                                    /*商品的零售价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_RETAIL)) {                                        shopvalues.setRetail(pullParser.nextText().trim());                                    }                                    /*商品单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_COMPANY)) {                                        shopvalues.setCompany(pullParser.nextText().trim());                                    }                                    /*商品生产日期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_EXP)) {                                        shopvalues.setExp(pullParser.nextText().trim());                                    }                                    /*商品的归属*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_GRADE)) {                                        shopvalues.setGrade(pullParser.nextText().trim());                                    }                                    /*商品的产地*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_INFROM)) {                                        shopvalues.setInfrom(pullParser.nextText().trim());                                    }                                    /*商品唯一标识 */                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_ONLYID)) {                                        shopvalues.setOnlyid(pullParser.nextText().trim());                                    }                                    /*商品的保质期*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PD)) {                                        shopvalues.setPd(pullParser.nextText().trim());                                    }                                    /*商品的终端建议售价*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_PRICE)) {                                        shopvalues.setPrice(pullParser.nextText().trim());                                    }                                    /*商品的规格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPEC)) {                                        shopvalues.setSpec(pullParser.nextText().trim());                                    }                                    /*商品的状态*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_STATIC)) {                                        shopvalues.set_static(pullParser.nextText().trim());                                    }                                    /*商品的起订数量*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SU)) {                                        shopvalues.setSu(pullParser.nextText().trim());                                    }                                    /*商品的批发价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_TP)) {                                        shopvalues.setTp(pullParser.nextText().trim());                                    }                                    /*商品的净重*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_WEIGHT)) {                                        shopvalues.setWeight(pullParser.nextText().trim());                                    }                                    /*商品的虚线的价格*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_DLP)) {                                        shopvalues.setDlp(pullParser.nextText().trim());                                    }                                    /*商品的图片地址*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_IMG)) {                                        shopvalues.setImg(pullParser.nextText().trim());                                    }                                    /*商品的最低的组合单位*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_SPLITUNIT)) {                                        shopvalues.setSplitUnit(pullParser.nextText().trim());                                    }                                    /*商品的对接商家*/                                    if (tag.equals(LocalAction.ACTION_SHOPVALUES                                            .ACTION_SHOPVALUES_BUSINSS)) {                                        shopvalues.setBusiness(pullParser.nextText().trim());                                    }                                } catch (Exception e) {                                    Log.i(MSG, "");                                }                            }                            @Override                            public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                            }                            @Override                            public void onEndDocument() {                                /*有存在商品  打开*/                                LazyCatStartActivityWithBundler(ShowshopOffice.class, true, Config.Windows                                                .GET_WINDOW_VALUE_SHOP_MESSAGE, shopvalues.getTitle().trim(),                                        GET_WINDOW_VALUE_SHOP_ACTION, LocalValues.VALUES_SEARCH                                                .VALUES_TO_SEARCH_SHOPKEYWORD);                           /*     initMainpage(_scanData);                                findViewById(R.id.activity_scanbarcode_SearchresultBody).setTag                                        (shopvalues);*/                            }                        });                    }                }                @Override                public WaitDialog.RefreshDialog onStartLoad() {                    return null;                }                @Override                public void onFaile(String data, int code) {                }            }, xmlInstance.getXmlTree().trim());        }    }    /**     * 开始整理界面     */    @SuppressLint("ResourceType")    private void initMainpage(String _scanData) {        if (shopvalues != null) {            /*开始整理商品的Item*/            switch (shopvalues.get_static()) {                case LocalValues.VALUES_SHOPPAGE.NORMAL:                    /*商品正常*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(false);                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color                                .colorPrice));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.colorPrice));                    }                    break;                case LocalValues.VALUES_SHOPPAGE.PROMOTION:                    /*促销*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(true)                            .setBackColor(getResources().getString(R.color.colorPrice))                            .setTextColor("#ffffff").setText("促销商品");                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color                                .colorPrice));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.colorPrice));                    }                    break;                case LocalValues.VALUES_SHOPPAGE.REDUCTION:                    /*满减*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(true)                            .setBackColor(getResources().getString(R.color.colorPrice))                            .setTextColor("#ffffff").setText("商品满减");                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color                                .colorPrice));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.colorPrice));                    }                    break;                case LocalValues.VALUES_SHOPPAGE.VOLUME:                    /*用卷*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(true)                            .setBackColor(getResources().getString(R.color.colorPrice))                            .setTextColor("#ffffff").setText("用卷商品");                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color                                .colorPrice));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.colorPrice));                    }                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_VIP:                    /*只有vip可以享受*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(true)                            .setBackColor(getResources().getString(R.color.colorVip))                            .setTextColor("#ffffff").setText("VIP专享");                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color.colorVip));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.colorVip));                    }                    break;                case LocalValues.VALUES_SHOPPAGE.ONLY_ONE:                    /*限定一件*/                    TextUnt.with(this, R.id.item_searchshoplist_titleStatic).setVisibility(true)                            .setBackColor(getResources().getString(R.color.ThemeColor))                            .setTextColor("#ffffff").setText("限定商品");                    if (userToolsInstance.isLogin()) {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText(shopvalues                                .getTp()).setTextColor(getResources().getString(R.color                                .ThemeColor));                    } else {                        TextUnt.with(this, R.id.item_searchshoplist_price).setText("*.**")                                .setTextColor(getResources().getString(R.color.ThemeColor));                    }                    break;            }            /*设置标题*/            TextUnt.with(this, R.id.item_searchshoplist_title).setText(shopvalues.getTitle());            /*设置价格单位*/            TextUnt.with(this, R.id.item_searchshoplist_company).setText("/" + shopvalues                    .getCompany());            /*设置商家供货单位*/            TextUnt.with(this, R.id.item_searchshoplist_businessName).setText(shopvalues                    .getBusiness());            /*图片的ITEM*/            ImageView img = findViewById(R.id.item_searchshoplist_img);            Glide.with(getApplicationContext()).load("http://f.freep.cn/583105/SHOP_DATABASE/" +                    shopvalues.getImg()).diskCacheStrategy(DiskCacheStrategy.NONE)                    .skipMemoryCache(false).into(img);        } else {        }    }}
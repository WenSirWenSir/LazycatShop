package shlm.lmcs.com.lazycat.LazyShopAct;import android.annotation.SuppressLint;import android.content.Intent;import android.os.Bundle;import android.util.Log;import android.view.View;import android.widget.ImageView;import android.widget.Toast;import com.bumptech.glide.Glide;import com.bumptech.glide.load.engine.DiskCacheStrategy;import org.xmlpull.v1.XmlPullParser;import cn.pedant.SweetAlert.SweetAlertDialog;import io.github.xudaojie.qrcodelib.CaptureActivity;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlTagValuesFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopTools.LocalProgramTools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import static shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config.Windows.GET_WINDOW_VALUE_SHOP_ACTION;public class ScanQRCodeAct extends LazyCatAct {    private String MSG = "ScanQRCodeAct.java[+]";    private LocalProgramTools.UserToolsInstance userToolsInstance;    private ImageView barcodeImg;    private LocalValues.HTTP_ADDRS http_addrs;    private pages _pages;/*商品表格*/    @Override    protected void onCreate(Bundle savedInstanceState) {        setTransparentBar();        setContentView(R.layout.activity_scanbarcode);        userToolsInstance = LocalProgramTools.getUserToolsInstance();        /**/        /*设置标题*/        //TextUnt.with(this, R.id.assembly_act_headTitle).setText("扫一扫");        setTransparentBar();        /*找到条码图片*/        //barcodeImg = findViewById(R.id.activity_scanbarcode_ScanBarcodeImg);        // doGetBarcodeImg();/*        doGetScandata("6902265360018");*/        init();        super.onCreate(savedInstanceState);    }    /**     * 初始化 调用扫描器     */    @SuppressLint("ResourceType")    private void init() {        /**         * 设置样式         */        findViewById(R.id.activity_scanbarcodeBtntoPay).setBackground(Tools.CreateDrawable(1,                getResources().getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 100));        /*获取地址工具类*/        http_addrs = LocalValues.getHttpaddrs(getApplicationContext());        StartScanActivity();    }    private void StartScanActivity() {        Intent i = new Intent(this, CaptureActivity.class);        startActivityForResult(i, LocalValues.VALUES_SCAN.VALUES_QR_CODE);    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if (resultCode == RESULT_OK && requestCode == LocalValues.VALUES_SCAN.VALUES_QR_CODE && data != null) {            String result = data.getStringExtra("result");            doGetScandata(result);        } else {        }    }    /**     * 获取到扫描结果之后的处理事件     */    private void doGetScandata(final String _scanData) {        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getJavaXmlinstanceBuilder(true);        xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BARCODE, _scanData);        xmlInstance.overJavaDom();        Log.i(MSG, "要提交的XML信息:" + xmlInstance.getXmlTree());        Net.doPostXml(http_addrs.HTTP_ADDR_SYSTEMSCANQR_SHOP, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "获取条码商品信息为:" + data.trim());                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                    @Override                    public void onFaile() {                    }                    @Override                    public void onStartDocument(String tag) {                        _pages = new pages();                    }                    @Override                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                        try {                            //状态                            if (tag.equals(LocalAction.ACTION_RETURNCODE)) {                                _pages.set_returnCode(pullParser.nextText().trim());                            }                            /*标题*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TITLE)) {                                _pages.set_title(pullParser.nextText().trim());                            }                            /*积分*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INTEGRAL)) {                                _pages.set_integral(pullParser.nextText().trim());                            }                            /*条码*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_BARCODE)) {                                _pages.set_barcode(pullParser.nextText().trim());                            }                            /*批发价格*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_TP)) {                                _pages.set_tp(pullParser.nextText().trim());                            }                            /*商品唯一ID*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_ONLYID)) {                                _pages.set_onlyId(pullParser.nextText().trim());                            }                            /*设置图片地址*/                            if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_IMG)) {                                _pages.set_img(pullParser.nextText().trim());                            }                        } catch (Exception e) {                            e.printStackTrace();                        }                    }                    @Override                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                    }                    @Override                    public void onEndDocument() {                        if (_pages.get_returnCode().equals(LocalValues.NET_ERROR)) {                            Tools.showConfirm(ScanQRCodeAct.this, "温馨提示", "在云仓库中没有发现该商品条码",                                    new SweetAlertDialog.OnSweetClickListener() {                                @Override                                public void onClick(SweetAlertDialog sweetAlertDialog) {                                    finish();                                }                            });                        } else {                            initMainpage();                        }                    }                });            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(ScanQRCodeAct.this);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree());    }    /**     * 开始整理界面     */    @SuppressLint("ResourceType")    private void initMainpage() {        /*设置批发价格*/        if (_pages.get_integral().equals("")) {            TextUnt.with(this, R.id.activity_scanbarcodeTpView).setText("*.**");        } else {            TextUnt.with(this, R.id.activity_scanbarcodeTpView).setText(Tools.calcToAdd(_pages.get_tp(), "0"));        }        /*设置积分*/        if (_pages.get_tp().equals("")) {            TextUnt.with(this, R.id.activity_scanbarcodeIntegralView).setText("*.**");        } else {            TextUnt.with(this, R.id.activity_scanbarcodeIntegralView).setText(Tools.calcToAdd(_pages.get_integral(),                    "0"));        }        /*设置商品标题*/        TextUnt.with(this, R.id.activity_scanbarcodeTitle).setText("[商品标题]" + _pages.get_title());        /*设置图片地址*/        ImageView img = findViewById(R.id.activity_scanbarcodeImg);        Glide.with(getApplicationContext()).load(http_addrs.HTTP_ADDR_PROGRAM_IMGSERVICE + _pages.get_img()).                diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(img);    }    /**     * 商品界面     */    class pages {        private String _title;/*商品标题*/        private String _onlyId;/*商品唯一ID*/        private String _img;/*图片地址*/        private String _tp;/*批发价格*/        private String _integral;/*积分*/        private String _barcode;/*商品条码*/        private String _returnCode;/*返回代码*/        public String get_title() {            return _title;        }        public void set_title(String _title) {            this._title = _title;        }        public String get_onlyId() {            return _onlyId;        }        public void set_onlyId(String _onlyId) {            this._onlyId = _onlyId;        }        public String get_img() {            return _img;        }        public void set_img(String _img) {            this._img = _img;        }        public String get_tp() {            return _tp;        }        public void set_tp(String _tp) {            this._tp = _tp;        }        public String get_integral() {            return _integral;        }        public void set_integral(String _integral) {            this._integral = _integral;        }        public String get_barcode() {            return _barcode;        }        public void set_barcode(String _barcode) {            this._barcode = _barcode;        }        public String get_returnCode() {            return _returnCode;        }        public void set_returnCode(String _returnCode) {            this._returnCode = _returnCode;        }    }}
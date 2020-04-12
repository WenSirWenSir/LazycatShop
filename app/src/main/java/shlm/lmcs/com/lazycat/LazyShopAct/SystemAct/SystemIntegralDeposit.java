package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.graphics.Color;import android.graphics.Typeface;import android.os.Bundle;import android.text.Editable;import android.text.Html;import android.text.TextWatcher;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.Window;import android.widget.EditText;import org.xmlpull.v1.XmlPullParser;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WAIT_ITME_DIALOGPAGE;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.TerminalSystemOS.Cash;/** * 提交提现积分请求 */public class SystemIntegralDeposit extends LazyCatAct {    private String MSG = "SystemIntegralDeposit.java[+]";    /**     * 提现的输入框     */    private EditText et_money;    private String _returnCode;    private Cash.Cashpages _cashpages;    private LocalValues.HTTP_ADDRS http_addrs;    @Override    protected void onCreate(Bundle savedInstanceState) {        setTransparentBar();        setContentView(R.layout.activity_integraldeposit);        et_money = findViewById(R.id.activity_integraldepositEditMoney);        http_addrs = new LocalValues.HTTP_ADDRS(getApplicationContext());        TextUnt.with(SystemIntegralDeposit.this, R.id.assembly_act_headTitle).setText("积分兑现金");        setBackListener(R.id.assembly_act_headBackImg);        init();        listener();        super.onCreate(savedInstanceState);    }    /**     * 系统初始化     */    @SuppressLint({"ResourceType", "NewApi"})    private void init() {        /*设置为空的输入*/        et_money.setText("");        /*判断用户是否已经有存在银行卡了*/        Cash._start(SystemIntegralDeposit.this, new Cash.onCashe() {            @Override            public void _onGet(Cash.Cashpages _Cashpages) {                _cashpages = _Cashpages;                /*整理界面*/                /*设置输入框的字体*/                Typeface typeface = Typeface.createFromAsset(getAssets(), "font/system_ttf" + "" + ".ttf");                et_money.setTypeface(typeface);                /*设置标题的颜色*/                findViewById(R.id.assembly_act_headBody).setBackgroundColor(Color.parseColor("#efefef"));                /*设置点击提现按钮的样式*/                TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBtnDeposit).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 5)).setTag(false);                /*设置用户的银行卡所属银行和后六位*/                TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBanksAndCard).setText(_Cashpages._bankInfo + ":" + _Cashpages._bankCard.substring(_Cashpages._bankCard.length() - 4, _Cashpages._bankCard.length()));                /*设置提现的边框*/                findViewById(R.id.activity_integraldepositBody).setBackground(Tools.CreateDrawable(1, "#ffffff",                        "#ffffff", 10));                TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositTomuchmsg).setText(                        "您最多可以兑换的积分为:").setaddText(Html.fromHtml("<font color=\"#f30d88\">" + Tools.calcToAdd(_Cashpages._integral, "0") + "积分" + "</font>")).setaddText(",其中冻结积分为:").setaddText(Html.fromHtml("<font color=\"#a9a9a9" + "\">" + _Cashpages._frozenIntegral + "积分" + "</font>"));            }            @Override            public void _onError() {                Tools.showError(SystemIntegralDeposit.this, "错误信息", getResources().getString(R.string.SystemError0004));            }            @Override            public void _onNologin() {                Tools.showError(SystemIntegralDeposit.this, "错误信息", "您还没登录系统");            }            @Override            public void _onNoBank() {                Tools.showConfirm(SystemIntegralDeposit.this, "错误信息", "您必须添加一张银行卡才能进行积分提现哦",                        new SweetAlertDialog.OnSweetClickListener() {                    @Override                    public void onClick(SweetAlertDialog sweetAlertDialog) {                        finish();                    }                });            }        });    }    /**     * 系统的监听     */    private void listener() {        /**         * 点击积分兑换的按钮的监听事件         *         */        findViewById(R.id.activity_integraldepositBtnDeposit).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                /*开始提交申请请求*/                if ((boolean) v.getTag()) {                    /*提交信息到服务器*/                    XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getJavaXmlinstanceBuilder(true);                    xmlInstance.setXmlTree(LocalAction.ACTION, LocalValues.VALUES_CASH._TAGTO_CASH);                    xmlInstance.setXmlTree(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INTEGRAL,                            et_money.getText().toString());//设置要求提现的积分                    xmlInstance.overJavaDom();                    Log.i(MSG, "提交的XML信息为:" + xmlInstance.getXmlTree());                    Net.doPostXml(http_addrs.HTTP_ADDR_POSTCASH, new ProgramInterface() {                        @Override                        public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                            _refreshDialog.dismiss();                            Log.i(MSG, "申请提现返回:" + data.trim());                            if (data.equals("") || data.equals(LocalValues.NET_ERROR)) {                                Tools.showError(SystemIntegralDeposit.this, "错误", "申请提现失败");                            } else {                                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                                    @Override                                    public void onFaile() {                                    }                                    @Override                                    public void onStartDocument(String tag) {                                    }                                    @Override                                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                                        try {                                            if (tag.equals(LocalAction.ACTION_RETURNCODE)) {                                                _returnCode = pullParser.nextText().trim();                                            }                                        } catch (Exception e) {                                            e.printStackTrace();                                        }                                    }                                    @Override                                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                                    }                                    @Override                                    public void onEndDocument() {                                        if (LocalValues.NET_OK.equals(_returnCode)) {                                            Tools.showSuccess(SystemIntegralDeposit.this, "成功", "提现提交成功,预计24小时内到账");                                            init();                                        }                                    }                                });                            }                        }                        @Override                        public WaitDialog.RefreshDialog onStartLoad() {                            return Tools.getShowwait(SystemIntegralDeposit.this);                        }                        @Override                        public void onFaile(String data, int code) {                        }                    }, xmlInstance.getXmlTree().trim());                } else {                    Tools.showError(SystemIntegralDeposit.this, "错误信息", "您的输入积分数量有误,或许您没有那么多可以使用积分哦");                }            }        });        /**         * 解释冻结积分         */        findViewById(R.id.activity_integraldepositBtnAsk).setOnClickListener(new View.OnClickListener() {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                AlertDialog dialog;                AlertDialog.Builder builder = new AlertDialog.Builder(SystemIntegralDeposit.this);                View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_msg, null, false);                builder.setView(item);                TextUnt.with(item, R.id.item_msgMsg).setText(R.string.aboutFrozen);                dialog = builder.show();                Window window = dialog.getWindow();                window.setWindowAnimations(R.style.DialogStyle);                TextUnt.with(item, R.id.item_msgBtnConfirm).setOnClick(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        AlertDialog dialg = (AlertDialog) v.getTag();                        dialg.dismiss();                    }                }).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor),                        getResources().getString(R.color.ThemeColor), 5)).setTag(dialog).setTextColor("#ffffff");            }        });        /**         * 判断输入是否大于输入的积分         */        et_money.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }            @Override            public void onTextChanged(CharSequence s, int start, int before, int count) {            }            @SuppressLint("ResourceType")            @Override            public void afterTextChanged(Editable s) {                try {                    if (s.toString().equals("")) {                        /*设置提现按钮样式  并且设置不能提交数据信息*/                        TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBtnDeposit).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 5)).setTag(false);                    } else if (_cashpages._integral.equals("0")) {                        et_money.setTextColor(Color.parseColor(getResources().getString(R.color.colorFrozen)));                        /*设置提现按钮样式  并且设置不能提交数据信息*/                        TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBtnDeposit).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.colorFrozen), getResources().getString(R.color.colorFrozen), 5)).setTag(false);                    } else if (Float.parseFloat(s.toString()) > Float.parseFloat(Tools.calcTodel(_cashpages._integral                            , _cashpages._frozenIntegral))) {                        et_money.setTextColor(Color.parseColor(getResources().getString(R.color.colorFrozen)));                        /*设置提现按钮样式  并且设置不能提交数据信息*/                        TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBtnDeposit).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.colorFrozen), getResources().getString(R.color.colorFrozen), 5)).setTag(false);                    } else {                        et_money.setTextColor(Color.parseColor("#000000"));                        /*设置提现按钮样式  设置可以提交数据信息*/                        TextUnt.with(SystemIntegralDeposit.this, R.id.activity_integraldepositBtnDeposit).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 5)).setTag(true);                    }                } catch (Exception e) {                    Tools.showError(SystemIntegralDeposit.this, "错误信息", "您的输入范围有点夸张啦,请检查检查哦");                }            }        });    }}
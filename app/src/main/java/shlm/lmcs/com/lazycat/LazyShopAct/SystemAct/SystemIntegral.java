package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.app.Dialog;import android.os.Bundle;import android.support.annotation.Nullable;import android.view.LayoutInflater;import android.view.View;import android.view.Window;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.R;import shlm.lmcs.com.lazycat.TerminalSystemOS.PagesgetIntegralMain;/** * 显示积分余额界面 */public class SystemIntegral extends LazyCatAct {    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {        setStatusBar("#efefef");        setContentView(R.layout.activity_integralbalance);        setBackListener(R.id.activity_integralbalanceBtnback);        super.onCreate(savedInstanceState);        init();        listener();    }    /**     * 初始化界面     */    @SuppressLint("ResourceType")    private void init() {        /*充值的按钮样式*/        TextUnt.with(this, R.id.activity_integralbalanceBtnRecharge).setBackground(Tools.CreateDrawable(1,                getResources().getString(R.color.ThemeColor), getResources().getString(R.color.ThemeColor), 5));        /*提现的按钮样式*/        TextUnt.with(this, R.id.activity_integralbalanceBtnDeposit).setBackground(Tools.CreateDrawable(1, "#e9e9e9",                "#e9e9e9", 5)).setTextColor(getResources().getString(R.color.ThemeColor));        /**         * 开始获取整理数据         */        PagesgetIntegralMain._get(SystemIntegral.this, new PagesgetIntegralMain.OnIntegralMainget() {            @Override            public void _OnGet(PagesgetIntegralMain.IntegralMainpages _IntegralMainpages) {                TextUnt.with(SystemIntegral.this, R.id.activity_integralbalanceIntegral).setText("￥" + Tools.calcToAdd(_IntegralMainpages._integral, "0"));                if (_IntegralMainpages._FrozenIntegral.equals("")) {                    findViewById(R.id.activity_integralbalanceFrozenIntegralBody).setVisibility(View.GONE);                } else {                    findViewById(R.id.activity_integralbalanceFrozenIntegralBody).setVisibility(View.VISIBLE);                    TextUnt.with(SystemIntegral.this, R.id.activity_integralbalanceFrozenIntegral).setText(                            "其中包含冻结积分:" + _IntegralMainpages._FrozenIntegral).setVisibility(true);                }            }            @Override            public void _OnError() {                Tools.showError(SystemIntegral.this, "系统错误", getResources().getString(R.string.SystemError0003));            }            @Override            public void _OnnoLogin() {                Tools.showError(SystemIntegral.this, "错误信息", "您还没有登录系统");                TextUnt.with(SystemIntegral.this, R.id.activity_integralbalanceIntegral).setText("￥:0.00");            }        });    }    /**     * 监听事件     */    private void listener() {        /**         * 查看积分详情         */        TextUnt.with(this, R.id.activity_integralbalanceBtnaboutIntegral).setOnClick(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatActStartActivity(SystemIntegralDetiled.class, false);            }        });        /**         * 申请提现积分         */        TextUnt.with(this, R.id.activity_integralbalanceBtnDeposit).setOnClick(new View.OnClickListener() {            @Override            public void onClick(View v) {                LazyCatActStartActivity(SystemIntegralDeposit.class, false);            }        });        /**         * 冻结积分         */        findViewById(R.id.activity_integralbalanceBtnAsk).setOnClickListener(new View.OnClickListener() {            @SuppressLint("ResourceType")            @Override            public void onClick(View v) {                AlertDialog dialog;                AlertDialog.Builder builder = new AlertDialog.Builder(SystemIntegral.this);                View item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_msg, null, false);                builder.setView(item);                TextUnt.with(item, R.id.item_msgMsg).setText(R.string.aboutFrozen);                dialog = builder.show();                Window window = dialog.getWindow();                window.setWindowAnimations(R.style.DialogStyle);                TextUnt.with(item, R.id.item_msgBtnConfirm).setOnClick(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        AlertDialog dialg = (AlertDialog) v.getTag();                        dialg.dismiss();                    }                }).setBackground(Tools.CreateDrawable(1, getResources().getString(R.color.ThemeColor),                        getResources().getString(R.color.ThemeColor), 5)).setTag(dialog).setTextColor("#ffffff");            }        });    }}
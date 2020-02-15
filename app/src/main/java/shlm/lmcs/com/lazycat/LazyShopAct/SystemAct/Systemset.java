package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.support.annotation.Nullable;import android.view.View;import android.widget.ImageView;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * 系统设置 */public class Systemset extends LazyCatAct {    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {        setStatusBar("#efefef");        setContentView(R.layout.activity_systemset);        TextUnt.with(this, R.id.assembly_act_headTitle).setText("系统设置");        setBackListener(R.id.assembly_act_headBackImg);        super.onCreate(savedInstanceState);        init();        listener();    }    /**     * 初始化设置     */    @SuppressLint("ResourceType")    private void init() {        /**         * 判断商品显示动画TOKEN是否为打开         */        String _ShopanimationStatus = Tools.getToken(getApplicationContext(),                LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME, LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY);        if (_ShopanimationStatus.equals("") || _ShopanimationStatus.equals(LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN)) {            /*没有设置的话 就直接设置默认打开*/            ImageView img = findViewById(R.id.activity_systemsetBtnShopanmationStatus);            img.setImageDrawable(getResources().getDrawable(R.drawable.ico_systemset));            img.setTag(true);        } else {            /*关闭*/            ImageView img = findViewById(R.id.activity_systemsetBtnShopanmationStatus);            img.setImageDrawable(getResources().getDrawable(R.drawable.ico_systemnoset));            img.setTag(false);        }        /**         * 判断订单共享是否打开         */        String _ShareorderStatus = Tools.getToken(getApplicationContext(),                LocalAction.ACTION_SYSTEM_SET._SHAREORDERNAME, LocalAction.ACTION_SYSTEM_SET._SHAREORDERKEY);        if (_ShareorderStatus.equals("") || _ShareorderStatus.equals(LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN)) {            /*没有设置默认为打开*/            ImageView img = findViewById(R.id.activity_systemsetBtnshareOrderStatus);            img.setImageDrawable(getResources().getDrawable(R.drawable.ico_systemset));            img.setTag(true);        } else {            /*关闭*/            ImageView img = findViewById(R.id.activity_systemsetBtnshareOrderStatus);            img.setImageDrawable(getResources().getDrawable(R.drawable.ico_systemnoset));            img.setTag(false);        }    }    /**     * 系统监听事件     */    private void listener() {        /**         * 判断是否要设置商品ITEM的动画         */        findViewById(R.id.activity_systemsetBtnShopanmationStatus).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if ((Boolean) findViewById(R.id.activity_systemsetBtnShopanmationStatus).getTag()) {                    Tools.showConfirm(Systemset.this, "提示信息", "确定关闭商品显示动画吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME,                                    LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_CLOSE);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                } else {                    Tools.showConfirm(Systemset.this, "提示信息", "确定显示商品显示动画吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME,                                    LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                }            }        });        /**         * 打开/关闭订单共享         */        findViewById(R.id.activity_systemsetBtnshareOrderStatus).setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if ((Boolean) findViewById(R.id.activity_systemsetBtnshareOrderStatus).getTag()) {                    Tools.showConfirm(Systemset.this, "提示信息", "确定关闭订单共享吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._SHAREORDERNAME,                                    LocalAction.ACTION_SYSTEM_SET._SHAREORDERKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_CLOSE);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                } else {                    Tools.showConfirm(Systemset.this, "提示信息", "确定打开订单共享吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._SHAREORDERNAME,                                    LocalAction.ACTION_SYSTEM_SET._SHAREORDERKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                }            }        });    }}
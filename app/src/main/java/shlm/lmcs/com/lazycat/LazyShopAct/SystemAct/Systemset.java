package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.support.annotation.Nullable;import android.view.View;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;import shlm.lmcs.com.lazycat.R;/** * 系统设置 */public class Systemset extends LazyCatAct {    @Override    protected void onCreate(@Nullable Bundle savedInstanceState) {        setStatusBar("#efefef");        setContentView(R.layout.activity_systemset);        TextUnt.with(this, R.id.assembly_act_headTitle).setText("系统设置");        setBackListener(R.id.assembly_act_headBackImg);        super.onCreate(savedInstanceState);        init();        listener();    }    /**     * 初始化设置     */    @SuppressLint("ResourceType")    private void init() {        /**         * 判断TOKEN是否为打开         */        String _ShopanimationStatus = Tools.getToken(getApplicationContext(),                LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME, LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY);        if (_ShopanimationStatus.equals("") || _ShopanimationStatus.equals(LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN)) {            /*没有设置的话 就直接设置默认打开*/            TextUnt.with(this, R.id.activity_systemsetBtnsetShopanimation).setText("打开").setTextColor(getResources().getString(R.color.colorText));        } else {            /*关闭*/            TextUnt.with(this, R.id.activity_systemsetBtnsetShopanimation).setText("关闭").setTextColor("#a9a9a9");        }    }    /**     * 系统监听事件     */    private void listener() {        /**         * 判断是否要设置商品ITEM的动画         */        TextUnt.with(this, R.id.activity_systemsetBtnsetShopanimation).setOnClick(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (TextUnt.with(Systemset.this, R.id.activity_systemsetBtnsetShopanimation).getTexttoString().equals("打开")) {                    Tools.showConfirm(Systemset.this, "提示信息", "确定关闭商品显示动画吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME,                                    LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_CLOSE);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                } else {                    Tools.showConfirm(Systemset.this, "提示信息", "确定显示商品显示动画吗?",                            new SweetAlertDialog.OnSweetClickListener() {                        @Override                        public void onClick(SweetAlertDialog sweetAlertDialog) {                            Tools.setToken(getApplicationContext(), LocalAction.ACTION_SYSTEM_SET._ANIMATIONNAME,                                    LocalAction.ACTION_SYSTEM_SET._ANIMATIONKEY,                                    LocalValues.VALUES_SYSTEM_SET._SYSTEM_OPEN);                            init();                            sweetAlertDialog.dismiss();                            Tools.showSuccess(Systemset.this, "提示信息", "操作成功");                        }                    });                }            }        });    }}
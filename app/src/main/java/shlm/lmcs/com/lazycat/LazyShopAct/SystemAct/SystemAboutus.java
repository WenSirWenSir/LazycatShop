package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.annotation.SuppressLint;import android.os.Bundle;import android.widget.LinearLayout;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.TextUnt;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.R;/** * 展示APP的版本信息 和关于我们 */public class SystemAboutus extends LazyCatAct {    private LinearLayout ll_icoBody;    @Override    protected void onCreate(Bundle savedInstanceState) {        setStatusBar("#efefef");        setContentView(R.layout.activity_systemaboutus);        ll_icoBody = findViewById(R.id.activity_systemaboutus_icoBody);        init();        super.onCreate(savedInstanceState);    }    /**     * 初始化信息     */    @SuppressLint({"ResourceType", "NewApi"})    private void init() {        /**         * 设置导航的文字         */        TextUnt.with(this, R.id.assembly_act_headTitle).setText("版本信息");        /**         * 设置背景的边框         */        ll_icoBody.setBackground(Tools.CreateDrawable(1, getResources().getString(R.color                .ThemeColor), getResources().getString(R.color.ThemeColor), 10));        /**         * 设置字体         */        TextUnt.with(this, R.id.activity_systemaboutus_icoTitle).setFontFile                (getApplicationContext(), "canLogo");        /**         * 设置退出按钮         */        setBackListener(R.id.assembly_act_headBackImg);    }}
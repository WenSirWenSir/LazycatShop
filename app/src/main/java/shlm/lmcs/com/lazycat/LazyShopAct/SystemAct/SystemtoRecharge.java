package shlm.lmcs.com.lazycat.LazyShopAct.SystemAct;import android.os.Bundle;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.LazyCatAct;import shlm.lmcs.com.lazycat.R;/** * 商户充值界面 */public class SystemtoRecharge extends LazyCatAct {    @Override    protected void onCreate(Bundle savedInstanceState) {        setContentView(R.layout.activity_systemtorecharge);        init();        super.onCreate(savedInstanceState);    }    /**     * 界面初始化     */    public void init(){}}
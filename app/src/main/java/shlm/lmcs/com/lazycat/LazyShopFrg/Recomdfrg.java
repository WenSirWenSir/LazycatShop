package shlm.lmcs.com.lazycat.LazyShopFrg;import android.annotation.SuppressLint;import android.graphics.Color;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import cn.pedant.SweetAlert.SweetAlertDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.LazyCatFragment;import shlm.lmcs.com.lazycat.LazyShopVip.SystemVip;import shlm.lmcs.com.lazycat.R;/** * 显示全部订单的界面 */public class Recomdfrg extends LazyCatFragment {    private View item;/*界面布局*/    @SuppressLint("NewApi")    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle            savedInstanceState) {        item = inflater.from(getContext()).inflate(R.layout.fragment_recomdfrg, null);        init();        return item;    }    @SuppressLint("NewApi")    private void init() {        /**         * 判断是否登录程序         */         SystemVip systemVip = new SystemVip(getContext());         systemVip.Start(new SystemVip.OnVipcheck() {             @Override             public void onIsvip() {             }             @Override             public void onIsnovip() {             }             @SuppressLint("ResourceType")             @Override             public void onIsnologin() {                 /*没有登录*/                 SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(),                         SweetAlertDialog.ERROR_TYPE);                 sweetAlertDialog.setContentTextSize(13);                 sweetAlertDialog.setContentText("您还没有登录云仓库");                 sweetAlertDialog.setTitle("没有登录");                 sweetAlertDialog.setConfirmButtonTextColor(Color.WHITE);                 sweetAlertDialog.setConfirmButtonBackgroundColor(Color.parseColor                         (getResources().getString(R.color.ThemeColor)));                 sweetAlertDialog.setConfirmButton("关闭", new SweetAlertDialog                         .OnSweetClickListener() {                     @Override                     public void onClick(SweetAlertDialog sweetAlertDialog) {                         sweetAlertDialog.dismiss();                     }                 });                 sweetAlertDialog.show();             }             @Override             public void onIslogin() {             }         });    }}
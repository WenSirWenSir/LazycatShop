package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.annotation.SuppressLint;
import android.os.Parcel;

import java.io.Serializable;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;

/**
 * 构造WebView控件的信息
 */
@SuppressLint("ParcelCreator")
public class WEB_VALUES_ACT implements Serializable {
    private String _title;

    public WaitDialog.RefreshDialog get_dialog() {
        return _dialog;
    }

    public void set_dialog(WaitDialog.RefreshDialog _dialog) {
        this._dialog = _dialog;
    }

    private WaitDialog.RefreshDialog _dialog;

    /**
     * 默认构造一个URL其他为默认
     *
     * @param Url
     */
    private String _url = "";

    public WEB_VALUES_ACT(String Url) {
        this._url = Url;
    }

    protected WEB_VALUES_ACT(Parcel in) {

    }

    /**
     * 获取构造的URL地址信息
     *
     * @return
     */
    public String get_url() {
        return _url;
    }


    /**
     * 获取设置的Title
     *
     * @return
     */
    public String get_title() {
        return this._title;
    }


    /**
     * 设置Title
     */
    public void set_title(String title) {
        this._title = title;
    }

}
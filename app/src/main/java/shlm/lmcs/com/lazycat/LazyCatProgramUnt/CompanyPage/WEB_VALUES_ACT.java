package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.annotation.SuppressLint;
import android.os.Parcel;

import java.io.Serializable;

/**
 * ����WebView�ؼ�����Ϣ
 */
@SuppressLint("ParcelCreator")
public class WEB_VALUES_ACT implements Serializable {
    private String _title;
    /**
     * Ĭ�Ϲ���һ��URL����ΪĬ��
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
     * ��ȡ�����URL��ַ��Ϣ
     *
     * @return
     */
    public String get_url() {
        return _url;
    }


    /**
     * ��ȡ���õ�Title
     *
     * @return
     */
    public String get_title() {
        return this._title;
    }


    /**
     * ����Title
     */
    public void set_title(String title) {
        this._title = title;
    }

}
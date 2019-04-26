package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;

/**
 * ��ҳ���õķ���
 */

public class WebMonitor {
    private Context mContext;
    private Activity mAc;

    public WebMonitor(Context _Context, Activity ac) {
        this.mAc = ac;
        this.mContext = _Context;
    }

    /**
     * ��ҳ�ӿ�  �����û�һ�����þ�  ����ֱ��ʹ�õ���ȯʹ��
     *
     * @param _pagetype ���þ�����
     * @param price     ���õĽ��
     * @param message   ��ע��Ϣ
     */
    @JavascriptInterface
    public void Getpage(int price, int _pagetype, String message) {

    }

    /**
     * ��ȡ��Ʒ��Ϣ
     *
     * @param barcode ��Ʒ����
     * @param id      ��ƷΨһID
     * @param number  ��������
     * @param message ��ע��Ϣ
     */
    @JavascriptInterface
    public void Getshop(int barcode, int id, int number, String message) {

    }


    /**
     * ִ��MYSQL������Ϣ
     *
     * @param sql MySQL���
     * @param url �ύ��ַ
     */
    @JavascriptInterface
    public void ExeclMysql(String sql, String url) {

    }

    /**
     * ��ȡ����
     *
     * @param Integral ��������
     * @param message  ��ע��Ϣ
     */
    @JavascriptInterface

    public void GetIntegral(int Integral, String message) {

    }


    /**
     * ��ȡ�ֻ��������ַ
     */
    @JavascriptInterface

    public void GetphoneAddr() {

    }

    /**
     * ��ʾһ����ʾ��Ϣ
     *
     * @param message ��Ϣ
     * @param in      0�������ʾ 1������ʾ
     */
    @JavascriptInterface
    public void ShowToast(String message, int in) {
        if (in == 0) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ������¼
     */
    @JavascriptInterface

    public void StartLogin() {

    }

    /**
     * ���һ�����ؼۻ�������������Ʒ
     *
     * @param barcode ��Ʒ������
     * @param id      ��Ʒ��ΨһID
     * @param title   ��Ʒ�ı���
     */
    public void StartShop(int barcode, int id, String title) {

    }


    /**
     * �ر�WebView�Ĵ���
     */
    @JavascriptInterface
    public void Finish() {
        if (mAc != null) {
            mAc.finish();
        } else {
            Log.e(Config.DEBUG, "WebMonitor.java[+]�����ActivityΪ�յ�ֵ ���ܹر�");
        }
    }

    /**
     * ���û��Ĺ��ﳵ
     */
    public void StartShopCart() {

    }

    /**
     * ����ϵͳ����־��Ϣ
     *
     * @param logMessage ��־����Ϣ
     * @param logTitle   ��־�ı���
     * @param code       ��־�ı��
     */
    public void SystemLog(String logMessage, String logTitle, int code) {

    }

    /**
     * �ص��û���������
     */
    public void Goindex() {

    }


    /**
     * ��ת���û���������
     */
    public void GoinUsercontent() {

    }

    /**
     * ��ת���û������ͽ���
     */
    public void GoinUserFreight() {

    }

    /**
     * �����ֻ���֤��
     *
     * @param phone   �ֻ�����
     * @param sendUrl ���͵ĵ�ַ
     */
    public void SendMessage(String phone, String sendUrl) {

    }


    /**
     * �����֤���Ƿ���ȷ ��ȷ�Ļ� Ҫ����JS�˵Ĵ���  xml�ύ��ʽ
     *
     * @param phone   �ֻ�����
     * @param code    ������֤��
     * @param sendUrl ���ĵ�ַ
     */
    public void CheckSendCode(String phone, String code, String sendUrl) {

    }


    /**
     * ����΢�ſռ���Ƽ�����Ϣ ������óɹ��Ļ�  ��Ҫ�ص���JS�ṩ�Ľӿڵ�ַ
     * @param title ����ı���
     * @param content ���������
     * @param photo �����ͼƬ
     * @param callBackurl �ɹ��Ļص���ַ
     */
    public void ShareWX(String title, String content, String photo,String callBackurl) {

    }

    /**
     * ����QQ�ؼ����Ƽ�����Ϣ  ������óɹ��Ļ� ��Ҫ�ص���JS�ṩ�Ľӿڵĵ�ַ
     * @param title ����ı���
     * @param content ���������
     * @param photo �����ͼƬ
     * @param callBackurl �ɹ��Ļص���ַ
     */
    public void ShareQQ(String title,String content,String photo,String callBackurl){

    }

}

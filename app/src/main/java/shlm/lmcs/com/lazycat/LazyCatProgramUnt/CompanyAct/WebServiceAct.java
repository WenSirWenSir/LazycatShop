package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass.WebMonitor;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;


/**
 * ����������ʾUrl��View����
 * <p>
 */
public class WebServiceAct extends LazyCatAct {
    private WebView _WebView;
    private WaitDialog.RefreshDialog refreshDialog;

    @SuppressLint({"LongLogTag", "SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayout item = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        item.setLayoutParams(params);//���ò���
        item.setBackgroundColor(Color.parseColor("#ffffff"));
        //����WebView����
        _WebView = new WebView(item.getContext());
        _WebView.addJavascriptInterface(new WebMonitor(getApplicationContext(),this), "webmonitor");
        item.addView(_WebView);
        setContentView(item);
        //��ȡ����������Ϣ
        WEB_VALUES_ACT web_values_act = (WEB_VALUES_ACT) getIntent().getSerializableExtra
                (WINDOW_PAGE.RESULT_WEBVIEW);
        if (web_values_act != null) {
            Log.i(Config.DEBUG, "WebServiceAct.java[+]��ȡ���Ĺ����URL:" + web_values_act.get_url());
            _WebView.loadUrl(web_values_act.get_url());
            WebSettings webSettings = _WebView.getSettings();//��ȡ����
            webSettings.setJavaScriptEnabled(true);//��JAVA��֧��
            _WebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String request) {
                    view.loadUrl(request);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    //refreshDialog.dismiss();

                }
            });
        } else {
            finish();
            Log.e(Config.DEBUG, "WebServiceAct.java[+] ��ȡWEB_VALUES_ACTΪnull");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if (_WebView != null) {
            _WebView.clearCache(true);//��ջ���
            _WebView.clearHistory();//�����ʷ
            _WebView = null;
            System.gc();
        }
        super.onDestroy();
    }
}

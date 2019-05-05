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
 * 用来管理显示Url的View界面
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
        item.setLayoutParams(params);//设置布局
        item.setBackgroundColor(Color.parseColor("#ffffff"));
        //增加WebView布局
        _WebView = new WebView(item.getContext());
        _WebView.addJavascriptInterface(new WebMonitor(getApplicationContext(), this),
                "webmonitor");
        item.addView(_WebView);
        setContentView(item);
        //获取构造数据信息
        WEB_VALUES_ACT web_values_act = (WEB_VALUES_ACT) getIntent().getSerializableExtra
                (WINDOW_PAGE.RESULT_WEBVIEW);
        if (web_values_act != null) {
            Log.i(Config.DEBUG, "WebServiceAct.java[+]获取到的构造的URL:" + web_values_act.get_url());
            _WebView.loadUrl(web_values_act.get_url());
            WebSettings webSettings = _WebView.getSettings();//获取配置
            webSettings.setJavaScriptEnabled(true);//对JAVA的支持
            _WebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String request) {
                    view.loadUrl(request);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    /*开始加载信息*/
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    //refreshDialog.dismiss();

                }
            });
        } else {
            finish();
            Log.e(Config.DEBUG, "WebServiceAct.java[+] 获取WEB_VALUES_ACT为null");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if (_WebView != null) {
            _WebView.clearCache(true);//清空缓存
            _WebView.clearHistory();//清空历史
            _WebView = null;
            System.gc();
        }
        super.onDestroy();
    }
}

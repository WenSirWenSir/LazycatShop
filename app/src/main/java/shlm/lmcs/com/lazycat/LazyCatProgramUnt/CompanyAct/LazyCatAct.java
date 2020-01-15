package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


/**
 * 押粥官方Activity
 */
public class LazyCatAct extends Activity {
    private String MSG = "LazyCatAct.java[+]";
    private Boolean isBackTwo = false;//退出再按一次
    private Boolean isBackOk = false;

    /**
     * 打开一个窗口
     *
     * @param Bclass Class的名称
     */
    protected void LazyCatActStartActivity(Class<?> Bclass, Boolean ColseF) {
        Intent i = new Intent();
        i.setClass(this, Bclass);
        this.startActivity(i);
        if (ColseF) {
            this.finish();
        }
        //界面动画
    }

    /**
     *
     */
    protected void LeftCompanyActStartActivityForResult(Class<?> mClass, Boolean ColseF, int
            requestCode) {
        Intent i = new Intent();
        i.setClass(this, mClass);
        startActivityForResult(i, requestCode);
        if (ColseF) {
            this.finish();
        }
    }

    /**
     * 打开一个窗口 并且传入值
     */
    protected void LazyCatStartActivityWithBundler(Class<?> Bclass, Boolean ColoseF, String...
            values) {
        Intent intent = new Intent();
        for (int i = 0; i < values.length; i += 2) {
            intent.putExtra(values[i], values[i + 1]);
        }
        intent.setClass(this, Bclass);
        this.startActivity(intent);
        if (ColoseF) {
            this.finish();
        } else {

        }
    }

    /**
     * 启动一个WebView
     *
     * @param ColoseF         是否关闭父窗口
     * @param _web_values_act 构造参数
     */
    @SuppressLint("NewApi")
    protected void LeftCompanyActStartWebView(Boolean ColoseF, WEB_VALUES_ACT _web_values_act) {
        Intent intent = new Intent();
        intent.setClass(this, WebServiceAct.class);
        intent.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, _web_values_act);
        startActivity(intent);
    }

    /**
     * 获取传入窗口的值
     *
     * @param key
     * @return
     */
    protected String getBundlerValue(String key) {
        try {
            Intent intent = getIntent();
            if (intent == null) {
                Log.e(MSG, "界面传值的Intent为空");
                return "";
            } else {
                Log.i(MSG, "要获取的KEY为:" + key);
                if (intent.getStringExtra(key.trim()) != null) {
                    return intent.getStringExtra(key);
                } else {
                    Log.e(MSG, "没有获取到数据信息");
                    return "";
                }

            }
        } catch (Exception e) {
            Log.e(MSG, e.getMessage());
            return "";
        }
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar(String tColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.O版本及以上
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.parseColor(tColor));
        }
    }

    /**
     * 设置透明状态栏
     */
    protected void setTransparentBar() {
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置导航栏透明
     */
    protected void setHideNav() {
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置是否要进行退出的监听  默认不监听
     *
     * @param i false 不监听 true 监听
     */
    protected void setBackStatic(Boolean i) {
        if (i) {
            isBackTwo = true;
            isBackOk = false;
        } else {
            isBackTwo = false;
        }

    }

    @Override
    public void onBackPressed() {
        /*判断是否需要退出提示*/
        if (isBackTwo) {
            if (isBackOk) {
                super.onBackPressed();
            } else {
                isBackOk = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
            }

        } else {
            super.onBackPressed();
        }
    }

    public void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View
                    .SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(options);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void _getPermission(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, 1);

    }

    /**
     * 重写使app字体大小不跟随系统
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}

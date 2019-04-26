package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


/**
 * Ѻ��ٷ�Activity
 */
public class LazyCatAct extends Activity {
    private Boolean isBackTwo = false;//�˳��ٰ�һ��
    private Boolean isBackOk = false;

    /**
     * ��һ������
     *
     * @param Bclass Class������
     */
    protected void LazyCatActStartActivity(Class<?> Bclass, Boolean ColseF) {
        Intent i = new Intent();
        i.setClass(this, Bclass);
        this.startActivity(i);
        if (ColseF) {
            this.finish();
        }
        //���涯��
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
     * ��һ������ ���Ҵ���ֵ
     */
    protected void LeftCompanyActStartActivityWithBundler(Class<?> Bclass, Boolean ColoseF,
                                                          String... values) {
        Intent intent = new Intent();
        for (int i = 0; i < values.length; i += 2) {
            intent.putExtra(values[i], values[i + 1]);
        }
        intent.setClass(this, Bclass);
        this.startActivity(intent);
    }

    /**
     * ����һ��WebView
     *
     * @param ColoseF         �Ƿ�رո�����
     * @param _web_values_act �������
     */
    @SuppressLint("NewApi")
    protected void LeftCompanyActStartWebView(Boolean ColoseF, WEB_VALUES_ACT _web_values_act) {
        Intent intent = new Intent();
        intent.setClass(this, WebServiceAct.class);
        intent.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, _web_values_act);
        startActivity(intent);
    }

    /**
     * ��ȡ���봰�ڵ�ֵ
     *
     * @param key
     * @return
     */
    protected String getBundlerValue(String key) {
        try {
            Intent intent = this.getIntent();
            if (intent.getStringExtra(key) != null) {
                return intent.getStringExtra(key);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * ����״̬��
     */
    protected void setStatusBar(String tColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.O�汾������
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.parseColor(tColor));
        }
    }

    /**
     * ����͸��״̬��
     */
    protected void setTransparentBar() {
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���õ�����͸��
     */
    protected void setHideNav() {
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �����Ƿ�Ҫ�����˳��ļ���  Ĭ�ϲ�����
     *
     * @param i false ������ true ����
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
        /*�ж��Ƿ���Ҫ�˳���ʾ*/
        if (isBackTwo) {
            if (isBackOk) {
                super.onBackPressed();
            } else {
                isBackOk = true;
                Toast.makeText(this, "�ٰ�һ���˳�", Toast.LENGTH_LONG).show();
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

}

package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.WebServiceAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


/**
 * 自定义的Fragment可以 用来实现一些经常用的方法
 */
public class LazyCatFragment extends Fragment {

    @SuppressLint("NewApi")
    protected void LazyCatFragmetStartAct(Class<?> into) {

        Intent i = new Intent();
        i.setClass(getContext(), into);
        startActivity(i);
    }


    @SuppressLint("NewApi")
    protected void LazyCatFragmentStartActivityWithBundler(Class<?> _Toin, String... kvs) {
        Intent intent = new Intent();
        for (int i = 0; i < kvs.length; i += 2) {
            intent.putExtra(kvs[i], kvs[i + 1]);
        }
        intent.setClass(getContext(), _Toin);
        startActivity(intent);

    }

    @SuppressLint("NewApi")
    protected void LazyCatFragmentStartWevact(WEB_VALUES_ACT values) {
        Intent i = new Intent();
        i.setClass(getContext(), WebServiceAct.class);
        i.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, values);
        startActivity(i);
    }

    protected void setStatusBar(String tColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.O版本及以上
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.parseColor(tColor));
        }
    }

    /**
     * 设置透明状态栏
     */
    protected void setTransparentBar() {
        try {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

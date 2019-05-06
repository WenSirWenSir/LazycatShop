package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyAct.WebServiceAct;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WEB_VALUES_ACT;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.WINDOW_PAGE;


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
    protected void LazyCatFragmentStartWevact(WEB_VALUES_ACT values) {
        Intent i = new Intent();
        i.setClass(getContext(), WebServiceAct.class);
        i.putExtra(WINDOW_PAGE.RESULT_WEBVIEW, values);
        startActivity(i);
    }
}

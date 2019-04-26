package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;


/**
 * 自定义的Fragment可以 用来实现一些经常用的方法
 */
public class LazyCatFragment extends Fragment{

    @SuppressLint("NewApi")
    protected void LazyCatFragmetStartAct(Class<?> into){
        Intent i = new Intent();
        i.setClass(getContext(),into);
        startActivity(i);
    }
}

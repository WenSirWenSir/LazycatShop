package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyClass;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;


/**
 * �Զ����Fragment���� ����ʵ��һЩ�����õķ���
 */
public class LazyCatFragment extends Fragment{

    @SuppressLint("NewApi")
    protected void LazyCatFragmetStartAct(Class<?> into){
        Intent i = new Intent();
        i.setClass(getContext(),into);
        startActivity(i);
    }
}

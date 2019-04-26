package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface;


import android.graphics.Bitmap;

import java.io.InputStream;
import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XMLUserAddr;

/**
 * ��Ҷ������õĽӿ�
 */
public interface ProgramInterface {

    /**
     * Ĭ�ϵĴ���ʽ
     * @param data
     * @param code
     */
    public void onSucess(String data, int code);
    public void onFaile(String data, int code);

    /**
     * ���ŷ��ͽӿ�
     */
    public interface SMSInterface{
        void onSendOk();
        void onSendError();
    }


    /**
     * XML��ȡ�ӿ�
     */
    public interface XMLDomServiceInterface{
        void onSucess(InputStream is);
        void onFain();
        void onNotService();
        void onJson(String origin);//Ҫ��JSON����
    }

    /**
     * ��ȡ�û������ݴ���֮��Ļص��¼�
     */
    public interface XMLforUserAllAddr{
        void onDone(ArrayList<XMLUserAddr> list);
        void onFain();

        /**
         * Token����
         */
        void onDated();
        void onJson(String origin);

    }

    interface doGetImg{
        void onSucess(Bitmap bitmap);
        void onFain();
    }
    interface onMemorySize{
        void onGet(int max,float total,float free);
    }

    /**
     * ���߳����������
     */
    interface onThreaddone{
        void onData(String tag);
    }

}



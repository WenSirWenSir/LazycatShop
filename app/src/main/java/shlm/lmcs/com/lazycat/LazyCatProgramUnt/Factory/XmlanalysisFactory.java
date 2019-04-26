package shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory;


import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;

/**
 * XML��������
 */
@SuppressLint("LongLogTag")
public class XmlanalysisFactory {
    private InputStream is;

    public XmlanalysisFactory(String _Xmldata) {
        is = new ByteArrayInputStream(_Xmldata.getBytes());/*����ת��ΪInputStream*/
    }

    /**
     * ��ʼ����
     */
    public void Startanalysis(XmlanalysisInterface _XmlanalysisInterface) {
        if (_XmlanalysisInterface != null) {
            XmlPullParser pullParser = Xml.newPullParser();//�õ�������
            if (is != null) {
                try {
                    pullParser.setInput(is, "UTF-8");
                    /*��ȡ�¼�����*/
                    int eventType = pullParser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                _XmlanalysisInterface.onStartDocument();/*�ĵ���ʼ�Ĵ����¼�*/
                                break;
                            case XmlPullParser.START_TAG:
                                _XmlanalysisInterface.onStartTag(pullParser.getName(),
                                        pullParser, 0);
                                break;
                            case XmlPullParser.END_TAG:
                                _XmlanalysisInterface.onEndTag();/*������ǩ*/
                                break;
                        }
                        eventType = pullParser.next();
                        if (eventType == XmlPullParser.END_DOCUMENT) {
                            _XmlanalysisInterface.onEndDocument();
                            return;
                        }
                    }
                } catch (Exception e) {
                    Log.e(Config.DEBUG, "XmlanalysisFactory.java[+]����ʧ��" + e.getMessage());
                    _XmlanalysisInterface.onFaile();
                    e.printStackTrace();
                }
            }

        } else {
            Log.e(Config.DEBUG, "XmlanalysisFactory.java[+]û�м����¼�");
        }
    }

    /**
     * ���������ӿ� ���ڽ����ɹ��ͽ���ʧ��
     */

    public interface XmlanalysisInterface {
        void onFaile();

        void onStartDocument();//�ĵ���ʼ

        void onStartTag(String tag, XmlPullParser pullParser, Integer id);//��ʼ�����ڵ�

        void onEndTag();//�����ڵ�

        void onEndDocument();//�ĵ������ص�����
    }
}

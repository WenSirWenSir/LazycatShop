package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;


import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XML_PAGE;

/**
 * ����һ��XML��������
 */
public class XmlBuilder {
    private StringBuilder xml = new StringBuilder();
    private String mParcel;

    /**
     * @param parcel       ������Χ�ڵ�
     */
    public XmlBuilder(String parcel) {
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        xml.append("<"+parcel + ">");//�������ͷ���ڵ�
        this.mParcel = parcel;
    }


    /**
     * ��ȡXML�ļ���
     * @param list �ļ���������Ϣ
     * @return
     */
    public StringBuilder getXmlString(ArrayList<XML_PAGE> list) {
        for(int i = 0;i < list.size();i++){
            xml.append(list.get(i).getXml());
        }
        //���β���ڵ�
        xml.append("</"+mParcel+">");
        return xml;

    }


}

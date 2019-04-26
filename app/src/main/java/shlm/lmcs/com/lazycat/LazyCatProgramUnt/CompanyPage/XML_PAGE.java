package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage;


import android.text.TextUtils;

/**
 * XML�ڵ���Ϣ
 * <p>
 * ���ص���Ϣ������
 * <fater sign = "values">
 * <name>���ȫ����</name>
 * </fater>
 */
public class XML_PAGE {
    private StringBuilder xml = new StringBuilder();
    private String _fater_nodex;

    /**
     * ����XML�����ļ���
     *
     * @param fater_node  ���ļ�ͷ ���Ϊ�� ��᷵��һ����ڵ�
     * @param sign        ���ļ��ı�־
     * @param sign_values ���ļ��ı�־��ֵ
     */
    public XML_PAGE(String fater_node, String sign, String sign_values) {
        this._fater_nodex = fater_node;//�ӽڵ���Ϣ


        /**
         * �ж��Ƿ񴴽���ڵ�
         *
         */
        if(TextUtils.isEmpty(fater_node)){
            //Ϊ��ʲô������

        }
        else{
            //��Ϊ��
            if (TextUtils.isEmpty(sign)) {
                xml.append("<" + fater_node + ">");//��ʼ�ڵ�
            } else {
                xml.append("<" + fater_node + " " + sign + "=\"" + sign_values + "\">");//��ʼ�ڵ�
            }

        }
    }

    /**
     * ����һ����ڵ�
     */
    public XML_PAGE addGrandsonNode(String node, String value) {
        xml.append("<" + node + ">" + value + "</" + node + ">");//һ����ڵ���Ϣ
        return this;
    }


    /**
     * ��ȡһ��������XML�ӽڵ���Ϣ
     *
     * @return
     */
    public StringBuilder getXml() {
        if(TextUtils.isEmpty(this._fater_nodex)){
            //ʲô������
        }
        else{
            xml.append("</" + this._fater_nodex + ">");//�պ�xmlʵ��
        }
        return xml;//��ȡ��ڵ���Ϣ
    }
}

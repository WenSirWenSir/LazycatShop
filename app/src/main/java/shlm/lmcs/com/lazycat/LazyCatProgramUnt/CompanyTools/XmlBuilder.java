package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;


import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XML_PAGE;

/**
 * 生成一段XML的生成器
 */
public class XmlBuilder {
    private StringBuilder xml = new StringBuilder();
    private String mParcel;
    static XmlInstance xmlInstance;/*构造器*/


    public static XmlInstance getXmlinstanceBuilder() {
        xmlInstance = new XmlInstance();
        return xmlInstance;
    }

    /**
     * @param parcel 最大的外围节点
     */
    public XmlBuilder(String parcel) {
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        xml.append("<" + parcel + ">");//添加最大的头部节点
        this.mParcel = parcel;
    }


    /**
     * 获取XML文件体
     *
     * @param list 文件的配置信息
     * @return
     */
    public StringBuilder getXmlString(ArrayList<XML_PAGE> list) {
        for (int i = 0; i < list.size(); i++) {
            xml.append(list.get(i).getXml());
        }
        //添加尾部节点
        xml.append("</" + mParcel + ">");
        return xml;

    }


    /**
     * xml构造器
     */
    public static class XmlInstance {
        StringBuilder xmlDom;/*Dom层的数据*/

        /**
         * 初始化dom层
         */
        public void initDom() {
            xmlDom = new StringBuilder();
            xmlDom.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");/*添加头部信息*/
            xmlDom.append("<xml>");

        }

        /**
         * 结束Xml的树
         */
        public void overDom() {
            xmlDom.append("</xml>");
        }


        /**
         * 添加一个Xml的树
         */
        public void setXmlTree(String key_tree, String tree) {
            xmlDom.append("<" + key_tree + ">" + tree + "</" + key_tree + ">");
        }


        /**
         * 获取Xml的树的信息
         *
         * @return
         */
        public String getXmlTree() {
            return xmlDom.toString();
        }


        /**
         * 清空Dom层
         */
        public void ClearDom() {
            xmlDom.setLength(0);
        }

    }

}

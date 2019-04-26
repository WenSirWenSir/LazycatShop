package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.USER_KEY_PAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XMLUserAddr;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyPage.XML_PAGE;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Config;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;

/**
 * ��˾��ȡ�û�����Ϣ���ϵĲ���API
 */
public class Usertools {
    /**
     * ��ȡ�û������е�������Ϣ
     */
    public static void getUservalues(Context mContext, final ProgramInterface programInterface) {
        String token = Tools.gettoKen(mContext, USER_KEY_PAGE.KEY_TOKEN);
        String phone = Tools.gettoKen(mContext, USER_KEY_PAGE.KEY_USERPHONE);
        Net.doGet(mContext, Config.HTTP_ADDR.getUser_init(), new Net.onVisitInterServiceListener() {
            @Override
            public void onSucess(String tOrgin) {
                if (programInterface != null) {
                    programInterface.onSucess(tOrgin, 0);
                }

            }

            @Override
            public void onNotConnect() {
                if (programInterface != null) {
                    programInterface.onFaile("", 0);
                }

            }

            @Override
            public void onFail(String tOrgin) {
                if (programInterface != null) {
                    programInterface.onFaile("", 0);
                }

            }
        }, Config.HttpMethodUserAction.KEY_ACTION, "" + Config.HttpMethodUserAction.GET_USERVAL,
                Config.HttpMethodUserAction.KEY_USER, Tools.getStringMD5(phone), Config
                        .HttpMethodUserAction.KEY_TOKEN, token);
    }

    /**
     * ��ȡ�û���Ĭ�ϵ�ַ
     */

    public static void getUserdefaultaddr() {

    }


    /**
     * ��ȡ�û������е�ַ
     */
    public static void getUserAllAddr(Context mContext, final ProgramInterface.XMLforUserAllAddr
            xmLforUserAllAddr) {
        String phone_md5 = Tools.getStringMD5(Tools.gettoKen(mContext, USER_KEY_PAGE
                .KEY_USERPHONE));
        String token = Tools.gettoKen(mContext, USER_KEY_PAGE.KEY_TOKEN);
        Net.doGetXml(mContext, Config.HTTP_ADDR.getallAddr(), new ProgramInterface
                .XMLDomServiceInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSucess(InputStream is) {
                //�ȱ���IS��������Ϣ
                if (is != null) {
                    ArrayList<XMLUserAddr> list = null;
                    try {
                        list = Tools.UserAddrXMLDomeService(is);
                    } catch (Exception e) {
                    }
                    if (list != null) {
                        if (xmLforUserAllAddr != null) {
                            xmLforUserAllAddr.onDone(list);
                        } else {
                            Log.e(Config.DEBUG, "Usertools.java[+]XML�����ص�ΪNULL");
                        }

                    } else {
                        if (xmLforUserAllAddr != null) {
                            Log.e(Config.DEBUG, "Usertools.java[+]XML�����ص�ΪNULL");
                            xmLforUserAllAddr.onFain();//����ʧ��
                        } else {
                            Log.e(Config.DEBUG, "Usertools.java[+]XML�����ص�ΪNULL");
                        }

                    }
                } else {
                }
            }

            @Override
            public void onFain() {
                if (xmLforUserAllAddr != null) {
                    xmLforUserAllAddr.onFain();
                }

            }

            @Override
            public void onNotService() {
                if (xmLforUserAllAddr != null) {
                    xmLforUserAllAddr.onFain();
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onJson(String origin) {
                Log.e(Config.DEBUG,"Usertools.java[+]��JSON���ݻص���");
                if (xmLforUserAllAddr != null) {
                    xmLforUserAllAddr.onJson(origin);
                }

            }
        }, Config.HttpMethodUserAction.KEY_ACTION, Config.HttpMethodUserAction.GET_ALLADDR,
                Config.HttpMethodUserAction.KEY_USER, phone_md5, Config.HttpMethodUserAction
                        .KEY_TOKEN, token);

    }


    /**
     * �û�����һ���ռ���ַ
     *
     * @param mContext    ������
     * @param name        �û���
     * @param phone       �绰
     * @param addr        ��ַ
     * @param physics_add �����ַ
     * @param addr_in     ��������
     * @param sex         �Ա�
     * @param year        ����
     * @param _Default    �Ƿ�Ĭ��
     * @param phone_md5   phone_md5 ��֤
     * @param token       token ��֤
     */
    public static void insertUseraddr(Context mContext, String name, String phone, String addr,
                                      String physics_add, String addr_in, int sex, String year,
                                      String _Default, String phone_md5, String token, final
                                      ProgramInterface programInterface) {
        physics_add = URLEncoder.encode(physics_add);//����
        XmlBuilder xmlBuilder = new XmlBuilder("body");
        XML_PAGE xml_page = new XML_PAGE("", "", "");
        xml_page.addGrandsonNode(Config.HttpMethodUserAction.KEY_USER, phone_md5).addGrandsonNode
                (Config.HttpMethodUserAction.KEY_TOKEN, token).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_NAME, name).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_TEL, phone).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_ADDR, addr).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_IN, addr_in).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_PHYSICS, physics_add).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ADDR_DEFAULT, _Default).addGrandsonNode(Config
                .HttpMethodUserAction.KEY_ACTION, Config.HttpMethodUserAction.INSERT_USER_ADDR)
                .addGrandsonNode(Config.HttpMethodUserAction.KEY_ADDR_USER_SEX, sex + "")
                .addGrandsonNode(Config.HttpMethodUserAction.KEY_ADDR_USER_YEAR, year);
        ArrayList<XML_PAGE> list = new ArrayList<XML_PAGE>();
        list.add(xml_page);
        Net.doPostXml(mContext, xmlBuilder.getXmlString(list), Config.HTTP_ADDR.getUser_init(),
                new ProgramInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSucess(String data, int code) {
                Log.i(Config.DEBUG, "xml���ݷ���" + data.toString());
                if (TextUtils.isEmpty(data.toString())) {
                    if (programInterface != null) {
                        programInterface.onFaile("", 0);
                    }
                } else {
                    if (programInterface != null) {
                        programInterface.onSucess(data.toString(), 0);
                    }
                }
            }

            @Override
            public void onFaile(String data, int code) {
                if (programInterface != null) {
                    programInterface.onFaile("", 0);
                }

            }
        });


    }
}

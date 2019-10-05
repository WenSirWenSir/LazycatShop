package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;


import android.annotation.SuppressLint;
import android.content.Context;
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
 * 公司获取用户的信息资料的操作API
 */
public class Usertools {

    /**
     * 获取用户的默认地址
     */

    public static void getUserdefaultaddr() {

    }


    /**
     * 获取用户的所有地址
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
                //先保存IS的数据信息
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
                            Log.e(Config.DEBUG, "Usertools.java[+]XML解析回调为NULL");
                        }

                    } else {
                        if (xmLforUserAllAddr != null) {
                            Log.e(Config.DEBUG, "Usertools.java[+]XML解析回调为NULL");
                            xmLforUserAllAddr.onFain();//解析失败
                        } else {
                            Log.e(Config.DEBUG, "Usertools.java[+]XML解析回调为NULL");
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
                Log.e(Config.DEBUG, "Usertools.java[+]在JSON数据回调中");
                if (xmLforUserAllAddr != null) {
                    xmLforUserAllAddr.onJson(origin);
                }

            }
        }, Config.HttpMethodUserAction.KEY_ACTION, Config.HttpMethodUserAction.GET_ALLADDR,
                Config.HttpMethodUserAction.KEY_USER, phone_md5, Config.HttpMethodUserAction
                        .KEY_TOKEN, token);

    }


    /**
     * 用户插入一条收件地址
     *
     * @param mContext    上下文
     * @param name        用户名
     * @param phone       电话
     * @param addr        地址
     * @param physics_add 物理地址
     * @param addr_in     所属区域
     * @param sex         性别
     * @param year        年龄
     * @param _Default    是否默认
     * @param phone_md5   phone_md5 验证
     * @param token       token 验证
     */
    public static void insertUseraddr(Context mContext, String name, String phone, String addr,
                                      String physics_add, String addr_in, int sex, String year,
                                      String _Default, String phone_md5, String token, final
                                      ProgramInterface programInterface) {
        physics_add = URLEncoder.encode(physics_add);//编码
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
       /* Net.doPostXml(mContext, xmlBuilder.getXmlString(list), Config.HTTP_ADDR.getUser_init(),
                new ProgramInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSucess(String data, int code) {
                Log.i(Config.DEBUG, "xml数据返回" + data.toString());
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
        });*/


    }
}

package shlm.lmcs.com.lazycat.LazyShopTools;


import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;

/**
 * 本地工具类 只针对于本程序使用 不能构成模块化
 */
public class LocalProgramTools {
    private static String MSG = "LocalProgramTools.java[+]";
    private static UserToolsInstance userToolsInstance = new UserToolsInstance();

    public static UserToolsInstance getUserToolsInstance() {
        if (userToolsInstance != null) {
            return userToolsInstance;
        } else {
            return null;
        }
    }

    public static class UserToolsInstance {
        private String Token;/*用户的TOKEN*/
        private String Shopname;/*店铺的名称*/
        private String Shopaddr;/*店铺的位置*/
        private String Shoptel;/*店铺的电话*/
        private String ShopusePeople;/*店铺的负责人*/
        private String Shoplong;/*店铺的经度*/
        private String Shoplat;/*店铺的维度*/
        private String Account;/*用户的账户*/
        private SetReadUserpageListener _setReadUserpageListener;

        public String getShopname() {
            return Shopname;
        }

        public void setShopname(String shopname) {
            Shopname = shopname;
        }

        public String getShopaddr() {
            return Shopaddr;
        }

        public void setShopaddr(String shopaddr) {
            Shopaddr = shopaddr;
        }

        public String getShoptel() {
            return Shoptel;
        }

        public void setShoptel(String shoptel) {
            Shoptel = shoptel;
        }

        public String getShopusePeople() {
            return ShopusePeople;
        }

        public void setShopusePeople(String shopusePeople) {
            ShopusePeople = shopusePeople;
        }

        public String getShoplong() {
            return Shoplong;
        }

        public void setShoplong(String shoplong) {
            Shoplong = shoplong;
        }

        public String getShoplat() {
            return Shoplat;
        }

        public void setShoplat(String shoplat) {
            Shoplat = shoplat;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String account) {
            Account = account;
        }


        public String getToken() {
            return Token;
        }

        public void setToken(String token) {
            Token = token;
        }

        /**
         * 保存到本地的XML用户的文件信息
         */
        public Boolean SaveingUserPageXml() {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE.xml");
                /*判断是否存在 存在就先删除之后再保存*/
                if (file.exists() && file.isFile()) {
                    if (file.delete()) {
                        Log.i(MSG, "原始文件清除成功");
                    } else {
                        Log.e(MSG, "删除原始数据失败");
                        return false;
                    }
                }
                FileOutputStream fos = new FileOutputStream(file);
                /*获取序列化工具*/
                XmlSerializer serializer = Xml.newSerializer();
                serializer.setOutput(fos, "utf-8");
                serializer.startDocument("utf-8", true);
                serializer.startTag(null, "body");
                serializer.startTag(null, "userPage");
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_TOKEN);
                serializer.text(Token.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_TOKEN);
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_ACCOUNT);
                serializer.text(Account.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_ACCOUNT);
                /*店铺的管理者*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE);
                serializer.text(ShopusePeople.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE);
                /*店铺的电话*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPTEL);
                serializer.text(Shoptel.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPTEL);
                /*店铺的名称*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPNAME);
                serializer.text(Shopname.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPNAME);
                /*店铺的位置*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPADDR);
                serializer.text(Shopaddr.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPADDR);
                /*店铺的经度*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPLONG);
                serializer.text(Shoplong.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPLONG);
                /*店铺的维度*/
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPLAT);
                serializer.text(Shoplat.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_SHOPLAT);

                serializer.endTag(null, "userPage");
                serializer.endTag(null, "body");
                serializer.endDocument();
                fos.close();
                Log.i(MSG, "保存用户的XML文件成功");
                return true;
            } catch (Exception e) {
                Log.e(MSG, "保存用户的XML文件失败,失败原因:" + e.getMessage());
                return false;
            }

        }


        public void StartPullerUserpageXml(SetReadUserpageListener setReadUserpageListener) {
            this._setReadUserpageListener = setReadUserpageListener;
            if (this._setReadUserpageListener != null) {
                try {
                    File path = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE"
                            + ".xml");
                    FileInputStream fis = new FileInputStream(path);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, "utf-8");
                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String tagName = parser.getName();/*获取当前节点的名称*/
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                /*账户的Token*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_TOKEN)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN,
                                            parser.nextText().trim());
                                }
                                /*用户的账户*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_ACCOUNT)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_ACCOUNT,
                                            parser.nextText().trim());
                                }
                                /*商品的地址*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_SHOPADDR)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPADDR,
                                            parser.nextText().trim());
                                }
                                /*商品的负责人*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE
                                            .ACTION_LOCALUSERPAGE_SHOPUSEPEOPLE, parser.nextText
                                            ().trim());
                                }
                                /*商品的名称*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_SHOPNAME)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPNAME,
                                            parser.nextText().trim());
                                }
                                /*店铺的经度*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_SHOPLONG)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLONG,
                                            parser.nextText().trim());
                                }
                                /*店铺的维度*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_SHOPLAT)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_SHOPLAT,
                                            parser.nextText().trim());
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    Log.e(MSG, "解析用户的XML文件为空,错误内容为:" + e.getMessage());
                    if (_setReadUserpageListener != null) {
                        _setReadUserpageListener.onError();
                    }
                }
            } else {
                Log.e(MSG, "解析用户的XML文件的回调为空!");
            }

        }

        public interface SetReadUserpageListener {
            void onRead(String tag, String values);

            void onError();
        }

        /**
         * 清空缓存
         */
        public void ClearLocalCach() {
            File file = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE.xml");
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    Log.i(MSG, "原始文件清除成功");
                } else {
                    Log.e(MSG, "删除原始数据失败");
                }
            }
        }
    }

}

package shlm.lmcs.com.lazycat.LazyShopTools;


import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;

/**
 * 本地工具类 只针对于本程序使用 不能构成模块化
 */
public class LocalProgramTools {
    private static String MSG = "LocalProgramTools.java[+]";
    private static UserToolsInstance userToolsInstance = new UserToolsInstance();
    private static ProgramServiceTools serviceToolsInstatnce = new ProgramServiceTools();

    public static UserToolsInstance getUserToolsInstance() {
        if (userToolsInstance != null) {
            return userToolsInstance;
        } else {
            return null;
        }
    }

    public static ProgramServiceTools getServiceToolsInstatnce() {
        if (serviceToolsInstatnce != null) {
            return serviceToolsInstatnce;
        } else {
            return null;
        }
    }

    public static class UserToolsInstance {
        private String Token = "";/*用户的TOKEN*/
        private String Shopname = "";/*店铺的名称*/
        private String Shopaddr = "";/*店铺的位置*/
        private String Shoptel = "";/*店铺的电话*/
        private String ShopusePeople = "";/*店铺的负责人*/
        private String Shoplong = "";/*店铺的经度*/
        private String Shoplat = "";/*店铺的维度*/
        private String Account = "";/*用户的账户*/

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
        public Boolean SaveingUserPageXml(Context _Context) {
            try {
                if (!Tools.isPermission(_Context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    /*读取的权限*/
                    Toast.makeText(_Context, "没有获取读取的权限", Toast.LENGTH_SHORT).show();
                }
                if (!Tools.isPermission(_Context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    /*写入的权限*/
                    Toast.makeText(_Context, "没有获取写入的权限", Toast.LENGTH_SHORT).show();
                }
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


        /**
         * 判断是否登录
         *
         * @return
         */
        public Boolean isLogin() {
            Boolean isLogin = false;
            try {
                File path = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE" +
                        "" + ".xml");
                FileInputStream fis = new FileInputStream(path);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(fis, "utf-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();/*获取当前节点的名称*/
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                    .ACTION_LOCALUSERPAGE_ACCOUNT)) {
                                if (!TextUtils.isEmpty(parser.nextText().trim())) {
                                    isLogin = true;
                                }
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                isLogin = false;
                Log.e(MSG, "检查用户是否登录失败,错误内容为:" + e.getMessage());
            }
            return isLogin;

        }

        public String GetUserpageOnAction(String ActionTag) {
            String _Values = "";
            try {
                File path = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE" +
                        "" + ".xml");
                FileInputStream fis = new FileInputStream(path);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(fis, "utf-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();/*获取当前节点的名称*/
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            Log.i(MSG,"tagName:" + tagName);
                            if (tagName.equals(ActionTag.trim())) {
                                _Values = parser.nextText().trim();
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.e(MSG, "解析用户的XML文件为空,错误内容为:" + e.getMessage());
            }
            return _Values;
        }


        /**
         * 清空缓存
         */
        public boolean ClearLocalCach() {
            File file = new File(Environment.getExternalStorageDirectory(), "CK_USERPAGE.xml");
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    Log.i(MSG, "原始文件清除成功");
                    return true;
                } else {
                    Log.e(MSG, "删除原始数据失败");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    /**
     * 用来保存获取到的服务器的地址
     */
    public static class ProgramServiceTools {
        private String _Service;

        public String get_Service() {
            return _Service;
        }

        public void set_Service(String _Service) {
            this._Service = _Service;
        }

        /*保存服务器缓存*/
        public void SaveService(Context _Context) {
            try {
                if (Tools.isPermission(_Context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    /*读取的权限*/
                } else {

                }
                if (Tools.isPermission(_Context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    /*写入的权限*/
                } else {

                }
                File file = new File(Environment.getExternalStorageDirectory(), "CK_SERVICE.xml");
                /*判断是否存在 存在就先删除之后再保存*/
                if (file.exists() && file.isFile()) {
                    if (file.delete()) {
                        Log.i(MSG, "原始文件清除成功");
                    } else {
                        Log.e(MSG, "删除原始数据失败");
                    }
                }
                FileOutputStream fos = new FileOutputStream(file);
                /*获取序列化工具*/
                XmlSerializer serializer = Xml.newSerializer();
                serializer.setOutput(fos, "utf-8");
                serializer.startDocument("utf-8", true);
                serializer.startTag(null, "body");
                serializer.startTag(null, "ServicePage");
                serializer.startTag(null, "ServiceAddr");
                serializer.text(_Service.trim());
                serializer.endTag(null, "ServiceAddr");
                serializer.endTag(null, "ServicePage");
                serializer.endTag(null, "body");
                serializer.endDocument();
                fos.close();
                Log.i(MSG, "保存本地服务器的数据成功");
            } catch (Exception e) {
                Log.e(MSG, "保存本地服务器的数据失败,失败原因:" + e.getMessage());
            }

        }

        public String GetService() {
            String _ServiceAddr = "";
            try {
                File path = new File(Environment.getExternalStorageDirectory(), "CK_SERVICE" + ""
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
                            if (tagName.equals("ServiceAddr")) {
                                _ServiceAddr = parser.nextText().trim();
                            }
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.e(MSG, "解析用户的XML文件为空,错误内容为:" + e.getMessage());
            }
            return _ServiceAddr;

        }
    }

    public static class TimeTools {
        public static int getTimeforsecond() {
            return 0;
        }

        /**
         * 把现在的现在的时间转换成秒数
         *
         * @return
         */
        public static String getNowtimeForsecond() {
            return "";
        }
    }

}
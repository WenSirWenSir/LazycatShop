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
        private String NiackName;/*用户的昵称*/
        private String Blance;/*用户余额*/
        private String Status;/*用户的账户状态*/
        private String Vipstatus;/*用户的VIP状态*/
        private String Token;/*用户的TOKEN*/

        private SetReadUserpageListener _setReadUserpageListener;

        public String getNiackName() {
            return NiackName;
        }

        public void setNiackName(String niackName) {
            NiackName = niackName;
        }

        public String getBlance() {
            return Blance;
        }

        public void setBlance(String blance) {
            Blance = blance;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getVipstatus() {
            return Vipstatus;
        }

        public void setVipstatus(String vipstatus) {
            Vipstatus = vipstatus;
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
                Log.i(MSG, "文件路径地址:" + file.getPath());
                FileOutputStream fos = new FileOutputStream(file);
                /*获取序列化工具*/
                XmlSerializer serializer = Xml.newSerializer();
                serializer.setOutput(fos, "utf-8");
                serializer.startDocument("utf-8", true);
                serializer.startTag(null, "body");
                serializer.startTag(null, "userPage");
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_NIACKNAME);
                serializer.text(NiackName.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_NIACKNAME);
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_BLANCE);
                serializer.text(Blance.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_BLANCE);
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_STATUS);
                serializer.text(Status.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_STATUS);
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_VIPSTATUS);
                serializer.text(Vipstatus.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_VIPSTATUS);
                serializer.startTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_TOKEN);
                serializer.text(Token.trim());
                serializer.endTag(null, LocalAction.ACTION_LOCALUSERPAGE
                        .ACTION_LOCALUSERPAGE_TOKEN);
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
                                /*商户的别称*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_NIACKNAME)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_NIACKNAME,
                                            parser.nextText().trim());
                                }
                                /*账户的状态*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_STATUS)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_STATUS,
                                            parser.nextText().trim());
                                }
                                /*账户的余额*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_BLANCE)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_BLANCE,
                                            parser.nextText().trim());
                                }
                                /*账户的VIP状态*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_VIPSTATUS)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_VIPSTATUS,
                                            parser.nextText().trim());
                                }
                                /*账户的Token*/
                                if (tagName.equals(LocalAction.ACTION_LOCALUSERPAGE
                                        .ACTION_LOCALUSERPAGE_TOKEN)) {
                                    _setReadUserpageListener.onRead(LocalAction
                                            .ACTION_LOCALUSERPAGE.ACTION_LOCALUSERPAGE_TOKEN,
                                            parser.nextText().trim());
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    Log.e(MSG, "解析用户的XML文件为空,错误内容为:" + e.getMessage());
                    if(_setReadUserpageListener != null){
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
    }

}

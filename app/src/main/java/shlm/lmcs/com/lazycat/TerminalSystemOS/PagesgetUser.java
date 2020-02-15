package shlm.lmcs.com.lazycat.TerminalSystemOS;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 界面整理获取用户界面的数据信息 */public class PagesgetUser {    private static String MSG = "PagesgetUser.java[+]";    /**     * 获取信息     */    public static void _get(final Context _context, final OnGetlistener _onGetlistener) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        final Pages pages = new Pages();        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.overDom();        Net.doPostXml(http_addrs.HTTP_ADDR_TERMINALSYSTEM_OS_PAGESGETUSER, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "界面整理获取信息回传：" + data.trim());                XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                    @Override                    public void onFaile() {                    }                    @Override                    public void onStartDocument(String tag) {                    }                    @Override                    public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                        try {                            if (tag.equals(LocalAction.ACTION_USER.ACTION_NAME)) {                                pages._name = pullParser.nextText().trim();                            }                            if (tag.equals(LocalAction.ACTION_USER.ACTION_VIP)) {                                if (pullParser.nextText().trim().equals(LocalValues.VALUES_USERCENTER.IS_VIP)) {                                    pages._isJoin = true;                                } else {                                    pages._isJoin = false;                                }                            }                        } catch (Exception e) {                            Log.i(MSG, "解析界面整理获取信息回传错误:" + e.getMessage());                        }                    }                    @Override                    public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                    }                    @Override                    public void onEndDocument() {                        _onGetlistener.onGet(pages);                    }                });            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree());    }    public interface OnGetlistener {        void onGet(Pages _pages);    }    /**     * 回传表格     */    public static class Pages {        public String _integral;/*积分*/        public String _doneTime;/*到期时间*/        public String _joinRecord;/*加盟记录*/        public String _xlocal;/*X坐标*/        public String _ylocal;/*Y坐标*/        public String _name;/*店铺的名称*/        public Boolean _isJoin;/*是否加盟商*/    }}
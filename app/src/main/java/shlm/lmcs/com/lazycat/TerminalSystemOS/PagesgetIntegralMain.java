package shlm.lmcs.com.lazycat.TerminalSystemOS;import android.content.Context;import android.util.Log;import org.xmlpull.v1.XmlPullParser;import java.util.ArrayList;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.XmlanalysisFactory;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalAction;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 获取展示积分界面的展示数据信息 */public class PagesgetIntegralMain {    private static String MSG = "PagesgetIntegralMain.java[+]";    private static String RETURN_CODE = "";    private static IntegralMainpages _IntegralMainpages = new IntegralMainpages();    /**     * 开始获取 接口回调     *     * @param _context     * @param _onIntegralMainget     */    public static void _get(final Context _context, final OnIntegralMainget _onIntegralMainget) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getXmlinstanceBuilder(true);        xmlInstance.overDom();        Net.doPostXml(http_addrs.HTTP_ADDR_TERMINALSYSTEM_OS_PAGEINTEGRALMAIN, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                Log.i(MSG, "获取展示积分界面数据信息:" + data.trim());                _refreshDialog.dismiss();                if (data.equals(LocalValues.NET_ERROR)) {                    _onIntegralMainget._OnError();                } else {                    XmlanalysisFactory xmlanalysisFactory = new XmlanalysisFactory(data.trim());                    xmlanalysisFactory.Startanalysis(new XmlanalysisFactory.XmlanalysisInterface() {                        @Override                        public void onFaile() {                        }                        @Override                        public void onStartDocument(String tag) {                        }                        @Override                        public void onStartTag(String tag, XmlPullParser pullParser, Integer id) {                            try {                                /*获取返回状态码*/                                if (tag.equals(LocalAction.ACTION_RETURNCODE)) {                                    RETURN_CODE = pullParser.nextText().trim();                                }                                /*获取积分*/                                if (tag.equals(LocalAction.ACTION_SHOPVALUES.ACTION_SHOPVALUES_INTEGRAL)) {                                    _IntegralMainpages._integral = pullParser.nextText().trim();                                }                                /*获取冻结的积分*/                                if (tag.equals(LocalAction.ACTION_INTEGRAL.INTEGRAL_FROZEN)) {                                    _IntegralMainpages._FrozenIntegral = pullParser.nextText().trim();                                }                            } catch (Exception e) {                                Log.e(MSG, "解析积分界面中出现出错误" + e.getMessage());                            }                        }                        @Override                        public void onEndTag(String tag, XmlPullParser pullParser, Integer id) {                        }                        @Override                        public void onEndDocument() {                            if (RETURN_CODE.equals(LocalValues.NET_OK)) {                                _onIntegralMainget._OnGet(_IntegralMainpages);                            } else if (RETURN_CODE.equals(LocalValues.VALUES_USERCENTER.LOGIN_ERROR)) {                                _onIntegralMainget._OnnoLogin();                            }                        }                    });                }            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree().trim());    }    public interface OnIntegralMainget {        void _OnGet(IntegralMainpages _IntegralMainpages);/*获取成功*/        void _OnError();/*系统处理错误*/        void _OnnoLogin();/*用户没有登录*/    }    /**     * 回传表格     */    public static class IntegralMainpages {        public String _integral;/*积分*/        public String _FrozenIntegral;/*冻结积分*/    }}
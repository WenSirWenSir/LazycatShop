package shlm.lmcs.com.lazycat.TerminalSystemOS;import android.content.Context;import android.util.Log;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools.XmlBuilder;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;/** * 执行兑换操作 */public class doExchange {    private static String MSG = "doExchange.java[+]";    private doExchange() {    }    /**     * 开始提交     *     * @param _context       上下文对象     * @param _exchanageCode 兑换代码     * @param _onDodone      监听回调     */    public static void toDo(final Context _context, String _exchanageCode, onDodone _onDodone) {        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getJavaXmlinstanceBuilder(true);        xmlInstance.initJaveDom();        xmlInstance.overJavaDom();        Net.doPostXml(http_addrs.HTTP_ADDR_SUPER_PULLBUSINESS, new ProgramInterface() {            @Override            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {                _refreshDialog.dismiss();                Log.i(MSG, "兑换检查获取数据为:" + data.trim());            }            @Override            public WaitDialog.RefreshDialog onStartLoad() {                return Tools.getShowwait(_context);            }            @Override            public void onFaile(String data, int code) {            }        }, xmlInstance.getXmlTree());    }    public interface onDodone {        void _onOk();        void _onError();    }}
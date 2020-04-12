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
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Factory.WaitDialog;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Interface.ProgramInterface;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Net;
import shlm.lmcs.com.lazycat.LazyCatProgramUnt.Tools;
import shlm.lmcs.com.lazycat.LazyShopValues.LocalValues;

/**
 * 公司获取用户的信息资料的操作API
 */
public class Usertools {
    private String MSG = "Usertools.java[+]";
    private UserpageValues userpageValues = new UserpageValues();

    public void getUserpage(Context _context, ongetUserpage _OngetUserpage) {
        LocalValues.HTTP_ADDRS http_addrs = new LocalValues.HTTP_ADDRS(_context);
        XmlBuilder.XmlInstance xmlInstance = XmlBuilder.getJavaXmlinstanceBuilder(true);
        xmlInstance.overJavaDom();
        Net.doPostXml(http_addrs.HTTP_ADDR_GET_USERPAGES, new ProgramInterface() {
            @Override
            public void onSucess(String data, int code, WaitDialog.RefreshDialog _refreshDialog) {

            }

            @Override
            public WaitDialog.RefreshDialog onStartLoad() {
                return null;
            }

            @Override
            public void onFaile(String data, int code) {

            }
        }, xmlInstance.getXmlTree());
        userpageValues._addr = "";

    }


    /**
     * 接口回调
     */
    public interface ongetUserpage {
        void onGetUserpage(int Return_code, UserpageValues _UserpageValues);

    }


    /**
     * 用户的表格值
     */
    public static class UserpageValues {
        String _name;/*店铺名称*/
        String _addr;/*地址*/
        String _tel;/*电话*/
        String _people;/*用户名*/
        String _vipDone;/*过期时间*/
        String _canUseintegral;/*可以使用积分*/
        String _forzenintegral;/*冻结的积分*/
        String _canUseandFrozenIntegral;/*可以使用和冻结的积分集合*/
    }
}

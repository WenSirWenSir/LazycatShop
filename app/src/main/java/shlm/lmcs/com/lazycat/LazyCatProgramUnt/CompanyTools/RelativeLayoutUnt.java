package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;import android.app.Activity;import android.view.View;import android.widget.RelativeLayout;public class RelativeLayoutUnt {    /**     * 通过控件获取工具类     *     * @param _reLayout     * @return     */    public static handlerRelative with(RelativeLayout _reLayout) {        try {            handlerRelative _handlerRelative = new handlerRelative();            _handlerRelative.get(_reLayout);            return _handlerRelative;        } catch (Exception e) {            return null;        }    }    /**     * 通过窗口ID获取控件     *     * @param _activity     * @param _Id     * @return     */    public static handlerRelative with(Activity _activity, int _Id) {        try {            RelativeLayout _relativelayout = _activity.findViewById(_Id);            handlerRelative _handlerRelative = new handlerRelative();            _handlerRelative.get(_relativelayout);            return _handlerRelative;        } catch (Exception e) {            return null;        }    }    public static handlerRelative with(View _item, int _Id) {        try {            RelativeLayout _relativelayout = _item.findViewById(_Id);            handlerRelative _handlerRelative = new handlerRelative();            _handlerRelative.get(_relativelayout);            return _handlerRelative;        } catch (Exception e) {            return null;        }    }}
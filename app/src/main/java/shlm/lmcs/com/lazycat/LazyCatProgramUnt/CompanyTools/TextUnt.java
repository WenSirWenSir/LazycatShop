package shlm.lmcs.com.lazycat.LazyCatProgramUnt.CompanyTools;import android.app.Activity;import android.view.View;import android.widget.TextView;/** * 文字处理 */public class TextUnt {    /**     * 直接使用找到TextView     * @param tv     * @return     */    public static handlerText with(TextView tv) {        handlerText handlerText = new handlerText();        return handlerText.get(tv);    }    /**     * 通过Activity来整理字体     *     * @param _class Activity     * @param _id    ID名称     * @return     */    public static handlerText with(Activity _class, int _id) {        handlerText handlerText = new handlerText();        TextView tv = _class.findViewById(_id);        return handlerText.get(tv);    }    public static handlerText with(View item,int _id){        handlerText handlerText = new handlerText();        TextView tv = item.findViewById(_id);        return handlerText.get(tv);    }}
package shlm.lmcs.com.lazycat.LazyCatProgramUnt;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json���ݽ���
 */
public class JsonEndata {
    private String mJsonString;

    public JsonEndata(String json) {
        this.mJsonString = json;

    }

    /**
     * ��ȡJSON�е�һ��ָ����KEY��ֵ
     */
    public String getJsonKeyValue(String key) {
        String keyValue = "";
        try {
            JSONObject jsonObject = new JSONObject(this.mJsonString);
            keyValue = jsonObject.getString(key);
        } catch (JSONException e) {
            Log.e("capitalist", "json��������:" + e.getMessage());
            return "";
        }
        return keyValue;
    }
}
